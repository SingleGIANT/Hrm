package com.example.hrm_new.controller.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.example.hrm_new.entity.employee.EmergencyContacts;
import com.example.hrm_new.repository.employee.EmergencyContactsRepository;
import com.example.hrm_new.service.employee.EmergencyContactsService;

@RestController
@CrossOrigin
public class EmergencyContactsController {
	
	@Autowired
	private EmergencyContactsService service;
	@Autowired
	private EmergencyContactsRepository repo;
	
	@GetMapping("/emergencycontacts")
	public ResponseEntity<?> getEmergencyContactss() {
		try {
			List<EmergencyContacts> EmergencyContactss = service.listAll();
			return ResponseEntity.ok(EmergencyContactss);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving EmergencyContactss: " + e.getMessage());
		}
	}


	
	@PostMapping("/emergencycontacts/save")
	public ResponseEntity<String> saveEmergencyContacts(@RequestBody EmergencyContacts EmergencyContacts) {
		try {
			EmergencyContacts.setStatus(true);
			service.saveOrUpdate(EmergencyContacts);
			return ResponseEntity.ok("EmergencyContacts saved with id: " + EmergencyContacts.getEmergencyContactsId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving EmergencyContacts: " + e.getMessage());
		}
	}

	@PutMapping("/emergencycontacts/or/{id}")
	public ResponseEntity<?> getEmergencyContactsById(@PathVariable(name = "id") long id) {
		try {
			EmergencyContacts EmergencyContacts = service.getById(id);
			if (EmergencyContacts != null) {
				   boolean currentStatus = EmergencyContacts.isStatus();
				   EmergencyContacts.setStatus(!currentStatus);
	                service.saveOrUpdate(EmergencyContacts); // Save the updated complaints
	            } else {
	                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
	            }

	            return ResponseEntity.ok(EmergencyContacts.isStatus()); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(false);
		}
	}

	@PutMapping("/emergencycontacts/edit/{id}")
	public ResponseEntity<EmergencyContacts> updateEmergencyContacts(@PathVariable("id") long id, @RequestBody EmergencyContacts EmergencyContacts) {
		try {
			EmergencyContacts existingEmergencyContacts = service.getById(id);
			if (existingEmergencyContacts == null) {
				return ResponseEntity.notFound().build();
			}
			existingEmergencyContacts.setAddress(EmergencyContacts.getAddress());
			existingEmergencyContacts.setCity(EmergencyContacts.getCity());
			existingEmergencyContacts.setEmployeeId(EmergencyContacts.getEmployeeId());
			existingEmergencyContacts.setRelatinoName(EmergencyContacts.getRelatinoName());
			existingEmergencyContacts.setPhoneNumber(EmergencyContacts.getPhoneNumber());
			existingEmergencyContacts.setCountry(EmergencyContacts.getCountry());
			existingEmergencyContacts.setState(EmergencyContacts.getState());	
			existingEmergencyContacts.setRelationTypeId(EmergencyContacts.getRelationTypeId());

			
			
			

			service.saveOrUpdate(existingEmergencyContacts);
			return ResponseEntity.ok(existingEmergencyContacts);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/emergencycontacts/delete/{id}")
	public ResponseEntity<String> deleteEmergencyContacts(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("EmergencyContacts deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting EmergencyContacts: " + e.getMessage());
		}
	}
	
	@GetMapping("/emergencycontacts/view")
	public List<Map<String,Object>>AllWorks(){
		return repo.getAllProjectWork();
	}
	
	@GetMapping("/emergencycontacts/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.Allemergencycontacts(employee_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("employee_id", totalList.getKey());
	        announcementMap.put("relatino_name", totalList.getValue().get(0).get("relatino_name"));
	        announcementMap.put("emergencycontacts Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}
}
