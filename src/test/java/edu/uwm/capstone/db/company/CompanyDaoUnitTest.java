package edu.uwm.capstone.db.company;

import java.util.List;
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
import edu.uwm.capstone.model.company.Company;
import edu.uwm.capstone.util.TestDataUtility;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class CompanyDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CompanyDao companyDao;

    @Before
    public void setUp() {
        assertNotNull(companyDao);
        assertNotNull(companyDao.sql("createCompany"));
        assertNotNull(companyDao.sql("readCompany"));
        assertNotNull(companyDao.sql("readManyCompanies"));
        assertNotNull(companyDao.sql("updateCompany"));
        assertNotNull(companyDao.sql("deleteCompany"));
    }

    /**
     * Verify that {@link CompanyDao#create} is working correctly.
     */
    @Test
    public void create() {
        Company createCompany = TestDataUtility.companyWithTestValues();
        companyDao.create(createCompany);
        assertNotNull(createCompany.getId());
    }

    /**
     * Verify that {@link CompanyDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullCompany() {
        companyDao.create(null);
    }

    /**
     * Verify that {@link CompanyDao#create} is working correctly when a request for a {@link Company} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullCompanyId() {
        Company createCompany = TestDataUtility.companyWithTestValues();
        createCompany.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        companyDao.create(createCompany);
    }

    /**
     * Verify that {@link CompanyDao#create} is working correctly when a request for a {@link Company} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createCompanyColumnTooLong() {
        // generate a test company value with a column that will exceed the database configuration
        Company createCompany = TestDataUtility.companyWithTestValues();
        createCompany.setName(RandomStringUtils.randomAlphabetic(2000));
        companyDao.create(createCompany);
    }

    /**
     * Verify that {@link CompanyDao#read} is working correctly.
     */
    @Test
    public void read() {
        Company createCompany = TestDataUtility.companyWithTestValues();
        companyDao.create(createCompany);
        assertNotNull(createCompany.getId());

        Company readCompany = companyDao.read(createCompany.getId());
        assertNotNull(readCompany);
        assertEquals(createCompany.getId(), readCompany.getId());
        assertEquals(createCompany, readCompany);
    }

    /**
     * Verify that {@link CompanyDao#readMany} is working correctly.
     */
    @Test
    public void readMany() {
        Long userID = new Long(12345);
        Company company = TestDataUtility.companyWithTestValues();
        company.setUserID(userID);
        companyDao.create(company);
        assertNotNull(company.getId());

        Company company2 = TestDataUtility.companyWithTestValues();
        company2.setUserID(userID);
        companyDao.create(company2);
        assertNotNull(company2.getId());

        Company company3 = TestDataUtility.companyWithTestValues();
        company3.setUserID(new Long(6789));
        companyDao.create(company3);
        assertNotEquals(userID, company3.getUserID());

        List list = companyDao.readMany(userID);
        assertNotNull(list);
        assertEquals(list.size(), 2);
    }

    /**
     * Verify that {@link CompanyDao#read} is working correctly when a request for a non-existent {@link Company#id} is made.
     */
    @Test
    public void readNonExistentCompany() {
        // create a random company id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Company company = companyDao.read(id);
        assertNull(company);
    }

    /**
     * Verify that {@link CompanyDao#update} is working correctly.
     */
    @Test
    public void update() {
        Company createCompany = TestDataUtility.companyWithTestValues();
        companyDao.create(createCompany);
        assertNotNull(createCompany.getId());

        Company verifyCreateCompany = companyDao.read(createCompany.getId());
        assertNotNull(verifyCreateCompany);
        assertEquals(createCompany.getId(), verifyCreateCompany.getId());
        assertEquals(createCompany, verifyCreateCompany);

        Company updateCompany = TestDataUtility.companyWithTestValues();
        updateCompany.setId(createCompany.getId());
        companyDao.update(updateCompany);

        Company verifyUpdateCompany = companyDao.read(updateCompany.getId());
        assertNotNull(verifyUpdateCompany);
        assertEquals(createCompany.getId(), verifyUpdateCompany.getId());
        assertEquals(updateCompany.getName(), verifyUpdateCompany.getName());
    }

    /**
     * Verify that {@link CompanyDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullCompany() {
        companyDao.update(null);
    }

    /**
     * Verify that {@link CompanyDao#update} is working correctly when a request for a non-existent {@link Company#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentCompany() {
        // create a random company id that will not be in our local database
        Company updateCompany = TestDataUtility.companyWithTestValues();
        updateCompany.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        companyDao.update(updateCompany);
    }

    /**
     * Verify that {@link CompanyDao#update} is working correctly when a request for a {@link Company} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateCompanyColumnTooLong() {
        // generate a test company value with a column that will exceed the database configuration
        Company createCompany = TestDataUtility.companyWithTestValues();
        companyDao.create(createCompany);
        assertNotNull(createCompany.getId());

        Company verifyCreateCompany = companyDao.read(createCompany.getId());
        assertNotNull(verifyCreateCompany);
        assertEquals(createCompany.getId(), verifyCreateCompany.getId());
        assertEquals(createCompany, verifyCreateCompany);

        Company updateCompany = TestDataUtility.companyWithTestValues();
        updateCompany.setId(createCompany.getId());
        updateCompany.setName(RandomStringUtils.randomAlphabetic(2000));
        companyDao.update(updateCompany);
    }

    /**
     * Verify that {@link CompanyDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Company createCompany = TestDataUtility.companyWithTestValues();
        companyDao.create(createCompany);
        assertNotNull(createCompany.getId());

        Company verifyCreateCompany = companyDao.read(createCompany.getId());
        assertNotNull(verifyCreateCompany);
        assertEquals(createCompany.getId(), verifyCreateCompany.getId());
        assertEquals(createCompany, verifyCreateCompany);

        companyDao.delete(createCompany.getId());

        Company verifyDeleteCompany = companyDao.read(createCompany.getId());
        assertNull(verifyDeleteCompany);
    }

    /**
     * Verify that {@link CompanyDao#delete} is working correctly when a request for a non-existent {@link Company#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentCompany() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        companyDao.delete(id);
    }

}