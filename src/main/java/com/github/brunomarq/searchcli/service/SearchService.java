package com.github.brunomarq.searchcli.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.brunomarq.searchcli.domain.Organization;
import com.github.brunomarq.searchcli.domain.Ticket;
import com.github.brunomarq.searchcli.domain.User;
import com.github.brunomarq.searchcli.dto.OrganizationDTO;
import com.github.brunomarq.searchcli.dto.TicketDTO;
import com.github.brunomarq.searchcli.dto.UserDTO;
import com.github.brunomarq.searchcli.exception.InvalidFieldNameException;
import com.github.brunomarq.searchcli.repository.SearchRepository;
import com.github.brunomarq.searchcli.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    static final Logger log = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private SearchRepository<Organization> organizationRepository;

    @Autowired
    private SearchRepository<Ticket> ticketRepository;

    @Autowired
    private SearchRepository<User> userRepository;

    /**
     * Invoke the preparation of the inverted index searches.
     * 
     * @param directoryPath path of the directory containing the json files.
     * @throws IOException an exception is raised if the files cannot be found or
     *                     loaded.
     */
    public void loadDatabase(String directoryPath) throws IOException {
        String path = "";
        if (directoryPath != null && !directoryPath.isEmpty()) {
            path = directoryPath + File.separator;
        }

        organizationRepository.loadDatabases(path + Constants.ORGANIZATIONS_FILENAME, Organization.class);
        ticketRepository.loadDatabases(path + Constants.TICKETS_FILENAME, Ticket.class);
        userRepository.loadDatabases(path + Constants.USERS_FILENAME, User.class);
    }

    /**
     * Fetches the users according to the parameter provided and assemble a data
     * transfer object containing all the associated information from all the
     * entities involved.
     * 
     * @param field name of the field to be looked at.
     * @param value value to be searched for in the field.
     * @return List<UserDTO> list of data transfer objects containing the results
     *         and relations to other entities.
     * @throws InvalidFieldNameException in case an invalid field name is provided.
     */
    public List<UserDTO> searchUsers(String field, String value) throws InvalidFieldNameException {
        List<UserDTO> userDTOs = new ArrayList<>();

        log.info("Searching users with {} equals to '{}'", field, value);
        Set<User> users = userRepository.findByFieldValue(field, value);

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUser(user);

            Organization organization = organizationRepository.findById(user.getOrganizationId());
            if (organization != null) {
                log.debug("Fetching organization for user {}", user.getName());
                userDTO.setOrganization(organization);
            } else {
                log.debug("Organization for user {} not found", user.getName());
            }

            log.debug("Fetching tickets in which the user {} is a submitter", user.getName());
            Set<Ticket> tickets = ticketRepository.findByFieldValue(Ticket.FIELD_SUBMITTER_ID, user.getId());
            List<Ticket> ticketList = new ArrayList<>();
            ticketList.addAll(tickets);
            userDTO.setTickets(ticketList);

            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    /**
     * Fetches the organizations according to the parameter provided and assemble a
     * data transfer object containing all the associated information from all the
     * entities involved.
     * 
     * @param field name of the field to be looked at.
     * @param value value to be searched for in the field.
     * @return List<UserDTO> list of data transfer objects containing the results
     *         and relations to other entities.
     * @throws InvalidFieldNameException in case an invalid field name is provided.
     */
    public List<OrganizationDTO> searchOrganizations(String field, String value) throws InvalidFieldNameException {
        List<OrganizationDTO> organizationDTOs = new ArrayList<>();

        log.info("Searching organizations with {} equals to '{}'", field, value);
        Set<Organization> organizations = organizationRepository.findByFieldValue(field, value);

        for (Organization organization : organizations) {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setOrganization(organization);

            log.debug("Fetching users beloging to organization {}", organization.getName());
            Set<User> users = userRepository.findByFieldValue(User.FIELD_ORGANIZATION_ID, organization.getId());
            List<User> userList = new ArrayList<>();
            userList.addAll(users);
            organizationDTO.setUsers(userList);

            log.debug("Fetching tickets related to organization {}", organization.getName());
            Set<Ticket> tickets = ticketRepository.findByFieldValue(Ticket.FIELD_ORGANIZATION_ID, organization.getId());
            List<Ticket> ticketList = new ArrayList<>();
            ticketList.addAll(tickets);
            organizationDTO.setTickets(ticketList);

            organizationDTOs.add(organizationDTO);
        }
        return organizationDTOs;
    }

    /**
     * Fetches the tickets according to the parameter provided and assemble a data
     * transfer object containing all the associated information from all the
     * entities involved.
     * 
     * @param field name of the field to be looked at.
     * @param value value to be searched for in the field.
     * @return List<UserDTO> list of data transfer objects containing the results
     *         and relations to other entities.
     * @throws InvalidFieldNameException in case an invalid field name is provided.
     */
    public List<TicketDTO> searchTickets(String field, String value) throws InvalidFieldNameException {
        List<TicketDTO> ticketDTOs = new ArrayList<>();

        log.info("Searching tickets with {} equals to '{}'", field, value);
        Set<Ticket> tickets = ticketRepository.findByFieldValue(field, value);

        for (Ticket ticket : tickets) {
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setTicket(ticket);

            Organization organization = organizationRepository.findById(ticket.getOrganizationId());
            if (organization != null) {
                log.debug("Fetching organization for ticket {}", ticket.getSubject());
                ticketDTO.setOrganization(organization);
            } else {
                log.debug("Organization for ticket {} not found", ticket.getSubject());
            }

            User submitter = userRepository.findById(ticket.getSubmitterId());
            if (submitter != null) {
                log.debug("Fetching submitter for ticket {}", ticket.getSubject());
                ticketDTO.setSubmitter(submitter);
            } else {
                log.debug("Submitter for ticket {} not found", ticket.getSubject());
            }

            User assignee = userRepository.findById(ticket.getAssigneeId());
            if (assignee != null) {
                log.debug("Fetching assignee for ticket {}", ticket.getSubject());
                ticketDTO.setAssignee(assignee);
            } else {
                log.debug("Assignee for ticket {} not found", ticket.getSubject());
            }

            ticketDTOs.add(ticketDTO);
        }
        return ticketDTOs;
    }

}
