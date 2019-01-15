package edu.uwm.capstone.model.user;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {
    protected Long id;
    private String email;
    private String password;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobilePhone;
    private String homePhone;
    private String website;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;

    public Long getId(){ return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String first_name){
        this.firstName = first_name;
    }

    public String getMiddleName(){
        return middleName;
    }

    public void setMiddleName(String middle_name){
        this.middleName = middle_name;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String last_name){
        this.lastName = last_name;
    }

    public String getMobilePhone(){
        return mobilePhone;
    }

    public void setMobilePhone(String mobile_phone){
        this.mobilePhone = mobile_phone;
    }

    public String getHomePhone(){
        return homePhone;
    }

    public void setHomePhone(String home_phone){
        this.homePhone = home_phone;
    }

    public String getWebsite(){
        return website;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime created_date) { this.createdDate = created_date; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(LocalDateTime updated_date) { this.updatedDate = updated_date; }

    @Override
    public boolean equals(Object object) {
        return object instanceof User && (this == object || EqualsBuilder.reflectionEquals(this, object));
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