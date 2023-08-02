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
import com.example.hrm_new.entity.Assest.MouseBrand;
import com.example.hrm_new.service.assest.MouseBrandService;

@RestController
@CrossOrigin
public class MouseBrandController {
	
	@Autowired

	private MouseBrandService mouseBrandService;

	@GetMapping("/MouseBrand")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<MouseBrand> assestDetails = mouseBrandService.listAll();

			return new ResponseEntity<>(assestDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/MouseBrand/save")

	public ResponseEntity<?> saveBank(@RequestBody MouseBrand mouseBrand) {

		try {
//			assest.setStatus(true);
			mouseBrandService.SaveorUpdate(mouseBrand);

			return ResponseEntity.status(HttpStatus.CREATED).body("mouseBrand details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving mouseBrand details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	@RequestMapping("/mouseBrand/{mouseBrandId}")

	private Optional<MouseBrand> getMouseBrand(@PathVariable(name = "mouseBrandId") long mouseBrandId) {

		return mouseBrandService.getMouseBrandById(mouseBrandId);

	}

	@PutMapping("/mouseBrand/editMouseBrand/{mouseBrandId}")

	public ResponseEntity<MouseBrand> updateMouseBrand(@PathVariable("mouseBrandId") Long mouseBrandId,
			@RequestBody MouseBrand brandDetails) {

		try {

			MouseBrand existingBrand = mouseBrandService.findById(mouseBrandId);

			if (existingBrand == null) {

				return ResponseEntity.notFound().build();

			}

			existingBrand.setMouseBrandName(brandDetails.getMouseBrandName());
			mouseBrandService.save(existingBrand);

			return ResponseEntity.ok(existingBrand);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	@DeleteMapping("/mouseBrandId/mouseBrandIddelete/{mouseBrandId}")

	public ResponseEntity<String> deleteKeyboardBrand(@PathVariable("mouseBrandId") Long mouseBrandId) {

		mouseBrandService.deleteMouseBrandById(mouseBrandId);

		return ResponseEntity.ok("mouseBrandId deleted successfully");

	}


}
