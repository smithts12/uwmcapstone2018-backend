package edu.uwm.capstone.model.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

public class Contact {
    @JsonProperty(value = "id")
    protected Long id;
    @JsonProperty(value = "userID")
    protected Long userID;
    @JsonProperty(value = "companyID")
    protected Long companyID;
    @JsonProperty(value = "position")
    protected String position;
    @JsonProperty(value = "firstName")
    protected String firstName;
    @JsonProperty(value = "lastName")
    protected String lastName;
    @JsonProperty(value = "email")
    protected String email;
    @JsonProperty(value = "phoneNumber")
    protected String phoneNumber;
    @JsonProperty(value = "notes")
    protected String notes;
    @JsonProperty(value = "createdDate")
    protected LocalDateTime createdDate;
    @JsonProperty(value = "updatedDate")
    protected LocalDateTime updatedDate;

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserID() { return this.userID; }
    public void setUserID(Long userID) { this.userID = userID; }

    public Long getCompanyID() { return this.companyID; }
    public void setCompanyID(Long companyID) { this.companyID = companyID;}

    public String getPosition() { return this.position; }
    public void setPosition(String position) { this.position = position; }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return this.phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getNotes() { return this.notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedDate() { return this.createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getUpdatedDate() { return this.updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

    @Override
    public boolean equals(Object object) { return object instanceof Contact && (this == object || EqualsBuilder.reflectionEquals(this, object)); }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
