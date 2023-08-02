package com.example.hrm_new.controller.assest;

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

import com.example.hrm_new.entity.Assest.KeyboardBrand;
import com.example.hrm_new.service.assest.KeyboardBrandService;

@RestController
@CrossOrigin
public class KeyboardBrandController {
	

	@Autowired

	private KeyboardBrandService keyboardBrandService;

	@GetMapping("/KeyboardBrand")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<KeyboardBrand> assestDetails = keyboardBrandService.listAll();

			return new ResponseEntity<>(assestDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/KeyboardBrand/save")

	public ResponseEntity<?> saveBank(@RequestBody KeyboardBrand keyboardBrand) {

		try {
//			assest.setStatus(true);
			keyboardBrandService.SaveorUpdate(keyboardBrand);

			return ResponseEntity.status(HttpStatus.CREATED).body("keyboardBrand details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving keyboardBrand details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	@RequestMapping("/KeyboardBrand/{keyboardBrandId}")

	private Optional<KeyboardBrand> getKeyboardBrand(@PathVariable(name = "keyboardBrandId") long keyboardBrandId) {

		return keyboardBrandService.getKeyboardBrandById(keyboardBrandId);

	}

	@PutMapping("/KeyboardBrand/editKeyboardBrand/{keyboardBrandId}")

	public ResponseEntity<KeyboardBrand> updateKeyboardBrand(@PathVariable("keyboardBrandId") Long keyboardBrandId,
			@RequestBody KeyboardBrand brandDetails) {

		try {

			KeyboardBrand existingBrand = keyboardBrandService.findById(keyboardBrandId);

			if (existingBrand == null) {

				return ResponseEntity.notFound().build();

			}

			existingBrand.setKeyboardBrandName(brandDetails.getKeyboardBrandName());
			keyboardBrandService.save(existingBrand);

			return ResponseEntity.ok(existingBrand);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	@DeleteMapping("/keyboardBrandId/keyboardBrandIddelete/{keyboardBrandId}")

	public ResponseEntity<String> deleteKeyboardBrand(@PathVariable("keyboardBrandId") Long keyboardBrandId) {

		keyboardBrandService.deleteKeyboardBrandById(keyboardBrandId);

		return ResponseEntity.ok("keyboardBrandId deleted successfully");

	}

	
	

}
