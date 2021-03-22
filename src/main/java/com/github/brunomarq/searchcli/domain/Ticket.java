package com.github.brunomarq.searchcli.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
* Represents a ticket.
*/
public class Ticket extends Entity {

    public static final String ENTITY_TYPE = "ticket";

    public static final String FIELD_TYPE = "type";
    public static final String FIELD_SUBJECT = "subject";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_SUBMITTER_ID = "submitter_id";
    public static final String FIELD_ASSIGNEE_ID = "assignee_id";
    public static final String FIELD_ORGANIZATION_ID = "organization_id";
    public static final String FIELD_HAS_INCIDENTS = "has_incidents";
    public static final String FIELD_DUE_AT = "due_at";
    public static final String FIELD_VIA = "via";

    public static final List<String> FIELDS = Collections.unmodifiableList(Stream.concat(Entity.FIELDS.stream(),
            Arrays.asList(FIELD_TYPE, FIELD_SUBJECT, FIELD_DESCRIPTION, FIELD_PRIORITY, FIELD_STATUS,
                    FIELD_SUBMITTER_ID, FIELD_ASSIGNEE_ID, FIELD_ORGANIZATION_ID, FIELD_HAS_INCIDENTS, FIELD_DUE_AT,
                    FIELD_VIA).stream())
            .collect(Collectors.toList()));

    @JsonProperty(FIELD_TYPE)
    private String type;

    @JsonProperty(FIELD_SUBJECT)
    private String subject;

    @JsonProperty(FIELD_DESCRIPTION)
    private String description;

    @JsonProperty(FIELD_PRIORITY)
    private String priority;

    @JsonProperty(FIELD_STATUS)
    private String status;

    @JsonProperty(FIELD_SUBMITTER_ID)
    private Long submitterId;

    @JsonProperty(FIELD_ASSIGNEE_ID)
    private Long assigneeId;

    @JsonProperty(FIELD_ORGANIZATION_ID)
    private Long organizationId;

    @JsonProperty(FIELD_HAS_INCIDENTS)
    private boolean hasIncidents;

    @JsonProperty(FIELD_DUE_AT)
    private String dueAt;

    @JsonProperty(FIELD_VIA)
    private String via;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the submitterId
     */
    public Long getSubmitterId() {
        return submitterId;
    }

    /**
     * @param submitterId the submitterId to set
     */
    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }

    /**
     * @return the assigneeId
     */
    public Long getAssigneeId() {
        return assigneeId;
    }

    /**
     * @param assigneeId the assigneeId to set
     */
    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    /**
     * @return the organizationId
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * @param organizationId the organizationId to set
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * @return the hasIncidents
     */
    public boolean isHasIncidents() {
        return hasIncidents;
    }

    /**
     * @param hasIncidents the hasIncidents to set
     */
    public void setHasIncidents(boolean hasIncidents) {
        this.hasIncidents = hasIncidents;
    }

    /**
     * @return the dueAt
     */
    public String getDueAt() {
        return dueAt;
    }

    /**
     * @param dueAt the dueAt to set
     */
    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    /**
     * @return the via
     */
    public String getVia() {
        return via;
    }

    /**
     * @param via the via to set
     */
    public void setVia(String via) {
        this.via = via;
    }

    /**
     * @return int
     */
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((assigneeId == null) ? 0 : assigneeId.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((dueAt == null) ? 0 : dueAt.hashCode());
        result = prime * result + (hasIncidents ? 1231 : 1237);
        result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((submitterId == null) ? 0 : submitterId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((via == null) ? 0 : via.hashCode());
        return result;
    }

    /**
     * @param obj
     * @return boolean
     */
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
        Ticket other = (Ticket) obj;
        if (assigneeId == null) {
            if (other.assigneeId != null)
                return false;
        } else if (!assigneeId.equals(other.assigneeId))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (dueAt == null) {
            if (other.dueAt != null)
                return false;
        } else if (!dueAt.equals(other.dueAt))
            return false;
        if (hasIncidents != other.hasIncidents)
            return false;
        if (organizationId == null) {
            if (other.organizationId != null)
                return false;
        } else if (!organizationId.equals(other.organizationId))
            return false;
        if (priority == null) {
            if (other.priority != null)
                return false;
        } else if (!priority.equals(other.priority))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (submitterId == null) {
            if (other.submitterId != null)
                return false;
        } else if (!submitterId.equals(other.submitterId))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (via == null) {
            if (other.via != null)
                return false;
        } else if (!via.equals(other.via))
            return false;
        return true;
    }

}