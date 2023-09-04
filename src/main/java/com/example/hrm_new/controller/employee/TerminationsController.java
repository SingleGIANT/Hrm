package com.example.hrm_new.controller.employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.entity.employee.Terminations;
import com.example.hrm_new.repository.employee.TerminationsRepository;
import com.example.hrm_new.service.employee.EmployeeService;
import com.example.hrm_new.service.employee.TerminationsService;

@CrossOrigin
@RestController
public class TerminationsController {
	@Autowired
	private TerminationsService service;
	@Autowired
	private TerminationsRepository repo;
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/terminations")
	public ResponseEntity<?> getTerminationss() {
		try {
			List<Terminations> Terminationss = service.listAll();
			return ResponseEntity.ok(Terminationss);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Terminationss: " + e.getMessage());
		}
	}

	@PostMapping("/terminations/save")
	public ResponseEntity<String> saveTerminations(@RequestBody Terminations Terminations) {
		try {
			
			Terminations.setStatus(false);

	            Long employeeId = Terminations.getEmployeeId();
	            Employee employee = employeeService.getEmployeeById(employeeId);
	            if (employee != null) {
	                employee.setStatus(false);
	                employeeService.saveOrUpdate(employee);
	            }
			Terminations.setStatus(true);
			service.saveOrUpdate(Terminations);
			return ResponseEntity.ok("Terminations saved with id: " + Terminations.getTerminationsId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving Terminations: " + e.getMessage());
		}
	}

	@PutMapping("/terminations/or/{id}")
	public ResponseEntity<?> getTerminationsById(@PathVariable(name = "id") long id) {
		try {
			Terminations Terminations = service.getById(id);
			if (Terminations != null) {
				boolean currentStatus = Terminations.isStatus();
				Terminations.setStatus(!currentStatus);
                service.saveOrUpdate(Terminations); // Save the updated complaints
            } else {
                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
            }

            return ResponseEntity.ok(Terminations.isStatus()); // Return the new status (true or false)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false); // Set response to false in case of an error
        }
    }

	@PutMapping("/terminations/edit/{id}")
	public ResponseEntity<Terminations> updateTerminations(@PathVariable("id") long id,
			@RequestBody Terminations Terminations) {
		try {
			Terminations existingTerminations = service.getById(id);
			if (existingTerminations == null) {
				return ResponseEntity.notFound().build();
			}

			existingTerminations.setTerminationsDate(Terminations.getTerminationsDate());
			existingTerminations.setDescription(Terminations.getDescription());
			existingTerminations.setEmployeeId(Terminations.getEmployeeId());
			existingTerminations.setTerminationsType(Terminations.getTerminationsType());
			existingTerminations.setStatus(Terminations.isStatus());

			service.saveOrUpdate(existingTerminations);
			return ResponseEntity.ok(existingTerminations);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/terminations/delete/{id}")
	public ResponseEntity<String> deleteTerminations(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Terminations deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Terminations: " + e.getMessage());
		}
	}

	@GetMapping("/terminations/view")
	public List<Map<String, Object>> AllTermination() {
		return repo.AllTerminations();
	}

	@GetMapping("/terminations/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
		List<Map<String, Object>> announcementlist = new ArrayList<>();
		List<Map<String, Object>> list = repo.Allterminations(employee_id);
		Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

		for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
			Map<String, Object> announcementMap = new HashMap<>();
			announcementMap.put("employee_id", totalList.getKey());
			announcementMap.put("terminations_type", totalList.getValue().get(0).get("terminations_type"));
			announcementMap.put("terminations Details", totalList.getValue());
			announcementlist.add(announcementMap);
		}
		return announcementlist;
	}

///////////////// 26 ///////////////////////
	
	
	@PostMapping("/terminations/date")
	public List<Map<String, Object>> getAllVoucherBetweenDates(
			@RequestBody Map<String, Object> requestBody) {
	    LocalDate startDate = LocalDate.parse(requestBody.get("startDate").toString(), DateTimeFormatter.ISO_DATE);
	    LocalDate endDate = LocalDate.parse(requestBody.get("endDate").toString(), DateTimeFormatter.ISO_DATE);
		return repo.getAllpromotionsBetweenDates(startDate, endDate);
	}

}
