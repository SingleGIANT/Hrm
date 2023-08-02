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
import com.example.hrm_new.entity.employee.Promotions;
import com.example.hrm_new.repository.employee.PromotionsRepository;
import com.example.hrm_new.service.employee.EmployeeService;
import com.example.hrm_new.service.employee.PromotionsService;

@CrossOrigin
@RestController
public class PromotionsController {
	@Autowired
    private  PromotionsService service;
	@Autowired
	private EmployeeService employeeService;
    
	@Autowired
    private  PromotionsRepository repo;

	@GetMapping("/promotions")
	public ResponseEntity<?> getPromotionss() {
		try {
			List<Promotions> Promotionss = service.listAll();
			return ResponseEntity.ok(Promotionss);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Promotionss: " + e.getMessage());
		}
	}


	
//	@PostMapping("/promotions/save")
//	public ResponseEntity<String> savePromotions(@RequestBody Promotions Promotions) {
//		try {
//			Promotions.setStatus(true);
//			service.saveOrUpdate(Promotions);
//			return ResponseEntity.ok("Promotions saved with id: " + Promotions.getPromotionsId());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error saving Promotions: " + e.getMessage());
//		}
//	}
	
//	@PostMapping("/promotions/save")
//	public ResponseEntity<String> savePromotions(@RequestBody Promotions promotions) {
//	    try {
//	        Long roleId = promotions.getRoleId();
//	        Employee employee = employeeService.getRoleById(roleId);
//	        if (employee != null) {
//	            promotions.setRoleId(employee.getRoleId()); 	           	     
//	            service.saveOrUpdate(promotions);	            
//	            promotions.setStatus(true); 
//	            service.saveOrUpdate(promotions);	            
//	            return ResponseEntity.ok("Promotions saved with id: " + promotions.getPromotionsId());
//	        } else {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with roleId " + roleId + " not found.");
//	        }
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body("Error saving Promotions: " + e.getMessage());
//	    }
//	}

	
	@PostMapping("/promotions/save")
	public ResponseEntity<String> savePromotions(@RequestBody Promotions promotions) {
	    try {
	    	promotions.setStatus(true);
	    	
	        Long employeeId = promotions.getEmployeeId();
	        Employee employee = employeeService.getEmployeeById(employeeId);
	        if (employee != null) {
	        	employee.setRoleId(promotions.getRoleId());

	        	service.saveOrUpdate(promotions);
	            employee.setRoleId(employee.getRoleId());

	            employeeService.saveOrUpdate(employee);

	            return ResponseEntity.ok("Promotions saved with id: " + promotions.getPromotionsId());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with roleId " + employeeId + " not found.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error saving Promotions: " + e.getMessage());
	    }
	}


	@PutMapping("/promotions/or/{id}")
	public ResponseEntity<?> getPromotionsById(@PathVariable(name = "id") long id) {
		try {
			Promotions Promotions = service.getById(id);
			if (Promotions != null) {
				boolean currentStatus = Promotions.isStatus();
				Promotions.setStatus(!currentStatus);
                service.saveOrUpdate(Promotions); // Save the updated complaints
            } else {
                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
            }

            return ResponseEntity.ok(Promotions.isStatus()); // Return the new status (true or false)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false); // Set response to false in case of an error
        }
    }

	@PutMapping("/promotions/edit/{id}")
	public ResponseEntity<Promotions> updatePromotions(@PathVariable("id") long id, @RequestBody Promotions Promotions) {
		try {
			Promotions existingPromotions = service.getById(id);
			if (existingPromotions == null) {
				return ResponseEntity.notFound().build();
			}
			existingPromotions.setDate(Promotions.getDate());		
			existingPromotions.setDescription(Promotions.getDescription());
			existingPromotions.setEmployeeId(Promotions.getEmployeeId());
			existingPromotions.setRoleId(Promotions.getRoleId());
			existingPromotions.setPromotionsBy(Promotions.getPromotionsBy());
			
			existingPromotions.setStatus(Promotions.isStatus());
			
			
			

			service.saveOrUpdate(existingPromotions);
			return ResponseEntity.ok(existingPromotions);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/promotions/delete/{id}")
	public ResponseEntity<String> deletePromotions(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Promotions deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Promotions: " + e.getMessage());
		}
	}
	
	@GetMapping("/promotions/view")
	public List<Map<String,Object>>INeedList(){
		return service.AllFine();
	}
	
	@GetMapping("/promotions/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.Allpromotions(employee_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("employee_id", totalList.getKey());
	        announcementMap.put("promotions_by", totalList.getValue().get(0).get("promotions_by"));
	        announcementMap.put("promotions Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}
	
	///////////////////21/////////////////////////	
	 @PostMapping("/promotions/date")
	    public List<Map<String, Object>> getAllVoucherBetweenDates(
	            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	    ) {
	        return repo.getAllpromotionsBetweenDates(startDate, endDate);
	    }
}


