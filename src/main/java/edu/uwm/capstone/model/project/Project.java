package edu.uwm.capstone.model.project;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Project {
    @JsonProperty(value = "id")
    protected Long id;
    @JsonProperty(value = "userID")
    protected Long userID;
    @JsonProperty(value = "positionID")
    protected Long positionID;
    @JsonProperty(value = "educationID")
    protected Long educationID;
    @JsonProperty(value = "title")
    protected String title;
    @JsonProperty(value = "description")
    protected String description;
    @JsonProperty(value = "startDate")
    protected LocalDateTime startDate;
    @JsonProperty(value = "endDate")
    protected LocalDateTime endDate;
    @JsonProperty(value = "createdDate")
    protected LocalDateTime createdDate;
    @JsonProperty(value = "updatedDate")
    protected LocalDateTime updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long id) {
        this.userID = id;
    }

    public Long getPositionID() {
        return positionID;
    }

    public void setPositionID(Long id) {
        this.positionID = id;
    }

    public Long getEducationID() {
        return educationID;
    }

    public void setEducationID(Long id) {
        this.educationID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Project && (this == object || EqualsBuilder.reflectionEquals(this, object));
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}