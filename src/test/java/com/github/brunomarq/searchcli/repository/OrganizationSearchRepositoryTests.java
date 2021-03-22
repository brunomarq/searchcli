package com.github.brunomarq.searchcli.repository;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomarq.searchcli.domain.Organization;
import com.github.brunomarq.searchcli.exception.InvalidFieldNameException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
public class OrganizationSearchRepositoryTests {

    private static String org104JSON = "{\"_id\": 104,\"url\": \"http://initech.zendesk.com/api/v2/organizations/104.json\",\"external_id\": \"f6eb60ad-fe37-4a45-9689-b32031399f93\",\"name\": \"Xylar\",\"domain_names\": [\"anixang.com\",\"exovent.com\",\"photobin.com\",\"marqet.com\"],\"created_at\": \"2016-03-21T10:11:18 -11:00\",\"details\": \"\",\"shared_tickets\": false,\"tags\": [\"Hendricks\",\"Mclaughlin\",\"Stephens\",\"Garner\"]}";

    @Autowired
    private SearchRepository<Organization> organizationRepository;

    @Test
    public void testLoadDatabasesWithInvalidPath() {
        final String invalidFilepath = "invalid";

        Exception exception = assertThrows(IOException.class,
                () -> organizationRepository.loadDatabases(invalidFilepath, Organization.class));

        assertTrue(exception.getMessage().contains("No such file or directory"));

    }

    @Test
    public void testLoadDatabasesWithValidPath() {
        loadOrganizationInvertedIndex();
    }

    private void loadOrganizationInvertedIndex() {
        final String validFilepath = "organizations.json";
        try {
            organizationRepository.loadDatabases(validFilepath, Organization.class);
        } catch (IOException e) {
            fail("Should have found the file", e);
        }
    }

    @Test
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        final long id = 104;

        loadOrganizationInvertedIndex();
        try {
            Organization org = organizationRepository.findById(id);

            ObjectMapper mapper = new ObjectMapper();
            Organization expected = mapper.readValue(org104JSON, Organization.class);
            assertEquals(expected, org);
        } catch (InvalidFieldNameException e) {
            fail("Should have found the record", e);
        }
    }

    @Test
    public void findByFieldValueWithStringTypeField() throws JsonMappingException, JsonProcessingException {
        final String name = "Xylar";

        loadOrganizationInvertedIndex();
        try {
            Set<Organization> orgs = organizationRepository.findByFieldValue(Organization.FIELD_NAME, name);

            assertEquals(1, orgs.size());

            Organization org = orgs.iterator().next();
            ObjectMapper mapper = new ObjectMapper();
            Organization expected = mapper.readValue(org104JSON, Organization.class);
            assertEquals(expected, org);
        } catch (InvalidFieldNameException e) {
            fail("Should have found the record", e);
        }
    }

    @Test
    public void findByFieldValueWithEmptyField() throws JsonMappingException, JsonProcessingException {
        final String details = "";

        loadOrganizationInvertedIndex();
        try {
            Set<Organization> orgs = organizationRepository.findByFieldValue(Organization.FIELD_DETAILS, details);

            assertEquals(1, orgs.size());

            Organization org = orgs.iterator().next();
            ObjectMapper mapper = new ObjectMapper();
            Organization expected = mapper.readValue(org104JSON, Organization.class);
            assertEquals(expected, org);
        } catch (InvalidFieldNameException e) {
            fail("Should have found the record", e);
        }
    }

    @Test
    public void findByFieldValueWithBooleanField() throws JsonMappingException, JsonProcessingException {

        loadOrganizationInvertedIndex();
        try {
            Set<Organization> orgs = organizationRepository.findByFieldValue(Organization.FIELD_SHARED_TICKETS,
                    Boolean.toString(false));

            assertEquals(1, orgs.size());

            Organization org = orgs.iterator().next();
            ObjectMapper mapper = new ObjectMapper();
            Organization expected = mapper.readValue(org104JSON, Organization.class);
            assertEquals(expected, org);
        } catch (InvalidFieldNameException e) {
            fail("Should have found the record", e);
        }
    }

    @Test
    public void findByFieldValueWithArrayItemField() throws JsonMappingException, JsonProcessingException {
        final String arrayItem = "Hendricks";

        loadOrganizationInvertedIndex();
        try {
            Set<Organization> orgs = organizationRepository.findByFieldValue(Organization.FIELD_TAGS, arrayItem);

            assertEquals(1, orgs.size());

            Organization org = orgs.iterator().next();
            ObjectMapper mapper = new ObjectMapper();
            Organization expected = mapper.readValue(org104JSON, Organization.class);
            assertEquals(expected, org);
        } catch (InvalidFieldNameException e) {
            fail("Should have found the record", e);
        }
    }

}
