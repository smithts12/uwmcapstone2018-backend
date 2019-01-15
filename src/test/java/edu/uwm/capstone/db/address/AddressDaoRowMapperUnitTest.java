package edu.uwm.capstone.db.address;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.address.Address;
import edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType;
import edu.uwm.capstone.db.address.AddressDaoRowMapper.AddressColumnType;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class AddressDaoRowMapperUnitTest {

    @Autowired
    AddressDaoRowMapper addressDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(addressDaoRowMapper);
    }

    /**
     * Verify that {@link AddressDaoRowMapper.AddressColumnType#values()} is working correctly.
     */
    @Test
    public void addressColumnType() {
        for (AddressDaoRowMapper.AddressColumnType columnType : AddressDaoRowMapper.AddressColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link AddressDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a address object with test values
        Address address = TestDataUtility.addressWithTestValues();
        address.setId(TestDataUtility.randomLong());
        address.setCreatedDate(randomLocalDateTime());
        address.setUpdatedDate(randomLocalDateTime());

        assertNotNull(address);

        Map<String, Object> mapObject = addressDaoRowMapper.mapObject(address);
        assertNotNull(mapObject);

        assertEquals(address.getId(), mapObject.get(BaseColumnType.ID.getColumnName()));
        assertEquals(address.getUserID(), mapObject.get(AddressColumnType.USER_ID.getColumnName()));
        assertEquals(address.getStreet1(), mapObject.get(AddressColumnType.STREET_1.getColumnName()));
        assertEquals(address.getStreet2(), mapObject.get(AddressColumnType.STREET_2.getColumnName()));
        assertEquals(address.getCity(), mapObject.get(AddressColumnType.CITY.getColumnName()));
        assertEquals(address.getState(), mapObject.get(AddressColumnType.STATE.getColumnName()));
        assertEquals(address.getZipcode(), mapObject.get(AddressColumnType.ZIP_CODE.getColumnName()));
        assertEquals(address.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(address.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseColumnType.UPDATED_DATE.getColumnName())));
    }

    /**
     * Verify that {@link AddressDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate an address object with test values
        Address address = TestDataUtility.addressWithTestValues();
        address.setId(TestDataUtility.randomLong());
        address.setCreatedDate(randomLocalDateTime());
        address.setUpdatedDate(randomLocalDateTime());

        assertNotNull(address);

        when(resultSet.getLong(BaseColumnType.ID.getColumnName())).thenReturn(address.getId());
        when(resultSet.getLong(AddressColumnType.USER_ID.getColumnName())).thenReturn(address.getUserID());
        when(resultSet.getString(AddressColumnType.STREET_1.getColumnName())).thenReturn(address.getStreet1());
        when(resultSet.getString(AddressColumnType.STREET_2.getColumnName())).thenReturn(address.getStreet2());
        when(resultSet.getString(AddressColumnType.CITY.getColumnName())).thenReturn(address.getCity());
        when(resultSet.getString(AddressColumnType.STATE.getColumnName())).thenReturn(address.getState());
        when(resultSet.getString(AddressColumnType.ZIP_CODE.getColumnName())).thenReturn(address.getZipcode());
        when(resultSet.getObject(BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(address.getCreatedDate()));
        when(resultSet.getObject(BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(address.getUpdatedDate()));

        Address verifyAddress = addressDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyAddress);

        assertEquals(address.getId(), verifyAddress.getId());
        assertEquals(address.getUserID(), verifyAddress.getUserID());
        assertEquals(address.getStreet1(), verifyAddress.getStreet1());
        assertEquals(address.getStreet2(), verifyAddress.getStreet2());
        assertEquals(address.getCity(), verifyAddress.getCity());
        assertEquals(address.getState(), verifyAddress.getState());
        assertEquals(address.getZipcode(), verifyAddress.getZipcode());
        assertEquals(address.getCreatedDate(), verifyAddress.getCreatedDate());
        assertEquals(address.getUpdatedDate(), verifyAddress.getUpdatedDate());
    }
}
