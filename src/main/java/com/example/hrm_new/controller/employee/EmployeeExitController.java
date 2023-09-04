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

import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.entity.employee.EmployeeExit;
import com.example.hrm_new.repository.employee.EmployeeExitRepository;
import com.example.hrm_new.service.employee.EmployeeExitService;
import com.example.hrm_new.service.employee.EmployeeService;

@RestController
@CrossOrigin
public class EmployeeExitController {

	@Autowired
	private EmployeeExitService service;
	@Autowired
	private EmployeeExitRepository repo;

	@GetMapping("/employeeexit")
	public ResponseEntity<?> getEmployeeExits() {
		try {
			List<EmployeeExit> EmployeeExits = service.listAll();
			return ResponseEntity.ok(EmployeeExits);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving EmployeeExits: " + e.getMessage());
		}
	}

	@Autowired
	private EmployeeService employeeService;

	 @PostMapping("/employeeexit/save")
	    public ResponseEntity<String> saveEmployeeExit(@RequestBody EmployeeExit employeeExit) {
	        try {
	            employeeExit.setStatus(false);

	            Long employeeId = employeeExit.getEmployeeId();
	            Employee employee = employeeService.getEmployeeById(employeeId);
	            if (employee != null) {
	                employee.setStatus(false);
	                employeeService.saveOrUpdate(employee);
	            }

	            service.saveOrUpdate(employeeExit);

	            return ResponseEntity.ok("EmployeeExit saved with id: " + employeeExit.getEmployeeExitId());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error saving EmployeeExit: " + e.getMessage());
	        }
	    }

	@PutMapping("/employeeexit/or/{id}")
	public ResponseEntity<?> getEmployeeExitById(@PathVariable(name = "id") long id) {
		try {
			EmployeeExit EmployeeExit = service.getById(id);
			if (EmployeeExit != null) {
				 boolean currentStatus = EmployeeExit.isStatus();
				 EmployeeExit.setStatus(!currentStatus);
	                service.saveOrUpdate(EmployeeExit); 
	            } else {
	                return ResponseEntity.ok(false); 
	            }
	            return ResponseEntity.ok(EmployeeExit.isStatus());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false); 
	        }
	    }

	@PutMapping("/employeeexit/edit/{id}")
	public ResponseEntity<EmployeeExit> updateEmployeeExit(@PathVariable("id") long id,
			@RequestBody EmployeeExit EmployeeExit) {
		try {
			EmployeeExit existingEmployeeExit = service.getById(id);
			if (existingEmployeeExit == null) {
				return ResponseEntity.notFound().build();
			}
			existingEmployeeExit.setDescription(EmployeeExit.getDescription());
			existingEmployeeExit.setEmployeeId(EmployeeExit.getEmployeeId());
			existingEmployeeExit.setDate(EmployeeExit.getDate());
			existingEmployeeExit.setStatus(EmployeeExit.isStatus());

			service.saveOrUpdate(existingEmployeeExit);
			return ResponseEntity.ok(existingEmployeeExit);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/employeeexit/delete/{id}")
	public ResponseEntity<String> deleteEmployeeExit(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("EmployeeExit deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting EmployeeExit: " + e.getMessage());
		}
	}

	@GetMapping("/employeeexit/view")
	public List<Map<String, Object>> AllWorks() {
		return repo.getAllProjectWork();
	}

	@GetMapping("/employeeexit/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
		List<Map<String, Object>> announcementlist = new ArrayList<>();
		List<Map<String, Object>> list = repo.Allemployeeexit(employee_id);
		Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

		for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
			Map<String, Object> announcementMap = new HashMap<>();
			announcementMap.put("employee_id", totalList.getKey());
			announcementMap.put("date", totalList.getValue().get(0).get("date"));
			announcementMap.put("employeeexit Details", totalList.getValue());
			announcementlist.add(announcementMap);
		}
		return announcementlist;
	}
}
