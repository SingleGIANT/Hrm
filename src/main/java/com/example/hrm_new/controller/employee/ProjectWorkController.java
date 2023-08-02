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

import com.example.hrm_new.entity.employee.ProjectWork;
import com.example.hrm_new.repository.employee.ProjectWorkReposirory;
import com.example.hrm_new.service.employee.ProjectWorkService;

@RestController
@CrossOrigin
public class ProjectWorkController {

	@Autowired
	private ProjectWorkService service;
	@Autowired
	private ProjectWorkReposirory repo;

	@GetMapping("/projectwork")
	public ResponseEntity<?> getProjectWorks() {
		try {
			List<ProjectWork> ProjectWorks = service.listAll();
			return ResponseEntity.ok(ProjectWorks);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving ProjectWorks: " + e.getMessage());
		}
	}

	@PostMapping("/projectwork/save")
	public ResponseEntity<String> saveProjectWork(@RequestBody ProjectWork ProjectWork) {
		try {

			ProjectWork.setStatus(true);
			service.saveOrUpdate(ProjectWork);
			return ResponseEntity.ok("ProjectWork saved with id: " + ProjectWork.getProjectWorkId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving ProjectWork: " + e.getMessage());
		}
	}

	@PutMapping("/ProjectWork/or/{id}")
	public ResponseEntity<?> getProjectWorkById(@PathVariable(name = "id") long id) {
		try {
			ProjectWork ProjectWork = service.getById(id);
			if (ProjectWork != null) {
				boolean currentStatus = ProjectWork.isStatus();
				ProjectWork.setStatus(!currentStatus);
                service.saveOrUpdate(ProjectWork); // Save the updated complaints
            } else {
                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
            }

            return ResponseEntity.ok(ProjectWork.isStatus()); // Return the new status (true or false)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false); // Set response to false in case of an error
        }
    }

	@PutMapping("/projectwork/edit/{id}")
	public ResponseEntity<ProjectWork> updateProjectWork(@PathVariable("id") long id,
			@RequestBody ProjectWork ProjectWork) {
		try {
			ProjectWork existingProjectWork = service.getById(id);
			if (existingProjectWork == null) {
				return ResponseEntity.notFound().build();
			}
			existingProjectWork.setDesignationId(ProjectWork.getDesignationId());
			existingProjectWork.setDuration(ProjectWork.getDuration());
			existingProjectWork.setEmployeeId(ProjectWork.getEmployeeId());
			existingProjectWork.setProjectId(ProjectWork.getProjectId());
			existingProjectWork.setDate(ProjectWork.getDate());
			existingProjectWork.setStatus(ProjectWork.isStatus());

			service.saveOrUpdate(existingProjectWork);
			return ResponseEntity.ok(existingProjectWork);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/projectwork/delete/{id}")
	public ResponseEntity<String> deleteProjectWork(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("ProjectWork deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting ProjectWork: " + e.getMessage());
		}
	}

	@GetMapping("/projectwork/view")
	public List<Map<String, Object>> AllWorks() {
		return repo.getAllProjectWork();
	}

	@GetMapping("/projectwork/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
		List<Map<String, Object>> announcementlist = new ArrayList<>();
		List<Map<String, Object>> list = repo.Allprojectwork(employee_id);
		Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

		for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
			Map<String, Object> announcementMap = new HashMap<>();
			announcementMap.put("employee_id", totalList.getKey());
			announcementMap.put("project_name", totalList.getValue().get(0).get("project_name"));
			announcementMap.put("projectwork Details", totalList.getValue());
			announcementlist.add(announcementMap);
		}
		return announcementlist;
	}

///////////////////   28   /////////////////////////	
	@PostMapping("/projectwork/date")
	public List<Map<String, Object>> getAllVoucherBetweenDates(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return repo.getAllpromotionsBetweenDates(startDate, endDate);
	}

///////////////////   29   /////////////////////////

	@GetMapping("/projectwork/count1/{employee_id}")
	public List<Map<String, Object>> getEmployeeProjectsDetails(@PathVariable Long employee_id) {
		return repo.getEmployeeProjectsDetails(employee_id);
	}

	@GetMapping("/projectwork/count/{employeeId}")
	public List<Map<String, Object>> getEmployeeProjectsDetails1(@PathVariable Long employeeId) {
		return repo.getEmployeeProjectWorkDetails(employeeId);
	}

	@GetMapping("/projectwork/count2/{employeeId}")
	public List<Map<String, Object>> getEmployeeProjectsDetails2(@PathVariable Long employeeId) {
		return repo.getEmployeeProjectWorkDetailsss(employeeId);
	}
	
	///////////////30//////////////////
	
	@PostMapping("/projectwork/name")
	public List<Map<String, Object>> getAllVoucherBetweenDates(@RequestParam("project_name") String project_name) {
	    return repo.getAllProjectNamelll(project_name);
	}
	

}
