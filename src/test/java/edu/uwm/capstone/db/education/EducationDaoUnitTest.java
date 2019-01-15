package edu.uwm.capstone.db.education;

import java.util.Random;

import edu.uwm.capstone.model.education.Education;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.util.TestDataUtility;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class EducationDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EducationDao educationDao;

    @Before
    public void setUp() {
        assertNotNull(educationDao);
        assertNotNull(educationDao.sql("createEducation"));
        assertNotNull(educationDao.sql("readEducation"));
        assertNotNull(educationDao.sql("updateEducation"));
        assertNotNull(educationDao.sql("deleteEducation"));
    }

    /**
     * Verify that {@link EducationDao#create} is working correctly.
     */
    @Test
    public void create() {
        Education createEducation = TestDataUtility.educationWithTestValues();
        educationDao.create(createEducation);
        assertNotNull(createEducation.getId());
    }

    /**
     * Verify that {@link EducationDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullEducation() {
        educationDao.create(null);
    }

    /**
     * Verify that {@link EducationDao#create} is working correctly when a request for a {@link Education} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullEducationId() {
        Education createEducation = TestDataUtility.educationWithTestValues();
        createEducation.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        educationDao.create(createEducation);
    }

    /**
     * Verify that {@link EducationDao#create} is working correctly when a request for a {@link Education} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createEducationColumnTooLong() {
        // generate a test education value with a column that will exceed the database configuration
        Education createEducation = TestDataUtility.educationWithTestValues();
        createEducation.setSchoolName(RandomStringUtils.randomAlphabetic(2000));
        educationDao.create(createEducation);
    }

    /**
     * Verify that {@link EducationDao#read} is working correctly.
     */
    @Test
    public void read() {
        Education createEducation = TestDataUtility.educationWithTestValues();
        educationDao.create(createEducation);
        assertNotNull(createEducation.getId());

        Education readEducation = educationDao.read(createEducation.getId());
        assertNotNull(readEducation);
        assertEquals(createEducation.getId(), readEducation.getId());
        assertEquals(createEducation, readEducation);
    }

    /**
     * Verify that {@link EducationDao#read} is working correctly when a request for a non-existent {@link Education#id} is made.
     */
    @Test
    public void readNonExistentEducation() {
        // create a random education id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Education education = educationDao.read(id);
        assertNull(education);
    }

    /**
     * Verify that {@link EducationDao#update} is working correctly.
     */
    @Test
    public void update() {
        Education createEducation = TestDataUtility.educationWithTestValues();
        educationDao.create(createEducation);
        assertNotNull(createEducation.getId());

        Education verifyCreateEducation = educationDao.read(createEducation.getId());
        assertNotNull(verifyCreateEducation);
        assertEquals(createEducation.getId(), verifyCreateEducation.getId());
        assertEquals(createEducation, verifyCreateEducation);

        Education updateEducation = TestDataUtility.educationWithTestValues();
        updateEducation.setId(createEducation.getId());
        educationDao.update(updateEducation);

        Education verifyUpdateEducation = educationDao.read(updateEducation.getId());
        assertNotNull(verifyUpdateEducation);
        assertEquals(createEducation.getId(), verifyUpdateEducation.getId());
        assertEquals(updateEducation.getUserID(), verifyUpdateEducation.getUserID());
        assertEquals(updateEducation.getStreet1(), verifyUpdateEducation.getStreet1());
        assertEquals(updateEducation.getStreet2(), verifyUpdateEducation.getStreet2());
        assertEquals(updateEducation.getCity(), verifyUpdateEducation.getCity());
        assertEquals(updateEducation.getState(), verifyUpdateEducation.getState());
        assertEquals(updateEducation.getZipCode(), verifyUpdateEducation.getZipCode());
        assertEquals(updateEducation.getSchoolName(), verifyUpdateEducation.getSchoolName());
        assertEquals(updateEducation.getDegree(), verifyUpdateEducation.getDegree());
        assertEquals(updateEducation.getFieldOfStudy(), verifyUpdateEducation.getFieldOfStudy());
        assertEquals(updateEducation.getStartDate(), verifyUpdateEducation.getStartDate());
        assertEquals(updateEducation.getEndDate(), verifyUpdateEducation.getEndDate());
        assertEquals(updateEducation.getStartDate(), verifyUpdateEducation.getStartDate());
        assertEquals(updateEducation.getUpdatedDate(), verifyUpdateEducation.getUpdatedDate());
    }

    /**
     * Verify that {@link EducationDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullEducation() {
        educationDao.update(null);
    }

    /**
     * Verify that {@link EducationDao#update} is working correctly when a request for a non-existent {@link Education#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentEducation() {
        // create a random education id that will not be in our local database
        Education updateEducation = TestDataUtility.educationWithTestValues();
        updateEducation.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        educationDao.update(updateEducation);
    }

    /**
     * Verify that {@link EducationDao#update} is working correctly when a request for a {@link Education} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateEducationColumnTooLong() {
        // generate a test education value with a column that will exceed the database configuration
        Education createEducation = TestDataUtility.educationWithTestValues();
        educationDao.create(createEducation);
        assertNotNull(createEducation.getId());

        Education verifyCreateEducation = educationDao.read(createEducation.getId());
        assertNotNull(verifyCreateEducation);
        assertEquals(createEducation.getId(), verifyCreateEducation.getId());
        assertEquals(createEducation, verifyCreateEducation);

        Education updateEducation = TestDataUtility.educationWithTestValues();
        updateEducation.setId(createEducation.getId());
        updateEducation.setSchoolName(RandomStringUtils.randomAlphabetic(2000));
        educationDao.update(updateEducation);
    }

    /**
     * Verify that {@link EducationDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Education createEducation = TestDataUtility.educationWithTestValues();
        educationDao.create(createEducation);
        assertNotNull(createEducation.getId());

        Education verifyCreateEducation = educationDao.read(createEducation.getId());
        assertNotNull(verifyCreateEducation);
        assertEquals(createEducation.getId(), verifyCreateEducation.getId());
        assertEquals(createEducation, verifyCreateEducation);

        educationDao.delete(createEducation.getId());

        Education verifyDeleteEducation = educationDao.read(createEducation.getId());
        assertNull(verifyDeleteEducation);
    }

    /**
     * Verify that {@link EducationDao#delete} is working correctly when a request for a non-existent {@link Education#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentEducation() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        educationDao.delete(id);
    }

}
