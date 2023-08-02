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

import com.example.hrm_new.entity.Assest.Brand;
import com.example.hrm_new.service.assest.BrandService;

@RestController
@CrossOrigin
public class BrandController {
	

	@Autowired

	private BrandService brandService;

	@GetMapping("/Brand")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<Brand> assestDetails = brandService.listAll();

			return new ResponseEntity<>(assestDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/Brand/save")

	public ResponseEntity<?> saveBank(@RequestBody Brand brand) {

		try {
//			assest.setStatus(true);
			brandService.SaveorUpdate(brand);

			return ResponseEntity.status(HttpStatus.CREATED).body("brand details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving brand details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	@RequestMapping("/Brand/{brandId}")

	private Optional<Brand> getBrand(@PathVariable(name = "brandId") long brandId) {

		return brandService.getBrandById(brandId);

	}

	@PutMapping("/Brand/editBrand/{brandId}")

	public ResponseEntity<Brand> updateBrand(@PathVariable("brandId") Long brandId,
			@RequestBody Brand brandDetails) {

		try {

			Brand existingBrand = brandService.findById(brandId);

			if (existingBrand == null) {

				return ResponseEntity.notFound().build();

			}

			existingBrand.setBrandName(brandDetails.getBrandName());
			brandService.save(existingBrand);

			return ResponseEntity.ok(existingBrand);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("/Brand/branddelete/{brandId}")

	public ResponseEntity<String> deleteBrand(@PathVariable("brandId") Long brandId) {

		brandService.deleteBrandById(brandId);

		return ResponseEntity.ok("Brand deleted successfully");

	}

}
