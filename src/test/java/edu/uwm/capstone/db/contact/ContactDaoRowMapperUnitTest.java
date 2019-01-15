package edu.uwm.capstone.db.contact;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.contact.Contact;
import edu.uwm.capstone.model.profile.Profile;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
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

import static edu.uwm.capstone.db.contact.ContactDaoRowMapper.ContactColumnType.*;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.dateFromJavaTime;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.javaTimeFromDate;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class ContactDaoRowMapperUnitTest {

    @Autowired
    ContactDaoRowMapper contactDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(contactDaoRowMapper);
    }

    /**
     * Verify that {@link ContactDaoRowMapper.ContactColumnType#values()} is working correctly.
     */
    @Test
    public void contactColumnType() {
        for (ContactDaoRowMapper.ContactColumnType columnType : ContactDaoRowMapper.ContactColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link ContactDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a contact object with test values
        Contact contact = TestDataUtility.contactWithTestValues();
        contact.setId(TestDataUtility.randomLong());
        contact.setCreatedDate(randomLocalDateTime());
        contact.setUpdatedDate(randomLocalDateTime());
        assertNotNull(contact);

        Map<String, Object> mapObject = contactDaoRowMapper.mapObject(contact);
        assertNotNull(mapObject);

        assertEquals(contact.getId(), mapObject.get(BaseRowMapper.BaseColumnType.ID.getColumnName()));
        assertEquals(contact.getUserID(), mapObject.get(USER_ID.getColumnName()));
        assertEquals(contact.getCompanyID(), mapObject.get(COMPANY_ID.getColumnName()));
        assertEquals(contact.getPosition(), mapObject.get(POSITION.getColumnName()));
        assertEquals(contact.getFirstName(), mapObject.get(FIRST_NAME.getColumnName()));
        assertEquals(contact.getLastName(), mapObject.get(LAST_NAME.getColumnName()));
        assertEquals(contact.getEmail(), mapObject.get(EMAIL.getColumnName()));
        assertEquals(contact.getPhoneNumber(), mapObject.get(PHONE_NUMBER.getColumnName()));
        assertEquals(contact.getNotes(), mapObject.get(NOTES.getColumnName()));
        assertEquals(contact.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(contact.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())));
    }

    /**
     * Verify that {@link ContactDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a contact object with test values
        Contact contact = TestDataUtility.contactWithTestValues();
        contact.setId(TestDataUtility.randomLong());
        contact.setCreatedDate(randomLocalDateTime());
        contact.setUpdatedDate(randomLocalDateTime());
        assertNotNull(contact);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseRowMapper.BaseColumnType.ID.getColumnName())).thenReturn(contact.getId());
        when(resultSet.getLong(USER_ID.getColumnName())).thenReturn(contact.getUserID());
        when(resultSet.getLong(COMPANY_ID.getColumnName())).thenReturn(contact.getCompanyID());
        when(resultSet.getString(POSITION.getColumnName())).thenReturn(contact.getPosition());
        when(resultSet.getString(FIRST_NAME.getColumnName())).thenReturn(contact.getFirstName());
        when(resultSet.getString(LAST_NAME.getColumnName())).thenReturn(contact.getLastName());
        when(resultSet.getString(EMAIL.getColumnName())).thenReturn(contact.getEmail());
        when(resultSet.getString(PHONE_NUMBER.getColumnName())).thenReturn(contact.getPhoneNumber());
        when(resultSet.getString(NOTES.getColumnName())).thenReturn(contact.getNotes());
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(contact.getCreatedDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(contact.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        Contact verifyContact = contactDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyContact);

        assertEquals(contact.getId(), verifyContact.getId());
        assertEquals(contact.getUserID(), verifyContact.getUserID());
        assertEquals(contact.getCompanyID(), verifyContact.getCompanyID());
        assertEquals(contact.getPosition(), verifyContact.getPosition());
        assertEquals(contact.getFirstName(), verifyContact.getFirstName());
        assertEquals(contact.getLastName(), verifyContact.getLastName());
        assertEquals(contact.getEmail(), verifyContact.getEmail());
        assertEquals(contact.getPhoneNumber(), verifyContact.getPhoneNumber());
        assertEquals(contact.getNotes(), verifyContact.getNotes());
        assertEquals(contact.getCreatedDate(), verifyContact.getCreatedDate());
        assertEquals(contact.getUpdatedDate(), verifyContact.getUpdatedDate());
    }

}
