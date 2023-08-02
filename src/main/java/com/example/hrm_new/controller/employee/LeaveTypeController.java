package com.example.hrm_new.controller.employee;

import java.util.List;

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

import com.example.hrm_new.entity.employee.LeaveType;
import com.example.hrm_new.repository.employee.LeaveTypeRepository;
import com.example.hrm_new.service.employee.LeaveTypeService;

@RestController
@CrossOrigin
public class LeaveTypeController {
	
	@Autowired
	private LeaveTypeService service;
	@Autowired
	private LeaveTypeRepository repo;
	
	@GetMapping("/LeaveType")
	public ResponseEntity<?> getLeaveTypes() {
		try {
			List<LeaveType> LeaveTypes = service.listAll();
			return ResponseEntity.ok(LeaveTypes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving LeaveTypes: " + e.getMessage());
		}
	}


	
	@PostMapping("/LeaveType/save")
	public ResponseEntity<String> saveLeaveType(@RequestBody LeaveType LeaveType) {
		try {

			service.saveOrUpdate(LeaveType);
			return ResponseEntity.ok("LeaveType saved with id: " + LeaveType.getLeaveTypeId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving LeaveType: " + e.getMessage());
		}
	}

	@RequestMapping("/LeaveType/{id}")
	public ResponseEntity<?> getLeaveTypeById(@PathVariable(name = "id") long id) {
		try {
			LeaveType LeaveType = service.getById(id);
			if (LeaveType != null) {
				return ResponseEntity.ok(LeaveType);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving LeaveType: " + e.getMessage());
		}
	}

	@PutMapping("/LeaveType/edit/{id}")
	public ResponseEntity<LeaveType> updateLeaveType(@PathVariable("id") long id, @RequestBody LeaveType LeaveType) {
		try {
			LeaveType existingLeaveType = service.getById(id);
			if (existingLeaveType == null) {
				return ResponseEntity.notFound().build();
			}
			existingLeaveType.setLeaveType(LeaveType.getLeaveType());
			
			service.saveOrUpdate(existingLeaveType);
			return ResponseEntity.ok(existingLeaveType);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/LeaveType/delete/{id}")
	public ResponseEntity<String> deleteLeaveType(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("LeaveType deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting LeaveType: " + e.getMessage());
		}
	}

}
