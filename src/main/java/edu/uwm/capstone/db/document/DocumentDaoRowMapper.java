package edu.uwm.capstone.db.document;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import edu.uwm.capstone.model.document.Document;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import static edu.uwm.capstone.db.document.DocumentDaoRowMapper.DocumentColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;

public class DocumentDaoRowMapper extends BaseRowMapper<Document>{

    public enum DocumentColumnType {
        USER_ID(),
        NAME(),
        CREATED_DATE(),
        UPDATED_DATE(),
        PATH()
        ;

        private String columnName;

        DocumentColumnType() {
            columnName = name().toLowerCase();
        }

        DocumentColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(Document object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(USER_ID.getColumnName(), object.getUserId());
        map.put(NAME.getColumnName(), object.getName());
        map.put(BaseColumnType.CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(BaseColumnType.UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        map.put(PATH.getColumnName(), object.getPath());
        return map;
    }

    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        Document document = new Document();
        document.setId(rs.getLong(ID.getColumnName()));
        document.setUserId(rs.getLong(USER_ID.getColumnName()));
        document.setName(rs.getString(NAME.getColumnName()));
        document.setCreatedDate(dateFromJavaTime(rs.getObject(BaseColumnType.CREATED_DATE.getColumnName())));
        document.setUpdatedDate(dateFromJavaTime(rs.getObject(BaseColumnType.UPDATED_DATE.getColumnName())));
        document.setPath(rs.getString(PATH.getColumnName()));
        return document;
    }
}