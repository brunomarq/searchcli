package com.github.brunomarq.searchcli.service;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchServiceTests {

    @Mock
    SearchRepository<Organization> organizationRepository;

    @Mock
    SearchRepository<Ticket> ticketRepository;

    @Mock
    SearchRepository<User> userRepository;

    @InjectMocks
    SearchService searchService;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadDatabaseWithInvalidDirectory() throws IOException {
        final String invalidDirectory = "invalid";
        // Mock behaviour of external dependencies
        doThrow(new IOException()).when(userRepository).loadDatabases(invalidDirectory + File.separator + "users.json",
                User.class);
        doThrow(new IOException()).when(ticketRepository)
                .loadDatabases(invalidDirectory + File.separator + "tickets.json", Ticket.class);
        doThrow(new IOException()).when(organizationRepository)
                .loadDatabases(invalidDirectory + File.separator + "organizations.json", Organization.class);

        // Execute code
        assertThrows(IOException.class, () -> searchService.loadDatabase(invalidDirectory));
    }

    @Test
    public void testLoadDatabase() throws IOException {
        final String directory = "valid";
        // Mock behaviour of external dependencies
        doNothing().when(userRepository).loadDatabases(directory + File.separator + "users.json", User.class);
        doNothing().when(ticketRepository).loadDatabases(directory + File.separator + "tickets.json", Ticket.class);
        doNothing().when(organizationRepository).loadDatabases(directory + File.separator + "organizations.json",
                Organization.class);

        // Execute code
        searchService.loadDatabase(directory);

        // Verify calls of external dependencies
        verify(userRepository, times(1)).loadDatabases(directory + File.separator + "users.json", User.class);
        verify(ticketRepository, times(1)).loadDatabases(directory + File.separator + "tickets.json", Ticket.class);
        verify(organizationRepository, times(1)).loadDatabases(directory + File.separator + "organizations.json",
                Organization.class);
    }

    @Test
    public void testSearchUsers() throws InvalidFieldNameException {
        User user = new User();
        user.setId("1");
        user.setOrganizationId(2l);
        Set<User> users = new HashSet<>();
        users.add(user);

        Organization org = new Organization();
        org.setId(Long.toString(user.getOrganizationId()));

        Ticket ticket = new Ticket();
        ticket.setId("3");
        ticket.setSubmitterId(Long.parseLong(user.getId()));
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        // Mock behaviour of external dependencies
        when(userRepository.findByFieldValue(anyString(), anyString())).thenReturn(users);
        when(organizationRepository.findById(anyLong())).thenReturn(org);
        when(ticketRepository.findByFieldValue(eq(Ticket.FIELD_SUBMITTER_ID), anyString())).thenReturn(tickets);

        // Execute code
        List<UserDTO> userDTOs = searchService.searchUsers(anyString(), anyString());

        // Validate results
        Assertions.assertThat(userDTOs.size()).isEqualTo(users.size());
        UserDTO userDTO = userDTOs.get(0);
        Assertions.assertThat(userDTO.getUser().getId()).isEqualTo(user.getId());

        Assertions.assertThat(userDTO.getOrganization().getId()).isEqualTo(org.getId());

        Assertions.assertThat(userDTO.getTickets().size()).isEqualTo(tickets.size());
        Ticket injectedTicket = userDTO.getTickets().get(0);
        Assertions.assertThat(injectedTicket.getId()).isEqualTo(ticket.getId());
    }

    @Test
    public void testSearchOrganizations() throws InvalidFieldNameException {
        Organization org = new Organization();
        org.setId("1");
        Set<Organization> orgs = new HashSet<>();
        orgs.add(org);

        User user = new User();
        user.setId("2");
        user.setOrganizationId(Long.parseLong(org.getId()));
        Set<User> users = new HashSet<>();
        users.add(user);

        Ticket ticket = new Ticket();
        ticket.setId("3");
        ticket.setOrganizationId(Long.parseLong(org.getId()));
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        // Mock behaviour of external dependencies
        when(organizationRepository.findByFieldValue(anyString(), anyString())).thenReturn(orgs);
        when(userRepository.findByFieldValue(eq(User.FIELD_ORGANIZATION_ID), anyString())).thenReturn(users);
        when(ticketRepository.findByFieldValue(eq(Ticket.FIELD_ORGANIZATION_ID), anyString())).thenReturn(tickets);

        // Execute code
        List<OrganizationDTO> organizationDTOs = searchService.searchOrganizations(anyString(), anyString());

        // Validate results
        Assertions.assertThat(organizationDTOs.size()).isEqualTo(orgs.size());
        OrganizationDTO organizationDTO = organizationDTOs.get(0);
        Assertions.assertThat(organizationDTO.getOrganization().getId()).isEqualTo(org.getId());

        Assertions.assertThat(organizationDTO.getUsers().size()).isEqualTo(users.size());
        User injectedUser = organizationDTO.getUsers().get(0);
        Assertions.assertThat(injectedUser.getId()).isEqualTo(user.getId());

        Assertions.assertThat(organizationDTO.getTickets().size()).isEqualTo(tickets.size());
        Ticket injectedTicket = organizationDTO.getTickets().get(0);
        Assertions.assertThat(injectedTicket.getId()).isEqualTo(ticket.getId());
    }

    @Test
    public void testSearchTickets() throws InvalidFieldNameException {
        Ticket ticket = new Ticket();
        ticket.setId("1");
        ticket.setOrganizationId(2l);
        ticket.setSubmitterId(3l);
        ticket.setAssigneeId(4l);
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        Organization org = new Organization();
        org.setId(Long.toString(ticket.getOrganizationId()));

        User submitter = new User();
        submitter.setId(Long.toString(ticket.getSubmitterId()));

        User assignee = new User();
        assignee.setId(Long.toString(ticket.getAssigneeId()));

        // Mock behaviour of external dependencies
        when(ticketRepository.findByFieldValue(anyString(), anyString())).thenReturn(tickets);
        when(organizationRepository.findById(ticket.getOrganizationId())).thenReturn(org);
        when(userRepository.findById(ticket.getSubmitterId())).thenReturn(submitter);
        when(userRepository.findById(ticket.getAssigneeId())).thenReturn(assignee);

        // Execute code
        List<TicketDTO> ticketDTOs = searchService.searchTickets(anyString(), anyString());

        // Validate results
        Assertions.assertThat(ticketDTOs.size()).isEqualTo(tickets.size());
        TicketDTO ticketDTO = ticketDTOs.get(0);
        Assertions.assertThat(ticketDTO.getTicket().getId()).isEqualTo(ticket.getId());

        Assertions.assertThat(ticketDTO.getSubmitter().getId()).isEqualTo(submitter.getId());

        Assertions.assertThat(ticketDTO.getAssignee().getId()).isEqualTo(assignee.getId());
    }
}
