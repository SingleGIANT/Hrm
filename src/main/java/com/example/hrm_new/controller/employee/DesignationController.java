package com.example.hrm_new.controller.employee;

import java.util.Optional;

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

import com.example.hrm_new.entity.employee.Designation;
import com.example.hrm_new.service.employee.DesignationService;



@RestController
@CrossOrigin
public class DesignationController {
	
	@Autowired
private DesignationService designationservice;
	

	@GetMapping("/designation")
	public ResponseEntity<?> getDetails() {

		try {

			Iterable<Designation> designationDetails = designationservice.listAll();

			return new ResponseEntity<>(designationDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/designation/save")
	public ResponseEntity<?> saveBank(@RequestBody Designation designation) {

		try {

			designationservice.SaveorUpdate(designation);

			return ResponseEntity.status(HttpStatus.CREATED).body("designation details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving designation details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}



	@RequestMapping("/designation/{designationId}")
	private Optional<Designation> getDesignation(@PathVariable(name = "designationId") long designationId) {

		return designationservice.getDesignationById(designationId);

	}
	
	
	@PutMapping("/designation/edit/{designationId}")
	public ResponseEntity<Designation> updateDesignation(@PathVariable("designationId") Long designationId, @RequestBody Designation designationDetails) {

		try {

			Designation existingdesignation = designationservice.findById(designationId);

			if (existingdesignation == null) {

				return ResponseEntity.notFound().build();

			}



			existingdesignation.setDesignationName(designationDetails.getDesignationName());



			designationservice.save(existingdesignation);

			return ResponseEntity.ok(existingdesignation);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	
	@DeleteMapping("/designation/delete/{designationtId}")
	public ResponseEntity<String> deleteddesignationName(@PathVariable("designationtId") Long designationtId) {

		designationservice.deleteDesignationtById(designationtId);

		return ResponseEntity.ok("designation deleted successfully");

	}
	

}
