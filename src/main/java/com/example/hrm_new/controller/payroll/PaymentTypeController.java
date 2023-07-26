package com.example.hrm_new.controller.payroll;

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

import com.example.hrm_new.entity.payroll.PaymentType;
import com.example.hrm_new.service.payroll.PaymentTypeService;

@RestController
@CrossOrigin
public class PaymentTypeController {
	

	@Autowired
	private PaymentTypeService paymentTypeService;
	
	@GetMapping("/PaymentType")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<PaymentType> payRollDetails = paymentTypeService.listAll();

			return new ResponseEntity<>(payRollDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/PaymentType/save")

	public ResponseEntity<?> saveBank(@RequestBody PaymentType paymentType) {

		try {

			paymentTypeService.SaveorUpdate(paymentType);

			return ResponseEntity.status(HttpStatus.CREATED).body("PaymentType details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving PaymentType details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}



	@RequestMapping("/PaymentType/{paymentTypeId}")

	private Optional<PaymentType> getPayRoll(@PathVariable(name = "paymentTypeId") long paymentTypeId) {

		return paymentTypeService.getPaymentTypeById(paymentTypeId);

	}
	
	
	@PutMapping("/PaymentType/editPaymentType/{paymentTypeId}")

	public ResponseEntity<PaymentType> updatePayRoll(@PathVariable("paymentTypeId") Long paymentTypeId, @RequestBody PaymentType paymentTypeDetails) {

		try {

			PaymentType existingPaymentType = paymentTypeService.findById(paymentTypeId);

			if (existingPaymentType == null) {

				return ResponseEntity.notFound().build();

			}

			existingPaymentType.setPaymentType(paymentTypeDetails.getPaymentType());
		
			
			paymentTypeService.save(existingPaymentType);

			return ResponseEntity.ok(existingPaymentType);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	
	@DeleteMapping("/PaymentType/PaymentTypedelete/{paymentTypeId}")

	public ResponseEntity<String> deletePayRoll(@PathVariable("paymentTypeId") Long paymentTypeId) {

		paymentTypeService.deletePaymentTypeIdById(paymentTypeId);

		return ResponseEntity.ok("PaymentType deleted successfully");

	}

}
