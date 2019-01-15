package edu.uwm.capstone.controller;

import edu.uwm.capstone.model.contact.Contact;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.uwm.capstone.db.contact.ContactDao;

@RestController
public class ContactRestController {

    @Autowired
    private ContactDao contactService;

    /**
 	* This endpoint is used to retrieve a contact object by
	* its id.
	* @param id {Long}
	* @return Contact
	*/
    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public Contact retrieveContact(@PathVariable long id) {
        try {
            return contactService.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
 	* This endpoint is used to retrieve a list of contact objects by
	* its userId.
	* @param userId {Long}
	* @return List<Map<String, Object>>
	*/
    @RequestMapping(value = "/contact/retrievemany/{userId}", method = RequestMethod.GET)
    public List<Contact> retrieveManyContacts(@PathVariable long userId) {
        try {
            return contactService.readMany(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
	* This endpoint is used to create a contact object.
	* @param contact {Contact}
	* @return Contact
	*/
    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public Contact createContact(@RequestBody Contact contact) {
        try {
            return contactService.create(contact);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
	* This endpoint is used to delete a contact object by
	* its id.
	* @param id {Long}
	*/
    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    public void deleteContact(@PathVariable long id) {
        try {
            contactService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
	* This endpoint is used to update a contact object.
	* @param contact {Contact}
	*/
    @RequestMapping(value = "/contact", method = RequestMethod.PUT)
    public void updateContact(@RequestBody Contact contact) {
        try {
            contactService.update(contact);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}