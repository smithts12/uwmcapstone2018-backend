package edu.uwm.capstone.db.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import edu.uwm.capstone.model.company.Company;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;
import static edu.uwm.capstone.db.company.CompanyDaoRowMapper.CompanyColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;

public class CompanyDaoRowMapper extends BaseRowMapper<Company> {

    public enum CompanyColumnType {
        USER_ID(),
        NAME(),
        STREET_1(),
        STREET_2(),
        CITY(),
        STATE(),
        ZIP_CODE(),
        PHONE_NUMBER(),
        WEBSITE(),
        ;

        private String columnName;

        CompanyColumnType() {
            columnName = name().toLowerCase();
        }

        CompanyColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(Company object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(USER_ID.getColumnName(), object.getUserID());
        map.put(NAME.getColumnName(), object.getName());
        map.put(STREET_1.getColumnName(), object.getStreet1());
        map.put(STREET_2.getColumnName(), object.getStreet2());
        map.put(CITY.getColumnName(), object.getCity());
        map.put(STATE.getColumnName(), object.getState());
        map.put(ZIP_CODE.getColumnName(), object.getZipCode());
        map.put(PHONE_NUMBER.getColumnName(), object.getPhoneNumber());
        map.put(WEBSITE.getColumnName(), object.getWebsite());
        map.put(CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        return map;
    }

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setId(rs.getLong(ID.getColumnName()));
        company.setUserID(rs.getLong(USER_ID.getColumnName()));
        company.setName(rs.getString(NAME.getColumnName()));
        company.setStreet1(rs.getString(STREET_1.getColumnName()));
        company.setStreet2(rs.getString(STREET_2.getColumnName()));
        company.setCity(rs.getString(CITY.getColumnName()));
        company.setState(rs.getString(STATE.getColumnName()));
        company.setZipCode(rs.getString(ZIP_CODE.getColumnName()));
        company.setPhoneNumber(rs.getString(PHONE_NUMBER.getColumnName()));
        company.setWebsite(rs.getString(WEBSITE.getColumnName()));
        company.setCreatedDate(dateFromJavaTime(rs.getObject(CREATED_DATE.getColumnName())));
        company.setUpdatedDate(dateFromJavaTime(rs.getObject(UPDATED_DATE.getColumnName())));
        return company;
    }
}