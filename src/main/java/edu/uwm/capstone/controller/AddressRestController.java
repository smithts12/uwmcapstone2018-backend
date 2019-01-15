package edu.uwm.capstone.controller;

import edu.uwm.capstone.model.address.Address;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.uwm.capstone.db.address.AddressDao;

@RestController
public class AddressRestController {

    @Autowired
    private AddressDao addressService;

    /**
 	* This endpoint is used to retrieve a address object by
	* its id.
	* @param id {Long}
	* @return Address
	*/
    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    public Address retrieveAddress(@PathVariable long id) {
        return addressService.read(id);
    }
    
    /**
 	* This endpoint is used to retrieve a list of address objects by
	* its userId.
	* @param userId {Long}
	* @return List<Map<String, Object>>
	*/
    @RequestMapping(value = "/address/retrievemany/{userId}", method = RequestMethod.GET)
    public List<Address> retrieveManyAddresses(@PathVariable long userId) {
        return addressService.readMany(userId);
    }

    /**
	* This endpoint is used to create a address object.
	* @param address {Address}
	* @return Address
	*/
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public Address createAddress(@RequestBody Address address) {
        return addressService.create(address);
    }

    /**
	* This endpoint is used to delete a address object by
	* its id.
	* @param id {Long}
	*/
    @RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
    public void deleteAddress(@PathVariable long id) {
        addressService.delete(id);
    }

    /**
	* This endpoint is used to update a address object.
	* @param address {Address}
	*/
    @RequestMapping(value = "/address", method = RequestMethod.PUT)
    public void updateAddress(@RequestBody Address address) {
        addressService.update(address);
    }
}