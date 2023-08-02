package com.example.hrm_new.repository.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.ProjectReport;

public interface ProjectReportRepository extends JpaRepository<ProjectReport, Long>{
	
	
	@Query(value = "select e.first_name ,e.last_name ,p.*,pp.project_title"
			+ "			from projectreport as p"
			+ "  join project as pp on pp.project_id=p.project_id"
			+ "		 join employee as e on e.employee_id=p.employee_id;", nativeQuery = true)
	List<Map<String, Object>> getAllProjectWork();
	
	@Query(value = "select e.first_name ,e.last_name ,p.*,pp.project_title"
			+ "from projectreport as p"
			+ "  join project as pp on pp.project_id=p.project_id"
			+ "  join employee as e on e.employee_id=p.employee_id"
			+  "  where p.employee_id=:employee_id", nativeQuery = true)
	List<Map<String, Object>> Allprojectreport(@Param("employee_id") Long employee_id);
	
	@Query(value = "select e.first_name ,e.last_name ,p.*,pp.project_title"
			+ "						from projectreport as p"
			+ "			  join project as pp on pp.project_id=p.project_id"
			+ "					 join employee as e on e.employee_id=p.employee_id"
			+ "			where p.extended_date between :startDate and :endDate", nativeQuery = true)
	List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	
	@Query(value = "select e.first_name ,e.last_name ,p.*,pp.project_title"
			+ "							from projectreport as p"
			+ "					  join project as pp on pp.project_id=p.project_id"
			+ "					 join employee as e on e.employee_id=p.employee_id"
			+ "					where p.extended_date =CURDATE();", nativeQuery = true)
	List<Map<String, Object>> getPurchaseAndSales();
}
