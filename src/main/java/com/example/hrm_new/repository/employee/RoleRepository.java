package com.example.hrm_new.repository.employee;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hrm_new.entity.employee.Role;






public interface RoleRepository extends JpaRepository<Role, Long>  {
	
	@Query(value =
		    "SELECT e.*, r.role_name, d.designation_name " +
		    "FROM employee AS e " +
		    "JOIN role AS r ON e.role_id = r.role_id " +
		    "JOIN designation AS d ON e.designation_id = d.designation_id",
		    nativeQuery = true)
List<Map<String, Object>> findAllProductsByCategory();


}
