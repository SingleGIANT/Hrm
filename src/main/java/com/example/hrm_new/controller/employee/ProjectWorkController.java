package com.example.hrm_new.controller.employee;

import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.entity.employee.ProjectWork;
import com.example.hrm_new.entity.employee.ProjectWorkResponse;
import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.repository.employee.ProjectWorkReposirory;
import com.example.hrm_new.service.employee.ProjectWorkService;
import com.example.hrm_new.service.project.ProjectService;

@RestController
@CrossOrigin
public class ProjectWorkController {

	@Autowired
	private ProjectWorkService service;
	@Autowired
	private ProjectWorkReposirory repo;

	@Autowired
	private ProjectService service1;

	@GetMapping("/projectwork")
	public ResponseEntity<List<ProjectWork>> getProjectWorks() {
		try {
			List<ProjectWork> projectWorks = service.listAll();
			return ResponseEntity.ok(projectWorks);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}
	private boolean isSameDay(Date date1, Date date2) {
		return date1.toLocalDate().isEqual(date2.toLocalDate());
	}

	private boolean isNextDay(Date date1, Date date2) {
		return date1.toLocalDate().isEqual(date2.toLocalDate().plusDays(1));
	}

	private boolean isBeforeDateOrSameDay(Date date1, Date date2) {
	    return date1.before(date2);
	}

	@PostMapping("/projectwork/save")
	public ResponseEntity<String> saveProjectWork(@RequestBody ProjectWork projectWork) {
	    try {
	        if (projectWork == null) {
	            return ResponseEntity.badRequest().body("Project work data is null.");
	        }

	        Long projectId = projectWork.getProjectId();
	        Project project = service1.getprojectById(projectId);

	        if (project != null) {
	            project.setProjectStatus(false);
	            service1.SaveorUpdate(project);
	        } else {
	            return ResponseEntity.badRequest().body("Project with ID " + projectId + " not found.");
	        }

	        Date currentDate = new Date(System.currentTimeMillis());
	        Date workDate = projectWork.getDate();

	        if (isSameDay(currentDate, workDate)) {
	            // Work date is the same day as the current date
	            projectWork.setStarted(true);
	            projectWork.setOnProcess(false);
	            projectWork.setPending(false);
	            projectWork.setHold(false);
	            projectWork.setCompleted(false);
	            projectWork.setWork("Started");
	        } else if (isBeforeDateOrSameDay(workDate, currentDate)) {
	            // Work date is before or the same as the current date
	            projectWork.setStarted(false);
	            projectWork.setOnProcess(true);
	            projectWork.setPending(false);
	            projectWork.setHold(false);
	            projectWork.setCompleted(false);
	            projectWork.setWork("OnProcess");
	        } else if (isNextDay(workDate, currentDate)) {
	            // Work date is the next day after the current date
	            projectWork.setStarted(false);
	            projectWork.setOnProcess(false);
	            projectWork.setPending(true);
	            projectWork.setHold(false);
	            projectWork.setCompleted(false);
	            projectWork.setWork("Pending");
	        } else {
	            // Other cases (Hold, Completed, etc.)
	            String workStatus = projectWork.getWork();
	            if (workStatus != null) {
	                switch (workStatus) {
	                    case "OnProcess":
	                        projectWork.setOnProcess(true);
	                        break;
	                    case "Hold":
	                        projectWork.setHold(true);
	                        break;
	                    case "Completed":
	                        projectWork.setCompleted(true);
	                        break;
	                    default:
	                        // Handle other work statuses or raise an error if needed
	                }
	            } else {
	                // Handle the case where workStatus is null
	                // You can add appropriate handling here, like setting a default value
	                projectWork.setWork("Pending");
	            }
	        }

	        projectWork.setStatus(true);
	        service.saveOrUpdate(projectWork);

	        return ResponseEntity.ok("Project work saved successfully. ProjectWorkId: " + projectWork.getProjectWorkId());
	    } catch (Exception e) {
	        // Log the exception for debugging purposes
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error saving project work: " + e.getMessage());
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); // Set response to false in case
																						// of an error
		}
	}

	@PutMapping("/projectwork/edit/{id}")
	public ResponseEntity<ProjectWork> updateOrSaveProjectWork(@PathVariable("id") long id,
			@RequestBody ProjectWork updatedProjectWork) {
		try {
			ProjectWork existingProjectWork = service.getById(id);
			if (existingProjectWork == null) {
				return ResponseEntity.notFound().build();
			}

			if (existingProjectWork.isCompleted()) {
				return ResponseEntity.notFound().build();
			}

//	            existingProjectWork.setPending(true);
//	            existingProjectWork.setWork("Pending");

	            existingProjectWork.setDesignationId(updatedProjectWork.getDesignationId());
	            existingProjectWork.setDateCompleted(updatedProjectWork.getDateCompleted());
	            existingProjectWork.setHoldReson(updatedProjectWork.getHoldReson());
	            existingProjectWork.setDuration(updatedProjectWork.getDuration());
	            existingProjectWork.setEmployeeId(updatedProjectWork.getEmployeeId());
	            existingProjectWork.setProjectId(updatedProjectWork.getProjectId());
	            existingProjectWork.setDate(updatedProjectWork.getDate());
	            existingProjectWork.setOnProcess(updatedProjectWork.isOnProcess());
	            existingProjectWork.setDescription(updatedProjectWork.getDescription());
	            existingProjectWork.setDepartmentId(updatedProjectWork.getDepartmentId());

			existingProjectWork.setWork(updatedProjectWork.getWork());

//	            // Check if the date is equal to the current date
//	            Date currentDate = new Date(System.currentTimeMillis()); // Use the current date
//	            Date workDate = existingProjectWork.getDate(); // Use the existing project work date

			if ("OnProcess".equals(updatedProjectWork.getWork())) {
				existingProjectWork.setStarted(false);
				existingProjectWork.setOnProcess(true);
				existingProjectWork.setHold(false);
				existingProjectWork.setPending(false);
				existingProjectWork.setCompleted(false);
				existingProjectWork.setWork("OnProcess");
			} else if ("Hold".equals(updatedProjectWork.getWork())) {
				existingProjectWork.setStarted(false);
				existingProjectWork.setOnProcess(false);
				existingProjectWork.setHold(true);
				existingProjectWork.setPending(false);
				existingProjectWork.setCompleted(false);
				existingProjectWork.setWork("Hold");
			} else if ("Pending".equals(updatedProjectWork.getWork())) {
				existingProjectWork.setStarted(false);
				existingProjectWork.setOnProcess(false);
				existingProjectWork.setHold(false);
				existingProjectWork.setPending(true);
				existingProjectWork.setCompleted(false);
				existingProjectWork.setWork("Pending");
			} else if ("Completed".equals(updatedProjectWork.getWork())) {
				existingProjectWork.setStarted(false);
				existingProjectWork.setOnProcess(false);
				existingProjectWork.setHold(false);
				existingProjectWork.setPending(false);
				existingProjectWork.setCompleted(true);
				existingProjectWork.setWork("Completed");
			}

			service.saveOrUpdate(existingProjectWork);

			return ResponseEntity.ok(existingProjectWork);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/update/status")
	public ResponseEntity<String> updateProjectStatus(@RequestBody ProjectWork projectWork) {
		try {
			Long projectWorkId = projectWork.getProjectWorkId();
			ProjectWork ProjectWork1 = service.getById(projectWorkId);
			if (ProjectWork1 == null) {
				return ResponseEntity.notFound().build();
			}
			Date currentDate = new Date(System.currentTimeMillis());
			Date workDate = ProjectWork1.getDate();
			 if (isSameDay(currentDate, workDate)) {
		            // Work date is the same day as current date
		            projectWork.setStarted(true);
		            projectWork.setOnProcess(false);
		            projectWork.setPending(false);
		            projectWork.setHold(false);
		            projectWork.setCompleted(false);
		            projectWork.setWork("Started");
		        }

		        else if (isBeforeDateOrSameDay(workDate, currentDate)) {
		            // Work date is before or same as current date
		            projectWork.setStarted(false);
		            projectWork.setOnProcess(true);
		            projectWork.setPending(false);
		            projectWork.setHold(false);
		            projectWork.setCompleted(false);
		            projectWork.setWork("OnProcess");
		        } else if (isNextDay(workDate, currentDate)) {
		            // Work date is the next day after current date
		            projectWork.setStarted(false);
		            projectWork.setOnProcess(false);
		            projectWork.setPending(true);
		            projectWork.setHold(false);
		            projectWork.setCompleted(false);
		            projectWork.setWork("Pending");
		        }
			ProjectWork1.setStatus(true);
			service.saveOrUpdate(ProjectWork1);
			return ResponseEntity.ok("ProjectWork status updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating project status: " + e.getMessage());
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

//	@GetMapping("/projectwork/view")
//	public List<ProjectWorkResponse> getAllWorks() {
//	    List<Map<String, Object>> queryResult = repo.getAllProjectWork();
//	    List<ProjectWorkResponse> responses = new ArrayList<>();
//
//	    for (Map<String, Object> row : queryResult) {
//	        ProjectWorkResponse response = new ProjectWorkResponse();
//
//	        response.setProjectWorkId(new BigInteger(row.get("project_work_id").toString().trim()).longValue());
//	        response.setProjectId(new BigInteger(row.get("project_id").toString().trim()).longValue());
//	        response.setProjectTitle((String) row.get("project_title")); // Set project title here
//	        response.setDescription((String) row.get("description"));
//	        Object dateObj = row.get("date");
//	        response.setDate(dateObj != null ? dateObj.toString() : null);
//
//	    
//	        response.setDuration((String) row.get("duration"));
//
//	        response.setStatus((Boolean) row.get("status"));
//
//	        response.setEmployeeId(parseIds((String) row.get("employee_id")));
//	        response.setFirstName(parseNames((String) row.get("first_name")));
//	        response.setLastName(parseNames((String) row.get("last_name")));
//	        response.setDesignationName(parseNames((String) row.get("designation_name")));
//	        response.setDesignationId(parseIds((String) row.get("designation_id")));
//	        response.setDepartmentId(parseIds((String) row.get("department_id")));
//	        response.setDepartmentName(parseNames((String) row.get("department_name")));
//	        responses.add(response);
//	    }
//
//	    return responses;
//	}

//	@GetMapping("/projectwork/view")
//	public List<Map<String, Object>> allcompanyDetails() {
//		return repo.getAllProjectWork();
//	
//	}

	@GetMapping("/projectwork/view")
	public List<ProjectWorkResponse> getAllWorks() {
		List<Map<String, Object>> queryResult = repo.getAllProjectWork();
		List<ProjectWorkResponse> responses = new ArrayList<>();

		for (Map<String, Object> row : queryResult) {
			ProjectWorkResponse response = new ProjectWorkResponse();

			response.setProjectWorkId(((BigInteger) row.get("project_work_id")).longValue());
			response.setProjectId(((BigInteger) row.get("project_id")).longValue());
			response.setProjectTitle((String) row.get("project_title"));
			response.setDescription((String) row.get("description"));

			Object dateObj = row.get("date");
			response.setDate(dateObj != null ? dateObj.toString() : null);

			response.setDuration((String) row.get("duration"));
			response.setStatus((Boolean) row.get("status"));

			response.setEmployeeId(parseIds((String) row.get("employee_id")));
			response.setFirstName(parseNames((String) row.get("first_name")));
			response.setLastName(parseNames((String) row.get("last_name")));
			response.setDesignationName(parseNames((String) row.get("designation_name")));
			response.setDepartmentName(parseNames((String) row.get("department_name")));
			response.setDesignationId(parseIds((String) row.get("designation_id")));
			response.setDepartmentId(parseIds((String) row.get("department_id")));

			response.setOnProcess((Boolean) row.get("on_process"));
			response.setHold((Boolean) row.get("hold"));
			response.setStarted((Boolean) row.get("started"));
			response.setCompleted((Boolean) row.get("completed"));

			response.setWork((String) row.get("work"));

			// Check if date_completed is not null before setting the date
			if (row.get("date_completed") != null) {
				response.setDateCompleted(((java.sql.Date) row.get("date_completed")).toLocalDate());
			} else {
				response.setDateCompleted(null);
			}

			response.setHoldReson((String) row.get("hold_reson"));
			response.setPending((Boolean) row.get("pending"));

			responses.add(response);
		}

		return responses;
	}

	private List<Long> parseIds(String value) {
		if (value != null && !value.isEmpty()) {
			return Arrays.stream(value.split(",")).filter(str -> !str.isEmpty()).map(Long::valueOf)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private List<String> parseNames(String value) {
		if (value != null && !value.isEmpty()) {
			return Arrays.stream(value.split(",")).filter(str -> !str.isEmpty()).collect(Collectors.toList());
		}
		return Collections.emptyList();
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
//	@PostMapping("/projectwork/date")
//	public List<Map<String, Object>> getAllVoucherBetweenDates(
//			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//		return repo.getAllpromotionsBetweenDates(startDate, endDate);
//	}

	@PostMapping("/projectwork/date")
	public List<ProjectWorkResponse> getAllVoucherBetweenDates(
			@RequestBody Map<String, Object> requestBody) {
		LocalDate startDate = LocalDate.parse(requestBody.get("startDate").toString(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(requestBody.get("endDate").toString(), DateTimeFormatter.ISO_DATE);
		List<Map<String, Object>> queryResult = repo.getAllpromotionsBetweenDates(startDate, endDate);
		List<ProjectWorkResponse> responses = new ArrayList<>();

		for (Map<String, Object> row : queryResult) {
			ProjectWorkResponse response = new ProjectWorkResponse();

			// Set project work fields
			response.setProjectWorkId(((BigInteger) row.get("project_work_id")).longValue());
			response.setProjectId(((BigInteger) row.get("project_id")).longValue());
			response.setProjectTitle((String) row.get("project_title"));
			response.setProjectTitle((String) row.get("description"));
			response.setDate(row.get("date").toString());
			response.setDuration((String) row.get("duration"));
			response.setStatus((Boolean) row.get("status"));

			// Set employeeId, employeeName, designationName, and designationId using helper
			// methods
			response.setEmployeeId(parseIds((String) row.get("employee_id")));
			response.setFirstName(parseNames((String) row.get("first_name")));
			response.setLastName(parseNames((String) row.get("last_name")));
			response.setDesignationName(parseNames((String) row.get("designation_name")));
			response.setDesignationId(parseIds((String) row.get("designation_id")));
			response.setDepartmentId(parseIds((String) row.get("department_id")));
			response.setDepartmentName(parseNames((String) row.get("department_name")));

			responses.add(response);
		}

		return responses;
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

	/////////////// 30//////////////////

	@PostMapping("/projectwork/name")
	public List<ProjectWorkResponse> getProjectWorkByProjectName(@RequestParam("project_name") String projectName) {
		List<Map<String, Object>> queryResult = repo.getAllProjectNamelll(projectName);
		List<ProjectWorkResponse> responses = new ArrayList<>();

		for (Map<String, Object> row : queryResult) {
			ProjectWorkResponse response = new ProjectWorkResponse();

			response.setProjectWorkId(((BigInteger) row.get("project_work_id")).longValue());
			response.setProjectId(((BigInteger) row.get("project_id")).longValue());
			response.setProjectTitle((String) row.get("project_title"));
			response.setProjectTitle((String) row.get("description"));
			response.setDate(row.get("date").toString());
			response.setDuration((String) row.get("duration"));
			response.setStatus((Boolean) row.get("status"));

			response.setEmployeeId(parseIds((String) row.get("employee_id")));
			response.setFirstName(parseNames((String) row.get("first_name")));
			response.setLastName(parseNames((String) row.get("last_name")));
			response.setDesignationName(parseNames((String) row.get("designation_name")));
			response.setDesignationId(parseIds((String) row.get("designation_id")));
			response.setDepartmentId(parseIds((String) row.get("department_id")));
			response.setDepartmentName(parseNames((String) row.get("department_name")));

			responses.add(response);
		}

		return responses;
	}

	
	

    @PostMapping("/projectwork6/save")
    public ResponseEntity<String> saveProjectWork6(@RequestBody ProjectWork projectWork) {
        try {
            if (projectWork == null) {
                return ResponseEntity.badRequest().body("Project work data is null.");
            }

            Long projectId = projectWork.getProjectId();
            Project project = service1.getprojectById(projectId);

            if (project != null) {
                project.setProjectStatus(false);
                service1.SaveorUpdate(project);
            } else {
                return ResponseEntity.badRequest().body("Project with ID " + projectId + " not found.");
            }

            Date currentDate = new Date(System.currentTimeMillis());
            Date workDate = projectWork.getDate();

            // Use SimpleDateFormat to format the dates
            SimpleDateFormat systemDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            if (isSameDayOrNextDay(currentDate, workDate, systemDateFormat)) {
                // Work date is the same day as current date or next day
                projectWork.setWork("Pending");
                projectWork.setPending(true);

                if (isSameDay1(currentDate, workDate)) {
                    projectWork.setWork("Started");
                    projectWork.setStarted(true);
                    projectWork.setOnProcess(false);
                    projectWork.setHold(false);
                    projectWork.setCompleted(false);
                }
            } else if (isBeforeDateOrSameDay(workDate, currentDate, systemDateFormat)) {
                // Work date is before or the same as the current date
                projectWork.setWork("OnProcess");
                projectWork.setOnProcess(true);
                projectWork.setStarted(false);
                projectWork.setPending(false);
                projectWork.setHold(false);
                projectWork.setCompleted(false);
            } else if (workDate.after(currentDate)) {
                // Work date is after the current date
                projectWork.setWork("Pending"); // You can change this to the desired behavior
                projectWork.setPending(true);
            } else {
                // Other cases (Hold, Completed, etc.)
                String workStatus = projectWork.getWork();
                if (workStatus != null) {
                    switch (workStatus) {
                        case "OnProcess":
                            projectWork.setOnProcess(true);
                            break;
                        case "Hold":
                            projectWork.setHold(true);
                            break;
                        case "Completed":
                            projectWork.setCompleted(true);
                            break;
                        default:
                            // Handle other work statuses or raise an error if needed
                    }
                } else {
                    // Handle the case where workStatus is null
                    projectWork.setWork("Pending");
                }
            }

            projectWork.setStatus(true);
            service.saveOrUpdate(projectWork);

            return ResponseEntity.ok("Project work saved successfully. ProjectWorkId: " + projectWork.getProjectWorkId());
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving project work: " + e.getMessage());
        }
    }

    private boolean isSameDayOrNextDay(Date date1, Date date2, SimpleDateFormat dateFormat) {
        String date1String = dateFormat.format(date1);
        String date2String = dateFormat.format(date2);
        LocalDate localDate1 = LocalDate.parse(date1String, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate localDate2 = LocalDate.parse(date2String, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return localDate1.isEqual(localDate2) || localDate1.plusDays(1).isEqual(localDate2);
    }

    private boolean isBeforeDateOrSameDay(Date date1, Date date2, SimpleDateFormat dateFormat) {
        String date1String = dateFormat.format(date1);
        String date2String = dateFormat.format(date2);
        LocalDate localDate1 = LocalDate.parse(date1String, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate localDate2 = LocalDate.parse(date2String, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return !localDate2.isBefore(localDate1);
    }

    private boolean isSameDay1(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateString1 = dateFormat.format(date1);
        String dateString2 = dateFormat.format(date2);
        return dateString1.equals(dateString2);
    }
    
    

    @PostMapping("/projectwork7/save")
    public ResponseEntity<String> saveProjectWork3(@RequestBody ProjectWork projectWork) {
        try {
            if (projectWork == null) {
                return ResponseEntity.badRequest().body("Project work data is null.");
            }

            Long projectId = projectWork.getProjectId();
            Project project = service1.getprojectById(projectId);

            if (project != null) {
                project.setProjectStatus(false);
                service1.SaveorUpdate(project);
            } else {
                return ResponseEntity.badRequest().body("Project with ID " + projectId + " not found.");
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate workDate = projectWork.getDate().toLocalDate(); // Convert java.sql.Date to LocalDate

            // Check if workDate is the same as the current date or the next day
            if (isSameDayOrNextDay(currentDate, workDate)) {
                projectWork.setWork("Pending");
                projectWork.setPending(true);

                if (isSameDay(currentDate, workDate)) {
                    projectWork.setWork("Started");
                    projectWork.setStarted(true);
                    projectWork.setOnProcess(false);
                    projectWork.setHold(false);
                    projectWork.setCompleted(false);
                }
            } else if (isBeforeDateOrSameDay(currentDate, workDate)) {
                projectWork.setWork("OnProcess");
                projectWork.setOnProcess(true);
                projectWork.setStarted(false);
                projectWork.setPending(false);
                projectWork.setHold(false);
                projectWork.setCompleted(false);
            } else if (workDate.isAfter(currentDate)) {
                projectWork.setWork("Pending");
                projectWork.setPending(true);
            } else {
                String workStatus = projectWork.getWork();
                if (workStatus != null) {
                    switch (workStatus) {
                        case "OnProcess":
                            projectWork.setOnProcess(true);
                            break;
                        case "Hold":
                            projectWork.setHold(true);
                            break;
                        case "Completed":
                            projectWork.setCompleted(true);
                            break;
                        default:
                            // Handle other work statuses or raise an error if needed
                    }
                } else {
                    projectWork.setWork("Pending");
                }
            }

            projectWork.setStatus(true);
            service.saveOrUpdate(projectWork);

            return ResponseEntity.ok("Project work saved successfully. ProjectWorkId: " + projectWork.getProjectWorkId());
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving project work: " + e.getMessage());
        }
    }

    private boolean isSameDayOrNextDay(LocalDate date1, LocalDate date2) {
        return date1.equals(date2) || date1.plusDays(1).equals(date2);
    }

    private boolean isBeforeDateOrSameDay(LocalDate date1, LocalDate date2) {
        return !date2.isBefore(date1);
    }

    private boolean isSameDay(LocalDate date1, LocalDate date2) {
        return date1.equals(date2);
    }
}