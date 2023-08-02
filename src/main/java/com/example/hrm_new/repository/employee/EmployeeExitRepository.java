package com.example.hrm_new.repository.employee;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.EmployeeExit;

public interface EmployeeExitRepository extends JpaRepository<EmployeeExit, Long>{
	
	@Query(value = "select e.first_name ,e.last_name ,c.*"
			+ " from employeeexit as c"
			+ " join employee as e on e.employee_id=c.employee_id", nativeQuery = true)
	List<Map<String, Object>> getAllProjectWork();

	
	@Query(value = "select e.first_name ,e.last_name ,c.*"
			+ " from employeeexit as c"
			+ " join employee as e on e.employee_id=c.employee_id"
			+ "  where c.employee_id=:employee_id", nativeQuery = true)
	List<Map<String, Object>> Allemployeeexit(@Param("employee_id")long  employee_id);
 

}
