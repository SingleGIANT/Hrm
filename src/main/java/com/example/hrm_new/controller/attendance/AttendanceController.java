package com.example.hrm_new.controller.attendance;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.hrm_new.entity.attendance.Attendance;
import com.example.hrm_new.entity.attendance.AttendanceList;
import com.example.hrm_new.repository.attendance.AttendanceRepository;
import com.example.hrm_new.service.attendance.AttendanceListService;
import com.example.hrm_new.service.attendance.AttendanceService;


@RestController
@CrossOrigin
public class AttendanceController {

	
	@Autowired
	private AttendanceService service;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private AttendanceListService ser;

	@GetMapping("/attendance")
	public ResponseEntity<?> getUser() {
		try {
			Iterable<Attendance> members = service.listAll();
			return ResponseEntity.ok(members);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while retrieving members");

		}
	}

	@PostMapping(value = "/attendance/save")
	    public ResponseEntity<String> saveMember(@RequestBody Attendance attendance) {
	        try {
	            Date attendanceDate = attendance.getAttendanceDate();
	            List<AttendanceList> attendanceList = attendance.getAttendance();

	            SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");

	            for (AttendanceList attendanceLoop : attendanceList) {
	                if (attendanceLoop.isAttstatus()) {
	                    attendanceLoop.setPresent(true);
	                    attendanceLoop.setAbsent(false);
	                } else {
	                    attendanceLoop.setPresent(false);
	                    attendanceLoop.setAbsent(true);
	                }

	                if ("Full".equals(attendanceLoop.getSection())) {
	                    attendanceLoop.setFullDay(true);
	                    attendanceLoop.setHalfDay(false);
	                } else if ("Half".equals(attendanceLoop.getSection())) {
	                    attendanceLoop.setFullDay(false);
	                    attendanceLoop.setHalfDay(true);
	                }

	              
	                if (attendanceLoop.getIntime() != null && !attendanceLoop.getIntime().isEmpty()) {
	                    try {
	                        String formattedIntime = formatTime(attendanceLoop.getIntime(), timeFormatter);
	                        attendanceLoop.setIntime(formattedIntime);
	                    } catch (Exception e) {
	                        // Handle parsing/formatting exceptions here
	                    }
	                }

	                if (attendanceLoop.getOuttime() != null && !attendanceLoop.getOuttime().isEmpty()) {
	                    try {
	                        String formattedOuttime = formatTime(attendanceLoop.getOuttime(), timeFormatter);
	                        attendanceLoop.setOuttime(formattedOuttime);
	                    } catch (Exception e) {
	                        // Handle parsing/formatting exceptions here
	                    }
	                }
	            }

	            Optional<Attendance> existingAttendance = attendanceRepository.findByAttendanceDate(attendanceDate);
	            if (existingAttendance.isPresent()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body("Attendance for date " + attendanceDate + " already exists.");
	            } else {
	                attendanceRepository.save(attendance);
	                System.out.println("Saving attendance: " + attendance);
	            }

	            long attendanceId = attendance.getAttendanceId();
	            return ResponseEntity.ok("Attendance saved successfully. Attendance ID: " + attendanceId);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error saving attendance: " + e.getMessage());
	        }
	    }

	private String formatTime(String time, SimpleDateFormat formatter) throws Exception {
		SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm:ss a");
		java.util.Date date = inputFormat.parse(time);
		return formatter.format(date);
	}

	


	@PutMapping("/attendance1/edit/{id}")
	public ResponseEntity<?> updateAttendanceList(@PathVariable("id") Long id,
			@RequestBody AttendanceList updatedAttendanceList) {
		try {
			AttendanceList existingAttendanceList = ser.findById(id);
			if (existingAttendanceList == null) {
				return ResponseEntity.notFound().build();
			}

	
			existingAttendanceList.setEmployeeId(updatedAttendanceList.getEmployeeId());
			existingAttendanceList.setAttstatus(updatedAttendanceList.isAttstatus());
			existingAttendanceList.setSection(updatedAttendanceList.getSection());
			existingAttendanceList.setIntime(updatedAttendanceList.getIntime());
			existingAttendanceList.setOuttime(updatedAttendanceList.getOuttime());
			ser.save(existingAttendanceList);
			return ResponseEntity.ok(existingAttendanceList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
//	@PutMapping("/attendance/edit/{id}")
//	public ResponseEntity<?> updateAttendanceList1(@PathVariable("id") Long id,
//	        @RequestBody AttendanceList updatedAttendanceList) {
//
//	    try {
//	        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");
//	        AttendanceList existingAttendanceList = ser.findById(id);
//
//	        if (existingAttendanceList == null) {
//	            return ResponseEntity.notFound().build();
//	        }
//
//	        boolean isAttStatus = updatedAttendanceList.isAttstatus();
//
//	        existingAttendanceList.setPresent(isAttStatus);
//	        existingAttendanceList.setAbsent(!isAttStatus);
//
//	        existingAttendanceList.setFullDay("Full".equals(updatedAttendanceList.getSection()));
//	        existingAttendanceList.setHalfDay("Half".equals(updatedAttendanceList.getSection()));
//
//	        if (updatedAttendanceList.getIntime() != null && !updatedAttendanceList.getIntime().isEmpty()) {
//	            try {
//	                String formattedIntime = formatTime(updatedAttendanceList.getIntime(), timeFormatter);
//	                existingAttendanceList.setIntime(formattedIntime);
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	                return ResponseEntity.badRequest().body("Error formatting intime");
//	            }
//	        }
//
//	        if (updatedAttendanceList.getOuttime() != null && !updatedAttendanceList.getOuttime().isEmpty()) {
//	            try {
//	                String formattedOuttime = formatTime(updatedAttendanceList.getOuttime(), timeFormatter);
//	                existingAttendanceList.setOuttime(formattedOuttime);
//	            } catch (Exception e) {
//	                e.printStackTrace();
//	                return ResponseEntity.badRequest().body("Error formatting outtime");
//	            }
//	        }
//
//	        ser.save(existingAttendanceList);
//
//	        return ResponseEntity.ok("Attendance list updated successfully");
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating attendance list");
//	    }
//	}

	
	@PutMapping("/attendance/edit/{id}")
	public ResponseEntity<?> updateAttendanceList1(@PathVariable("id") Long id,
	        @RequestBody AttendanceList updatedAttendanceList) {

	    try {
	        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");
	        AttendanceList existingAttendanceList = ser.findById(id);

	        if (existingAttendanceList == null) {
	            return ResponseEntity.notFound().build();
	        }

	        existingAttendanceList.setEmployeeId(updatedAttendanceList.getEmployeeId());
	        existingAttendanceList.setAttstatus(updatedAttendanceList.isAttstatus());

	        existingAttendanceList.setPresent(updatedAttendanceList.isAttstatus());
	        existingAttendanceList.setAbsent(!updatedAttendanceList.isAttstatus());

	        existingAttendanceList.setSection(updatedAttendanceList.getSection()); // Update section directly

	        if ("Full".equals(updatedAttendanceList.getSection())) {
	            existingAttendanceList.setFullDay(true);
	            existingAttendanceList.setHalfDay(false);
	        } else if ("Half".equals(updatedAttendanceList.getSection())) {
	            existingAttendanceList.setFullDay(false);
	            existingAttendanceList.setHalfDay(true);
	        } else {
	            existingAttendanceList.setFullDay(false);
	            existingAttendanceList.setHalfDay(false);
	        }

	        if (updatedAttendanceList.getIntime() != null && !updatedAttendanceList.getIntime().isEmpty()) {
	            try {
	                String formattedIntime = formatTime(updatedAttendanceList.getIntime(), timeFormatter);
	                existingAttendanceList.setIntime(formattedIntime);
	            } catch (Exception e) {
	                e.printStackTrace();
	                return ResponseEntity.badRequest().body("Error formatting intime");
	            }
	        }

	        if (updatedAttendanceList.getOuttime() != null && !updatedAttendanceList.getOuttime().isEmpty()) {
	            try {
	                String formattedOuttime = formatTime(updatedAttendanceList.getOuttime(), timeFormatter);
	                existingAttendanceList.setOuttime(formattedOuttime);
	            } catch (Exception e) {
	                e.printStackTrace();
	                return ResponseEntity.badRequest().body("Error formatting outtime");
	            }
	        }

	        ser.save(existingAttendanceList);

	        return ResponseEntity.ok("Attendance list updated successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating attendance list");
	    }
	}

	@GetMapping("/attendance/employee")
	public List<Map<String, Object>> getAllMemberDetails() {
		return attendanceRepository.getAllMemberDetails();
	}

	@GetMapping("/attendance/date")
	public List<Map<String, Object>> getAllemployeeDetails() {
		return attendanceRepository.getAllemployeeDetails();}
		
		 @PostMapping("/attendance/date/list")
		    public List<Map<String, Object>> getAllVoucherBetweenDates2(
					@RequestBody Map<String, Object> requestBody) {
				LocalDate startDate = LocalDate.parse(requestBody.get("startDate").toString(), DateTimeFormatter.ISO_DATE);
				
		        return attendanceRepository.getAllpromotionsBetweenDates(startDate );
		    }
	
	
	@GetMapping("/attendance/absent")
	public List<Map<String, Object>> getAllemployeeDetails2() {
		return attendanceRepository.getAllemployeeDetails2();
	}
	
	@GetMapping("/attendance/absent/count")
	public List<Map<String, Object>> getAllemployeeDetails5() {
		return attendanceRepository.getAllemployeeDetails5();
	}
	@GetMapping("/attendance/currentdate")
	public List<Map<String, Object>> getAllMemberDetailsByMemberByDate() {
		return attendanceRepository.getAllMemberDetailsByMemberByDate();
	}

	@GetMapping("/attendance/{employeeid}")
	public List<Map<String, Object>> getAllMemberDetailsByMemberId(@PathVariable Long employeeid) {
		return attendanceRepository.getAllMemberDetailsByMemberId(employeeid);
	}

}
