package edu.uwm.capstone.db.document;

import edu.uwm.capstone.UnitTestConfig;
import edu.uwm.capstone.db.document.DocumentDao;
import edu.uwm.capstone.model.document.Document;
import edu.uwm.capstone.util.TestDataUtility;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class)
public class DocumentDaoUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DocumentDao documentDao;

    @Before
    public void setUp() {
        assertNotNull(documentDao);
        assertNotNull(documentDao.sql("createDocument"));
        assertNotNull(documentDao.sql("readDocument"));
        assertNotNull(documentDao.sql("updateDocument"));
        assertNotNull(documentDao.sql("deleteDocument"));
    }

    /**
     * Verify that {@link DocumentDao#create} is working correctly.
     */
    @Test
    public void create() {
        Document createDocument = TestDataUtility.documentWithTestValues();
        documentDao.create(createDocument);
        assertNotNull(createDocument.getId());
    }

    /**
     * Verify that {@link DocumentDao#create} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNullDocument() {
        documentDao.create(null);
    }

    /**
     * Verify that {@link DocumentDao#create} is working correctly when a request for a {@link Document} with a non-null id is made.
     */
    @Test(expected = RuntimeException.class)
    public void createNonNullDocumentId() {
        Document createDocument = TestDataUtility.documentWithTestValues();
        createDocument.setId(new Random().longs(1L, Long.MAX_VALUE).findAny().getAsLong());
        documentDao.create(createDocument);
    }

    /**
     * Verify that {@link DocumentDao#create} is working correctly when a request for a {@link Document} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void createDocumentColumnTooLong() {
        // generate a test document value with a column that will exceed the database configuration
        Document createDocument = TestDataUtility.documentWithTestValues();
        createDocument.setName(RandomStringUtils.randomAlphabetic(2000));
        documentDao.create(createDocument);
    }

    /**
     * Verify that {@link DocumentDao#read} is working correctly.
     */
    @Test
    public void read() {
        Document createDocument = TestDataUtility.documentWithTestValues();
        documentDao.create(createDocument);
        assertNotNull(createDocument.getId());

        Document readDocument = documentDao.read(createDocument.getId());
        assertNotNull(readDocument);
        assertEquals(createDocument.getId(), readDocument.getId());
        assertEquals(createDocument, readDocument);
    }

    /**
     * Verify that {@link DocumentDao#read} is working correctly when a request for a non-existent {@link Document#id} is made.
     */
    @Test
    public void readNonExistentDocument() {
        // create a random document id that will not be in our local database
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        Document document = documentDao.read(id);
        assertNull(document);
    }

    /**
     * Verify that {@link DocumentDao#update} is working correctly.
     */
    @Test
    public void update() {
        Document createDocument = TestDataUtility.documentWithTestValues();
        documentDao.create(createDocument);
        assertNotNull(createDocument.getId());

        Document verifyCreateDocument = documentDao.read(createDocument.getId());
        assertNotNull(verifyCreateDocument);
        assertEquals(createDocument.getId(), verifyCreateDocument.getId());
        assertEquals(createDocument, verifyCreateDocument);

        Document updateDocument = TestDataUtility.documentWithTestValues();
        updateDocument.setId(createDocument.getId());
        documentDao.update(updateDocument);

        Document verifyUpdateDocument = documentDao.read(updateDocument.getId());
        assertNotNull(verifyUpdateDocument);
        assertEquals(createDocument.getId(), verifyUpdateDocument.getId());
        assertEquals(updateDocument.getName(), verifyUpdateDocument.getName());
    }

    /**
     * Verify that {@link DocumentDao#update} is working correctly when a request for creating a null object is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNullDocument() {
        documentDao.update(null);
    }

    /**
     * Verify that {@link DocumentDao#update} is working correctly when a request for a non-existent {@link Document#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateNonExistentDocument() {
        // create a random document id that will not be in our local database
        Document updateDocument = TestDataUtility.documentWithTestValues();
        updateDocument.setId(new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong());
        documentDao.update(updateDocument);
    }

    /**
     * Verify that {@link DocumentDao#update} is working correctly when a request for a {@link Document} that contains a value
     * which exceeds the database configuration is made.
     */
    @Test(expected = RuntimeException.class)
    public void updateDocumentColumnTooLong() {
        // generate a test document value with a column that will exceed the database configuration
        Document createDocument = TestDataUtility.documentWithTestValues();
        documentDao.create(createDocument);
        assertNotNull(createDocument.getId());

        Document verifyCreateDocument = documentDao.read(createDocument.getId());
        assertNotNull(verifyCreateDocument);
        assertEquals(createDocument.getId(), verifyCreateDocument.getId());
        assertEquals(createDocument, verifyCreateDocument);

        Document updateDocument = TestDataUtility.documentWithTestValues();
        updateDocument.setId(createDocument.getId());
        updateDocument.setName(RandomStringUtils.randomAlphabetic(2000));
        documentDao.update(updateDocument);
    }

    /**
     * Verify that {@link DocumentDao#delete} is working correctly.
     */
    @Test
    public void delete() {
        Document createDocument = TestDataUtility.documentWithTestValues();
        documentDao.create(createDocument);
        assertNotNull(createDocument.getId());

        Document verifyCreateDocument = documentDao.read(createDocument.getId());
        assertNotNull(verifyCreateDocument);
        assertEquals(createDocument.getId(), verifyCreateDocument.getId());
        assertEquals(createDocument, verifyCreateDocument);

        documentDao.delete(createDocument.getId());

        Document verifyDeleteDocument = documentDao.read(createDocument.getId());
        assertNull(verifyDeleteDocument);
    }

    /**
     * Verify that {@link DocumentDao#delete} is working correctly when a request for a non-existent {@link Document#id} is made.
     */
    @Test(expected = RuntimeException.class)
    public void deleteNonExistentDocument() {
        Long id = new Random().longs(10000L, Long.MAX_VALUE).findAny().getAsLong();
        documentDao.delete(id);
    }
}