package edu.uwm.capstone.db.contact;

import edu.uwm.capstone.model.contact.Contact;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static edu.uwm.capstone.db.contact.ContactDaoRowMapper.ContactColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.CREATED_DATE;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.ID;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.UPDATED_DATE;

public class ContactDaoRowMapper extends BaseRowMapper<Contact> {

    public enum ContactColumnType {
        USER_ID(),
        COMPANY_ID(),
        POSITION(),
        FIRST_NAME(),
        LAST_NAME(),
        EMAIL(),
        PHONE_NUMBER(),
        NOTES(),
        ;

        private String columnName;

        ContactColumnType() {
            columnName = name().toLowerCase();
        }

        ContactColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(Contact object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(USER_ID.getColumnName(), object.getUserID());
        map.put(COMPANY_ID.getColumnName(), object.getCompanyID());
        map.put(POSITION.getColumnName(), object.getPosition());
        map.put(FIRST_NAME.getColumnName(), object.getFirstName());
        map.put(LAST_NAME.getColumnName(), object.getLastName());
        map.put(EMAIL.getColumnName(), object.getEmail());
        map.put(PHONE_NUMBER.getColumnName(), object.getPhoneNumber());
        map.put(NOTES.getColumnName(), object.getNotes());
        map.put(CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        return map;
    }

    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getLong(ID.getColumnName()));
        contact.setUserID(rs.getLong(USER_ID.getColumnName()));
        contact.setCompanyID(rs.getLong(COMPANY_ID.getColumnName()));
        contact.setPosition(rs.getString(POSITION.getColumnName()));
        contact.setFirstName(rs.getString(FIRST_NAME.getColumnName()));
        contact.setLastName(rs.getString(LAST_NAME.getColumnName()));
        contact.setEmail(rs.getString(EMAIL.getColumnName()));
        contact.setPhoneNumber(rs.getString(PHONE_NUMBER.getColumnName()));
        contact.setNotes(rs.getString(NOTES.getColumnName()));
        contact.setCreatedDate(dateFromJavaTime(rs.getObject(CREATED_DATE.getColumnName())));
        contact.setUpdatedDate(dateFromJavaTime(rs.getObject(UPDATED_DATE.getColumnName())));
        return contact;
    }

}
