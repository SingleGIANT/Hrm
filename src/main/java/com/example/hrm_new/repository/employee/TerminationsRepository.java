package com.example.hrm_new.repository.employee;

import java.time.LocalDate;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.Terminations;



public interface TerminationsRepository extends JpaRepository<Terminations, Long>{
	
	
@Query(value = "select e.first_name ,e.last_name ,t.*"
		+ " from terminations as t"
		+ " join employee as e on e.employee_id=t.employee_id;",nativeQuery = true)
 List<Map<String,Object>>AllTerminations();
@Query(value = "select e.first_name ,e.last_name ,t.*"
		+ " from terminations as t"
		+ " join employee as e on e.employee_id=t.employee_id"
		+ "  where t.employee_id=:employee_id",nativeQuery = true)
List<Map<String, Object>> Allterminations(@Param("employee_id") Long employee_id);

@Query(value = "select e.first_name ,e.last_name ,t.*"
		+ "		 from terminations as t"
		+ "		 join employee as e on e.employee_id=t.employee_id"
		+ " where t.terminations_date BETWEEN :startDate AND :endDate",nativeQuery = true)	
List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);










}
