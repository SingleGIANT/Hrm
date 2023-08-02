package com.example.hrm_new.controller.customer;

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

import com.example.hrm_new.entity.customer.FormType;
import com.example.hrm_new.service.customer.FormTypeService;



@CrossOrigin
@RestController
public class FormTypeController {
	@Autowired
	private FormTypeService service;

	@GetMapping("/formrypes")
	public ResponseEntity<?> getFormTypes() {
	
		try {
			
			List<FormType> FormTypes = service.listAll();
			return ResponseEntity.ok(FormTypes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving FormTypes: " + e.getMessage());
		}
	}

	@PostMapping("/formrypes/save")
    public ResponseEntity<String> saveFormType(@RequestBody FormType FormType) {
        try {          
     
            service.saveOrUpdate(FormType);
            

            return ResponseEntity.ok("FormType saved with id: " + FormType.getFormTypeId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving FormType: " + e.getMessage());
        }
    }

	@RequestMapping("/formrypes/{id}")
	public ResponseEntity<?> getFormTypeById(@PathVariable(name = "id") long id) {
		try {
			FormType FormType = service.getById(id);
			if (FormType != null) {
				return ResponseEntity.ok(FormType);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving FormType: " + e.getMessage());
		}
	}

	@PutMapping("/formrypes/edit/{id}")
	public ResponseEntity<FormType> updateFormType(@PathVariable("id") long id, @RequestBody FormType FormType) {
		try {
			FormType existingFormType = service.getById(id);
			if (existingFormType == null) {
				return ResponseEntity.notFound().build();
			}
			
			existingFormType.setFormTypeName(FormType.getFormTypeName());
			


			service.saveOrUpdate(existingFormType);
			return ResponseEntity.ok(existingFormType);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/formrypes/delete/{id}")
	public ResponseEntity<String> deleteFormType(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("FormType deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting FormType: " + e.getMessage());
		}
	}
}
