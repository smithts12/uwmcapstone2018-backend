package edu.uwm.capstone.controller;
import edu.uwm.capstone.model.project.Project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.uwm.capstone.db.project.ProjectDao;

@RestController
public class ProjectRestController {
    @Autowired
    private ProjectDao projectService;
    
    /**
 	* This endpoint is used to retrieve a project object by
	* its id.
	* @param id {Long}
	* @return Project
	*/
    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public Project retrieveOne(@PathVariable long id) {
        try {
            return projectService.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
 	* This endpoint is used to retrieve a list of project objects by
	* its userId.
	* @param userId {Long}
	* @return List<Map<String, Project>>
	*/
    @RequestMapping(value = "/project/retrievemany/{userId}", method = RequestMethod.GET)
    public List<Project> retrieveMany(@PathVariable long userId) {
        try {
            return projectService.readMany(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
	* This endpoint is used to create a project object.
	* @param project {Project}
	* @return Project
	*/
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public Project createProject(@RequestBody Project project) {
        try {
            return projectService.create(project);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
	* This endpoint is used to delete a project object by
	* its id.
	* @param id {Long}
	*/
    @RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable long id) {
        try {
            projectService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
	* This endpoint is used to update a project object.
	* @param project {Project}
	*/
    @RequestMapping(value = "/project", method = RequestMethod.PUT)
    public void updateProject(@RequestBody Project project) {
        try {
            projectService.update(project);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

