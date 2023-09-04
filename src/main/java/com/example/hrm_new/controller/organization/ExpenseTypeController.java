package com.example.hrm_new.controller.organization;

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

import com.example.hrm_new.entity.organization.ExpenseType;
import com.example.hrm_new.service.organization.ExpenseTypeService;

@RestController
@CrossOrigin
public class ExpenseTypeController {
	
	
	@Autowired
	private ExpenseTypeService expenseTypeService;

	@GetMapping("/expensetype")
	public ResponseEntity<?> getDetails() {

		try {

			Iterable<ExpenseType> expenseTypeDetails = expenseTypeService.listAll();

			return new ResponseEntity<>(expenseTypeDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/ExpenseType/save")
	public ResponseEntity<?> saveBank(@RequestBody ExpenseType expenseType) {

		try {
		
			expenseTypeService.SaveorUpdate(expenseType);

			return ResponseEntity.status(HttpStatus.CREATED).body("expenseType details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving expenseType details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	@RequestMapping("/ExpenseType/{expenseTypeId}")

	private Optional<ExpenseType> getExpenseType(@PathVariable(name = "expenseTypeId") long expenseTypeId) {

		return expenseTypeService.getExpenseTypeById(expenseTypeId);

	}

	@PutMapping("/ExpenseType/editExpenseType/{expenseTypeId}")

	public ResponseEntity<ExpenseType> updateExpenseType(@PathVariable("expenseTypeId") Long expenseTypeId,
			@RequestBody ExpenseType expenseTypeDetails) {

		try {

			ExpenseType existingExpenseType = expenseTypeService.findById(expenseTypeId);

			if (existingExpenseType == null) {

				return ResponseEntity.notFound().build();

			}

			existingExpenseType.setExpenseType(expenseTypeDetails.getExpenseType());
			
			expenseTypeService.save(existingExpenseType);

			return ResponseEntity.ok(existingExpenseType);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("/ExpenseType/ExpenseTypedelete/{expenseTypeId}")

	public ResponseEntity<String> deleteAmount(@PathVariable("expenseTypeId") Long expenseTypeId) {

		expenseTypeService.deleteExpenseTypeIdById(expenseTypeId);

		return ResponseEntity.ok("ExpenseType deleted successfully");

	}


}
