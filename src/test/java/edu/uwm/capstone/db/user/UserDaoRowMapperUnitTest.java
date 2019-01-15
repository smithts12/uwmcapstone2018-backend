package edu.uwm.capstone.db.user;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.db.user.UserDaoRowMapper;
import edu.uwm.capstone.model.user.User;
import edu.uwm.capstone.sql.dao.BaseRowMapper.BaseColumnType;
import edu.uwm.capstone.db.user.UserDaoRowMapper.UserColumnType;
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
import static edu.uwm.capstone.util.TestDataUtility.randomInt;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)

public class UserDaoRowMapperUnitTest {
    @Autowired
    UserDaoRowMapper userDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(userDaoRowMapper);
    }

    /**
     * Verify that {@link UserDaoRowMapper.UserColumnType#values()} is working correctly.
     */
    @Test
    public void userColumnType() {
        for (UserDaoRowMapper.UserColumnType columnType : UserDaoRowMapper.UserColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link UserDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a user object with test values
        User user = TestDataUtility.userWithTestValues();
        user.setId(TestDataUtility.randomLong());
        user.setEmail(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        user.setPassword(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        user.setTitle(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        user.setFirstName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setMiddleName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setLastName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setMobilePhone(TestDataUtility.randomNumeric(randomInt(1, 100)));
        user.setHomePhone(TestDataUtility.randomNumeric(randomInt(1, 100)));
        user.setWebsite(TestDataUtility.randomAlphanumeric(randomInt(1 ,100)));
        user.setCreatedDate(randomLocalDateTime());
        user.setUpdatedDate(randomLocalDateTime());
        assertNotNull(user);

        userDaoRowMapper.mapObject(user);
        Map<String, Object> mapObject = userDaoRowMapper.mapObject(user);
        assertNotNull(mapObject);
        assertEquals(user.getId(), mapObject.get(BaseColumnType.ID.getColumnName()));
        assertEquals(user.getEmail(), mapObject.get(UserColumnType.EMAIL.getColumnName()));
        assertEquals(user.getPassword(), mapObject.get(UserColumnType.PASSWORD.getColumnName()));
        assertEquals(user.getTitle(), mapObject.get(UserColumnType.TITLE.getColumnName()));
        assertEquals(user.getFirstName(), mapObject.get(UserColumnType.FIRST_NAME.getColumnName()));
        assertEquals(user.getMiddleName(), mapObject.get(UserColumnType.MIDDLE_NAME.getColumnName()));
        assertEquals(user.getLastName(), mapObject.get(UserColumnType.LAST_NAME.getColumnName()));
        assertEquals(user.getMobilePhone(), mapObject.get(UserColumnType.MOBILE_PHONE.getColumnName()));
        assertEquals(user.getHomePhone(), mapObject.get(UserColumnType.HOME_PHONE.getColumnName()));
        assertEquals(user.getWebsite(), mapObject.get(UserColumnType.WEBSITE.getColumnName()));
        assertEquals(user.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(user.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseColumnType.UPDATED_DATE.getColumnName())));
    }

    /**
     * Verify that {@link UserDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a user object with test values
        User user = TestDataUtility.userWithTestValues();
        user.setId(TestDataUtility.randomLong());
        user.setPassword(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        user.setEmail(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        user.setTitle(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        user.setFirstName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setMiddleName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setLastName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setWebsite(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        user.setMobilePhone(TestDataUtility.randomNumeric(randomInt(1, 100)));
        user.setHomePhone(TestDataUtility.randomNumeric(randomInt(1, 100)));
        user.setCreatedDate(randomLocalDateTime());
        user.setUpdatedDate(randomLocalDateTime());
        assertNotNull(user);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseColumnType.ID.getColumnName())).thenReturn(user.getId());
        when(resultSet.getString(UserColumnType.PASSWORD.getColumnName())).thenReturn(user.getPassword());
        when(resultSet.getString(UserColumnType.EMAIL.getColumnName())).thenReturn(user.getEmail());
        when(resultSet.getString(UserColumnType.TITLE.getColumnName())).thenReturn(user.getTitle());
        when(resultSet.getString(UserColumnType.FIRST_NAME.getColumnName())).thenReturn(user.getFirstName());
        when(resultSet.getString(UserColumnType.MIDDLE_NAME.getColumnName())).thenReturn(user.getMiddleName());
        when(resultSet.getString(UserColumnType.LAST_NAME.getColumnName())).thenReturn(user.getLastName());
        when(resultSet.getString(UserColumnType.WEBSITE.getColumnName())).thenReturn(user.getWebsite());
        when(resultSet.getString(UserColumnType.MOBILE_PHONE.getColumnName())).thenReturn(user.getMobilePhone());
        when(resultSet.getString(UserColumnType.HOME_PHONE.getColumnName())).thenReturn(user.getHomePhone());
        when(resultSet.getObject(BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(user.getCreatedDate()));
        when(resultSet.getObject(BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(user.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        User verifyUser = userDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyUser);

        assertEquals(user.getId(), verifyUser.getId());
        assertEquals(user.getPassword(), verifyUser.getPassword());
        assertEquals(user.getEmail(), verifyUser.getEmail());
        assertEquals(user.getTitle(), verifyUser.getTitle());
        assertEquals(user.getFirstName(), verifyUser.getFirstName());
        assertEquals(user.getLastName(), verifyUser.getLastName());
        assertEquals(user.getMobilePhone(), verifyUser.getMobilePhone());
        assertEquals(user.getHomePhone(), verifyUser.getHomePhone());
        assertEquals(user.getWebsite(), verifyUser.getWebsite());
        assertEquals(user.getCreatedDate(), verifyUser.getCreatedDate());
        assertEquals(user.getUpdatedDate(), verifyUser.getUpdatedDate());
    }
}