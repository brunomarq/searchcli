package com.github.brunomarq.searchcli.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
* Represents an user.
*/
public class User extends Entity {

    public static final String ENTITY_TYPE = "user";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_ALIAS = "alias";
    public static final String FIELD_ACTIVE = "active";
    public static final String FIELD_VERIFIED = "verified";
    public static final String FIELD_SHARED = "shared";
    public static final String FIELD_LOCALE = "locale";
    public static final String FIELD_TIMEZONE = "timezone";
    public static final String FIELD_LAST_LOGIN_AT = "last_login_at";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_SIGNATURE = "signature";
    public static final String FIELD_ORGANIZATION_ID = "organization_id";
    public static final String FIELD_SUSPENDED = "suspended";
    public static final String FIELD_ROLE = "role";

    public static final List<String> FIELDS = Collections
            .unmodifiableList(Stream
                    .concat(Entity.FIELDS.stream(),
                            Arrays.asList(FIELD_NAME, FIELD_ALIAS, FIELD_ACTIVE, FIELD_VERIFIED, FIELD_SHARED,
                                    FIELD_LOCALE, FIELD_TIMEZONE, FIELD_LAST_LOGIN_AT, FIELD_EMAIL, FIELD_PHONE,
                                    FIELD_SIGNATURE, FIELD_ORGANIZATION_ID, FIELD_SUSPENDED, FIELD_ROLE).stream())
                    .collect(Collectors.toList()));

    @JsonProperty(FIELD_NAME)
    private String name;

    @JsonProperty(FIELD_ALIAS)
    private String alias;

    @JsonProperty(FIELD_ACTIVE)
    private boolean active;

    @JsonProperty(FIELD_VERIFIED)
    private boolean verified;

    @JsonProperty(FIELD_SHARED)
    private boolean shared;

    @JsonProperty(FIELD_LOCALE)
    private String locale;

    @JsonProperty(FIELD_TIMEZONE)
    private String timezone;

    @JsonProperty(FIELD_LAST_LOGIN_AT)
    private String lastLoginAt;

    @JsonProperty(FIELD_EMAIL)
    private String email;

    @JsonProperty(FIELD_PHONE)
    private String phone;

    @JsonProperty(FIELD_SIGNATURE)
    private String signature;

    @JsonProperty(FIELD_ORGANIZATION_ID)
    private Long organizationId;

    @JsonProperty(FIELD_SUSPENDED)
    private boolean suspended;

    @JsonProperty(FIELD_ROLE)
    private String role;

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
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * @return the shared
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * @param shared the shared to set
     */
    public void setShared(boolean shared) {
        this.shared = shared;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return the timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone the timezone to set
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * @return the lastLoginAt
     */
    public String getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * @param lastLoginAt the lastLoginAt to set
     */
    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
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
     * @return the suspended
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * @param suspended the suspended to set
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
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
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((lastLoginAt == null) ? 0 : lastLoginAt.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + (shared ? 1231 : 1237);
        result = prime * result + ((signature == null) ? 0 : signature.hashCode());
        result = prime * result + (suspended ? 1231 : 1237);
        result = prime * result + ((timezone == null) ? 0 : timezone.hashCode());
        result = prime * result + (verified ? 1231 : 1237);
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
        User other = (User) obj;
        if (active != other.active)
            return false;
        if (alias == null) {
            if (other.alias != null)
                return false;
        } else if (!alias.equals(other.alias))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (lastLoginAt == null) {
            if (other.lastLoginAt != null)
                return false;
        } else if (!lastLoginAt.equals(other.lastLoginAt))
            return false;
        if (locale == null) {
            if (other.locale != null)
                return false;
        } else if (!locale.equals(other.locale))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (organizationId == null) {
            if (other.organizationId != null)
                return false;
        } else if (!organizationId.equals(other.organizationId))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (shared != other.shared)
            return false;
        if (signature == null) {
            if (other.signature != null)
                return false;
        } else if (!signature.equals(other.signature))
            return false;
        if (suspended != other.suspended)
            return false;
        if (timezone == null) {
            if (other.timezone != null)
                return false;
        } else if (!timezone.equals(other.timezone))
            return false;
        if (verified != other.verified)
            return false;
        return true;
    }

}
