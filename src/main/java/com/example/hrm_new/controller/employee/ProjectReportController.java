package com.example.hrm_new.controller.employee;

import java.sql.Date;
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

import com.example.hrm_new.entity.employee.ProjectReport;
import com.example.hrm_new.repository.employee.ProjectReportRepository;
import com.example.hrm_new.service.employee.ProjectReportService;

@RestController
@CrossOrigin
public class ProjectReportController {

	@Autowired
	private ProjectReportService service;
	@Autowired
	private ProjectReportRepository repo;

	@GetMapping("/projectreport")
	public ResponseEntity<?> getProjectReports() {
		try {
			List<ProjectReport> ProjectReports = service.listAll();
			return ResponseEntity.ok(ProjectReports);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving ProjectReports: " + e.getMessage());
		}
	}

//	@PostMapping("/projectreport/save")
//	public ResponseEntity<String> saveProjectReport(@RequestBody ProjectReport ProjectReport) {
//		try {
//			ProjectReport.setStatus(true);
//			service.saveOrUpdate(ProjectReport);
//			return ResponseEntity.ok("ProjectReport saved with id: " + ProjectReport.getProjectReportId());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error saving ProjectReport: " + e.getMessage());
//		}
//	}
	
	  @PostMapping("/projectreport/save")
	    public ResponseEntity<String> saveProjectReport(@RequestBody ProjectReport projectReport) {
	        try {
	            Date dateGive = projectReport.getDateGive();
	            Date extendedDate = projectReport.getExtendedDate();
	            int duration = calculateDuration(dateGive, extendedDate);
	            projectReport.setDuration(duration);
	            projectReport.setStatus(true);
	            service.saveOrUpdate(projectReport);
	            return ResponseEntity.ok("ProjectReport saved with id: " + projectReport.getProjectReportId());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error saving ProjectReport: " + e.getMessage());
	        }
	    }

	    // Helper method to calculate the duration between two dates
	    private int calculateDuration(Date date1, Date date2) {
	        long diffInMillis = Math.abs(date2.getTime() - date1.getTime());
	        int daysDifference = (int) (diffInMillis / (24 * 60 * 60 * 1000));
	        return daysDifference;
	    }

	@PutMapping("/projectreport/or/{id}")
	public ResponseEntity<?> getProjectReportById(@PathVariable(name = "id") long id) {
		try {
			ProjectReport ProjectReport = service.getById(id);
			if (ProjectReport != null) {
				boolean currentStatus = ProjectReport.isStatus();
				ProjectReport.setStatus(!currentStatus);
                service.saveOrUpdate(ProjectReport); // Save the updated complaints
            } else {
                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
            }

            return ResponseEntity.ok(ProjectReport.isStatus()); // Return the new status (true or false)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false); // Set response to false in case of an error
        }
    }
	@PutMapping("/projectreport/edit/{id}")
	public ResponseEntity<ProjectReport> updateProjectReport(@PathVariable("id") long id,
			@RequestBody ProjectReport ProjectReport) {
		try {
			ProjectReport existingProjectReport = service.getById(id);
			if (existingProjectReport == null) {
				return ResponseEntity.notFound().build();
			}
			existingProjectReport.setExtendedDate(ProjectReport.getExtendedDate());
			existingProjectReport.setDuration(ProjectReport.getDuration());
			existingProjectReport.setEmployeeId(ProjectReport.getEmployeeId());
			existingProjectReport.setProjectId(ProjectReport.getProjectId());
			existingProjectReport.setDateGive(ProjectReport.getDateGive());
			existingProjectReport.setStatus(ProjectReport.isStatus());

			service.saveOrUpdate(existingProjectReport);
			return ResponseEntity.ok(existingProjectReport);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/projectreport/delete/{id}")
	public ResponseEntity<String> deleteProjectReport(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("ProjectReport deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting ProjectReport: " + e.getMessage());
		}
	}

	@GetMapping("/projectreport/view")
	public List<Map<String, Object>> AllWorks() {
		return repo.getAllProjectWork();
	}

	@GetMapping("/projectreport/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
		List<Map<String, Object>> announcementlist = new ArrayList<>();
		List<Map<String, Object>> list = repo.Allprojectreport(employee_id);
		Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

		for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
			Map<String, Object> announcementMap = new HashMap<>();
			announcementMap.put("employee_id", totalList.getKey());
			announcementMap.put("duration", totalList.getValue().get(0).get("duration"));
			announcementMap.put("projectreport Details", totalList.getValue());
			announcementlist.add(announcementMap);
		}
		return announcementlist;
	}

	//////////////// 31 /////////////////////

	@PostMapping("/projectreport/date")
	public List<Map<String, Object>> getAllVoucherBetweenDates(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return repo.getAllpromotionsBetweenDates(startDate, endDate);
	}

	//////////////////////// 32 ///////////////////////////////////
	
	
	  @GetMapping("/projectreport/count")
	    public List<Map<String, Object>> getPurchaseAndSales() {
	        return repo.getPurchaseAndSales();
	    }
	
	///////////////// 33 ///////////////////
	

}
