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

import com.example.hrm_new.entity.employee.RelationType;
import com.example.hrm_new.repository.employee.RelationTypeRepository;
import com.example.hrm_new.service.employee.RelationTypeService;

@RestController
@CrossOrigin
public class RelationTypeController {
	
	@Autowired
	private RelationTypeService service;
	@Autowired
	private RelationTypeRepository repo;
	
	@GetMapping("/relationtype")
	public ResponseEntity<?> getRelationTypes() {
		try {
			List<RelationType> RelationTypes = service.listAll();
			return ResponseEntity.ok(RelationTypes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving RelationTypes: " + e.getMessage());
		}
	}


	
	@PostMapping("/relationtype/save")
	public ResponseEntity<String> saveRelationType(@RequestBody RelationType RelationType) {
		try {

			service.saveOrUpdate(RelationType);
			return ResponseEntity.ok("RelationType saved with id: " + RelationType.getRelationTypeId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving RelationType: " + e.getMessage());
		}
	}

	@RequestMapping("/relationtype/{id}")
	public ResponseEntity<?> getRelationTypeById(@PathVariable(name = "id") long id) {
		try {
			RelationType RelationType = service.getById(id);
			if (RelationType != null) {
				return ResponseEntity.ok(RelationType);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving RelationType: " + e.getMessage());
		}
	}

	@PutMapping("/relationtype/edit/{id}")
	public ResponseEntity<RelationType> updateRelationType(@PathVariable("id") long id, @RequestBody RelationType RelationType) {
		try {
			RelationType existingRelationType = service.getById(id);
			if (existingRelationType == null) {
				return ResponseEntity.notFound().build();
			}
			
			existingRelationType.setRelationType(RelationType.getRelationType());
		
			
			
			

			service.saveOrUpdate(existingRelationType);
			return ResponseEntity.ok(existingRelationType);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/relationtype/delete/{id}")
	public ResponseEntity<String> deleteRelationType(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("RelationType deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting RelationType: " + e.getMessage());
		}
	}
}
