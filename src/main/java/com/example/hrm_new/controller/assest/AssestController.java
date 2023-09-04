package com.example.hrm_new.controller.assest;

import java.util.Collections;
import java.util.Comparator;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import com.example.hrm_new.entity.Assest.Assest;
import com.example.hrm_new.entity.customer.Customer;
import com.example.hrm_new.repository.assest.AssestRepository;
import com.example.hrm_new.service.assest.AssestService;

@RestController
@CrossOrigin
public class AssestController {

	@Autowired

	private AssestService assestservice;
	@Autowired
	private AssestRepository repo;

//	@GetMapping("/Assest")
//	public ResponseEntity<?> getDetails() {
//		try {
//			Iterable<Assest> assestDetails = assestservice.listAll();
//			 Collections.sort(assestDetails, Comparator.comparing(Assest::getAssestId).reversed());
//			return new ResponseEntity<>(assestDetails, HttpStatus.OK);
//		} catch (Exception e) {
//			String errorMessage = "An error occurred while retrieving l details.";
//			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

    @GetMapping("/Assest")
    public ResponseEntity<?> getDetails() {
        try {
            Iterable<Assest> assetDetails = assestservice.listAll();
            List<Assest> sortedAssets = StreamSupport.stream(assetDetails.spliterator(), false)
                .sorted(Comparator.comparing(Assest::getAssestId).reversed())
                .collect(Collectors.toList());

            return new ResponseEntity<>(sortedAssets, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving asset details.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	@PostMapping("/Assest/save")
	public ResponseEntity<?> saveBank(@RequestBody Assest assest) {
		try {
			assest.setStatus(true);
			assestservice.SaveorUpdate(assest);
			return ResponseEntity.status(HttpStatus.CREATED).body("assest details saved successfully.");
		} catch (Exception e) {
			String errorMessage = "An error occurred while saving assest details.";
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	
	
	  @PutMapping("/assest/or/{id}")
	    public ResponseEntity<Boolean> toggleCustomerStatus(@PathVariable(name = "id") long assestId) {
	        try {
	        	Assest existingAssest = assestservice.findById(assestId);
	            if (existingAssest != null) {
	               
	                boolean currentStatus = existingAssest.isStatus();
	                existingAssest.setStatus(!currentStatus);
	                assestservice.save(existingAssest);
	            } else {
	                
	                return ResponseEntity.ok(false);
	            }

	            return ResponseEntity.ok(existingAssest.isStatus()); 
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false); 
	        }
	    }
	@RequestMapping("/assest/{assestId}")

	private Assest getAssest(@PathVariable(name = "assestId") long assestId) {

		return assestservice.findById(assestId);

	}

	

	@PutMapping("/assest/editassest/{assestId}")
	public ResponseEntity<Assest> updateAssest(@PathVariable("assestId") Long assestId,
			@RequestBody Assest assestDetails) {
		try {
			Assest existingAssest = assestservice.findById(assestId);
			if (existingAssest == null) {
				return ResponseEntity.notFound().build();
			}
			existingAssest.setProductName(assestDetails.getProductName());
			existingAssest.setSerialNumber(assestDetails.getSerialNumber());
			existingAssest.setPurchaseDate(assestDetails.getPurchaseDate());
			existingAssest.setModelNumber(assestDetails.getModelNumber());
			existingAssest.setBrandId(assestDetails.getBrandId());
			existingAssest.setCountOfProducts(assestDetails.getCountOfProducts());
			existingAssest.setKeyboardBrandId(assestDetails.getKeyboardBrandId());
			existingAssest.setMouseBrandId(assestDetails.getMouseBrandId());
			existingAssest.setStatus(assestDetails.isStatus());
			assestservice.save(existingAssest);
			return ResponseEntity.ok(existingAssest);
		} catch (Exception e) {
			e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/assest/assestdelete/{assestId}")
	public ResponseEntity<String> deleteprojectName(@PathVariable("assestId") Long assestId) {
		assestservice.deleteAssestIdById(assestId);
		return ResponseEntity.ok("assest deleted successfully");

	}

	@GetMapping("/assestdetails/view")
	public List<Map<String, Object>> allAssestDetails() {
		return assestservice.allAssestDetails();
	}
	
	
	@GetMapping("/searchstatus1")
	public List<Assest> findStatus() {
		return repo.findByStatusTrue();

	}

	

}
