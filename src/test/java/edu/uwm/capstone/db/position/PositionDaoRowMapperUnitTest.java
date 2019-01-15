package edu.uwm.capstone.db.position;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.db.position.PositionDaoRowMapper;
import edu.uwm.capstone.model.position.Position;
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

import static edu.uwm.capstone.sql.dao.BaseRowMapper.dateFromJavaTime;
import static edu.uwm.capstone.sql.dao.BaseRowMapper.javaTimeFromDate;
import static edu.uwm.capstone.util.TestDataUtility.randomDouble;
import static edu.uwm.capstone.util.TestDataUtility.randomInt;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class PositionDaoRowMapperUnitTest {

    @Autowired
    PositionDaoRowMapper positionDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(positionDaoRowMapper);
    }

    /**
     * Verify that {@link PositionDaoRowMapper.PositionColumnType#values()} is working correctly.
     */
    @Test
    public void positionColumnType() {
        for (PositionDaoRowMapper.PositionColumnType columnType : PositionDaoRowMapper.PositionColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link PositionDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a position object with test values
        Position position = TestDataUtility.positionWithTestValues();
        position.setId(TestDataUtility.randomLong());
        position.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        position.setCompanyId(TestDataUtility.randomLong());
        position.setDescription(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        position.setCreatedDate(TestDataUtility.randomLocalDateTime());
        position.setUpdatedDate(TestDataUtility.randomLocalDateTime());
        position.setStartDate(TestDataUtility.randomLocalDateTime());
        position.setEndDate(TestDataUtility.randomLocalDateTime());
        position.setStartPay(TestDataUtility.randomDouble());
        position.setEndPay(TestDataUtility.randomDouble());
        assertNotNull(position);

        Map<String, Object> mapObject = positionDaoRowMapper.mapObject(position);
        assertNotNull(mapObject);

        assertEquals(position.getId(), mapObject.get(BaseRowMapper.BaseColumnType.ID.getColumnName()));
        assertEquals(position.getUserID(), mapObject.get(PositionDaoRowMapper.PositionColumnType.USER_ID.getColumnName()));
        assertEquals(position.getName(), mapObject.get(PositionDaoRowMapper.PositionColumnType.NAME.getColumnName()));
        assertEquals(position.getCompanyId(), mapObject.get(PositionDaoRowMapper.PositionColumnType.COMPANY_ID.getColumnName()));
        assertEquals(position.getDescription(), mapObject.get(PositionDaoRowMapper.PositionColumnType.DESCRIPTION.getColumnName()));
        assertEquals(position.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(position.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())));
        assertEquals(position.getStartDate(), dateFromJavaTime(mapObject.get(PositionDaoRowMapper.PositionColumnType.START_DATE.getColumnName())));
        assertEquals(position.getEndDate(), dateFromJavaTime(mapObject.get(PositionDaoRowMapper.PositionColumnType.END_DATE.getColumnName())));
        assertEquals(position.getStartPay(), mapObject.get(PositionDaoRowMapper.PositionColumnType.START_PAY.getColumnName()));
        assertEquals(position.getEndPay(), mapObject.get(PositionDaoRowMapper.PositionColumnType.END_PAY.getColumnName()));
    }

    /**
     * Verify that {@link PositionDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a position object with test values
        Position position = TestDataUtility.positionWithTestValues();
        position.setId(TestDataUtility.randomLong());
        position.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        position.setCompanyId(TestDataUtility.randomLong());
        position.setDescription(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        position.setCreatedDate(randomLocalDateTime());
        position.setUpdatedDate(randomLocalDateTime());
        position.setStartDate(randomLocalDateTime());
        position.setEndDate(randomLocalDateTime());
        position.setStartPay(TestDataUtility.randomDouble());
        position.setEndPay(TestDataUtility.randomDouble());
        assertNotNull(position);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseRowMapper.BaseColumnType.ID.getColumnName())).thenReturn(position.getId());
        when(resultSet.getLong(PositionDaoRowMapper.PositionColumnType.USER_ID.getColumnName())).thenReturn(position.getUserID());
        when(resultSet.getString(PositionDaoRowMapper.PositionColumnType.NAME.getColumnName())).thenReturn(position.getName());
        when(resultSet.getLong(PositionDaoRowMapper.PositionColumnType.COMPANY_ID.getColumnName())).thenReturn(position.getCompanyId());
        when(resultSet.getString(PositionDaoRowMapper.PositionColumnType.DESCRIPTION.getColumnName())).thenReturn(position.getDescription());
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(position.getCreatedDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(position.getUpdatedDate()));
        when(resultSet.getObject(PositionDaoRowMapper.PositionColumnType.START_DATE.getColumnName())).thenReturn(javaTimeFromDate(position.getStartDate()));
        when(resultSet.getObject(PositionDaoRowMapper.PositionColumnType.END_DATE.getColumnName())).thenReturn(javaTimeFromDate(position.getEndDate()));
        when(resultSet.getDouble(PositionDaoRowMapper.PositionColumnType.START_PAY.getColumnName())).thenReturn(position.getStartPay());
        when(resultSet.getDouble(PositionDaoRowMapper.PositionColumnType.END_PAY.getColumnName())).thenReturn(position.getEndPay());

        // exercise the mapRow functionality and verify the expected results
        Position verifyPosition = positionDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyPosition);

        assertEquals(position.getId(), verifyPosition.getId());
        assertEquals(position.getUserID(), verifyPosition.getUserID());
        assertEquals(position.getName(), verifyPosition.getName());
        assertEquals(position.getCompanyId(), verifyPosition.getCompanyId());
        assertEquals(position.getDescription(), verifyPosition.getDescription());
        assertEquals(position.getCreatedDate(), verifyPosition.getCreatedDate());
        assertEquals(position.getUpdatedDate(), verifyPosition.getUpdatedDate());
        assertEquals(position.getStartDate(), verifyPosition.getStartDate());
        assertEquals(position.getEndDate(), verifyPosition.getEndDate());
        assertEquals(position.getStartPay(), verifyPosition.getStartPay());
        assertEquals(position.getEndPay(), verifyPosition.getEndPay());
    }
}