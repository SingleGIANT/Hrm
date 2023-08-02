package com.example.hrm_new.controller.employee;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.employee.EmployeeLeave;
import com.example.hrm_new.repository.employee.EmployeeLeaveRepository;
import com.example.hrm_new.service.employee.EmployeeLeaveService;
@RestController
@CrossOrigin
public class EmployeeLeaveController {
	@Autowired
	private EmployeeLeaveService service;
	@Autowired
	private EmployeeLeaveRepository repo;
	
	@GetMapping("/employeeleave")
	public ResponseEntity<?> getEmployeeLeaves() {
		try {
			List<EmployeeLeave> EmployeeLeaves = service.listAll();
			return ResponseEntity.ok(EmployeeLeaves);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving EmployeeLeaves: " + e.getMessage());
		}
	}


	
	@PostMapping("/employeeleave/save")
	public ResponseEntity<String> saveEmployeeLeave(@RequestBody EmployeeLeave EmployeeLeave) {
		try {
			
			Date date = EmployeeLeave .getDate();
			Date toDate = EmployeeLeave.getToDate();
			int totalDuration=calculateDuration(date,toDate);
			EmployeeLeave.setTotalDay(totalDuration);
			EmployeeLeave.setStatus(true);
			service.saveOrUpdate(EmployeeLeave);
			
			return ResponseEntity.ok("EmployeeLeave saved with id: " + EmployeeLeave.getEmployeeLeaveId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving EmployeeLeave: " + e.getMessage());
		}
	}
	
	  private int calculateDuration(Date date1, Date date2) {
	        long diffInMillis = Math.abs(date2.getTime() - date1.getTime());
	        int daysDifference = (int) (diffInMillis / (24 * 60 * 60 * 1000));
	        return daysDifference;
	    }


	@PutMapping("/employeeleave/or/{id}")
	public ResponseEntity<?> getEmployeeLeaveById(@PathVariable(name = "id") long id) {
		try {
			EmployeeLeave EmployeeLeave = service.getById(id);
			if (EmployeeLeave != null) {
				 boolean currentStatus = EmployeeLeave.isStatus();
				 EmployeeLeave.setStatus(!currentStatus);
	                service.saveOrUpdate(EmployeeLeave); // Save the updated complaints
	            } else {
	                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
	            }

	            return ResponseEntity.ok(EmployeeLeave.isStatus()); // Return the new status (true or false)
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false); // Set response to false in case of an error
	        }
	    }

	@PutMapping("/employeeleave/edit/{id}")
	public ResponseEntity<EmployeeLeave> updateEmployeeLeave(@PathVariable("id") long id, @RequestBody EmployeeLeave EmployeeLeave) {
		try {
			EmployeeLeave existingEmployeeLeave = service.getById(id);
			if (existingEmployeeLeave == null) {
				return ResponseEntity.notFound().build();
			}
			existingEmployeeLeave.setApprovedBy(EmployeeLeave.getApprovedBy());
			existingEmployeeLeave.setEmployeeId(EmployeeLeave.getEmployeeId());
			existingEmployeeLeave.setLeaveTypeId(EmployeeLeave.getLeaveTypeId());
			existingEmployeeLeave.setReason(EmployeeLeave.getReason());
			existingEmployeeLeave.setDate(EmployeeLeave.getDate());
			existingEmployeeLeave.setToDate(EmployeeLeave.getToDate());
			existingEmployeeLeave.setTotalDay(EmployeeLeave.getTotalDay());
//			existingEmployeeLeave.setStatus(EmployeeLeave.isStatus());
			 int totalDuration = calculateDuration(EmployeeLeave.getDate(), EmployeeLeave.getToDate());
			 existingEmployeeLeave.setTotalDay(totalDuration);
			
			

			service.saveOrUpdate(existingEmployeeLeave);
			return ResponseEntity.ok(existingEmployeeLeave);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/employeeleave/delete/{id}")
	public ResponseEntity<String> deleteEmployeeLeave(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("EmployeeLeave deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting EmployeeLeave: " + e.getMessage());
		}
	}
	
	@GetMapping("/employeeleave/view")
	public List<Map<String,Object>>AllWorks(){
		return repo.getAllProjectWork();
	}
	
	@GetMapping("/employeeleave/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.Allemployeeleave(employee_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("employee_id", totalList.getKey());
	        announcementMap.put("approved_by", totalList.getValue().get(0).get("approved_by"));
	        announcementMap.put("employeeleave Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}
	
	
	////////////////// 35///////////////////////
	
	 @PostMapping("/employeeleave/count")
	    public List<Map<String, Object>> getAllcomplaintsss(@RequestParam ("employee_id")long employee_id){
		return repo.getAllProject(employee_id) ;
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
}
