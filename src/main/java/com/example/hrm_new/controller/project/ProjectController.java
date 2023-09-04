package com.example.hrm_new.controller.project;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.employee.ProjectWork;
import com.example.hrm_new.entity.employee.ProjectWorkResponse;
import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.entity.project.ProjectResponse;
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
			List<Project> projectDetails = projectservice.listAll();
			return new ResponseEntity<>(projectDetails, HttpStatus.OK);
		} catch (Exception e) {
			String errorMessage = "An error occurred while retrieving l details.";
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/project/save")
    public ResponseEntity<String> saveProject(@RequestBody Project project) {
        try {
            
            Date fromDate = project.getFromDate();
            Date toDate = project.getToDate();
            int totalDuration = calculateDuration(fromDate, toDate);
            project.setTotalDuration(totalDuration);
            project.setStatus(true);
            project.setProjectStatus(true);
            
            
            projectservice.SaveorUpdate(project);

            return ResponseEntity.ok("Project saved with id: " + project.getProjectId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving project: " + e.getMessage());
        }
    }
	
	
	

    private int calculateDuration(Date date1, Date date2) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return (int) (diffInMillis / (24 * 60 * 60 * 1000));
    }

    
    @PutMapping("/project/or/{projectId}")
    public ResponseEntity<Boolean> toggleCustomerStatus(@PathVariable(name = "projectId") long projectId) {
        try {
        	Project project = projectservice.findById(projectId);
            if (project != null) {
                boolean currentStatus = project.isStatus();
                project.setStatus(!currentStatus);
                projectservice.SaveorUpdate(project);
            } else {
                return ResponseEntity.ok(false);
            }

            return ResponseEntity.ok(project.isStatus()); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false); 
        }
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
	        existingProject.setCustomerId(projectDetails.getCustomerId());
	        existingProject.setTotalDuration(projectDetails.getTotalDuration());
	        existingProject.setFromDate(projectDetails.getFromDate());
	        existingProject.setTotalProjectAmount(projectDetails.getTotalProjectAmount());
	        existingProject.setToDate(projectDetails.getToDate());
	        existingProject.setStatus(projectDetails.isStatus());
	        existingProject.setDesignationId(projectDetails.getDesignationId());
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

	




    @GetMapping("/project/view")
    public List<ProjectResponse> getAllProjects1() {
        List<Object[]> queryResult = repo.getAllProjects();
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for (Object[] row : queryResult) {
            ProjectResponse response = new ProjectResponse();

            String projectIdStr = row[0] != null ? row[0].toString() : null;
            BigInteger projectIdBigInt = parseBigInteger(projectIdStr);
            response.setProjectId(projectIdBigInt != null ? projectIdBigInt.longValue() : null);

            String projectTitle = (String) row[1];
            response.setProjectTitle(projectTitle);

            String customerIdStr = row[2] != null ? row[2].toString() : null;
            BigInteger customerIdBigInt = parseBigInteger(customerIdStr);
            response.setCustomerId(customerIdBigInt != null ? customerIdBigInt.longValue() : null);

            BigInteger contactBigInt = (BigInteger) row[3];
            response.setContact(contactBigInt != null ? contactBigInt.longValue() : null);


            String designationNames = (String) row[10];
            String designationIds = (String) row[11];

            if (designationNames != null && designationIds != null) {
                List<String> designationNameList = Arrays.asList(designationNames.split(","));
                List<Long> designationIdList = Arrays.stream(designationIds.split(","))
                                                     .map(this::parseLongSafely)
                                                     .collect(Collectors.toList());

                response.setDesignationName(designationNameList);
                response.setDesignationId(designationIdList);
            }

            response.setLocation((String) row[4]);
            response.setTotalDuration((Integer) row[5]);

            Date fromDate = (Date) row[6];
            String fromDateStr = fromDate != null ? fromDate.toString() : null;
            response.setFromDate(fromDateStr);

            Date toDate = (Date) row[7];
            String toDateStr = toDate != null ? toDate.toString() : null;
            response.setToDate(toDateStr);

            BigInteger totalProjectAmountBigInt = (BigInteger) row[8];
            Double totalProjectAmount = totalProjectAmountBigInt != null ? totalProjectAmountBigInt.doubleValue() : null;
            response.setTotalProjectAmount(totalProjectAmount);

            Boolean status = (Boolean) row[9];
            response.setStatus(status);

            String customerName = (String) row[12];
            response.setCustomerName(customerName);

            String city = (String) row[13];
            response.setCity(city);

            BigInteger phoneNo1BigInt = (BigInteger) row[14];
            response.setPhoneNo1(phoneNo1BigInt != null ? phoneNo1BigInt.longValue() : null);

            BigInteger phoneNo2BigInt = (BigInteger) row[15];
            response.setPhoneNo2(phoneNo2BigInt != null ? phoneNo2BigInt.longValue() : null);

            projectResponses.add(response);
        }

        return projectResponses;
    }
    
    @GetMapping("/project/show")
    public List<ProjectResponse> getAllProjects2() {
        List<Map<String, Object>> queryResult = repo.getAllProjects2();
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for (Map<String, Object> row : queryResult) {
            ProjectResponse response = new ProjectResponse();

            response.setProjectId(((BigInteger) row.get("projectId")).longValue());
            response.setProjectTitle((String) row.get("projectTitle"));
            response.setCustomerId(((BigInteger) row.get("customerId")).longValue());
            response.setContact(((BigInteger) row.get("contact")).longValue());
            response.setLocation((String) row.get("location"));
            response.setTotalDuration(((Integer) row.get("totalDuration")).intValue());
            java.sql.Date fromDateSql = (java.sql.Date) row.get("fromDate");
            response.setFromDate(fromDateSql != null ? fromDateSql.toString() : null);

            java.sql.Date toDateSql = (java.sql.Date) row.get("toDate");
            response.setToDate(toDateSql != null ? toDateSql.toString() : null);

            response.setTotalProjectAmount(((BigInteger) row.get("totalProjectAmount")).doubleValue());
            response.setStatus((Boolean) row.get("status"));
            response.setProjectStatus((Boolean) row.get("project_status"));
            response.setDesignationName(parseNames((String) row.get("designationName")));
            response.setDesignationId(parseIds((String) row.get("designationId")));
            response.setCustomerName((String) row.get("customerName"));
            response.setCity((String) row.get("city"));
            response.setPhoneNo1(((BigInteger) row.get("phoneNo1")).longValue());
            response.setPhoneNo2(((BigInteger) row.get("phoneNo2")).longValue());

            projectResponses.add(response);
        }

        return projectResponses;
    }

//    private List<String> parseNames(String namesString) {
//        return Arrays.asList(namesString.split(","));
//    }
    
    private List<String> parseNames(String namesString) {
        if (namesString != null) {
            return Arrays.asList(namesString.split(","));
        } else {
            // Handle the case when namesString is null, e.g., return an empty list or log a message
            return Collections.emptyList(); // Return an empty list as a default
        }
    }

//    private List<Long> parseIds(String idsString) {
//        return Arrays.stream(idsString.split(","))
//                     .map(Long::parseLong)
//                     .collect(Collectors.toList());
//    }

    private List<Long> parseIds(String idsString) {
        if (idsString != null) {
            return Arrays.stream(idsString.split(","))
                         .map(Long::parseLong)
                         .collect(Collectors.toList());
        } else {
            // Handle the case when idsString is null, e.g., return an empty list or log a message
            return Collections.emptyList(); // Return an empty list as a default
        }
    }

    private BigInteger parseBigInteger(String str) {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException e) {
           
            return null;
        }
    }

    private Long parseLongSafely(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
          
            return null;
        }
    }

 @GetMapping("/hhhhhhhhhhh")
    public List<Map<String, Object>>dddddddddddddd(){
    	return repo.getAllProjectss1();
    }

     



 @PostMapping("/findprojectbydate")
 public List<ProjectResponse> findByDateBetween(@RequestBody Map<String, Object> requestBody) {
     LocalDate fromdate = LocalDate.parse(requestBody.get("fromdate").toString(), DateTimeFormatter.ISO_DATE);
     LocalDate todate = LocalDate.parse(requestBody.get("todate").toString(), DateTimeFormatter.ISO_DATE);
     List<Map<String, Object>> queryResult = repo.getAllpromotionsBetweenDates(fromdate, todate);

     List<ProjectResponse> projectResponses = new ArrayList<>();

     for (Map<String, Object> row : queryResult) {
         ProjectResponse response = new ProjectResponse();

         if (row.get("project_id") != null) {
             response.setProjectId(((BigInteger) row.get("project_id")).longValue());
         }
         response.setProjectTitle((String) row.get("project_title"));
         if (row.get("customer_id") != null) {
             response.setCustomerId(((BigInteger) row.get("customer_id")).longValue());
         } 
         
//         response.setCustomerId(((BigInteger) row.get("customerId")).longValue());
         response.setContact(((BigInteger) row.get("contact")).longValue());
         response.setLocation((String) row.get("location"));
//         response.setTotalDuration(((Integer) row.get("totalDuration")).intValue());
         Object totalDurationObj = row.get("total_duration");
         if (totalDurationObj != null) {
             response.setTotalDuration(((Integer) totalDurationObj).intValue());
         } else {
             // Handle the case when totalDuration is null, e.g., set a default value or log a message
             response.setTotalDuration(0); // Set a default value of 0, for example
         }
         java.sql.Date fromDateSql = (java.sql.Date) row.get("from_date");
         response.setFromDate(fromDateSql != null ? fromDateSql.toString() : null);

         java.sql.Date toDateSql = (java.sql.Date) row.get("to_date");
         response.setToDate(toDateSql != null ? toDateSql.toString() : null);

//         response.setTotalProjectAmount(((BigInteger) row.get("totalProjectAmount")).doubleValue());
         Object totalProjectAmountObj = row.get("total_project_amount");
         if (totalProjectAmountObj != null) {
             if (totalProjectAmountObj instanceof BigInteger) {
                 response.setTotalProjectAmount(((BigInteger) totalProjectAmountObj).doubleValue());
             } else if (totalProjectAmountObj instanceof Double) {
                 response.setTotalProjectAmount((Double) totalProjectAmountObj);
             }
             // Handle other possible types if necessary
         } else {
             // Handle the case when totalProjectAmount is null, e.g., set a default value or log a message
             response.setTotalProjectAmount(0.0); // Set a default value of 0.0, for example
         }

         response.setStatus((Boolean) row.get("status"));
         response.setProjectStatus((Boolean) row.get("project_status"));
         response.setDesignationName(parseNames((String) row.get("designation_name")));
         response.setDesignationId(parseIds((String) row.get("designation_id")));
         response.setCustomerName((String) row.get("customer_name"));
         response.setCity((String) row.get("city"));
//         response.setPhoneNo1(((BigInteger) row.get("phoneNo1")).longValue());
         Object phoneNo1Obj = row.get("phone_no1");
         if (phoneNo1Obj != null) {
             if (phoneNo1Obj instanceof BigInteger) {
                 response.setPhoneNo1(((BigInteger) phoneNo1Obj).longValue());
             } else if (phoneNo1Obj instanceof Long) {
                 response.setPhoneNo1((Long) phoneNo1Obj);
             }
             // Handle other possible types if necessary
         } else {
             // Handle the case when phoneNo1 is null, e.g., set a default value or log a message
             response.setPhoneNo1(0L); // Set a default value of 0L, for example
         }
         response.setPhoneNo2(((BigInteger) row.get("phone_no2")).longValue());

         projectResponses.add(response);
     }

     return projectResponses;
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
	public ResponseEntity<Map<BigInteger, Long>> totalcountOfProjectsByClient() {
	    List<Map<String, Object>> clientProjectCounts = repo.getClientProjectCounts();

	    Map<BigInteger, Long> result = new HashMap<>();
	    for (Map<String, Object> row : clientProjectCounts) {
	        BigInteger clientId = (BigInteger) row.get("customer_id"); // Assuming this is the correct key
	        BigInteger projectCountBigInteger = (BigInteger) row.get("count(p.project_id)");
	        
	        if (clientId != null) {
	            Long projectCount = projectCountBigInteger != null ? projectCountBigInteger.longValue() : null;
	            result.put(clientId, projectCount);
	        }
	    }
	    
	    return ResponseEntity.ok(result);
	}



		@GetMapping("/durationofprojects")
		public List<Map<String, Object>> totalDurationOfProjects() {
			return repo.findBytotalDuration();

		}
	 
	
}
