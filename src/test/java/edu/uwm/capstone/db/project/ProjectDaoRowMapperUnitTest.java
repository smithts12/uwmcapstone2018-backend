package edu.uwm.capstone.db.project;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.db.ProfileDaoRowMapper;
import edu.uwm.capstone.model.project.Project;
import edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType;
import edu.uwm.capstone.db.project.ProjectDaoRowMapper.ProjectColumnType;
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
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class ProjectDaoRowMapperUnitTest {

    @Autowired
    ProjectDaoRowMapper projectDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(projectDaoRowMapper);
    }

    /**
     * Verify that {@link ProjectDaoRowMapper.ProjectColumnType#values()} is working correctly.
     */
    @Test
    public void projectColumnType() {
        for (ProjectDaoRowMapper.ProjectColumnType columnType : ProjectDaoRowMapper.ProjectColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link ProjectDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a project object with test values
        Project project = TestDataUtility.projectWithTestValues();
        project.setId(TestDataUtility.randomLong());
        project.setCreatedDate(randomLocalDateTime());
        project.setUpdatedDate(randomLocalDateTime());
        assertNotNull(project);

        projectDaoRowMapper.mapObject(project);
        Map<String, Object> mapObject = projectDaoRowMapper.mapObject(project);
        assertNotNull(mapObject);
        assertEquals(project.getId(), mapObject.get(BaseColumnType.ID.getColumnName()));
        assertEquals(project.getUserID(), mapObject.get(ProjectColumnType.USER_ID.getColumnName()));
        assertEquals(project.getPositionID(), mapObject.get(ProjectColumnType.POSITION_ID.getColumnName()));
        assertEquals(project.getEducationID(), mapObject.get(ProjectColumnType.EDUCATION_ID.getColumnName()));
        assertEquals(project.getTitle(), mapObject.get(ProjectColumnType.TITLE.getColumnName()));
        assertEquals(project.getDescription(), mapObject.get(ProjectColumnType.DESCRIPTION.getColumnName()));
        assertEquals(project.getStartDate(), dateFromJavaTime(mapObject.get(ProjectColumnType.START_DATE.getColumnName())));
        assertEquals(project.getEndDate(), dateFromJavaTime(mapObject.get(ProjectColumnType.END_DATE.getColumnName())));
        assertEquals(project.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(project.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseColumnType.UPDATED_DATE.getColumnName())));
    }

    /**
     * Verify that {@link ProjectDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a project object with test values
        Project project = TestDataUtility.projectWithTestValues();
        project.setId(TestDataUtility.randomLong());
        project.setCreatedDate(randomLocalDateTime());
        project.setUpdatedDate(randomLocalDateTime());
        assertNotNull(project);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseColumnType.ID.getColumnName())).thenReturn(project.getId());
        when(resultSet.getLong(ProjectColumnType.USER_ID.getColumnName())).thenReturn(project.getUserID());
        when(resultSet.getLong(ProjectColumnType.POSITION_ID.getColumnName())).thenReturn(project.getPositionID());
        when(resultSet.getLong(ProjectColumnType.EDUCATION_ID.getColumnName())).thenReturn(project.getEducationID());
        when(resultSet.getString(ProjectColumnType.TITLE.getColumnName())).thenReturn(project.getTitle());
        when(resultSet.getString(ProjectColumnType.DESCRIPTION.getColumnName())).thenReturn(project.getDescription());
        when(resultSet.getObject(ProjectColumnType.START_DATE.getColumnName())).thenReturn(javaTimeFromDate(project.getStartDate()));
        when(resultSet.getObject(ProjectColumnType.END_DATE.getColumnName())).thenReturn(javaTimeFromDate(project.getEndDate()));
        when(resultSet.getObject(BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(project.getCreatedDate()));
        when(resultSet.getObject(BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(project.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        Project verifyProject = projectDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyProject);

        assertEquals(project.getId(), verifyProject.getId());
        assertEquals(project.getUserID(), verifyProject.getUserID());
        assertEquals(project.getPositionID(), verifyProject.getPositionID());
        assertEquals(project.getEducationID(), verifyProject.getEducationID());
        assertEquals(project.getTitle(), verifyProject.getTitle());
        assertEquals(project.getDescription(), verifyProject.getDescription());
        assertEquals(project.getStartDate(), verifyProject.getStartDate());
        assertEquals(project.getEndDate(), verifyProject.getEndDate());
        assertEquals(project.getCreatedDate(), verifyProject.getCreatedDate());
        assertEquals(project.getUpdatedDate(), verifyProject.getUpdatedDate());
    }

}
