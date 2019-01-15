package edu.uwm.capstone.model.certification;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Certification {
    @JsonProperty(value = "id")
    protected Long id;
    @JsonProperty(value = "userID")
    protected Long userID;
    @JsonProperty(value = "createdDate")
    protected LocalDateTime createDate;
    @JsonProperty(value = "updatedDate")
    protected LocalDateTime updatedDate;
    @JsonProperty(value = "name")
    protected String name;
    @JsonProperty(value = "authority")
    protected String authority;
    @JsonProperty(value = "licenseNumber")
    protected String licenseNumber;
    @JsonProperty(value = "acquiredDate")
    protected LocalDateTime acquiredDate;
    @JsonProperty(value = "expiredDate")
    protected LocalDateTime expiredDate;
    @JsonProperty(value = "website")
    protected String website;

    public Long getId(){ return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID(){ return userID; }

    public void setUserID(Long user_id){ this.userID = user_id; }

    public LocalDateTime getCreatedDate() {
        return createDate;
    }

    public void setCreatedDate(LocalDateTime created_date) { this.createDate = created_date; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(LocalDateTime updated_date) { this.updatedDate = updated_date; }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAuthority(){
        return authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public String getLicenseNumber(){
        return licenseNumber;
    }

    public void setLicenseNumber(String license_number){
        this.licenseNumber = license_number;
    }

    public LocalDateTime getAcquiredDate() {
        return acquiredDate;
    }

    public void setAcquiredDate(LocalDateTime acquired_date) { this.acquiredDate = acquired_date; }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expired_date) { this.expiredDate = expired_date; }

    public String getWebsite(){
        return website;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Certification && (this == object || EqualsBuilder.reflectionEquals(this, object));
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