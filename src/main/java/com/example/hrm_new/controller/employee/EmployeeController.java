package com.example.hrm_new.controller.employee;


import java.util.List;
import java.util.Map;
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
import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.repository.employee.EmployeeRepository;
import com.example.hrm_new.service.employee.EmployeeService;

@CrossOrigin
@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService service;
	@Autowired
	private EmployeeRepository repo;

//	@GetMapping("/employees/true")
//	public ResponseEntity<?> getEmployees() {
//		try {
//			List<Employee> Employees = service.listAll();
//			return ResponseEntity.ok(Employees);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error retrieving Employees: " + e.getMessage());
//		}
//	}
	
	@GetMapping("/employees")
	public ResponseEntity<?> getEmployeess() {
		try {
			List<Employee> Employees = service.listAll1();
			return ResponseEntity.ok(Employees);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving Employees: " + e.getMessage());
		}
	}

	@PostMapping("/employees/save")
	public ResponseEntity<String> saveEmployee(@RequestBody Employee Employee) {
		try {
			Employee.setStatus(true);
			service.saveOrUpdate(Employee);
			
			return ResponseEntity.ok("Employee saved with id: " + Employee.getEmployeeId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving Employee: " + e.getMessage());
		}
	}

	 @PutMapping("/employees/or/{id}")
	    public ResponseEntity<Boolean> toggleComplaintsStatus(@PathVariable(name = "id") long id) {
	        try {
	        	Employee complaints = service.getById(id);
	            if (complaints != null) {
	                // Toggle the status
	                boolean currentStatus = complaints.isStatus();
	                complaints.setStatus(!currentStatus);
	                service.saveOrUpdate(complaints); // Save the updated complaints
	            } else {
	                return ResponseEntity.ok(false); // Complaints with the given ID does not exist, return false
	            }

	            return ResponseEntity.ok(complaints.isStatus()); // Return the new status (true or false)
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false);
	        }
	 }

	@PutMapping("/employees/edit/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee Employee) {
		try {
			Employee existingEmployee = service.getById(id);
			if (existingEmployee == null) {
				return ResponseEntity.notFound().build();
			}
			existingEmployee.setCity(Employee.getCity());
			existingEmployee.setAddress(Employee.getAddress());
			existingEmployee.setExperience(Employee.getExperience());
			existingEmployee.setFirstName(Employee.getFirstName());
			existingEmployee.setEmail(Employee.getEmail());
			existingEmployee.setLastName(Employee.getLastName());
			existingEmployee.setCountry(Employee.getCountry());
			existingEmployee.setGender(Employee.getGender());
			existingEmployee.setDob(Employee.getDob());
			existingEmployee.setPhoneNumber(Employee.getPhoneNumber());
			existingEmployee.setDateOfJoining(Employee.getDateOfJoining());
			existingEmployee.setRoleId(Employee.getRoleId());
			existingEmployee.setDesignationId(Employee.getDesignationId());
			existingEmployee.setMarital(Employee.getMarital());
			existingEmployee.setState(Employee.getState());
			existingEmployee.setIfseCode(Employee.getIfseCode());
			existingEmployee.setAccountNumber(Employee.getAccountNumber());
			existingEmployee.setHolderName(Employee.getHolderName());
			existingEmployee.setBankName(Employee.getBankName());
			existingEmployee.setDepartmentId(Employee.getDepartmentId());
			existingEmployee.setBranchName(Employee.getBranchName());
			existingEmployee.setDescription(Employee.getDescription());
			
			

			service.saveOrUpdate(existingEmployee);
			return ResponseEntity.ok(existingEmployee);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/employees/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("Employee deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting Employee: " + e.getMessage());
		}
	}

	@GetMapping("/employees/view")
	public List<Map<String, Object>> allcompanyDetails() {
		return service.AllEmployee();

	}

	@GetMapping("/employees/true")
	public List<Map<String, Object>> AllcompanyDetails() {
		return repo.AllEmployees();

	}

//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody Employee employee) {
//		String email = employee.getEmail();
//		String password = employee.getPassword();
//
//		if (service.login(email, password)) {
//			return ResponseEntity.ok("Login successful.");
//		} else {
//			return ResponseEntity.badRequest().body("Invalid credentials.");
//		}
//	}
//
//	@GetMapping("/contact/{contactNo1}")
//	public Employee checkEmployeeContactNumber1(@PathVariable long contactNo1) {
//		return service.checkEmployeeContactNumber1(contactNo1);
//	}
//
//	@GetMapping("/email/{email}")
//	public Employee checkCustomerByEmail(@PathVariable String email) {
//		return service.checkCustomerByEmail(email);
//	}
//
//	@GetMapping("/password/{email}")
//	public String getEmployeePasswordByEmail(@PathVariable String email) {
//		return service.getEmployeePasswordByEmail(email);
//	}
//
//	@GetMapping("/status/{email}/{password}")
//	public boolean findStatusEmployee(@PathVariable String email, @PathVariable String password) {
//		return service.findStatusEmployee(email, password);
//	}
//
//	@PutMapping("/change-password/{email}/{password}")
//	public ResponseEntity<String> changePassword(@PathVariable String email, @PathVariable String password) {
//		service.changePassword(email, password);
//		return ResponseEntity.ok("Password changed successfully.");
//	}
//
//	@GetMapping("/id/{email}")
//	public Long findEmployeeId(@PathVariable String email) {
//		return service.findEmployeeId(email);
//	}

}
