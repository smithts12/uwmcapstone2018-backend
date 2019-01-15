package edu.uwm.capstone.controller;

import edu.uwm.capstone.model.document.Document;
import edu.uwm.capstone.db.document.DocumentDao;
import edu.uwm.capstone.db.user.UserDao;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import java.util.List;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class S3RestController {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private DocumentDao documentService;

    @Autowired
    private UserDao user;

    /**
     * This endpoint is used to create a document object and store
     * the document in s3 using local filePath and userId to get email for bucket name.
     * @param filePath {String}
     * @param userId {Long}
     */
    @RequestMapping(value = "/document", method = RequestMethod.POST)
    public void uploadFile(@RequestBody String filePath, @RequestBody Long userId) {
        String fileName = Paths.get(filePath).getFileName().toString();
        String email = user.read(userId).getEmail();
        Document document = new Document();
        document.setName(fileName);
        document.setPath(filePath);
        document.setUpdatedDate(LocalDateTime.now());

        if (!this.s3.doesBucketExistV2(email)) {
            try {
                this.s3.createBucket(email);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }

        try {
            s3.putObject(email, fileName, new File(filePath));
        } catch (
                AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        documentService.create(document);
    }

    /**
     * This endpoint is used to update a document object if the document
     * already exists in the bucket from email gotten from userID else create the document
     * @param filePath {String}
     * @param userId {Long}
     * @param id {Long}
     */
    @RequestMapping(value = "/document/{id}", method = RequestMethod.PUT)
    public void updateDocument(@RequestBody String filePath, @RequestBody Long userId, @PathVariable long id){
        String fileName = Paths.get(filePath).getFileName().toString();
        String email = user.read(userId).getEmail();
        Document document = documentService.read(id);
        document.setName(fileName);
        document.setPath(filePath);
        document.setUpdatedDate(LocalDateTime.now());

        try {
            S3Object o = s3.getObject(email, fileName);
            if(o != null) {
                s3.putObject(email, fileName, new File(filePath));
                documentService.update(document);
            }
            else{
                s3.putObject(email, fileName, new File(filePath));
                documentService.create(document);
            }

        } catch (
                AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    /**
     * This endpoint is used to get a document object and download the corresponding document from s3
     * @param userId {Long}
     * @param id {Long}
     * @return Document
     */
    @RequestMapping(value = "document/{id}", method = RequestMethod.GET)
    public Document getFile(@RequestBody Long userId, @PathVariable long id){
        String email = user.read(userId).getEmail();
        String fileName = documentService.read(id).getName();

        try {
            S3Object o = s3.getObject(email, fileName);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            byte[] readBuf = new byte[1024];
            int readLen = 0;
            while ((readLen = s3is.read(readBuf)) > 0) {
                fos.write(readBuf, 0, readLen);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return documentService.read(id);
    }

    /**
     * This endpoint is used to get a list of all document objects with userId
     * @param userId {Long}
     * @return List<Map<String, Object>>
     */
    @RequestMapping(value = "/document/retrievemany/{userId}", method = RequestMethod.GET)
    public List<Document> retrieveFiles(@PathVariable long userId){
        return documentService.readMany(userId);
    }

    /**
     * This endpoint is used to delete document object with id from db and s3
     * @param userId {Long}
     * @param id {Long}
     */
    @RequestMapping(value = "/document/{id}", method = RequestMethod.DELETE)
    public void deleteFile(@RequestBody Long userId, @PathVariable long id){
        String email = user.read(userId).getEmail();
        String fileName = documentService.read(id).getName();
            try {
            s3.deleteObject(email, fileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        documentService.delete(id);
    }
}