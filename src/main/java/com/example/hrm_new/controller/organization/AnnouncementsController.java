package com.example.hrm_new.controller.organization;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
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
import com.example.hrm_new.entity.organization.Announcements;
import com.example.hrm_new.repository.organization.AnnouncementsRepository;
import com.example.hrm_new.service.organization.AnnouncementsService;


@RestController
@CrossOrigin
public class AnnouncementsController {

	@Autowired
	private AnnouncementsService announcementtService;

	@Autowired
	private AnnouncementsRepository repo;

	@GetMapping("/announcement")
	public ResponseEntity<?> getDetails() {
		try {
			Iterable<Announcements> announcementDetails = announcementtService.listAll();
			return new ResponseEntity<>(announcementDetails, HttpStatus.OK);
		} catch (Exception e) {
			String errorMessage = "An error occurred while retrieving l details.";
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/announcement/save")
	public ResponseEntity<?> saveBank(@RequestBody Announcements announcement) {

		try {
			announcement.setStatus(true);
			announcementtService.SaveorUpdate(announcement);
			return ResponseEntity.status(HttpStatus.CREATED).body("announcement details saved successfully.");
		} catch (Exception e) {
			String errorMessage = "An error occurred while saving announcement details.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}
	
//	@PostMapping("/announcement/save")
//	public ResponseEntity<?> saveAnnouncement(@RequestBody Announcements announcement) {
//	    try {
//	        // Check if fromDate is later than toDate
//	        if (announcement.getFromDate() != null && announcement.getToDate() != null &&
//	            announcement.getFromDate().after(announcement.getToDate())) {
//	            String errorMessage = "FromDate cannot be later than ToDate.";
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
//	        }
//	        
//	        announcement.setStatus(true);
//	        announcementtService.SaveorUpdate(announcement);
//	        return ResponseEntity.status(HttpStatus.CREATED).body("Announcement details saved successfully.");
//	    } catch (Exception e) {
//	        String errorMessage = "An error occurred while saving announcement details.";
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//	    }
//	}


	@RequestMapping("/announcement/{announcementId}")
	private Optional<Announcements> getAnnouncement(@PathVariable(name = "announcementId") long announcementId) {
		return announcementtService.getAnnouncementsById(announcementId);

	}

	
	@PutMapping("/announcement/or/{announcement_id}")
    public ResponseEntity<Boolean> toggleCustomerStatus(@PathVariable(name = "announcement_id") long announcement_id) {
        try {
        	Announcements announcement = announcementtService.findById(announcement_id);
            if (announcement != null) {
                // Customer with the given id exists, toggle the status
                boolean currentStatus = announcement.isStatus();
                announcement.setStatus(!currentStatus);
                announcementtService.SaveorUpdate(announcement); // Save the updated customer
            } else {
                // Customer with the given id does not exist, return false
                return ResponseEntity.ok(false);
            }

            return ResponseEntity.ok(announcement.isStatus()); // Return the new status (true or false)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false); // Set response to false in case of an error
        }
    }

	@PutMapping("/announcement/editAnnouncement/{announcementId}")
	public ResponseEntity<Announcements> updateAnnouncement(@PathVariable("announcementId") Long announcementId,
			@RequestBody Announcements announcementDetails) {
		try {
			Announcements existingAnnouncement = announcementtService.findById(announcementId);
			if (existingAnnouncement == null) {
				return ResponseEntity.notFound().build();
			}
			existingAnnouncement.setTitle(announcementDetails.getTitle());
			existingAnnouncement.setFromDate(announcementDetails.getFromDate());
			existingAnnouncement.setToDate(announcementDetails.getToDate());
			existingAnnouncement.setCompanyId(announcementDetails.getCompanyId());
			existingAnnouncement.setInformedBy(announcementDetails.getInformedBy());
			
			announcementtService.save(existingAnnouncement);
			return ResponseEntity.ok(existingAnnouncement);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping("/announcement/announcementdelete/{announcementId}")
	public ResponseEntity<String> deleteTitle(@PathVariable("announcementId") Long announcementId) {
		announcementtService.deleteAnnouncementsIdById(announcementId);
		return ResponseEntity.ok("announcement deleted successfully");

	}

	@GetMapping("/announcementdetails/view")
	public List<Map<String, Object>> allDeptDetails() {
		return announcementtService.allAnnouncementsDetails();

	}

	@GetMapping("/announcementdetails1/{announcement_id}")
	private List<Map<String, Object>> idbasedAnnouncements(@PathVariable Long announcement_id) {
		List<Map<String, Object>> announcementlist = new ArrayList<>();
		List<Map<String, Object>> list = announcementtService.findAllByAnnouncementsId(announcement_id);
		Map<String, List<Map<String, Object>>> announcementGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("announcement_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : announcementGroupMap.entrySet()) {
			Map<String, Object> announcementMap = new HashMap<>();
			announcementMap.put("AnnouncementId", totalList.getKey());
			announcementMap.put("Title", totalList.getValue().get(0).get("title"));
			announcementMap.put("Announcement Details", totalList.getValue());
			announcementlist.add(announcementMap);

		}
		return announcementlist;
	}

	@GetMapping("/searchstatus")
	public List<Announcements> findStatus() {
		return repo.findByStatusTrue();

	}

	@PostMapping("/announcements/startdate")
	public List<Map<String, Object>> allAnnouncementsDetailsByFromDate(@RequestBody Map<String, Object> requestBody) {
		LocalDate startdate = LocalDate.parse(requestBody.get("startdate").toString(), DateTimeFormatter.ISO_DATE);
		return announcementtService.allAnnouncementsDetailsByFromDate(startdate);
	}

	@PostMapping("/announcements/enddate")
	public List<Map<String, Object>> allAnnouncementsDetailsByToDate(@RequestBody Map<String, Object> requestBody) {
		LocalDate enddate = LocalDate.parse(requestBody.get("enddate").toString(), DateTimeFormatter.ISO_DATE);
		return announcementtService.allAnnouncementsDetailsByToDate(enddate);
	}

	@PostMapping("/announcements1/correctdate")
	public List<Map<String, Object>> allAnnouncementsDetailsByFromDateAndToDate1(
			@RequestBody Map<String, Object> requestBody) {
		LocalDate startdate = LocalDate.parse(requestBody.get("startdate").toString(), DateTimeFormatter.ISO_DATE);
		LocalDate enddate = LocalDate.parse(requestBody.get("enddate").toString(), DateTimeFormatter.ISO_DATE);
		return announcementtService.allAnnouncementsDetailsByFromDateAndToDate(startdate, enddate);
	}
	
	@PostMapping("/announcements/correctdate")
	public List<Map<String, Object>> allAnnouncementsDetailsByFromDateAndToDate(
			@RequestBody Map<String, Object> requestBody) {
		LocalDate startdate = LocalDate.parse(requestBody.get("startdate").toString(), DateTimeFormatter.ISO_DATE);
		LocalDate enddate = LocalDate.parse(requestBody.get("enddate").toString(), DateTimeFormatter.ISO_DATE);
		return repo.allAnnouncementsDetailsByFromDateAndToDate1(startdate, enddate);
	}

}
