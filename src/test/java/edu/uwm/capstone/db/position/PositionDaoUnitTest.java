package edu.uwm.capstone.db.position;

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
import edu.uwm.capstone.db.position.PositionDao;
import edu.uwm.capstone.db.position.PositionDaoRowMapper;
import edu.uwm.capstone.model.position.Position;
import edu.uwm.capstone.util.TestDataUtility;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class PositionDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    PositionDao positionDao;

    @Before
    public void setUp() {
        assertNotNull(positionDao);
        assertNotNull(positionDao.sql("createPosition"));
        assertNotNull(positionDao.sql("readPosition"));
        assertNotNull(positionDao.sql("readManyPositions"));
        assertNotNull(positionDao.sql("updatePosition"));
        assertNotNull(positionDao.sql("deletePosition"));
    }

    /**
     * Verify that {@link PositionDao#create} is working correctly.
     */
    @Test
    public void create() {
        Position createPosition = TestDataUtility.positionWithTestValues();
        positionDao.create(createPosition);
        assertNotNull(createPosition.getId());
    }

    /**
     * Verify that {@link PositionDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullPosition() {
        positionDao.create(null);
    }

    /**
     * Verify that {@link PositionDao#create} is working correctly when a request for a {@link Position} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullPositionId() {
        Position createPosition = TestDataUtility.positionWithTestValues();
        createPosition.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        positionDao.create(createPosition);
    }

    /**
     * Verify that {@link PositionDao#create} is working correctly when a request for a {@link Position} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createPositionColumnTooLong() {
        // generate a test position value with a column that will exceed the database configuration
        Position createPosition = TestDataUtility.positionWithTestValues();
        createPosition.setName(RandomStringUtils.randomAlphabetic(2000));
        positionDao.create(createPosition);
    }

    /**
     * Verify that {@link PositionDao#read} is working correctly.
     */
    @Test
    public void read() {
        Position createPosition = TestDataUtility.positionWithTestValues();
        positionDao.create(createPosition);
        assertNotNull(createPosition.getId());

        Position readPosition = positionDao.read(createPosition.getId());
        assertNotNull(readPosition);
        assertEquals(createPosition.getId(), readPosition.getId());
        assertEquals(createPosition, readPosition);
    }

    /**
     * Verify that {@link PositionDao#readMany} is working correctly.
     */
    @Test
    public void readMany() {
        Long userID = new Long(12345);
        Position position = TestDataUtility.positionWithTestValues();
        position.setUserID(userID);
        positionDao.create(position);
        assertNotNull(position.getId());

        Position position2 = TestDataUtility.positionWithTestValues();
        position2.setUserID(userID);
        positionDao.create(position2);
        assertNotNull(position2.getId());

        Position position3 = TestDataUtility.positionWithTestValues();
        position3.setUserID(new Long(6789));
        positionDao.create(position3);
        assertNotEquals(userID, position3.getUserID());

        List list = positionDao.readMany(userID);
        assertNotNull(list);
        assertEquals(list.size(), 2);
    }

    /**
     * Verify that {@link PositionDao#read} is working correctly when a request for a non-existent {@link Position#id} is made.
     */
    @Test
    public void readNonExistentPosition() {
        // create a random position id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Position position = positionDao.read(id);
        assertNull(position);
    }

    /**
     * Verify that {@link PositionDao#update} is working correctly.
     */
    @Test
    public void update() {
        Position createPosition = TestDataUtility.positionWithTestValues();
        positionDao.create(createPosition);
        assertNotNull(createPosition.getId());

        Position verifyCreatePosition = positionDao.read(createPosition.getId());
        assertNotNull(verifyCreatePosition);
        assertEquals(createPosition.getId(), verifyCreatePosition.getId());
        assertEquals(createPosition, verifyCreatePosition);

        Position updatePosition = TestDataUtility.positionWithTestValues();
        updatePosition.setId(createPosition.getId());
        positionDao.update(updatePosition);

        Position verifyUpdatePosition = positionDao.read(updatePosition.getId());
        assertNotNull(verifyUpdatePosition);
        assertEquals(createPosition.getId(), verifyUpdatePosition.getId());
        assertEquals(updatePosition.getName(), verifyUpdatePosition.getName());
    }

    /**
     * Verify that {@link PositionDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullPosition() {
        positionDao.update(null);
    }

    /**
     * Verify that {@link PositionDao#update} is working correctly when a request for a non-existent {@link Position#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentPosition() {
        // create a random position id that will not be in our local database
        Position updatePosition = TestDataUtility.positionWithTestValues();
        updatePosition.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        positionDao.update(updatePosition);
    }

    /**
     * Verify that {@link PositionDao#update} is working correctly when a request for a {@link Position} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updatePositionColumnTooLong() {
        // generate a test position value with a column that will exceed the database configuration
        Position createPosition = TestDataUtility.positionWithTestValues();
        positionDao.create(createPosition);
        assertNotNull(createPosition.getId());

        Position verifyCreatePosition = positionDao.read(createPosition.getId());
        assertNotNull(verifyCreatePosition);
        assertEquals(createPosition.getId(), verifyCreatePosition.getId());
        assertEquals(createPosition, verifyCreatePosition);

        Position updatePosition = TestDataUtility.positionWithTestValues();
        updatePosition.setId(createPosition.getId());
        updatePosition.setName(RandomStringUtils.randomAlphabetic(2000));
        positionDao.update(updatePosition);
    }

    /**
     * Verify that {@link PositionDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Position createPosition = TestDataUtility.positionWithTestValues();
        positionDao.create(createPosition);
        assertNotNull(createPosition.getId());

        Position verifyCreatePosition = positionDao.read(createPosition.getId());
        assertNotNull(verifyCreatePosition);
        assertEquals(createPosition.getId(), verifyCreatePosition.getId());
        assertEquals(createPosition, verifyCreatePosition);

        positionDao.delete(createPosition.getId());

        Position verifyDeletePosition = positionDao.read(createPosition.getId());
        assertNull(verifyDeletePosition);
    }

    /**
     * Verify that {@link PositionDao#delete} is working correctly when a request for a non-existent {@link Position#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentPosition() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        positionDao.delete(id);
    }

}