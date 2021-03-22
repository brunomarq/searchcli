package com.github.brunomarq.searchcli.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String ERROR_LOADING_JSON_FILES = "JSON files could not be loaded.";
    public static final String DATABASE_READY = "Database ready.";
    public static final String INVALID_ENTITY = "Invalid entity. Choose organization, ticket or user.";
    public static final String INVALID_FIELD = "Invalid field. Use `fields <entity>` to list all available fields for an entity.";

    public static final String ORGANIZATIONS_FILENAME = "organizations.json";
    public static final String TICKETS_FILENAME = "tickets.json";
    public static final String USERS_FILENAME = "users.json";

}
