package edu.uwm.capstone.db.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.company.Company;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import edu.uwm.capstone.util.TestDataUtility;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.dateFromJavaTime;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.javaTimeFromDate;
import static edu.uwm.capstone.util.TestDataUtility.randomInt;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class CompanyDaoRowMapperUnitTest {

    @Autowired
    CompanyDaoRowMapper companyDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(companyDaoRowMapper);
    }

    /**
     * Verify that {@link CompanyDaoRowMapper.CompanyColumnType#values()} is working correctly.
     */
    @Test
    public void companyColumnType() {
        for (CompanyDaoRowMapper.CompanyColumnType columnType : CompanyDaoRowMapper.CompanyColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link CompanyDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a company object with test values
        Company company = TestDataUtility.companyWithTestValues();
        company.setId(TestDataUtility.randomLong());
        company.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        company.setPhoneNumber(TestDataUtility.randomNumeric(randomInt(1, 100)));
        company.setWebsite(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        company.setCreatedDate(randomLocalDateTime());
        company.setUpdatedDate(randomLocalDateTime());
        assertNotNull(company);

        Map<String, Object> mapObject = companyDaoRowMapper.mapObject(company);
        assertNotNull(mapObject);

        assertEquals(company.getId(), mapObject.get(BaseRowMapper.BaseColumnType.ID.getColumnName()));
        assertEquals(company.getUserID(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.USER_ID.getColumnName()));
        assertEquals(company.getName(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.NAME.getColumnName()));
        assertEquals(company.getStreet1(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.STREET_1.getColumnName()));
        assertEquals(company.getStreet2(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.STREET_2.getColumnName()));
        assertEquals(company.getCity(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.CITY.getColumnName()));
        assertEquals(company.getState(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.STATE.getColumnName()));
        assertEquals(company.getZipCode(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.ZIP_CODE.getColumnName()));
        assertEquals(company.getPhoneNumber(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.PHONE_NUMBER.getColumnName()));
        assertEquals(company.getWebsite(), mapObject.get(CompanyDaoRowMapper.CompanyColumnType.WEBSITE.getColumnName()));
        assertEquals(company.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(company.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())));
    }

    /**
     * Verify that {@link CompanyDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a company object with test values
        Company company = TestDataUtility.companyWithTestValues();
        company.setId(TestDataUtility.randomLong());
        company.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        company.setPhoneNumber(TestDataUtility.randomNumeric(randomInt(1, 100)));
        company.setWebsite(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        company.setCreatedDate(randomLocalDateTime());
        company.setUpdatedDate(randomLocalDateTime());
        assertNotNull(company);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseRowMapper.BaseColumnType.ID.getColumnName())).thenReturn(company.getId());
        when(resultSet.getLong(CompanyDaoRowMapper.CompanyColumnType.USER_ID.getColumnName())).thenReturn(company.getUserID());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.NAME.getColumnName())).thenReturn(company.getName());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.STREET_1.getColumnName())).thenReturn(company.getStreet1());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.STREET_2.getColumnName())).thenReturn(company.getStreet2());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.CITY.getColumnName())).thenReturn(company.getCity());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.STATE.getColumnName())).thenReturn(company.getState());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.ZIP_CODE.getColumnName())).thenReturn(company.getZipCode());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.PHONE_NUMBER.getColumnName())).thenReturn(company.getPhoneNumber());
        when(resultSet.getString(CompanyDaoRowMapper.CompanyColumnType.WEBSITE.getColumnName())).thenReturn(company.getWebsite());
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(company.getCreatedDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(company.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        Company verifyCompany = companyDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyCompany);

        assertEquals(company.getId(), verifyCompany.getId());
        assertEquals(company.getUserID(), verifyCompany.getUserID());
        assertEquals(company.getName(), verifyCompany.getName());
        assertEquals(company.getStreet1(), verifyCompany.getStreet1());
        assertEquals(company.getStreet2(), verifyCompany.getStreet2());
        assertEquals(company.getCity(), verifyCompany.getCity());
        assertEquals(company.getState(), verifyCompany.getState());
        assertEquals(company.getZipCode(), verifyCompany.getZipCode());
        assertEquals(company.getPhoneNumber(), verifyCompany.getPhoneNumber());
        assertEquals(company.getWebsite(), verifyCompany.getWebsite());
        assertEquals(company.getCreatedDate(), verifyCompany.getCreatedDate());
        assertEquals(company.getUpdatedDate(), verifyCompany.getUpdatedDate());
    }

}