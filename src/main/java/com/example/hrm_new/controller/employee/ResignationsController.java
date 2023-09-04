package com.example.hrm_new.controller.employee;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.hrm_new.entity.employee.Resignations;
import com.example.hrm_new.repository.employee.ResignationsRepository;
import com.example.hrm_new.service.employee.ResignationsService;



@CrossOrigin
@RestController
public class ResignationsController {
	@Autowired
    private  ResignationsService service;

	@Autowired
    private  ResignationsRepository repo;


	@GetMapping("/resignations")
	public ResponseEntity<?> getResignationss() {
		try {
			List<Resignations> Resignationss = service.listAll();
			return ResponseEntity.ok(Resignationss);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Resignationss: " + e.getMessage());
		}
	}


	
	@PostMapping("/resignations/save")
	public ResponseEntity<String> saveResignations(@RequestBody Resignations Resignations) {
		try {
			Date noticeDate = Resignations .getNoticeDate();
			Date toDate = Resignations.getToDate();
			int totalDuration=calculateDuration(noticeDate,toDate);
			Resignations.setDurations(totalDuration);
			
			Resignations.setStatus(true);
			service.saveOrUpdate(Resignations);
			return ResponseEntity.ok("Resignations saved with id: " + Resignations.getResignationsId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving Resignations: " + e.getMessage());
		}
	}
	
	  private int calculateDuration(Date date1, Date date2) {
	        long diffInMillis = Math.abs(date2.getTime() - date1.getTime());
	        int daysDifference = (int) (diffInMillis / (24 * 60 * 60 * 1000));
	        return daysDifference;
	    }

	  @PutMapping("/resignations/or/{id}")
	  public ResponseEntity<?> toggleResignationStatus(@PathVariable(name = "id") long id) {
	      try {	        
	          Resignations resignation = service.getById(id);
	          if (resignation != null) {	         
	              boolean currentStatus = resignation.isStatus();
	              resignation.setStatus(!currentStatus);	            
	              service.saveOrUpdate(resignation);            
	              return ResponseEntity.ok(resignation.isStatus());
	          } else {	            
	              return ResponseEntity.ok(false);
	          }
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                  .body(false);
	      }
	  }

	

//	@PutMapping("/resignations/edit/{id}")
//	public ResponseEntity<Resignations> updateResignations(@PathVariable("id") long id, @RequestBody Resignations Resignations) {
//		try {
//			Resignations existingResignations = service.getById(id);
//			if (existingResignations == null) {
//				return ResponseEntity.notFound().build();
//			}
//			existingResignations.setResignationsDate(Resignations.getResignationsDate());
//			existingResignations.setToDate(Resignations.getToDate());
//			existingResignations.setNoticeDate(Resignations.getNoticeDate());
//			existingResignations.setReason(Resignations.getReason());
//			existingResignations.setDurations(Resignations.getDurations());
//			existingResignations.setEmployeeId(Resignations.getEmployeeId());
//	
//			
//			
//			
//
//			service.saveOrUpdate(existingResignations);
//			return ResponseEntity.ok(existingResignations);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	@PutMapping("/resignations/edit/{id}")
	public ResponseEntity<Resignations> updateResignations(@PathVariable("id") long id, @RequestBody Resignations resignations) {
	    try {
	        Resignations existingResignations = service.getById(id);
	        if (existingResignations == null) {
	            return ResponseEntity.notFound().build();
	        }

	        // Update relevant fields from the request body
	        existingResignations.setResignationsDate(resignations.getResignationsDate());
	        existingResignations.setToDate(resignations.getToDate());
	        existingResignations.setNoticeDate(resignations.getNoticeDate());
	        existingResignations.setReason(resignations.getReason());
	        existingResignations.setEmployeeId(resignations.getEmployeeId());

	        // Calculate and set the duration
	        int totalDuration = calculateDuration(resignations.getNoticeDate(), resignations.getToDate());
	        existingResignations.setDurations(totalDuration);

	        service.saveOrUpdate(existingResignations);
	        return ResponseEntity.ok(existingResignations);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	@DeleteMapping("/resignations/delete/{id}")
	public ResponseEntity<String> deleteResignations(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Resignations deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Resignations: " + e.getMessage());
		}
	}
	@GetMapping("/resignations/view")
	public List<Map<String,Object>>AllReg(){
		return service.ALLOver();
	}
	
	@GetMapping("/resignations/{employee_id}")
	private List<Map<String, Object>> idbasedAnnouncement(@PathVariable("employee_id") Long employee_id) {
	    List<Map<String, Object>> announcementlist = new ArrayList<>();
	    List<Map<String, Object>> list = repo.AllTimeGoat(employee_id);
	    Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
	            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

	    for (Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
	        Map<String, Object> announcementMap = new HashMap<>();
	        announcementMap.put("employee_id", totalList.getKey());
	        announcementMap.put("resignations_date", totalList.getValue().get(0).get("resignations_date"));
	        announcementMap.put("resignations Details", totalList.getValue());
	        announcementlist.add(announcementMap);
	    }
	    return announcementlist;
	}
	
	///////////////18////////////////////
	
	@PostMapping("/resignations/date")
	public List<Map<String, Object>> getAllVoucherBetweenDates( @RequestBody Map<String, Object> requestBody) {
	    LocalDate startDate = LocalDate.parse(requestBody.get("startDate").toString(), DateTimeFormatter.ISO_DATE);
	    LocalDate endDate = LocalDate.parse(requestBody.get("endDate").toString(), DateTimeFormatter.ISO_DATE);
		return repo.getAllReceiptBetweenDate(startDate, endDate);
	}
	
	/////////////// 19 /////////////////////
	
	@GetMapping("/resignations/durationcount")
	public List<Map<String, Object>> getAllDurationSDate(){
		return repo.getAllDurationDate();
	}
	
	
}

