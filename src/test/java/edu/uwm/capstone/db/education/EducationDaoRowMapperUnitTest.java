package edu.uwm.capstone.db.education;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import edu.uwm.capstone.model.education.Education;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.db.education.EducationDaoRowMapper.EducationColumnType;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import edu.uwm.capstone.util.TestDataUtility;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.dateFromJavaTime;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.javaTimeFromDate;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class EducationDaoRowMapperUnitTest {

    @Autowired
    EducationDaoRowMapper educationDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(educationDaoRowMapper);
    }

    /**
     * Verify that {@link EducationDaoRowMapper.EducationColumnType#values()} is working correctly.
     */
    @Test
    public void educationColumnType() {
        for (EducationDaoRowMapper.EducationColumnType columnType : EducationDaoRowMapper.EducationColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link EducationDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a education object with test values
        Education education = TestDataUtility.educationWithTestValues();
        education.setId(TestDataUtility.randomLong());
        education.setCreatedDate(randomLocalDateTime());
        education.setUpdatedDate(randomLocalDateTime());
        assertNotNull(education);

        Map<String, Object> mapObject = educationDaoRowMapper.mapObject(education);
        assertNotNull(mapObject);

        assertEquals(education.getId(), mapObject.get(BaseRowMapper.BaseColumnType.ID.getColumnName()));
        assertEquals(education.getUserID(), mapObject.get(EducationColumnType.USER_ID.getColumnName()));
        assertEquals(education.getStreet1(), mapObject.get(EducationColumnType.STREET_1.getColumnName()));
        assertEquals(education.getStreet2(), mapObject.get(EducationColumnType.STREET_2.getColumnName()));
        assertEquals(education.getCity(), mapObject.get(EducationColumnType.CITY.getColumnName()));
        assertEquals(education.getState(), mapObject.get(EducationColumnType.STATE.getColumnName()));
        assertEquals(education.getZipCode(), mapObject.get(EducationColumnType.ZIP_CODE.getColumnName()));
        assertEquals(education.getSchoolName(), mapObject.get(EducationColumnType.SCHOOL_NAME.getColumnName()));
        assertEquals(education.getDegree(), mapObject.get(EducationColumnType.DEGREE.getColumnName()));
        assertEquals(education.getFieldOfStudy(), mapObject.get(EducationColumnType.FIELD_OF_STUDY.getColumnName()));
        assertEquals(education.getStartDate(), dateFromJavaTime(mapObject.get(EducationColumnType.START_DATE.getColumnName())));
        assertEquals(education.getEndDate(), dateFromJavaTime(mapObject.get(EducationColumnType.END_DATE.getColumnName())));
        assertEquals(education.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(education.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())));
    }

    /**
     * Verify that {@link EducationDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a education object with test values
        Education education = TestDataUtility.educationWithTestValues();
        education.setId(TestDataUtility.randomLong());
        education.setCreatedDate(randomLocalDateTime());
        education.setUpdatedDate(randomLocalDateTime());
        assertNotNull(education);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseRowMapper.BaseColumnType.ID.getColumnName())).thenReturn(education.getId());
        when(resultSet.getLong(EducationColumnType.USER_ID.getColumnName())).thenReturn(education.getUserID());
        when(resultSet.getString(EducationColumnType.STREET_1.getColumnName())).thenReturn(education.getStreet1());
        when(resultSet.getString(EducationColumnType.STREET_2.getColumnName())).thenReturn(education.getStreet2());
        when(resultSet.getString(EducationColumnType.CITY.getColumnName())).thenReturn(education.getCity());
        when(resultSet.getString(EducationColumnType.STATE.getColumnName())).thenReturn(education.getState());
        when(resultSet.getString(EducationColumnType.ZIP_CODE.getColumnName())).thenReturn(education.getZipCode());
        when(resultSet.getString(EducationColumnType.SCHOOL_NAME.getColumnName())).thenReturn(education.getSchoolName());
        when(resultSet.getString(EducationColumnType.DEGREE.getColumnName())).thenReturn(education.getDegree());
        when(resultSet.getString(EducationColumnType.FIELD_OF_STUDY.getColumnName())).thenReturn(education.getFieldOfStudy());
        when(resultSet.getObject(EducationColumnType.START_DATE.getColumnName())).thenReturn(javaTimeFromDate(education.getStartDate()));
        when(resultSet.getObject(EducationColumnType.END_DATE.getColumnName())).thenReturn(javaTimeFromDate(education.getEndDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(education.getCreatedDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(education.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        Education verifyEducation = educationDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyEducation);

        assertEquals(verifyEducation.getId(), verifyEducation.getId());
        assertEquals(education.getUserID(), verifyEducation.getUserID());
        assertEquals(education.getStreet1(), verifyEducation.getStreet1());
        assertEquals(education.getStreet2(), verifyEducation.getStreet2());
        assertEquals(education.getCity(), verifyEducation.getCity());
        assertEquals(education.getState(), verifyEducation.getState());
        assertEquals(education.getZipCode(), verifyEducation.getZipCode());
        assertEquals(education.getSchoolName(), verifyEducation.getSchoolName());
        assertEquals(education.getDegree(), verifyEducation.getDegree());
        assertEquals(education.getFieldOfStudy(), verifyEducation.getFieldOfStudy());
        assertEquals(education.getStartDate(), verifyEducation.getStartDate());
        assertEquals(education.getEndDate(), verifyEducation.getEndDate());
        assertEquals(education.getCreatedDate(), verifyEducation.getCreatedDate());
        assertEquals(education.getUpdatedDate(), verifyEducation.getUpdatedDate());
    }

}