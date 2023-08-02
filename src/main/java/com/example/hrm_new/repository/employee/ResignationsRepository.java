package com.example.hrm_new.repository.employee;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.Resignations;

public interface ResignationsRepository extends JpaRepository<Resignations, Long>{

	
	
	@Query(value=
			  " select e.first_name ,e.last_name ,d.*"
			  + " from resignations as d"
			  + " join employee as e on e.employee_id=d.employee_id",nativeQuery = true)
	List <Map<String,Object>>AllGoat();
	
	@Query(value=
			  " select e.first_name ,e.last_name ,d.*"
			  + " from resignations as d"
			  + " join employee as e on e.employee_id=d.employee_id"
			  + " where d.employee_id=:employee_id",nativeQuery = true)
	List <Map<String,Object>>AllTimeGoat(@Param("employee_id")long employee_id);

	@Query(value = "select e.first_name ,e.last_name ,d.*"
			+ "			 from resignations as d"
			+ "			  join employee as e on e.employee_id=d.employee_id"
			+ "              where d.resignations_date between :startDate and :endDate", nativeQuery = true)
	 List<Map<String, Object>> getAllReceiptBetweenDates( LocalDate startDate, LocalDate endDate);
	
	@Query(value = "select e.first_name, e.last_name, d.resignations_id, d.notice_date, d.reason, d.resignations_date, d.status,"
			+ "       datediff( d.resignations_date,d.notice_date) as duration_date"
			+ " from resignations as d"
			+ "  join employee as e on e.employee_id = d.employee_id;", nativeQuery = true)
	 List<Map<String, Object>> getAllDurationDate();
	
}
