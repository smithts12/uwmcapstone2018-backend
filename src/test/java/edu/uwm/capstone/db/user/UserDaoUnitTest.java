
package edu.uwm.capstone.db.user;

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
import edu.uwm.capstone.db.user.UserDao;
import edu.uwm.capstone.db.user.UserDaoRowMapper;
import edu.uwm.capstone.model.user.User;
import edu.uwm.capstone.util.TestDataUtility;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class UserDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserDao userDao;

    @Before
    public void setUp() {
        assertNotNull(userDao);
        assertNotNull(userDao.sql("createUser"));
        assertNotNull(userDao.sql("readUser"));
        assertNotNull(userDao.sql("updateUser"));
        assertNotNull(userDao.sql("deleteUser"));
    }

    /**
     * Verify that {@link UserDao#create} is working correctly.
     */
    @Test
    public void create() {
        User createUser = TestDataUtility.userWithTestValues();
        userDao.create(createUser);
        assertNotNull(createUser.getId());
    }

    /**
     * Verify that {@link UserDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullUser() {
        userDao.create(null);
    }

    /**
     * Verify that {@link UserDao#create} is working correctly when a request for a {@link User} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullUserId() {
        User createUser = TestDataUtility.userWithTestValues();
        createUser.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        userDao.create(createUser);
    }

    /**
     * Verify that {@link UserDao#create} is working correctly when a request for a {@link User} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createUserColumnTooLong() {
        // generate a test user value with a column that will exceed the database configuration
        User createUser = TestDataUtility.userWithTestValues();
        createUser.setFirstName(TestDataUtility.randomAlphabetic(10000));
        userDao.create(createUser);
    }

    /**
     * Verify that {@link UserDao#read} is working correctly.
     */
    @Test
    public void read() {
        User createUser = TestDataUtility.userWithTestValues();
        userDao.create(createUser);
        assertNotNull(createUser.getId());

        User readUser = userDao.read(createUser.getId());
        assertNotNull(readUser);
        assertEquals(createUser.getId(), readUser.getId());
        assertEquals(createUser, readUser);
    }

    /**
     * Verify that {@link UserDao#read} is working correctly when a request for a non-existent {@link User#id} is made.
     */
    @Test
    public void readNonExistentUser() {
        // create a random user id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        User user = userDao.read(id);
        assertNull(user);
    }

    /**
     * Verify that {@link UserDao#update} is working correctly.
     */
    @Test
    public void update() {
        User createUser = TestDataUtility.userWithTestValues();
        userDao.create(createUser);
        assertNotNull(createUser.getId());

        User verifyCreateUser = userDao.read(createUser.getId());
        assertNotNull(verifyCreateUser);
        assertEquals(createUser.getId(), verifyCreateUser.getId());
        assertEquals(createUser, verifyCreateUser);

        User updateUser = TestDataUtility.userWithTestValues();
        updateUser.setId(createUser.getId());
        userDao.update(updateUser);

        User verifyUpdateUser = userDao.read(updateUser.getId());
        assertNotNull(verifyUpdateUser);
        assertEquals(createUser.getId(), verifyUpdateUser.getId());
    }

    /**
     * Verify that {@link UserDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullUser() {
        userDao.update(null);
    }

    /**
     * Verify that {@link UserDao#update} is working correctly when a request for a non-existent {@link User#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentUser() {
        // create a random user id that will not be in our local database
        User updateUser = TestDataUtility.userWithTestValues();
        updateUser.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        userDao.update(updateUser);
    }

    /**
     * Verify that {@link UserDao#update} is working correctly when a request for a {@link User} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateUserColumnTooLong() {
        // generate a test user value with a column that will exceed the database configuration
        User createUser = TestDataUtility.userWithTestValues();
        userDao.create(createUser);
        assertNotNull(createUser.getId());

        User verifyCreateUser = userDao.read(createUser.getId());
        assertNotNull(verifyCreateUser);
        assertEquals(createUser.getId(), verifyCreateUser.getId());
        assertEquals(createUser, verifyCreateUser);

        User updateUser = TestDataUtility.userWithTestValues();
        updateUser.setId(createUser.getId());
        updateUser.setFirstName(TestDataUtility.randomAlphabetic(10000));
        userDao.update(updateUser);
    }

    /**
     * Verify that {@link UserDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        User createUser = TestDataUtility.userWithTestValues();
        userDao.create(createUser);
        assertNotNull(createUser.getId());

        User verifyCreateUser = userDao.read(createUser.getId());
        assertNotNull(verifyCreateUser);
        assertEquals(createUser.getId(), verifyCreateUser.getId());
        assertEquals(createUser, verifyCreateUser);

        userDao.delete(createUser.getId());

        User verifyDeleteUser = userDao.read(createUser.getId());
        assertNull(verifyDeleteUser);
    }

    /**
     * Verify that {@link UserDao#delete} is working correctly when a request for a non-existent {@link User#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentUser() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        userDao.delete(id);
    }
}