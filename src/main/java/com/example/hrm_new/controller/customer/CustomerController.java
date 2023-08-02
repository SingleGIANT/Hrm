package com.example.hrm_new.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.customer.Customer;
import com.example.hrm_new.repository.customer.CustomerRepository;
import com.example.hrm_new.service.customer.CustomerService;



@CrossOrigin
@RestController
public class CustomerController {
	@Autowired
	private CustomerService service;
	
	@Autowired
	private CustomerRepository repo;

	@GetMapping("/customers")
	public ResponseEntity<?> getCustomers() {
	
		try {
			
			List<Customer> Customers = service.listAll();
			return ResponseEntity.ok(Customers);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Customers: " + e.getMessage());
		}
	}

	@PostMapping("/customers/save")
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer) {
        try {          
        	customer.setStatus(true);
            service.saveOrUpdate(customer);
            

            return ResponseEntity.ok("Customer saved with id: " + customer.getCustomerId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving Customer: " + e.getMessage());
        }
    }
//	@RequestMapping("/customers/{id}")
//	public ResponseEntity<?> getCustomerById(@PathVariable(name = "id") long id) {
//		try {
//			Customer Customer = service.getById(id);
//			if (Customer != null) {
//				return ResponseEntity.ok(Customer);
//			} else {
//				return ResponseEntity.notFound().build();
//			}
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error retrieving Customer: " + e.getMessage());
//		}
//	}
	
	
	  @PutMapping("/customers/or/{id}")
	    public ResponseEntity<Boolean> toggleCustomerStatus(@PathVariable(name = "id") long id) {
	        try {
	            Customer customer = service.getById(id);
	            if (customer != null) {
	                // Customer with the given id exists, toggle the status
	                boolean currentStatus = customer.isStatus();
	                customer.setStatus(!currentStatus);
	                service.saveOrUpdate(customer); // Save the updated customer
	            } else {
	                // Customer with the given id does not exist, return false
	                return ResponseEntity.ok(false);
	            }

	            return ResponseEntity.ok(customer.isStatus()); // Return the new status (true or false)
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false); // Set response to false in case of an error
	        }
	    }



	@PutMapping("/customers/edit/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer Customer) {
		try {
			Customer existingCustomer = service.getById(id);
			if (existingCustomer == null) {
				return ResponseEntity.notFound().build();
			}
//			Customer.setStatus(true);
			existingCustomer.setAddress(Customer.getAddress());
			existingCustomer.setCity(Customer.getCity());
			existingCustomer.setName(Customer.getName());
			existingCustomer.setEmail(Customer.getEmail());
			existingCustomer.setCountry(Customer.getCountry());
			existingCustomer.setFormTypeId(Customer.getFormTypeId());
			existingCustomer.setGender(Customer.getGender());
			existingCustomer.setState(Customer.getState());
			existingCustomer.setDate(Customer.getDate());
			existingCustomer.setPhoneNo1(Customer.getPhoneNo1());
			existingCustomer.setPhoneNo2(Customer.getPhoneNo2());
//			existingCustomer.setStatus(Customer.isStatus());

			service.saveOrUpdate(existingCustomer);
			return ResponseEntity.ok(existingCustomer);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/customers/delete/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Customer deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Customer: " + e.getMessage());
		}
	}

	@GetMapping("/customers/view")
	public List<Map<String, Object>> allcompanyDetails() {
		return service.allCustomer();

	}
	

	
	@GetMapping("/customers/list/{customer_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("customer_id") Long customer_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.Allemployeeleave(customer_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("customer_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("customer_id", totalList.getKey());
	        announcementMap.put("name", totalList.getValue().get(0).get("name"));
	        announcementMap.put("customers Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}

	
	

	

}