package edu.uwm.capstone.db.certification;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.certification.Certification;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import edu.uwm.capstone.util.TestDataUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static edu.uwm.capstone.sql.dao.BaseRowMapper.dateFromJavaTime;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.javaTimeFromDate;
import static edu.uwm.capstone.util.TestDataUtility.randomInt;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class CertificationDaoRowMapperUnitTest {

    @Autowired
    CertificationDaoRowMapper certificationDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(certificationDaoRowMapper);
    }

    /**
     * Verify that {@link CertificationDaoRowMapper.CertificationColumnType#values()} is working correctly.
     */
    @Test
    public void certificationColumnType() {
        for (CertificationDaoRowMapper.CertificationColumnType columnType : CertificationDaoRowMapper.CertificationColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link CertificationDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a certification object with test values
        Certification certification = TestDataUtility.certificationWithTestValues();
        certification.setId(TestDataUtility.randomLong());
        certification.setUserID(TestDataUtility.randomLong());
        certification.setCreatedDate(randomLocalDateTime());
        certification.setUpdatedDate(randomLocalDateTime());
        certification.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        certification.setAuthority(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        certification.setLicenseNumber(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        certification.setAcquiredDate(randomLocalDateTime());
        certification.setExpiredDate(randomLocalDateTime());
        certification.setWebsite(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        assertNotNull(certification);

        Map<String, Object> mapObject = certificationDaoRowMapper.mapObject(certification);
        assertNotNull(mapObject);

        assertEquals(certification.getId(), mapObject.get(BaseRowMapper.BaseColumnType.ID.getColumnName()));
        assertEquals(certification.getUserID(), mapObject.get(CertificationDaoRowMapper.CertificationColumnType.USER_ID.getColumnName()));
        assertEquals(certification.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(certification.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())));
        assertEquals(certification.getName(), mapObject.get(CertificationDaoRowMapper.CertificationColumnType.NAME.getColumnName()));
        assertEquals(certification.getAuthority(), mapObject.get(CertificationDaoRowMapper.CertificationColumnType.AUTHORITY.getColumnName()));
        assertEquals(certification.getLicenseNumber(), mapObject.get(CertificationDaoRowMapper.CertificationColumnType.LICENSE_NUMBER.getColumnName()));
        assertEquals(certification.getAcquiredDate(), dateFromJavaTime(mapObject.get(CertificationDaoRowMapper.CertificationColumnType.ACQUIRED_DATE.getColumnName())));
        assertEquals(certification.getExpiredDate(), dateFromJavaTime(mapObject.get(CertificationDaoRowMapper.CertificationColumnType.EXPIRED_DATE.getColumnName())));
        assertEquals(certification.getWebsite(), mapObject.get(CertificationDaoRowMapper.CertificationColumnType.WEBSITE.getColumnName()));
    }

    /**
     * Verify that {@link CertificationDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a certification object with test values
        Certification certification = TestDataUtility.certificationWithTestValues();
        certification.setId(TestDataUtility.randomLong());
        certification.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        certification.setWebsite(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        certification.setCreatedDate(randomLocalDateTime());
        certification.setUpdatedDate(randomLocalDateTime());
        assertNotNull(certification);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseRowMapper.BaseColumnType.ID.getColumnName())).thenReturn(certification.getId());
        when(resultSet.getLong(CertificationDaoRowMapper.CertificationColumnType.USER_ID.getColumnName())).thenReturn(certification.getUserID());
        when(resultSet.getString(CertificationDaoRowMapper.CertificationColumnType.NAME.getColumnName())).thenReturn(certification.getName());
        when(resultSet.getString(CertificationDaoRowMapper.CertificationColumnType.AUTHORITY.getColumnName())).thenReturn(certification.getAuthority());
        when(resultSet.getString(CertificationDaoRowMapper.CertificationColumnType.LICENSE_NUMBER.getColumnName())).thenReturn(certification.getLicenseNumber());
        when(resultSet.getObject(CertificationDaoRowMapper.CertificationColumnType.ACQUIRED_DATE.getColumnName())).thenReturn(javaTimeFromDate(certification.getAcquiredDate()));
        when(resultSet.getObject(CertificationDaoRowMapper.CertificationColumnType.EXPIRED_DATE.getColumnName())).thenReturn(javaTimeFromDate(certification.getExpiredDate()));
        when(resultSet.getString(CertificationDaoRowMapper.CertificationColumnType.WEBSITE.getColumnName())).thenReturn(certification.getWebsite());
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(certification.getCreatedDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(certification.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        Certification verifyCertification = certificationDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyCertification);

        assertEquals(certification.getId(), verifyCertification.getId());
        assertEquals(certification.getUserID(), verifyCertification.getUserID());
        assertEquals(certification.getCreatedDate(), verifyCertification.getCreatedDate());
        assertEquals(certification.getUpdatedDate(), verifyCertification.getUpdatedDate());
        assertEquals(certification.getName(), verifyCertification.getName());
        assertEquals(certification.getAuthority(), verifyCertification.getAuthority());
        assertEquals(certification.getLicenseNumber(), verifyCertification.getLicenseNumber());
        assertEquals(certification.getAcquiredDate(), verifyCertification.getAcquiredDate());
        assertEquals(certification.getExpiredDate(), verifyCertification.getExpiredDate());
        assertEquals(certification.getWebsite(), verifyCertification.getWebsite());
    }
}