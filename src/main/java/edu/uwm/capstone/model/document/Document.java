package edu.uwm.capstone.model.document;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Document {
    @JsonProperty(value = "id")
    protected Long id;
    @JsonProperty(value = "user_ID")
    protected Long user_ID;
    @JsonProperty(value = "created_date")
    protected LocalDateTime created_date;
    @JsonProperty(value = "updated_date")
    protected LocalDateTime updated_date;
    @JsonProperty(value = "name")
    protected String name;
    @JsonProperty(value = "path")
    protected String path;

    public Long getId(){ return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId(){ return user_ID; }

    public void setUserId(Long user_id){ this.user_ID = user_id; }

    public LocalDateTime getCreatedDate() {
        return created_date;
    }

    public void setCreatedDate(LocalDateTime created_date) { this.created_date = created_date; }

    public LocalDateTime getUpdatedDate() { return updated_date; }

    public void setUpdatedDate(LocalDateTime updated_date) { this.updated_date = updated_date; }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String authority){
        this.path = path;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Document && (this == object || EqualsBuilder.reflectionEquals(this, object));
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