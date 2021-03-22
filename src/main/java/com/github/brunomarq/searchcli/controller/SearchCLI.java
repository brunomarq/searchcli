package com.github.brunomarq.searchcli.controller;

import java.io.IOException;
import java.util.List;

import com.github.brunomarq.searchcli.domain.Organization;
import com.github.brunomarq.searchcli.domain.Ticket;
import com.github.brunomarq.searchcli.domain.User;
import com.github.brunomarq.searchcli.dto.OrganizationDTO;
import com.github.brunomarq.searchcli.dto.TicketDTO;
import com.github.brunomarq.searchcli.dto.UserDTO;
import com.github.brunomarq.searchcli.exception.InvalidFieldNameException;
import com.github.brunomarq.searchcli.service.SearchService;
import com.github.brunomarq.searchcli.utils.Constants;
import com.github.brunomarq.searchcli.utils.InputValidator;
import com.github.brunomarq.searchcli.utils.ResponseFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SearchCLI {

    static final Logger log = LoggerFactory.getLogger(SearchCLI.class);

    private boolean databaseReady;

    @Autowired
    private ResponseFormatter responseFormatter;

    @Autowired
    private InputValidator inputValidator;

    @Autowired
    private SearchService searchService;

    /**
     * Shell command responsible for loading the json files and creating the
     * inverted index.
     * 
     * @param directoryPath if empty, looks for the json files in the resource
     *                      folder in the jar. Otherwise, looks for the files in the
     *                      directory specified.
     * @return String returns a formatted string indicating if the files and index
     *         were created successfully or not.
     */
    @ShellMethod("Load organization, ticket and user json files and prepare the inverted indexes.")
    public String loadDatabase(@ShellOption(defaultValue = "") String directoryPath) {
        log.info("Loading JSON files and creating inverted index...");
        this.databaseReady = false;
        try {
            searchService.loadDatabase(directoryPath);
        } catch (IOException e) {
            log.error(String.format("Error while loading files. Reason: %s", e.getMessage()));
            return responseFormatter.formatError(Constants.ERROR_LOADING_JSON_FILES);
        }
        this.databaseReady = true;
        log.info("Files loaded successfully.");

        return AnsiOutput.toString(AnsiColor.GREEN, Constants.DATABASE_READY, AnsiColor.DEFAULT);
    }

    /**
     * Shell command responsible for searching for tickets, users or organizations
     * that match the value for a specific field.
     * 
     * @param entity String representing the entity: organization, user or ticket.
     * @param field  String representing the json field to be used in the search.
     * @param value  String representing the full value to be searched on the field
     *               specified.
     * @return String returns a formatted string in the format of a table indicating
     *         all the records that match the search criteria.
     */
    @ShellMethod("Search for tickets, users or organisations.")
    public String search(String entity, String field, @ShellOption(defaultValue = "") String value) {
        log.info("Validating inputs...");
        if (!inputValidator.isEntityValid(entity)) {
            return responseFormatter.formatError(Constants.INVALID_ENTITY);
        } else if (!inputValidator.isFieldValid(entity, field)) {
            return responseFormatter.formatError(Constants.INVALID_FIELD);
        }

        log.info("Performing search...");
        String formattedResponse = "";
        try {
            switch (entity.trim().toLowerCase()) {
            case Organization.ENTITY_TYPE:
                List<OrganizationDTO> organizations = searchService.searchOrganizations(field, value);
                formattedResponse = responseFormatter.formatOrganizationResponse(organizations);
                break;
            case Ticket.ENTITY_TYPE:
                List<TicketDTO> tickets = searchService.searchTickets(field, value);
                formattedResponse = responseFormatter.formatTicketResponse(tickets);
                break;
            case User.ENTITY_TYPE:
                List<UserDTO> users = searchService.searchUsers(field, value);
                formattedResponse = responseFormatter.formatUserResponse(users);
                break;
            default:
                formattedResponse = responseFormatter.formatError(Constants.INVALID_ENTITY);
                break;
            }
        } catch (InvalidFieldNameException e1) {
            formattedResponse = responseFormatter.formatError(Constants.INVALID_FIELD);
        }
        formattedResponse += responseFormatter
                .formatInfo("\nSearch command: 'search " + entity + " " + field + " " + value + "'");

        log.info("Search completed.");
        return formattedResponse;
    }

    /**
     * Shell command that lists all the fields of an entity available to be used in
     * a search.
     * 
     * @param entity String representing the entity: organization, user or ticket.
     * @return String returns a formatter response in a table format containing all
     *         fields available.
     */
    @ShellMethod("Show available search field for tickets, users or organisations.")
    public String fields(String entity) {
        log.info("Validating inputs...");
        if (!inputValidator.isEntityValid(entity)) {
            return responseFormatter.formatError(Constants.INVALID_ENTITY);
        }

        log.info("Fetching field list...");
        String formattedResponse = null;
        switch (entity.trim().toLowerCase()) {
        case Organization.ENTITY_TYPE:
            formattedResponse = responseFormatter.formatFieldResponse(Organization.FIELDS);
            break;
        case Ticket.ENTITY_TYPE:
            formattedResponse = responseFormatter.formatFieldResponse(Ticket.FIELDS);
            break;
        case User.ENTITY_TYPE:
            formattedResponse = responseFormatter.formatFieldResponse(User.FIELDS);
            break;
        default:
            formattedResponse = responseFormatter.formatError(Constants.INVALID_ENTITY);
            break;
        }

        log.info("Done listing fields.");
        return formattedResponse;
    }

    /**
     * This method allows disabling the search and fields shell commands until the
     * database load is invoked and complete.
     * 
     * @return Availability Spring Shell uses this return to determine if the user
     *         can invoke the search and fields commands.
     */
    @ShellMethodAvailability({ "search", "fields" })
    public Availability availabilityCheck() {
        return this.databaseReady ? Availability.available()
                : Availability.unavailable("the database has not been loaded. Run `load_databases` first.");
    }

}