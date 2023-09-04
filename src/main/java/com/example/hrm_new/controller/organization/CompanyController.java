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

import com.example.hrm_new.entity.customer.Customer;
import com.example.hrm_new.entity.organization.Company;
import com.example.hrm_new.service.organization.CompanyService;



@RestController
@CrossOrigin
public class CompanyController {
	
	@Autowired
	private CompanyService companyservice;
	
	@GetMapping("/company")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<Company> companyDetails = companyservice.listAll();

			return new ResponseEntity<>(companyDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/company/save")

	public ResponseEntity<?> saveBank(@RequestBody Company company) {

		try {
			company.setStatus(true);
			companyservice.SaveorUpdate(company);

			return ResponseEntity.status(HttpStatus.CREATED).body("company details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving company details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}




	  @PutMapping("/company/or/{id}")
	    public ResponseEntity<Boolean> toggleCustomerStatus(@PathVariable(name = "id") long id) {
	        try {
	        	Company company = companyservice.findById(id);
	            if (company != null) {
	                // Customer with the given id exists, toggle the status
	                boolean currentStatus = company.isStatus();
	                company.setStatus(!currentStatus);
	                companyservice.SaveorUpdate(company); // Save the updated customer
	            } else {
	                // Customer with the given id does not exist, return false
	                return ResponseEntity.ok(false);
	            }

	            return ResponseEntity.ok(company.isStatus()); // Return the new status (true or false)
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false); // Set response to false in case of an error
	        }
	    }

	
	
	@PutMapping("/company/editCompany/{companyId}")

	public ResponseEntity<Company> updateCompany(@PathVariable("companyId") Long companyId, @RequestBody Company companyDetails) {

		try {

			Company existingCompany = companyservice.findById(companyId);

			if (existingCompany == null) {

				return ResponseEntity.notFound().build();

			}

			existingCompany.setCompanyName(companyDetails.getCompanyName());
			existingCompany.setAddress(companyDetails.getAddress());
			existingCompany.setPincode(companyDetails.getPincode());
			existingCompany.setState(companyDetails.getState());
			existingCompany.setCountry(companyDetails.getCountry());
			existingCompany.setLocation(companyDetails.getLocation());
			existingCompany.setPhoneNumber1(companyDetails.getPhoneNumber1());
			existingCompany.setPhoneNumber2(companyDetails.getPhoneNumber2());
			existingCompany.setGstNo(companyDetails.getGstNo());	
			existingCompany.setFaxNo(companyDetails.getFaxNo());
			existingCompany.setEmail(companyDetails.getEmail());
			existingCompany.setBankName(companyDetails.getBankName());
			existingCompany.setAccountNo(companyDetails.getAccountNo());
			existingCompany.setIfscCode(companyDetails.getIfscCode());
			existingCompany.setBranchName(companyDetails.getBranchName());
			existingCompany.setAccountHolderName(companyDetails.getAccountHolderName());
			existingCompany.setStatus(companyDetails.isStatus());
			
			companyservice.save(existingCompany);

			return ResponseEntity.ok(existingCompany);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	
	@DeleteMapping("/company/companydelete/{companyId}")

	public ResponseEntity<String> deleteTitle(@PathVariable("companyId") Long companyId) {

		companyservice.deleteCompanyById(companyId);

		return ResponseEntity.ok("company deleted successfully");

	}
	
	
}
