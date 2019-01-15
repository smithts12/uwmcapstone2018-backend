package edu.uwm.capstone.db.project;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.project.Project;
import edu.uwm.capstone.util.TestDataUtility;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class ProjectDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProjectDao projectDao;

    @Before
    public void setUp() {
        assertNotNull(projectDao);
        assertNotNull(projectDao.sql("createProject"));
        assertNotNull(projectDao.sql("readProject"));
        assertNotNull(projectDao.sql("updateProject"));
        assertNotNull(projectDao.sql("deleteProject"));
    }

    /**
     * Verify that {@link ProjectDao#create} is working correctly.
     */
    @Test
    public void create() {
        Project createProject = TestDataUtility.projectWithTestValues();
        projectDao.create(createProject);
        assertNotNull(createProject.getId());
    }

    /**
     * Verify that {@link ProjectDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullProject() {
        projectDao.create(null);
    }

    /**
     * Verify that {@link ProjectDao#create} is working correctly when a request for a {@link Project} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullProjectId() {
        Project createProject = TestDataUtility.projectWithTestValues();
        createProject.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        projectDao.create(createProject);
    }

    /**
     * Verify that {@link ProjectDao#create} is working correctly when a request for a {@link Project} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createProjectColumnTooLong() {
        // generate a test project value with a column that will exceed the database configuration
        Project createProject = TestDataUtility.projectWithTestValues();
        createProject.setTitle(RandomStringUtils.randomAlphabetic(2000));
        projectDao.create(createProject);
    }

    /**
     * Verify that {@link ProjectDao#read} is working correctly.
     */
    @Test
    public void read() {
        Project createProject = TestDataUtility.projectWithTestValues();
        projectDao.create(createProject);
        assertNotNull(createProject.getId());

        Project readProject = projectDao.read(createProject.getId());
        assertNotNull(readProject);
        assertEquals(createProject.getId(), readProject.getId());
        assertEquals(createProject, readProject);
    }

    /**
     * Verify that {@link ProjectDao#read} is working correctly when a request for a non-existent {@link Project#id} is made.
     */
    @Test
    public void readNonExistentProject() {
        // create a random project id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Project project = projectDao.read(id);
        assertNull(project);
    }

    /**
     * Verify that {@link ProjectDao#update} is working correctly.
     */
    @Test
    public void update() {
        Project createProject = TestDataUtility.projectWithTestValues();
        projectDao.create(createProject);
        assertNotNull(createProject.getId());

        Project verifyCreateProject = projectDao.read(createProject.getId());
        assertNotNull(verifyCreateProject);
        assertEquals(createProject.getId(), verifyCreateProject.getId());
        assertEquals(createProject, verifyCreateProject);

        Project updateProject = TestDataUtility.projectWithTestValues();
        updateProject.setId(createProject.getId());
        projectDao.update(updateProject);

        Project verifyUpdateProject = projectDao.read(updateProject.getId());
        assertNotNull(verifyUpdateProject);
        assertEquals(createProject.getId(), verifyUpdateProject.getId());
        assertEquals(updateProject.getTitle(), verifyUpdateProject.getTitle());
    }

    /**
     * Verify that {@link ProjectDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullProject() {
        projectDao.update(null);
    }

    /**
     * Verify that {@link ProjectDao#update} is working correctly when a request for a non-existent {@link Project#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentProject() {
        // create a random project id that will not be in our local database
        Project updateProject = TestDataUtility.projectWithTestValues();
        updateProject.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        projectDao.update(updateProject);
    }

    /**
     * Verify that {@link ProjectDao#update} is working correctly when a request for a {@link Project} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateProjectColumnTooLong() {
        // generate a test project value with a column that will exceed the database configuration
        Project createProject = TestDataUtility.projectWithTestValues();
        projectDao.create(createProject);
        assertNotNull(createProject.getId());

        Project verifyCreateProject = projectDao.read(createProject.getId());
        assertNotNull(verifyCreateProject);
        assertEquals(createProject.getId(), verifyCreateProject.getId());
        assertEquals(createProject, verifyCreateProject);

        Project updateProject = TestDataUtility.projectWithTestValues();
        updateProject.setId(createProject.getId());
        updateProject.setTitle(RandomStringUtils.randomAlphabetic(2000));
        projectDao.update(updateProject);
    }

    /**
     * Verify that {@link ProjectDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Project createProject = TestDataUtility.projectWithTestValues();
        projectDao.create(createProject);
        assertNotNull(createProject.getId());

        Project verifyCreateProject = projectDao.read(createProject.getId());
        assertNotNull(verifyCreateProject);
        assertEquals(createProject.getId(), verifyCreateProject.getId());
        assertEquals(createProject, verifyCreateProject);

        projectDao.delete(createProject.getId());

        Project verifyDeleteProject = projectDao.read(createProject.getId());
        assertNull(verifyDeleteProject);
    }

    /**
     * Verify that {@link ProjectDao#delete} is working correctly when a request for a non-existent {@link Project#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentProject() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        projectDao.delete(id);
    }

}

