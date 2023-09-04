package com.example.hrm_new.controller.employee;



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
import org.springframework.web.bind.annotation.RestController;
import com.example.hrm_new.entity.employee.Department;
import com.example.hrm_new.service.employee.DepartmentService;

@RestController
@CrossOrigin
public class DepartmentController {

	@Autowired
	private DepartmentService DepartmentService;
	
	

	@GetMapping("/department")
	public ResponseEntity<?> getDetails() {
		try {
			Iterable<Department> DepartmentDetails = DepartmentService.listAll();
			return new ResponseEntity<>(DepartmentDetails, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Employees: " + e.getMessage());
		}
	}

	
//	@GetMapping("/department")
//	public ResponseEntity<CustomResponse> getDetails() {
//	    try {
//	        Iterable<Department> departmentDetails = DepartmentService.listAll();
//	        CustomResponse response = new CustomResponse(true, departmentDetails);
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//	    } catch (Exception e) {
//	        CustomResponse response = new CustomResponse(false, "Error retrieving Departments: " + e.getMessage());
//	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}




	@PostMapping("/department/save")
	public ResponseEntity<?> saveDepartment(@RequestBody Department Department) {
		try {
			DepartmentService.SaveorUpdate(Department);
			long id = Department.getDepartmentId();
			return ResponseEntity.status(HttpStatus.OK).body("Department details saved successfully."  + id);
		} catch (Exception e) {
			String errorMessage = "An error occurred while saving Department details.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}


	@PutMapping("/department/edit/{DepartmentId}")

	public ResponseEntity<Department> updateDepartmentId(@PathVariable("DepartmentId") Long DepartmentId, @RequestBody Department DepartmentIdDetails) {
		try {
			Department existingDepartment = DepartmentService.findById(DepartmentId);
			if (existingDepartment == null) {
				return ResponseEntity.notFound().build();
			}
			existingDepartment.setDepartmentName(DepartmentIdDetails.getDepartmentName());
			existingDepartment.setColour(DepartmentIdDetails.getColour());
			DepartmentService.save(existingDepartment);
			return ResponseEntity.ok(existingDepartment);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/department/delete/{DepartmentId}")
	public ResponseEntity<String> deletDepartmentName(@PathVariable("DepartmentId") Long DepartmentId) {
		DepartmentService.deleteById(DepartmentId);
		return ResponseEntity.ok("Department deleted successfully");
	}
}
