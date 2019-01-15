package edu.uwm.capstone.db.contact;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.contact.Contact;
import edu.uwm.capstone.util.TestDataUtility;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class ContactDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ContactDao contactDao;

    @Before
    public void setUp() {
        assertNotNull(contactDao);
        assertNotNull(contactDao.sql("createContact"));
        assertNotNull(contactDao.sql("readContact"));
        assertNotNull(contactDao.sql("readManyContacts"));
        assertNotNull(contactDao.sql("updateContact"));
        assertNotNull(contactDao.sql("deleteContact"));
    }

    /**
     * Verify that {@link ContactDao#create} is working correctly.
     */
    @Test
    public void create() {
        Contact createContact = TestDataUtility.contactWithTestValues();
        contactDao.create(createContact);
        assertNotNull(createContact.getId());
    }

    /**
     * Verify that {@link ContactDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullContact() {
        contactDao.create(null);
    }

    /**
     * Verify that {@link ContactDao#create} is working correctly when a request for a {@link Contact} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullContactId() {
        Contact createContact = TestDataUtility.contactWithTestValues();
        createContact.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        contactDao.create(createContact);
    }

    /**
     * Verify that {@link ContactDao#create} is working correctly when a request for a {@link Contact} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createContactColumnTooLong() {
        // generate a test contact value with a column that will exceed the database configuration
        Contact createContact = TestDataUtility.contactWithTestValues();
        createContact.setPosition(RandomStringUtils.randomAlphabetic(2000));
        contactDao.create(createContact);
    }

    /**
     * Verify that {@link ContactDao#read} is working correctly.
     */
    @Test
    public void read() {
        Contact createContact = TestDataUtility.contactWithTestValues();
        contactDao.create(createContact);
        assertNotNull(createContact.getId());

        Contact readContact = contactDao.read(createContact.getId());
        assertNotNull(readContact);
        assertEquals(createContact.getId(), readContact.getId());
        assertEquals(createContact, readContact);
    }

    /**
     * Verify that {@link ContactDao#readMany} is working correctly.
     */
    @Test
    public void readMany() {
        Long userID = new Long(12345);
        Contact createContact = TestDataUtility.contactWithTestValues();
        createContact.setUserID(userID);
        contactDao.create(createContact);
        assertNotNull(createContact.getId());

        Contact contact2 = TestDataUtility.contactWithTestValues();
        contact2.setUserID(userID);
        contactDao.create(contact2);
        assertNotNull(contact2.getId());

        Contact contact3 = TestDataUtility.contactWithTestValues();
        contact3.setUserID(new Long(6789));
        contactDao.create(contact3);
        assertNotEquals(userID, contact3.getUserID());

        List list = contactDao.readMany(userID);
        assertNotNull(list);
        assertEquals(list.size(), 2);
    }

    /**
     * Verify that {@link ContactDao#read} is working correctly when a request for a non-existent {@link Contact#id} is made.
     */
    @Test
    public void readNonExistentContact() {
        // create a random profile id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Contact profile = contactDao.read(id);
        assertNull(profile);
    }

    /**
     * Verify that {@link ContactDao#update} is working correctly.
     */
    @Test
    public void update() {
        Contact createContact = TestDataUtility.contactWithTestValues();
        contactDao.create(createContact);
        assertNotNull(createContact.getId());

        Contact verifyCreateContact = contactDao.read(createContact.getId());
        assertNotNull(verifyCreateContact);
        assertEquals(createContact.getId(), verifyCreateContact.getId());
        assertEquals(createContact, verifyCreateContact);

        Contact updateContact = TestDataUtility.contactWithTestValues();
        updateContact.setId(createContact.getId());
        contactDao.update(updateContact);

        Contact verifyUpdateContact = contactDao.read(updateContact.getId());
        assertNotNull(verifyUpdateContact);
        assertEquals(createContact.getId(), verifyUpdateContact.getId());
        assertEquals(updateContact.getPosition(), verifyUpdateContact.getPosition());
        assertEquals(updateContact.getFirstName(), verifyUpdateContact.getFirstName());
        assertEquals(updateContact.getLastName(), verifyUpdateContact.getLastName());
        assertEquals(updateContact.getEmail(), verifyUpdateContact.getEmail());
        assertEquals(updateContact.getPhoneNumber(), verifyUpdateContact.getPhoneNumber());
        assertEquals(updateContact.getNotes(), verifyUpdateContact.getNotes());
    }

    /**
     * Verify that {@link ContactDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullContact() {
        contactDao.update(null);
    }

    /**
     * Verify that {@link ContactDao#update} is working correctly when a request for a non-existent {@link Contact#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentContact() {
        // create a random profile id that will not be in our local database
        Contact updateContact = TestDataUtility.contactWithTestValues();
        updateContact.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        contactDao.update(updateContact);
    }

    /**
     * Verify that {@link ContactDao#update} is working correctly when a request for a {@link Contact} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateContactColumnTooLong() {
        // generate a test profile value with a column that will exceed the database configuration
        Contact createContact = TestDataUtility.contactWithTestValues();
        contactDao.create(createContact);
        assertNotNull(createContact.getId());

        Contact verifyCreateContact = contactDao.read(createContact.getId());
        assertNotNull(verifyCreateContact);
        assertEquals(createContact.getId(), verifyCreateContact.getId());
        assertEquals(createContact, verifyCreateContact);

        Contact updateContact = TestDataUtility.contactWithTestValues();
        updateContact.setId(createContact.getId());
        updateContact.setPosition(RandomStringUtils.randomAlphabetic(2000));
        contactDao.update(updateContact);
    }

    /**
     * Verify that {@link ContactDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Contact createContact = TestDataUtility.contactWithTestValues();
        contactDao.create(createContact);
        assertNotNull(createContact.getId());

        Contact verifyCreateContact = contactDao.read(createContact.getId());
        assertNotNull(verifyCreateContact);
        assertEquals(createContact.getId(), verifyCreateContact.getId());
        assertEquals(createContact, verifyCreateContact);

        contactDao.delete(createContact.getId());

        Contact verifyDeleteContact = contactDao.read(createContact.getId());
        assertNull(verifyDeleteContact);
    }

    /**
     * Verify that {@link ContactDao#delete} is working correctly when a request for a non-existent {@link Contact#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentContact() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        contactDao.delete(id);
    }

}
