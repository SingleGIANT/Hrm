package com.example.hrm_new.controller.WorkSheet;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.entity.worksheet.WorkSheet;
import com.example.hrm_new.repository.worksheet.WorkSheetRepository;
import com.example.hrm_new.service.worksheet.WorkSheetService;

@RestController
@CrossOrigin
public class WorkSheetController {

	
	@Autowired
	private WorkSheetService workSheetservice;
	
	@Autowired
	private WorkSheetRepository repo;
	
	@GetMapping("/worksheet")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<WorkSheet> workSheetDetails = workSheetservice.listAll();

			return new ResponseEntity<>(workSheetDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/worksheet/save")

	public ResponseEntity<?> saveBank(@RequestBody WorkSheet workSheet) {

		try {
			
			workSheet.setStatus(true);
			workSheetservice.SaveorUpdate(workSheet);

			return ResponseEntity.status(HttpStatus.CREATED).body("workSheet details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving company details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}



	@RequestMapping("/workSheet/{workSheetId}")

	private Optional<WorkSheet> getAnnouncement(@PathVariable(name = "workSheetId") long workSheetId) {

		return workSheetservice.getWorkSheetById(workSheetId);

	}
		
	@PutMapping("/workSheet/editWorkSheet/{workSheetId}")

	public ResponseEntity<WorkSheet> updateWorkSheet(@PathVariable("workSheetId") Long workSheetId, @RequestBody WorkSheet workSheetDetails) {

		try {

			WorkSheet existingWorkSheet = workSheetservice.findById(workSheetId);

			if (existingWorkSheet == null) {

				return ResponseEntity.notFound().build();

			}

			existingWorkSheet.setProjectTitle(workSheetDetails.getProjectTitle());
			existingWorkSheet.setEmployeeNameId(workSheetDetails.getEmployeeNameId());
			existingWorkSheet.setFromDate(workSheetDetails.getFromDate());
			existingWorkSheet.setToDate(workSheetDetails.getToDate());
			existingWorkSheet.setProjectId(workSheetDetails.getProjectId());
			existingWorkSheet.setTotalDuration(workSheetDetails.getTotalDuration());
			existingWorkSheet.setDescription(workSheetDetails.getDescription());
			existingWorkSheet.setStatus(workSheetDetails.isStatus());
			
			workSheetservice.save(existingWorkSheet);

			return ResponseEntity.ok(existingWorkSheet);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	
	@DeleteMapping("/workSheet/workSheetdelete/{workSheetId}")

	public ResponseEntity<String> deleteworkSheet(@PathVariable("workSheetId") Long workSheetId) {

		workSheetservice.deleteWorkSheetById(workSheetId);

		return ResponseEntity.ok("workSheet deleted successfully");

	}
	@PostMapping("/findworksheetbydate")
	public List<WorkSheet> findByDateBetween(@RequestParam("fromdate") Date from, @RequestParam("todate") Date to) {
		return repo.findByFromDateBetween(from, to);

	}
	
	@PostMapping("/worksheetdetailsbymonth")
	public List<Map<String, Object>> worksheetDetailsByMonth(@Param("year") int year, @Param("month") int month) {
		return repo.findByYearAndMonth(year, month);
	}
	
	@GetMapping("/worksheetdetails/view")
	public 	List <Map<String,Object>> allWorkSheetDetails(){
     return workSheetservice.allWorkSheetDetails();

}
	@GetMapping("/employeecountinoneproject")
	public 	List <Map<String,Object>> employreeCount(){
     return repo.employreeCount();
}
	@GetMapping("/durationofprojectsWorksheet")
	public List<Map<String, Object>> totalDurationOfProjects() {
		return repo.findBytotalDuration();

	}

	@PostMapping("/wowrksheetdetailsbyprojecttitle")
	public 	List <Map<String,Object>> findByProjectTitle(@Param("project_title") String project_title){
		return repo.findByProjectTitle(project_title);

}

}
