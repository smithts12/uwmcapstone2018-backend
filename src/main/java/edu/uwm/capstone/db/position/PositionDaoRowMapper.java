package edu.uwm.capstone.db.position;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import edu.uwm.capstone.model.position.Position;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import static edu.uwm.capstone.db.position.PositionDaoRowMapper.PositionColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;

public class PositionDaoRowMapper extends BaseRowMapper<Position>{

    public enum PositionColumnType {
        USER_ID(),
        NAME(),
        COMPANY_ID(),
        DESCRIPTION(),
        START_DATE(),
        END_DATE(),
        START_PAY(),
        END_PAY()
        ;

        private String columnName;

        PositionColumnType() {
            columnName = name().toLowerCase();
        }

        PositionColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(Position object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(USER_ID.getColumnName(), object.getUserID());
        map.put(NAME.getColumnName(), object.getName());
        map.put(COMPANY_ID.getColumnName(), object.getCompanyId());
        map.put(DESCRIPTION.getColumnName(), object.getDescription());
        map.put(CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        map.put(START_DATE.getColumnName(), javaTimeFromDate(object.getStartDate()));
        map.put(END_DATE.getColumnName(), javaTimeFromDate(object.getEndDate()));
        map.put(START_PAY.getColumnName(), object.getStartPay());
        map.put(END_PAY.getColumnName(), object.getEndPay());
        return map;
    }

    @Override
    public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
        Position position = new Position();
        position.setId(rs.getLong(ID.getColumnName()));
        position.setUserID(rs.getLong(USER_ID.getColumnName()));
        position.setName(rs.getString(NAME.getColumnName()));
        position.setCompanyId(rs.getLong(COMPANY_ID.getColumnName()));
        position.setDescription(rs.getString(DESCRIPTION.getColumnName()));
        position.setCreatedDate(dateFromJavaTime(rs.getObject(CREATED_DATE.getColumnName())));
        position.setUpdatedDate(dateFromJavaTime(rs.getObject(UPDATED_DATE.getColumnName())));
        position.setStartDate(dateFromJavaTime(rs.getObject(START_DATE.getColumnName())));
        position.setEndDate(dateFromJavaTime(rs.getObject(END_DATE.getColumnName())));
        position.setStartPay(rs.getDouble(START_PAY.getColumnName()));
        position.setEndPay(rs.getDouble(END_PAY.getColumnName()));
        return position;
    }
}