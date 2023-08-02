package com.example.hrm_new.controller.employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.employee.Complaints;
import com.example.hrm_new.repository.employee.ComplaintsRepository;
import com.example.hrm_new.service.employee.ComplaintsService;



@CrossOrigin
@RestController
public class ComplaintsController {
	@Autowired
    private  ComplaintsService service;

	@Autowired
    private  ComplaintsRepository repo;


	@GetMapping("/complaints")
	public ResponseEntity<?> getComplaintss() {
		try {
			List<Complaints> Complaintss = service.listAll();
			return ResponseEntity.ok(Complaintss);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Complaintss: " + e.getMessage());
		}
	}

	

	
	@PostMapping("/complaints/save")
	public ResponseEntity<String> saveComplaints(@RequestBody Complaints Complaints) {
		try {
			Complaints.setStatus(true);
			service.saveOrUpdate(Complaints);
		
			return ResponseEntity.ok("Complaints saved with id: " + Complaints.getComplaintsId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving Complaints: " + e.getMessage());
		}
	}

	  @PutMapping("/complaints/or/{id}")
	    public ResponseEntity<Boolean> toggleComplaintsStatus(@PathVariable(name = "id") long id) {
	        try {
	            Complaints complaints = service.getById(id);
	            if (complaints != null) {
	                // Toggle the status
	                boolean currentStatus = complaints.isStatus();
	                complaints.setStatus(!currentStatus);
	                service.saveOrUpdate(complaints); // Save the updated complaints
	            } else {
	                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
	            }

	            return ResponseEntity.ok(complaints.isStatus()); // Return the new status (true or false)
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false); // Set response to false in case of an error
	        }
	    }

	@PutMapping("/complaints/edit/{id}")
	public ResponseEntity<Complaints> updateComplaints(@PathVariable("id") long id, @RequestBody Complaints Complaints) {
		try {
			Complaints existingComplaints = service.getById(id);
			if (existingComplaints == null) {
				return ResponseEntity.notFound().build();
			}
			existingComplaints.setComplaintsAgainst(Complaints.getComplaintsAgainst());
			existingComplaints.setDescription(Complaints.getDescription());
			existingComplaints.setEmployeeId(Complaints.getEmployeeId());
			existingComplaints.setComplaintsDate(Complaints.getComplaintsDate());
			existingComplaints.setComplaintsTitle(Complaints.getComplaintsTitle());
			//existingComplaints.setStatus(Complaints.isStatus());
			
			
			

			service.saveOrUpdate(existingComplaints);
			return ResponseEntity.ok(existingComplaints);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/complaints/delete/{id}")
	public ResponseEntity<String> deleteComplaints(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Complaints deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Complaints: " + e.getMessage());
		}
	}
	
	@GetMapping("/complaints/view")
	public List<Map<String,Object>>AllTermination(){
		return repo.AllComplaints();
	}
	
	@GetMapping("/complaints/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.Allcomplaints(employee_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("employee_id", totalList.getKey());
	        announcementMap.put("complaints_title", totalList.getValue().get(0).get("complaints_title"));
	        announcementMap.put("complaints Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}
	///////////////// 23 ///////////////////////
	 @PostMapping("/complaints/date")
	    public List<Map<String, Object>> getAllVoucherBetweenDates(
	            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	    ) {
	        return repo.getAllpromotionsBetweenDates(startDate, endDate);
	    }
	 
	 
	 ///////////////// 24 ///////////////////
	 
	 @GetMapping("/complaints/count")
	    public List<Map<String, Object>> getAllcomplaintsss(){
		return repo.getAllcomplaints() ;
	 }
	 
	 
}


