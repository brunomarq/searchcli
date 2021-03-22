package com.github.brunomarq.searchcli.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.brunomarq.searchcli.domain.Entity;
import com.github.brunomarq.searchcli.domain.Organization;
import com.github.brunomarq.searchcli.domain.Ticket;
import com.github.brunomarq.searchcli.domain.User;
import com.github.brunomarq.searchcli.dto.OrganizationDTO;
import com.github.brunomarq.searchcli.dto.TicketDTO;
import com.github.brunomarq.searchcli.dto.UserDTO;

import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;

@Component
public class ResponseFormatter {

    /**
     * Formats the response for a list of fields in an entity.
     * 
     * @param fields list of field names.
     * @return String formatted response.
     */
    public String formatFieldResponse(List<String> fields) {
        StringBuilder responseBuilder = new StringBuilder();

        ArrayList<String[]> tableList = new ArrayList<>();
        for (String field : fields) {
            tableList.add(new String[] { field });
        }

        formatRecord(responseBuilder, tableList);

        return responseBuilder.toString();
    }

    /**
     * Formats the response for a list of users and its associations.
     * 
     * @param users list of users containing all information on how it associaties
     *              itself with the ther entities.
     * @return String formatted response.
     */
    public String formatUserResponse(List<UserDTO> users) {
        StringBuilder responseBuilder = new StringBuilder();
        long recordCounter = 0;

        for (UserDTO userDTO : users) {
            ArrayList<String[]> tableList = new ArrayList<>();
            User user = userDTO.getUser();

            tableList.add(new String[] { Entity.FIELD_ID, user.getId() });
            tableList.add(new String[] { Entity.FIELD_EXTERNAL_ID, user.getExternalId() });
            tableList.add(new String[] { Entity.FIELD_CREATED_AT, user.getCreatedAt() });
            tableList.add(new String[] { Entity.FIELD_URL, user.getUrl() });
            tableList.add(new String[] { Entity.FIELD_TAGS, user.getTags().toString() });
            tableList.add(new String[] { User.FIELD_NAME, user.getName() });
            tableList.add(new String[] { User.FIELD_ALIAS, user.getAlias() });
            tableList.add(new String[] { User.FIELD_ACTIVE, Boolean.toString(user.isActive()) });
            tableList.add(new String[] { User.FIELD_VERIFIED, Boolean.toString(user.isVerified()) });
            tableList.add(new String[] { User.FIELD_SHARED, Boolean.toString(user.isShared()) });
            tableList.add(new String[] { User.FIELD_LOCALE, user.getLocale() });
            tableList.add(new String[] { User.FIELD_TIMEZONE, user.getTimezone() });
            tableList.add(new String[] { User.FIELD_LAST_LOGIN_AT, user.getLastLoginAt() });
            tableList.add(new String[] { User.FIELD_EMAIL, user.getEmail() });
            tableList.add(new String[] { User.FIELD_PHONE, user.getPhone() });
            tableList.add(new String[] { User.FIELD_SIGNATURE, user.getSignature() });
            tableList.add(new String[] { User.FIELD_SUSPENDED, Boolean.toString(user.isSuspended()) });
            tableList.add(new String[] { User.FIELD_ROLE, user.getRole() });

            if (userDTO.getOrganization() != null) {
                tableList.add(new String[] { "organization_" + Organization.FIELD_NAME,
                        userDTO.getOrganization().getName() });
            }

            List<Ticket> tickets = userDTO.getTickets();
            if (tickets != null) {
                for (int i = 0; i < tickets.size(); i++) {
                    Ticket ticket = tickets.get(i);
                    tableList.add(new String[] { "ticket_" + i, ticket.getSubject() });
                }
            }
            formatRecord(responseBuilder, tableList);
            recordCounter++;
        }
        addCounter(responseBuilder, recordCounter);

        return responseBuilder.toString();

    }

    /**
     * Formats the response for a list of organizations and its associations.
     * 
     * @param organizations list of organizations containing all information
     *                      regarding the relationship with other entities.
     * @return String formatted response.
     */
    public String formatOrganizationResponse(List<OrganizationDTO> organizations) {
        StringBuilder responseBuilder = new StringBuilder();
        long recordCounter = 0;

        for (OrganizationDTO organizationDTO : organizations) {
            ArrayList<String[]> tableList = new ArrayList<>();
            Organization organization = organizationDTO.getOrganization();

            tableList.add(new String[] { Entity.FIELD_ID, organization.getId() });
            tableList.add(new String[] { Entity.FIELD_EXTERNAL_ID, organization.getExternalId() });
            tableList.add(new String[] { Entity.FIELD_CREATED_AT, organization.getCreatedAt() });
            tableList.add(new String[] { Entity.FIELD_URL, organization.getUrl() });
            tableList.add(new String[] { Entity.FIELD_TAGS, organization.getTags().toString() });
            tableList.add(new String[] { Organization.FIELD_NAME, organization.getName() });
            tableList.add(new String[] { Organization.FIELD_DOMAIN_NAMES, organization.getDomainNames().toString() });
            tableList.add(new String[] { Organization.FIELD_DETAILS, organization.getDetails() });
            tableList.add(new String[] { Organization.FIELD_SHARED_TICKETS,
                    Boolean.toString(organization.isSharedTickets()) });

            List<User> users = organizationDTO.getUsers();
            if (users != null) {
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    tableList.add(new String[] { "user_" + i, user.getName() });
                }
            }

            List<Ticket> tickets = organizationDTO.getTickets();
            if (tickets != null) {
                for (int i = 0; i < tickets.size(); i++) {
                    Ticket ticket = tickets.get(i);
                    tableList.add(new String[] { "ticket_" + i, ticket.getSubject() });
                }
            }

            formatRecord(responseBuilder, tableList);
            recordCounter++;
        }
        addCounter(responseBuilder, recordCounter);

        return responseBuilder.toString();
    }

    /**
     * Formats the response for a list of tickets and its associations.
     * 
     * @param tickets list of tickets containing all information regarding the
     *                relationship with other entities.
     * @return String formatted response.
     */
    public String formatTicketResponse(List<TicketDTO> tickets) {
        StringBuilder responseBuilder = new StringBuilder();
        long recordCounter = 0;

        for (TicketDTO ticketDTO : tickets) {
            ArrayList<String[]> tableList = new ArrayList<>();
            Ticket ticket = ticketDTO.getTicket();

            tableList.add(new String[] { Entity.FIELD_ID, ticket.getId() });
            tableList.add(new String[] { Entity.FIELD_EXTERNAL_ID, ticket.getExternalId() });
            tableList.add(new String[] { Entity.FIELD_CREATED_AT, ticket.getCreatedAt() });
            tableList.add(new String[] { Entity.FIELD_URL, ticket.getUrl() });
            tableList.add(new String[] { Entity.FIELD_TAGS, ticket.getTags().toString() });
            tableList.add(new String[] { Ticket.FIELD_TYPE, ticket.getType() });
            tableList.add(new String[] { Ticket.FIELD_SUBJECT, ticket.getSubject() });
            tableList.add(new String[] { Ticket.FIELD_DESCRIPTION, ticket.getDescription() });
            tableList.add(new String[] { Ticket.FIELD_PRIORITY, ticket.getPriority() });
            tableList.add(new String[] { Ticket.FIELD_STATUS, ticket.getStatus() });
            tableList.add(new String[] { Ticket.FIELD_HAS_INCIDENTS, Boolean.toString(ticket.isHasIncidents()) });
            tableList.add(new String[] { Ticket.FIELD_DUE_AT, ticket.getDueAt() });
            tableList.add(new String[] { Ticket.FIELD_VIA, ticket.getVia() });

            if (ticketDTO.getSubmitter() != null) {
                tableList.add(new String[] { "submitter_" + User.FIELD_NAME, ticketDTO.getSubmitter().getName() });
            }

            if (ticketDTO.getAssignee() != null) {
                tableList.add(new String[] { "assignee_" + User.FIELD_NAME, ticketDTO.getAssignee().getName() });
            }

            if (ticketDTO.getOrganization() != null) {
                tableList.add(new String[] { "organization_" + Organization.FIELD_NAME,
                        ticketDTO.getOrganization().getName() });
            }

            formatRecord(responseBuilder, tableList);
            recordCounter++;
        }
        addCounter(responseBuilder, recordCounter);

        return responseBuilder.toString();
    }

    /**
     * Adds the total number of records to the response.
     * 
     * @param responseBuilder StringBuilder to concatenate the message to.
     * @param recordCounter   Counter to be added.
     */
    private void addCounter(StringBuilder responseBuilder, long recordCounter) {
        String totalMessage = this.formatInfo("Total number of records: " + recordCounter);
        responseBuilder.append(totalMessage);
    }

    /**
     * @param responseBuilder
     * @param tableList
     */
    private void formatRecord(StringBuilder responseBuilder, ArrayList<String[]> tableList) {
        String[][] data = new String[tableList.size()][];
        TableModel model = new ArrayTableModel(tableList.toArray(data));
        TableBuilder tableBuilder = new TableBuilder(model).addFullBorder(BorderStyle.air)
                .addOutlineBorder(BorderStyle.fancy_light);

        String record = AnsiOutput.toString(AnsiColor.GREEN, tableBuilder.build().render(-1), AnsiColor.DEFAULT);
        responseBuilder.append(record).append('\n');
    }

    /**
     * Formats an error message.
     * 
     * @param message
     * @return String
     */
    public String formatError(String message) {
        return AnsiOutput.toString(AnsiColor.RED, message, AnsiColor.DEFAULT);
    }

    /**
     * Format a info message.
     * 
     * @param message
     * @return String
     */
    public String formatInfo(String message) {
        return AnsiOutput.toString(AnsiColor.BRIGHT_CYAN, message, AnsiColor.DEFAULT);
    }

}
