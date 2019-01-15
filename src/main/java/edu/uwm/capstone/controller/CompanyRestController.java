package edu.uwm.capstone.controller;

import edu.uwm.capstone.model.company.Company;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.uwm.capstone.db.company.CompanyDao;

@RestController
public class CompanyRestController {

    @Autowired
    private CompanyDao companyService;

    /**
     * This endpoint is used to retrieve a company object by
     * its id.
     * @param id {Long}
     * @return company
     */
    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public Company retrieveCompany(@PathVariable long id) {
        try {
            return companyService.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This endpoint is used to retrieve a list of company objects by
     * its userId.
     * @param userId {Long}
     * @return List<Map<String, Object>>
     */
    @RequestMapping(value="/company/retrievemany/{userId}", method = RequestMethod.GET)
    public List<Company> retrieveManyCompanies(@PathVariable long userId) {
        try {
            return companyService.readMany(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This endpoint is used to create a company object.
     * @param company {Company}
     * @return Company
     */
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public Company createCompany(@RequestBody Company company) {
        try {
            return companyService.create(company);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This endpoint is used to delete a company object by
     * its id.
     * @param id {Long}
     */
    @RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable long id) {
        try {
            companyService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * This endpoint is used to update a company object.
     * @param company {Company}
     */
    @RequestMapping(value = "/company", method = RequestMethod.PUT)
    public void updateCompany(@RequestBody Company company) {
        try {
            companyService.update(company);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}