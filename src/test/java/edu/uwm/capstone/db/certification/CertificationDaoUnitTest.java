package edu.uwm.capstone.db.certification;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.db.certification.CertificationDao;
import edu.uwm.capstone.model.certification.Certification;
import edu.uwm.capstone.util.TestDataUtility;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class CertificationDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CertificationDao certificationDao;

    @Before
    public void setUp() {
        assertNotNull(certificationDao);
        assertNotNull(certificationDao.sql("createCertification"));
        assertNotNull(certificationDao.sql("readCertification"));
        assertNotNull(certificationDao.sql("updateCertification"));
        assertNotNull(certificationDao.sql("deleteCertification"));
    }

    /**
     * Verify that {@link CertificationDao#create} is working correctly.
     */
    @Test
    public void create() {
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        certificationDao.create(createCertification);
        assertNotNull(createCertification.getId());
    }

    /**
     * Verify that {@link CertificationDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullCertification() {
        certificationDao.create(null);
    }

    /**
     * Verify that {@link CertificationDao#create} is working correctly when a request for a {@link Certification} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullCertificationId() {
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        createCertification.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        certificationDao.create(createCertification);
    }

    /**
     * Verify that {@link CertificationDao#create} is working correctly when a request for a {@link Certification} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createCertificationColumnTooLong() {
        // generate a test certification value with a column that will exceed the database configuration
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        createCertification.setName(RandomStringUtils.randomAlphabetic(2000));
        certificationDao.create(createCertification);
    }

    /**
     * Verify that {@link CertificationDao#read} is working correctly.
     */
    @Test
    public void read() {
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        certificationDao.create(createCertification);
        assertNotNull(createCertification.getId());

        Certification readCertification = certificationDao.read(createCertification.getId());
        assertNotNull(readCertification);
        assertEquals(createCertification.getId(), readCertification.getId());
        assertEquals(createCertification, readCertification);
    }

    /**
     * Verify that {@link CertificationDao#read} is working correctly when a request for a non-existent {@link Certification#id} is made.
     */
    @Test
    public void readNonExistentCertification() {
        // create a random certification id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Certification certification = certificationDao.read(id);
        assertNull(certification);
    }

    /**
     * Verify that {@link CertificationDao#update} is working correctly.
     */
    @Test
    public void update() {
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        certificationDao.create(createCertification);
        assertNotNull(createCertification.getId());

        Certification verifyCreateCertification = certificationDao.read(createCertification.getId());
        assertNotNull(verifyCreateCertification);
        assertEquals(createCertification.getId(), verifyCreateCertification.getId());
        assertEquals(createCertification, verifyCreateCertification);

        Certification updateCertification = TestDataUtility.certificationWithTestValues();
        updateCertification.setId(createCertification.getId());
        certificationDao.update(updateCertification);

        Certification verifyUpdateCertification = certificationDao.read(updateCertification.getId());
        assertNotNull(verifyUpdateCertification);
        assertEquals(createCertification.getId(), verifyUpdateCertification.getId());
        assertEquals(updateCertification.getName(), verifyUpdateCertification.getName());
    }

    /**
     * Verify that {@link CertificationDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullCertification() {
        certificationDao.update(null);
    }

    /**
     * Verify that {@link CertificationDao#update} is working correctly when a request for a non-existent {@link Certification#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentCertification() {
        // create a random certification id that will not be in our local database
        Certification updateCertification = TestDataUtility.certificationWithTestValues();
        updateCertification.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        certificationDao.update(updateCertification);
    }

    /**
     * Verify that {@link CertificationDao#update} is working correctly when a request for a {@link Certification} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateCertificationColumnTooLong() {
        // generate a test certification value with a column that will exceed the database configuration
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        certificationDao.create(createCertification);
        assertNotNull(createCertification.getId());

        Certification verifyCreateCertification = certificationDao.read(createCertification.getId());
        assertNotNull(verifyCreateCertification);
        assertEquals(createCertification.getId(), verifyCreateCertification.getId());
        assertEquals(createCertification, verifyCreateCertification);

        Certification updateCertification = TestDataUtility.certificationWithTestValues();
        updateCertification.setId(createCertification.getId());
        updateCertification.setName(RandomStringUtils.randomAlphabetic(2000));
        certificationDao.update(updateCertification);
    }

    /**
     * Verify that {@link CertificationDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Certification createCertification = TestDataUtility.certificationWithTestValues();
        certificationDao.create(createCertification);
        assertNotNull(createCertification.getId());

        Certification verifyCreateCertification = certificationDao.read(createCertification.getId());
        assertNotNull(verifyCreateCertification);
        assertEquals(createCertification.getId(), verifyCreateCertification.getId());
        assertEquals(createCertification, verifyCreateCertification);

        certificationDao.delete(createCertification.getId());

        Certification verifyDeleteCertification = certificationDao.read(createCertification.getId());
        assertNull(verifyDeleteCertification);
    }

    /**
     * Verify that {@link CertificationDao#delete} is working correctly when a request for a non-existent {@link Certification#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentCertification() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        certificationDao.delete(id);
    }
}