package com.example.hrm_new.service.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.repository.employee.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	public List<Employee> listAll1() {
		return repo.findAll();
	}
	
	public List<Employee> listAll() {
	    List<Employee> allEmployees = repo.findAll();
	    List<Employee> trueStatusEmployees = new ArrayList<>();

	    for (Employee employee : allEmployees) {
	        if (employee.isStatus()) {
	            trueStatusEmployees.add(employee);
	        }
	    }

	    return trueStatusEmployees;
	}

	public void saveOrUpdate(Employee employee) {
		repo.save(employee);
	}

	public Employee getById(long id) {
		return repo.findById(id).get();
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public Employee getEmployeeById(Long employeeId) {
		return repo.findById(employeeId).orElse(null);
	}

	public List<Map<String, Object>> AllEmployee() {
		return repo.AllEmployee();
	}
//
//	public Employee checkEmployeeContactNumber1(long contactNo1) {
//		return repo.checkEmployeeContactNumber1(contactNo1);
//	}
//
//	public Employee checkCustomerByEmail(String email) {
//		return repo.checkCustomerByEmail(email);
//	}
//
//	public String getEmployeePasswordByEmail(String email) {
//		return repo.getEmployeePasswordByEmail(email);
//	}
//
//	public boolean findStatusEmployee(String email, String password) {
//		return repo.findStatusEmployee(email, password);
//	}
//
//	public void changePassword(String email, String password) {
//		repo.changePassword(email, password);
//	}
//
//	public Long findEmployeeId(String email) {
//		return repo.findEmployeeId(email);
//	}
//	
//	public boolean login(String email, String password) {
//        Employee employee = repo.checkEmployeeByEmail(email);
//        if (employee != null && employee.getPassword().equals(password)) {
//            return true;
//        }
//        return false;
//    }

	

}
