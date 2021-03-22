package com.github.brunomarq.searchcli.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organization extends Entity {

    public static final String ENTITY_TYPE = "organization";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_DOMAIN_NAMES = "domain_names";
    public static final String FIELD_DETAILS = "details";
    public static final String FIELD_SHARED_TICKETS = "shared_tickets";

    public static final List<String> FIELDS = Collections.unmodifiableList(Stream
            .concat(Entity.FIELDS.stream(),
                    Arrays.asList(FIELD_NAME, FIELD_DOMAIN_NAMES, FIELD_DETAILS, FIELD_SHARED_TICKETS).stream())
            .collect(Collectors.toList()));

    @JsonProperty(FIELD_NAME)
    private String name;

    @JsonProperty(FIELD_DOMAIN_NAMES)
    private List<String> domainNames;

    @JsonProperty(FIELD_DETAILS)
    private String details;

    @JsonProperty(FIELD_SHARED_TICKETS)
    private boolean sharedTickets;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the domainNames
     */
    public List<String> getDomainNames() {
        return domainNames;
    }

    /**
     * @param domainNames the domainNames to set
     */
    public void setDomainNames(List<String> domainNames) {
        this.domainNames = domainNames;
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return the sharedTickets
     */
    public boolean isSharedTickets() {
        return sharedTickets;
    }

    /**
     * @param sharedTickets the sharedTickets to set
     */
    public void setSharedTickets(boolean sharedTickets) {
        this.sharedTickets = sharedTickets;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((details == null) ? 0 : details.hashCode());
        result = prime * result + ((domainNames == null) ? 0 : domainNames.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (sharedTickets ? 1231 : 1237);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Organization other = (Organization) obj;
        if (details == null) {
            if (other.details != null)
                return false;
        } else if (!details.equals(other.details))
            return false;
        if (domainNames == null) {
            if (other.domainNames != null)
                return false;
        } else if (!domainNames.equals(other.domainNames))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (sharedTickets != other.sharedTickets)
            return false;
        return true;
    }

    /**
     * 
     */
    public Organization() {
        // Do nothing because of X and Y.
    }

}