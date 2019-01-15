package edu.uwm.capstone.db.document;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.model.document.Document;
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
import static edu.uwm.capstone.util.TestDataUtility.randomInt;
import static edu.uwm.capstone.util.TestDataUtility.randomLocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class DocumentDaoRowMapperUnitTest {

    @Autowired
    DocumentDaoRowMapper documentDaoRowMapper;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws IOException {
        assertNotNull(documentDaoRowMapper);
    }

    /**
     * Verify that {@link DocumentDaoRowMapper.DocumentColumnType#values()} is working correctly.
     */
    @Test
    public void documentColumnType() {
        for (DocumentDaoRowMapper.DocumentColumnType columnType : DocumentDaoRowMapper.DocumentColumnType.values()) {
            assertNotNull(columnType.name());
            assertNotNull(columnType.getColumnName());
        }
    }

    /**
     * Verify that {@link DocumentDaoRowMapper#mapObject} is working correctly.
     */
    @Test
    public void mapObject() {
        // generate a document object with test values
        Document document = TestDataUtility.documentWithTestValues();
        document.setId(TestDataUtility.randomLong());
        document.setUserId(TestDataUtility.randomLong());
        document.setCreatedDate(randomLocalDateTime());
        document.setUpdatedDate(randomLocalDateTime());
        document.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        document.setPath(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        assertNotNull(document);

        Map<String, Object> mapObject = documentDaoRowMapper.mapObject(document);
        assertNotNull(mapObject);

        assertEquals(document.getId(), mapObject.get(BaseRowMapper.BaseColumnType.ID.getColumnName()));
        assertEquals(document.getUserId(), mapObject.get(DocumentDaoRowMapper.DocumentColumnType.USER_ID.getColumnName()));
        assertEquals(document.getCreatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())));
        assertEquals(document.getUpdatedDate(), dateFromJavaTime(mapObject.get(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())));
        assertEquals(document.getName(), mapObject.get(DocumentDaoRowMapper.DocumentColumnType.NAME.getColumnName()));
        assertEquals(document.getPath(), mapObject.get(DocumentDaoRowMapper.DocumentColumnType.PATH.getColumnName()));
    }

    /**
     * Verify that {@link DocumentDaoRowMapper#mapRow} is working correctly.
     */
    @Test
    public void mapRow() throws SQLException {
        // generate a document object with test values
        Document document = TestDataUtility.documentWithTestValues();
        document.setId(TestDataUtility.randomLong());
        document.setName(TestDataUtility.randomAlphabetic(randomInt(1, 100)));
        document.setPath(TestDataUtility.randomAlphanumeric(randomInt(1, 100)));
        document.setCreatedDate(randomLocalDateTime());
        document.setUpdatedDate(randomLocalDateTime());
        assertNotNull(document);

        // define the behavior of the resultSet that is being mocked
        when(resultSet.getLong(BaseRowMapper.BaseColumnType.ID.getColumnName())).thenReturn(document.getId());
        when(resultSet.getLong(DocumentDaoRowMapper.DocumentColumnType.USER_ID.getColumnName())).thenReturn(document.getUserId());
        when(resultSet.getString(DocumentDaoRowMapper.DocumentColumnType.NAME.getColumnName())).thenReturn(document.getName());
        when(resultSet.getString(DocumentDaoRowMapper.DocumentColumnType.PATH.getColumnName())).thenReturn(document.getPath());
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.CREATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(document.getCreatedDate()));
        when(resultSet.getObject(BaseRowMapper.BaseColumnType.UPDATED_DATE.getColumnName())).thenReturn(javaTimeFromDate(document.getUpdatedDate()));

        // exercise the mapRow functionality and verify the expected results
        Document verifyDocument = documentDaoRowMapper.mapRow(resultSet, 0);
        assertNotNull(verifyDocument);

        assertEquals(document.getId(), verifyDocument.getId());
        assertEquals(document.getUserId(), verifyDocument.getUserId());
        assertEquals(document.getCreatedDate(), verifyDocument.getCreatedDate());
        assertEquals(document.getUpdatedDate(), verifyDocument.getUpdatedDate());
        assertEquals(document.getName(), verifyDocument.getName());
        assertEquals(document.getPath(), verifyDocument.getPath());
    }
}