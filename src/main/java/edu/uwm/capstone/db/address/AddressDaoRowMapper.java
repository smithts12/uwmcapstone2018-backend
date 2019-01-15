package edu.uwm.capstone.db.address;

import edu.uwm.capstone.model.address.Address;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static edu.uwm.capstone.db.address.AddressDaoRowMapper.AddressColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;

public class AddressDaoRowMapper extends BaseRowMapper<Address> {

    public enum AddressColumnType {
        USER_ID(),
        STREET_1(),
        STREET_2(),
        CITY(),
        STATE(),
        ZIP_CODE(),
        ;

        private String columnName;

        AddressColumnType() {
            columnName = name().toLowerCase();
        }

        AddressColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(Address object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(USER_ID.getColumnName(), object.getUserID());
        map.put(STREET_1.getColumnName(), object.getStreet1());
        map.put(STREET_2.getColumnName(), object.getStreet2());
        map.put(CITY.getColumnName(), object.getCity());
        map.put(STATE.getColumnName(), object.getState());
        map.put(ZIP_CODE.getColumnName(), object.getZipcode());
        map.put(CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        return map;
    }

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(rs.getLong(ID.getColumnName()));
        address.setUserID(rs.getLong(USER_ID.getColumnName()));
        address.setStreet1(rs.getString(STREET_1.getColumnName()));
        address.setStreet2(rs.getString(STREET_2.getColumnName()));
        address.setCity(rs.getString(CITY.getColumnName()));
        address.setState(rs.getString(STATE.getColumnName()));
        address.setZipcode(rs.getString(ZIP_CODE.getColumnName()));
        address.setCreatedDate(dateFromJavaTime(rs.getObject(CREATED_DATE.getColumnName())));
        address.setUpdatedDate(dateFromJavaTime(rs.getObject(UPDATED_DATE.getColumnName())));
        return address;
    }
}
