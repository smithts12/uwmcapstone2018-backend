package edu.uwm.capstone.db.address;

import java.util.Random;

import edu.uwm.capstone.model.address.Address;
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
public class AddressDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    AddressDao addressDao;

    @Before
    public void setUp() {
        assertNotNull(addressDao);
        assertNotNull(addressDao.sql("createAddress"));
        assertNotNull(addressDao.sql("readAddress"));
        assertNotNull(addressDao.sql("updateAddress"));
        assertNotNull(addressDao.sql("deleteAddress"));
    }

    /**
     * Verify that {@link AddressDao#create} is working correctly.
     */
    @Test
    public void create() {
        Address createAddress = TestDataUtility.addressWithTestValues();
        addressDao.create(createAddress);
        assertNotNull(createAddress.getId());
    }

    /**
     * Verify that {@link AddressDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullAddress() {
        addressDao.create(null);
    }

    /**
     * Verify that {@link AddressDao#create} is working correctly when a request for a {@link Address} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullAddressId() {
        Address createAddress = TestDataUtility.addressWithTestValues();
        createAddress.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        addressDao.create(createAddress);
    }

    /**
     * Verify that {@link AddressDao#create} is working correctly when a request for a {@link Address} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createAddressColumnTooLong() {
        // generate a test address value with a column that will exceed the database configuration
        Address createAddress = TestDataUtility.addressWithTestValues();
        createAddress.setStreet1(RandomStringUtils.randomAlphabetic(2000));
        addressDao.create(createAddress);
    }

    /**
     * Verify that {@link AddressDao#read} is working correctly.
     */
    @Test
    public void read() {
        Address createAddress = TestDataUtility.addressWithTestValues();
        addressDao.create(createAddress);
        assertNotNull(createAddress.getId());

        Address readAddress = addressDao.read(createAddress.getId());
        assertNotNull(readAddress);
        assertEquals(createAddress.getId(), readAddress.getId());
        assertEquals(createAddress, readAddress);
    }

    /**
     * Verify that {@link AddressDao#read} is working correctly when a request for a non-existent {@link Address#id} is made.
     */
    @Test
    public void readNonExistentAddress() {
        // create a random address id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Address address = addressDao.read(id);
        assertNull(address);
    }

    /**
     * Verify that {@link AddressDao#update} is working correctly.
     */
    @Test
    public void update() {
        Address createAddress = TestDataUtility.addressWithTestValues();
        addressDao.create(createAddress);
        assertNotNull(createAddress.getId());

        Address verifyCreateAddress = addressDao.read(createAddress.getId());
        assertNotNull(verifyCreateAddress);
        assertEquals(createAddress.getId(), verifyCreateAddress.getId());
        assertEquals(createAddress, verifyCreateAddress);

        Address updateAddress = TestDataUtility.addressWithTestValues();
        updateAddress.setId(createAddress.getId());
        addressDao.update(updateAddress);

        Address verifyUpdateAddress = addressDao.read(updateAddress.getId());
        assertNotNull(verifyUpdateAddress);
        assertEquals(createAddress.getId(), verifyUpdateAddress.getId());
        assertEquals(updateAddress.getUserID(), verifyUpdateAddress.getUserID());
        assertEquals(updateAddress.getStreet1(), verifyUpdateAddress.getStreet1());
        assertEquals(updateAddress.getStreet2(), verifyUpdateAddress.getStreet2());
        assertEquals(updateAddress.getCity(), verifyUpdateAddress.getCity());
        assertEquals(updateAddress.getState(), verifyUpdateAddress.getState());
        assertEquals(updateAddress.getZipcode(), verifyUpdateAddress.getZipcode());
    }

    /**
     * Verify that {@link AddressDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullAddress() {
        addressDao.update(null);
    }

    /**
     * Verify that {@link AddressDao#update} is working correctly when a request for a non-existent {@link Address#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentAddress() {
        // create a random address id that will not be in our local database
        Address updateAddress = TestDataUtility.addressWithTestValues();
        updateAddress.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        addressDao.update(updateAddress);
    }

    /**
     * Verify that {@link AddressDao#update} is working correctly when a request for a {@link Address} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateAddressColumnTooLong() {
        // generate a test address value with a column that will exceed the database configuration
        Address createAddress = TestDataUtility.addressWithTestValues();
        addressDao.create(createAddress);
        assertNotNull(createAddress.getId());

        Address verifyCreateAddress = addressDao.read(createAddress.getId());
        assertNotNull(verifyCreateAddress);
        assertEquals(createAddress.getId(), verifyCreateAddress.getId());
        assertEquals(createAddress, verifyCreateAddress);

        Address updateAddress = TestDataUtility.addressWithTestValues();
        updateAddress.setId(createAddress.getId());
        updateAddress.setStreet1(RandomStringUtils.randomAlphabetic(2000));
        addressDao.update(updateAddress);
    }

    /**
     * Verify that {@link AddressDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Address createAddress = TestDataUtility.addressWithTestValues();
        addressDao.create(createAddress);
        assertNotNull(createAddress.getId());

        Address verifyCreateAddress = addressDao.read(createAddress.getId());
        assertNotNull(verifyCreateAddress);
        assertEquals(createAddress.getId(), verifyCreateAddress.getId());
        assertEquals(createAddress, verifyCreateAddress);

        addressDao.delete(createAddress.getId());

        Address verifyDeleteAddress = addressDao.read(createAddress.getId());
        assertNull(verifyDeleteAddress);
    }

    /**
     * Verify that {@link AddressDao#delete} is working correctly when a request for a non-existent {@link Address#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentAddress() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        addressDao.delete(id);
    }

}
