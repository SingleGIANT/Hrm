package com.example.hrm_new.repository.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.Complaints;



public interface ComplaintsRepository extends JpaRepository<Complaints, Long>{
	
	@Query(value = "select e.first_name ,e.last_name ,c.*"
			+ "from complaints as c"
			+ " join employee as e on e.employee_id=c.employee_id;",nativeQuery = true)
	 List<Map<String,Object>>AllComplaints();
	
	@Query(value = "select e.first_name ,e.last_name ,c.*"
			+ "from complaints as c"
			+ " join employee as e on e.employee_id=c.employee_id"
			+ " where e.employee_id=:employee_id",nativeQuery = true)	
	List<Map<String, Object>> Allcomplaints( @Param("employee_id") Long employee_id);
	
	
	@Query(value = "select e.first_name ,e.last_name ,c.*"
			+ "from complaints as c"
			+ " join employee as e on e.employee_id=c.employee_id"
			+ " where c.complaints_date BETWEEN :startDate AND :endDate",nativeQuery = true)	
	List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

	
	
	@Query(value = " select year(complaints_date) as year_number, month(complaints_date) as month_number, count(complaints_id) as total_complaints"
			+ " from complaints"
			+ " group by year(complaints_date), month(complaints_date);",nativeQuery = true)	
	List<Map<String, Object>> getAllcomplaints();

}
