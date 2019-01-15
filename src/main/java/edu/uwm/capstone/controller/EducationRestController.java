package edu.uwm.capstone.controller;

import edu.uwm.capstone.model.education.Education;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.uwm.capstone.db.education.EducationDao;
import io.swagger.annotations.ApiOperation;

@RestController
public class EducationRestController {

    @Autowired
    private EducationDao educationService;

    // Get
    @RequestMapping(value = "/education/{id}", method = RequestMethod.GET)
    public Education retrieveEducation(@PathVariable long id) {
        try {
            return educationService.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    // Get many
    @RequestMapping(value="/education/retrievemany/{userid}", method = RequestMethod.GET)
    public List<Education> retrieveManyEducations(@PathVariable long userid) {
        try {
            return educationService.readMany(userid);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Post
    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public Education createEducation(@RequestBody Education education) {
        try {
            return educationService.create(education);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Delete
    @RequestMapping(value = "/education/{id}", method = RequestMethod.DELETE)
    public void deleteEducation(@PathVariable long id) {
        try {
            educationService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Put
    @RequestMapping(value = "/education", method = RequestMethod.PUT)
    public void updateEducation(@RequestBody Education education) {
        try {
            educationService.update(education);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
