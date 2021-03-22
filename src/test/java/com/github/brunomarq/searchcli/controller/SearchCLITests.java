package com.github.brunomarq.searchcli.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.github.brunomarq.searchcli.domain.Organization;
import com.github.brunomarq.searchcli.domain.Ticket;
import com.github.brunomarq.searchcli.domain.User;
import com.github.brunomarq.searchcli.exception.InvalidFieldNameException;
import com.github.brunomarq.searchcli.service.SearchService;
import com.github.brunomarq.searchcli.utils.Constants;
import com.github.brunomarq.searchcli.utils.InputValidator;
import com.github.brunomarq.searchcli.utils.ResponseFormatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchCLITests {

    @Mock
    SearchService searchServiceMock;

    @Mock
    InputValidator inputValidatorMock;

    @Mock
    ResponseFormatter responseFormatterMock;

    @InjectMocks
    SearchCLI searchCLI;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadDatabaseCommandWithInvalidPath() throws IOException {
        final String invalidDirectory = "invalidDirectory";

        // Mock behaviour of external dependencies
        doThrow(new IOException()).when(searchServiceMock).loadDatabase(invalidDirectory);
        when(responseFormatterMock.formatError(Constants.ERROR_LOADING_JSON_FILES))
                .thenReturn(Constants.ERROR_LOADING_JSON_FILES);

        // Execute code
        String response = searchCLI.loadDatabase(invalidDirectory);

        // Validate results
        Assertions.assertThat(response).contains(Constants.ERROR_LOADING_JSON_FILES);

        // Verify calls of external dependencies
        verify(searchServiceMock, times(1)).loadDatabase(invalidDirectory);
    }

    @Test
    public void testLoadDatabaseCommand() throws IOException {
        final String validDirectory = "validDirectory";

        // Mock behaviour of external dependencies
        doNothing().when(searchServiceMock).loadDatabase(validDirectory);
        when(responseFormatterMock.formatInfo(Constants.DATABASE_READY)).thenReturn(Constants.DATABASE_READY);

        // Execute code
        String response = searchCLI.loadDatabase(validDirectory);

        // Validate results
        Assertions.assertThat(response).contains(Constants.DATABASE_READY);

        // Verify calls of external dependencies
        verify(searchServiceMock, times(1)).loadDatabase(validDirectory);
    }

    @Test
    public void testFieldsCommandWithInvalidEntity() {
        final String invalidEntity = "invalidEntity";

        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(invalidEntity)).thenReturn(false);
        when(responseFormatterMock.formatError(Constants.INVALID_ENTITY)).thenReturn(Constants.INVALID_ENTITY);

        // Execute code
        String response = searchCLI.fields(invalidEntity);

        // Validate results
        Assertions.assertThat(response).contains(Constants.INVALID_ENTITY);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(invalidEntity);

    }

    @ParameterizedTest
    @ValueSource(strings = { Organization.ENTITY_TYPE, Ticket.ENTITY_TYPE, User.ENTITY_TYPE })
    public void testFieldsCommand(String validEntity) {
        final String validResponse = "validResponseWithListOfFields";
        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(validEntity)).thenReturn(true);
        when(responseFormatterMock.formatFieldResponse(anyList())).thenReturn(validResponse);

        // Execute code
        String response = searchCLI.fields(validEntity);

        // Validate results
        Assertions.assertThat(response).contains(validResponse);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(validEntity);

    }

    @Test
    public void testSearchOrganizationCommand() throws InvalidFieldNameException {
        final String validEntity = Organization.ENTITY_TYPE;
        final String validField = "validField";
        final String validResponse = "validResponse";
        final String validValue = "validValue";
        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(validEntity)).thenReturn(true);
        when(inputValidatorMock.isFieldValid(validEntity, validField)).thenReturn(true);
        when(responseFormatterMock.formatOrganizationResponse(anyList())).thenReturn(validResponse);
        when(searchServiceMock.searchOrganizations(validField, validValue)).thenReturn(anyList());

        // Execute code
        String response = searchCLI.search(validEntity, validField, validValue);

        // Validate results
        Assertions.assertThat(response).contains(validResponse);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(validEntity);
        verify(inputValidatorMock, times(1)).isFieldValid(validEntity, validField);
        verify(searchServiceMock, times(1)).searchOrganizations(validField, validValue);

    }

    @Test
    public void testSearchUserCommand() throws InvalidFieldNameException {
        final String validEntity = User.ENTITY_TYPE;
        final String validField = "validField";
        final String validResponse = "validResponse";
        final String validValue = "validValue";
        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(validEntity)).thenReturn(true);
        when(inputValidatorMock.isFieldValid(validEntity, validField)).thenReturn(true);
        when(responseFormatterMock.formatUserResponse(anyList())).thenReturn(validResponse);
        when(searchServiceMock.searchUsers(validField, validValue)).thenReturn(anyList());

        // Execute code
        String response = searchCLI.search(validEntity, validField, validValue);

        // Validate results
        Assertions.assertThat(response).contains(validResponse);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(validEntity);
        verify(inputValidatorMock, times(1)).isFieldValid(validEntity, validField);
        verify(searchServiceMock, times(1)).searchUsers(validField, validValue);

    }

    @Test
    public void testSearchTicketCommand() throws InvalidFieldNameException {
        final String validEntity = Ticket.ENTITY_TYPE.toUpperCase();
        final String validField = "validField";
        final String validResponse = "validResponse";
        final String validValue = "validValue";
        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(validEntity)).thenReturn(true);
        when(inputValidatorMock.isFieldValid(validEntity, validField)).thenReturn(true);
        when(responseFormatterMock.formatTicketResponse(anyList())).thenReturn(validResponse);
        when(searchServiceMock.searchTickets(validField, validValue)).thenReturn(anyList());

        // Execute code
        String response = searchCLI.search(validEntity, validField, validValue);

        // Validate results
        Assertions.assertThat(response).contains(validResponse);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(validEntity);
        verify(inputValidatorMock, times(1)).isFieldValid(validEntity, validField);
        verify(searchServiceMock, times(1)).searchTickets(validField, validValue);

    }

    @Test
    public void testSearchCommandWithInvalidEntity() throws InvalidFieldNameException {
        final String invalidEntity = "invalidEntity";
        final String validField = "validField";
        final String validValue = "validValue";
        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(invalidEntity)).thenReturn(false);
        when(responseFormatterMock.formatError(Constants.INVALID_ENTITY)).thenReturn(Constants.INVALID_ENTITY);

        // Execute code
        String response = searchCLI.search(invalidEntity, validField, validValue);

        // Validate results
        Assertions.assertThat(response).contains(Constants.INVALID_ENTITY);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(invalidEntity);

    }

    @Test
    public void testSearchCommandWithInvalidField() throws InvalidFieldNameException {
        final String validEntity = "validEntity";
        final String invalidField = "invalidField";
        final String validValue = "validValue";
        // Mock behaviour of external dependencies
        when(inputValidatorMock.isEntityValid(validEntity)).thenReturn(true);
        when(inputValidatorMock.isFieldValid(validEntity, invalidField)).thenReturn(false);
        when(responseFormatterMock.formatError(Constants.INVALID_FIELD)).thenReturn(Constants.INVALID_FIELD);

        // Execute code
        String response = searchCLI.search(validEntity, invalidField, validValue);

        // Validate results
        Assertions.assertThat(response).contains(Constants.INVALID_FIELD);

        // Verify calls of external dependencies
        verify(inputValidatorMock, times(1)).isEntityValid(validEntity);
        verify(inputValidatorMock, times(1)).isFieldValid(validEntity, invalidField);

    }

}
