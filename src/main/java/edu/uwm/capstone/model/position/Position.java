package edu.uwm.capstone.model.position;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.uwm.capstone.jsoncomponent.LocalDateTimeJsonDeserializer;
import edu.uwm.capstone.jsoncomponent.LocalDateTimeJsonSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Position {
    @JsonProperty(value = "id")
    protected Long id;
    @JsonProperty(value = "userID")
    protected Long userID;
    @JsonProperty(value = "name")
    protected String name;
    @JsonProperty(value = "companyID")
    protected Long companyID;
    @JsonProperty(value = "description")
    protected String description;
    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    @JsonProperty(value = "createdDate")
    protected LocalDateTime createdDate;
    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    @JsonProperty(value = "updatedDate")
    protected LocalDateTime updatedDate;
    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    @JsonProperty(value = "startDate")
    protected LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    @JsonProperty(value = "endDate")
    protected LocalDateTime endDate;
    @JsonProperty(value = "startPay")
    protected Double startPay;
    @JsonProperty(value = "endPay")
    protected Double endPay;


    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() { return this.userID; }
    public void setUserID(Long userID) { this.userID = userID; }

    public String getName() {return name; }

    public void setName(String name) { this.name = name;}

    public Long getCompanyId(){ return companyID; }

    public void setCompanyId(Long company_id) { this.companyID = company_id; }

    public String getDescription() {return description; }

    public void setDescription(String description) { this.description = description;}

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime created_date) { this.createdDate = created_date; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(LocalDateTime updated_date) { this.updatedDate = updated_date; }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime start_date) { this.startDate = start_date; }

    public LocalDateTime getEndDate() { return endDate; }

    public void setEndDate(LocalDateTime end_date) { this.endDate = end_date; }

    public Double getStartPay() { return startPay; }

    public void setStartPay(Double start_pay){ this.startPay = start_pay; }

    public Double getEndPay() { return endPay; }

    public void setEndPay(Double end_pay){ this.endPay = end_pay; }

    @Override
    public boolean equals(Object object) {
        return object instanceof Position && (this == object || EqualsBuilder.reflectionEquals(this, object));
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