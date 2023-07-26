package com.example.hrm_new.controller.project;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import com.example.hrm_new.repository.project.ProjectRepository;
import com.example.hrm_new.service.project.ProjectService;

@RestController
@CrossOrigin
public class ProjectController {

	@Autowired

	private ProjectService projectservice;
	@Autowired
	private ProjectRepository repo;

	@GetMapping("/project")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<Project> projectDetails = projectservice.listAll();

			return new ResponseEntity<>(projectDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/project/save")

	public ResponseEntity<?> saveBank(@RequestBody Project project) {

		try {
			project.setStatus(true);
			projectservice.SaveorUpdate(project);

			return ResponseEntity.status(HttpStatus.CREATED).body("project details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving project details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	@RequestMapping("/project/{projectId}")

	private Optional<Project> getProject(@PathVariable(name = "projectId") long projectId) {

		return projectservice.getProjectById(projectId);

	}

	@PutMapping("/project/editproject/{projectId}")

	public ResponseEntity<Project> updateProject(@PathVariable("projectId") Long projectId,
			@RequestBody Project projectDetails) {

		try {

			Project existingProject = projectservice.findById(projectId);

			if (existingProject == null) {

				return ResponseEntity.notFound().build();

			}

			existingProject.setProjectTitle(projectDetails.getProjectTitle());
			existingProject.setClientName(projectDetails.getClientName());
			existingProject.setContact(projectDetails.getContact());
			existingProject.setLocation(projectDetails.getLocation());
			existingProject.setTotalDuration(projectDetails.getTotalDuration());
			existingProject.setFromDate(projectDetails.getFromDate());
			existingProject.setTotalProjectAmount(projectDetails.getTotalProjectAmount());
			existingProject.setToDate(projectDetails.getToDate());
			existingProject.setStatus(projectDetails.isStatus());

			projectservice.save(existingProject);

			return ResponseEntity.ok(existingProject);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("/project/projectdelete/{projectId}")

	public ResponseEntity<String> deleteprojectName(@PathVariable("projectId") Long projectId) {

		projectservice.deleteProjectById(projectId);

		return ResponseEntity.ok("project deleted successfully");

	}

	@PostMapping("/findprojectbydate")
	public List<Project> findByDateBetween(@RequestParam("fromdate") Date from, @RequestParam("todate") Date to) {
		return repo.findByFromDateBetween(from, to);

	}

	@PostMapping("/projectdetailsbymonth")
	public List<Map<String, Object>> projectDetailsByMonth(@Param("year") int year, @Param("month") int month) {
		return repo.findByYearAndMonth(year, month);
	}

	@GetMapping("/countofprojects")
	public Map<String, Object> totalcountOfProjects() {
		return repo.findByCountOfProjects();

	}

	 @GetMapping("/countofprojectsbyclient")
	    public Map<String, Long> totalcountOfProjectsByClient() {
	        List<Object[]> clientProjectCounts = repo.getClientProjectCounts();

	        Map<String, Long> result = new HashMap<>();
	        for (Object[] row : clientProjectCounts) {
	            String clientName = (String) row[0];
	            BigInteger projectCountBigInteger = (BigInteger) row[1];
	            Long projectCount = projectCountBigInteger.longValue();
	            result.put(clientName, projectCount);
	        }
	        return result;
	    }


		@GetMapping("/durationofprojects")
		public List<Map<String, Object>> totalDurationOfProjects() {
			return repo.findBytotalDuration();

		}
	 
	
}
