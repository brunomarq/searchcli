package com.github.brunomarq.searchcli.utils;

import com.github.brunomarq.searchcli.domain.Organization;
import com.github.brunomarq.searchcli.domain.Ticket;
import com.github.brunomarq.searchcli.domain.User;

import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    /**
     * Checks if the string is one of the available entities: organization, user or
     * ticket.
     * 
     * @param entity string representing the entity
     * @return boolean false if it does not match
     */
    public boolean isEntityValid(String entity) {
        boolean valid = false;
        String rawEntity = entity.trim().toLowerCase();
        if (rawEntity.equals(Organization.ENTITY_TYPE) || rawEntity.equals(User.ENTITY_TYPE)
                || rawEntity.equals(Ticket.ENTITY_TYPE)) {
            valid = true;
        }
        return valid;
    }

    /**
     * Checks if the string is one of the available fiels for a specific entity.
     * 
     * @param entity string representing the entity
     * @return boolean false if it does not match
     */
    public boolean isFieldValid(String entity, String field) {
        String rawEntity = entity.trim().toLowerCase();
        boolean valid = this.isEntityValid(rawEntity);

        if (valid) {
            String rawField = field.trim().toLowerCase();
            switch (rawEntity) {
            case Organization.ENTITY_TYPE:
                valid = Organization.FIELDS.contains(rawField);
                break;
            case User.ENTITY_TYPE:
                valid = User.FIELDS.contains(rawField);
                break;
            case Ticket.ENTITY_TYPE:
                valid = Ticket.FIELDS.contains(rawField);
                break;
            default:
                valid = false;
                break;
            }
        }

        return valid;
    }
}
