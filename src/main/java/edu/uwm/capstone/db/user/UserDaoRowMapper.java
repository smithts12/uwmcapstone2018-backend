package edu.uwm.capstone.db.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import edu.uwm.capstone.model.user.User;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import static edu.uwm.capstone.db.user.UserDaoRowMapper.UserColumnType.*;
import static edu.uwm.capstone.db.user.UserDaoRowMapper.UserColumnType.CREATED_DATE;
import static edu.uwm.capstone.db.user.UserDaoRowMapper.UserColumnType.UPDATED_DATE;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;

public class UserDaoRowMapper extends BaseRowMapper<User> {

    public enum UserColumnType {
        PASSWORD(),
        EMAIL(),
        TITLE(),
        FIRST_NAME(),
        MIDDLE_NAME(),
        LAST_NAME(),
        MOBILE_PHONE(),
        HOME_PHONE(),
        WEBSITE(),
        CREATED_DATE(),
        UPDATED_DATE();

        private String columnName;

        UserColumnType() {
            columnName = name().toLowerCase();
        }

        UserColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(User object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(PASSWORD.getColumnName(), object.getPassword());
        map.put(EMAIL.getColumnName(), object.getEmail());
        map.put(TITLE.getColumnName(), object.getTitle());
        map.put(FIRST_NAME.getColumnName(), object.getFirstName());
        map.put(MIDDLE_NAME.getColumnName(), object.getMiddleName());
        map.put(LAST_NAME.getColumnName(), object.getLastName());
        map.put(MOBILE_PHONE.getColumnName(), object.getMobilePhone());
        map.put(HOME_PHONE.getColumnName(), object.getHomePhone());
        map.put(WEBSITE.getColumnName(), object.getWebsite());
        map.put(CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        return map;
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID.getColumnName()));
        user.setPassword(rs.getString(PASSWORD.getColumnName()));
        user.setEmail(rs.getString(EMAIL.getColumnName()));
        user.setTitle(rs.getString(TITLE.getColumnName()));
        user.setFirstName(rs.getString(FIRST_NAME.getColumnName()));
        user.setMiddleName(rs.getString(MIDDLE_NAME.getColumnName()));
        user.setLastName(rs.getString(LAST_NAME.getColumnName()));
        user.setMobilePhone(rs.getString(MOBILE_PHONE.getColumnName()));
        user.setHomePhone(rs.getString(HOME_PHONE.getColumnName()));
        user.setWebsite(rs.getString(WEBSITE.getColumnName()));
        user.setCreatedDate(dateFromJavaTime(rs.getObject(CREATED_DATE.getColumnName())));
        user.setUpdatedDate(dateFromJavaTime(rs.getObject(UPDATED_DATE.getColumnName())));
        return user;
    }
}