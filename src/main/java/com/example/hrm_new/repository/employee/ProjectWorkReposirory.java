package com.example.hrm_new.repository.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.ProjectWork;

public interface ProjectWorkReposirory extends JpaRepository<ProjectWork, Long> {

	@Query(value = "select e.first_name ,e.last_name ,p.*,d.designation_name,pp.project_title"
			+ "		from projectwork as p"
			+ "        join project as pp on pp.project_id=p.project_id"
			+ "			       join designation as d on d.designation_id=p.designation_id "
			+ "			 join employee as e on e.employee_id=p.employee_id;", nativeQuery = true)
	List<Map<String, Object>> getAllProjectWork();

	@Query(value = "select e.first_name ,e.last_name ,p.*,d.designation_name,pp.project_title" 
	+ "		from projectwork as p"
			+ "        join designation as d on d.designation_id=p.designation_id"
			+ " join project as pp on pp.project_id=p.project_id"
			+ "		 join employee as e on e.employee_id=p.employee_id"
			+ " where p.employee_id=:employee_id", nativeQuery = true)
	List<Map<String, Object>> Allprojectwork(@Param("employee_id") Long employee_id);

	@Query(value = "select e.first_name ,e.last_name ,p.*,d.designation_name,pp.project_title"
			+ "					from projectwork as p"
			+ "			       join designation as d on d.designation_id=p.designation_id"
			+ "  join project as pp on pp.project_id=p.project_id"
			+ "			 join employee as e on e.employee_id=p.employee_id"
			+ "  WHERE p.date BETWEEN :startDate AND :endDate", nativeQuery = true)
List<Map<String, Object>> getAllpromotionsBetweenDates(  @Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	
	 @Query(value = "SELECT e.first_name, e.last_name, p.*, d.designation_name ,pp.project_title ,COUNT(p.project_work_id) AS project_count"
	 		+ "            FROM projectwork AS p"
	 		+ "            JOIN designation AS d ON d.designation_id = p.designation_id"
	 		+ "   join project as pp on pp.project_id=p.project_id"
	 		+ "            JOIN employee AS e ON e.employee_id = p.employee_id "
	 		+ "            WHERE p.employee_id = :employee_id"
	 		+ "            GROUP BY e.first_name, e.last_name, p.project_work_id, d.designation_name;",nativeQuery = true)
	    List<Map<String, Object>> getEmployeeProjectsDetails(@Param("employee_id") Long employee_id);
	

	@Query(value = "SELECT e.first_name, e.last_name, pp.project_title, COUNT(p.project_work_id) AS total_projectwork_count "
			+ "			 FROM projectwork AS p "
			+ "		join project as pp on pp.project_id=p.project_id"
			+ "			 JOIN employee AS e ON e.employee_id = p.employee_id "
			+ "			 WHERE p.employee_id = :employeeId "
			+ "		 GROUP BY e.first_name, e.last_name, pp.project_title", nativeQuery = true)
	List<Map<String, Object>> getEmployeeProjectWorkDetails(@Param("employeeId") Long employeeId);

	@Query(value = "select e.first_name, e.last_name, count(p.project_work_id) as total_projectwork_count"
			+ "	from projectwork as p join employee as e on e.employee_id = p.employee_id "
			+ " join project as pp on pp.project_id=p.project_id"
			+ "	where p.employee_id = :employee_id", nativeQuery = true)
	List<Map<String, Object>> getEmployeeProjectWorkDetailsss(@Param("employee_id") Long employee_id);
	


	 @Query(value = "select e.first_name, e.last_name, p.*, d.designation_name, pp.project_title "
	            + "from projectwork as p "
	            + "join project as pp on pp.project_id = p.project_id "
	            + "join designation as d on d.designation_id = p.designation_id "
	            + "join employee as e on e.employee_id = p.employee_id "
	            + "where pp.project_title = :project_name", nativeQuery = true)
	    List<Map<String, Object>> getAllProjectNamelll(@Param("project_name") String project_name);
	
}
