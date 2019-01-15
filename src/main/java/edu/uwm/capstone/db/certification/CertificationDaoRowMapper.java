package edu.uwm.capstone.db.certification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import edu.uwm.capstone.model.certification.Certification;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import static edu.uwm.capstone.db.certification.CertificationDaoRowMapper.CertificationColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType.*;

public class CertificationDaoRowMapper extends BaseRowMapper<Certification>{

    public enum CertificationColumnType {
        USER_ID(),
        NAME(),
        CREATED_DATE(),
        UPDATED_DATE(),
        AUTHORITY(),
        LICENSE_NUMBER(),
        ACQUIRED_DATE(),
        EXPIRED_DATE(),
        WEBSITE()
        ;

        private String columnName;

        CertificationColumnType() {
            columnName = name().toLowerCase();
        }

        CertificationColumnType(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName(){
            return columnName;
        }
    }

    @Override
    public Map<String, Object> mapObject(Certification object) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID.getColumnName(), object.getId());
        map.put(USER_ID.getColumnName(), object.getUserID());
        map.put(NAME.getColumnName(), object.getName());
        map.put(BaseColumnType.CREATED_DATE.getColumnName(), javaTimeFromDate(object.getCreatedDate()));
        map.put(BaseColumnType.UPDATED_DATE.getColumnName(), javaTimeFromDate(object.getUpdatedDate()));
        map.put(AUTHORITY.getColumnName(), object.getAuthority());
        map.put(LICENSE_NUMBER.getColumnName(), object.getLicenseNumber());
        map.put(ACQUIRED_DATE.getColumnName(), javaTimeFromDate(object.getAcquiredDate()));
        map.put(EXPIRED_DATE.getColumnName(), javaTimeFromDate(object.getExpiredDate()));
        map.put(WEBSITE.getColumnName(), object.getWebsite());
        return map;
    }

    @Override
    public Certification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certification certification = new Certification();
        certification.setId(rs.getLong(ID.getColumnName()));
        certification.setUserID(rs.getLong(USER_ID.getColumnName()));
        certification.setName(rs.getString(NAME.getColumnName()));
        certification.setCreatedDate(dateFromJavaTime(rs.getObject(BaseColumnType.CREATED_DATE.getColumnName())));
        certification.setUpdatedDate(dateFromJavaTime(rs.getObject(BaseColumnType.UPDATED_DATE.getColumnName())));
        certification.setAuthority(rs.getString(AUTHORITY.getColumnName()));
        certification.setLicenseNumber(rs.getString(LICENSE_NUMBER.getColumnName()));
        certification.setAcquiredDate(dateFromJavaTime(rs.getObject(ACQUIRED_DATE.getColumnName())));
        certification.setExpiredDate(dateFromJavaTime(rs.getObject(EXPIRED_DATE.getColumnName())));
        certification.setWebsite(rs.getString(WEBSITE.getColumnName()));
        return certification;
    }
}