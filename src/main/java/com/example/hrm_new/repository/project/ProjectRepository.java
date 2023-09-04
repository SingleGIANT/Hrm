package com.example.hrm_new.repository.project;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.entity.project.ProjectResponse;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByFromDateBetween(Date from, Date to);

	@Query(value = " SELECT * FROM projectss  " + "WHERE YEAR(from_date) = :year " + " AND MONTH(from_date) = :month "

			, nativeQuery = true)
	List<Map<String, Object>> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

	@Query(value = " select count(project_id) as total_projects,count(status) as incompleted_projects "
			+ " from  projectss ", nativeQuery = true)
	Map<String, Object> findByCountOfProjects();
	
	@Query(value = "select p.customer_id, count(p.project_id) " + "from projectss as p "
			+ "group by p.customer_id", nativeQuery = true)
	List<Map<String, Object>> getClientProjectCounts();
	

	@Query(value =" select p.total_duration,p.project_id  from projectss as p "
			 + " group by project_id ",
			nativeQuery = true)
	List<Map<String, Object>> findBytotalDuration();
	
	
	@Query(value ="SELECT p.*, "
			+ "       ( SELECT GROUP_CONCAT(d.designation_name) FROM designation d WHERE FIND_IN_SET(d.designation_id, p.designation_id)) AS designation_name,"
			+ "       ( SELECT c.name FROM customer c WHERE c.customer_id = p.customer_id) AS customer_name,"
			+ "       ( SELECT c.city FROM customer c WHERE c.customer_id = p.customer_id) AS city,"
			+ "       ( SELECT c.phone_no1 FROM customer c WHERE c.customer_id = p.customer_id) AS phone_no1,"
			+ "       ( SELECT c.phone_no2 FROM customer c WHERE c.customer_id = p.customer_id) AS phone_no2"
			+ "  FROM projectss AS p ",
			nativeQuery = true)
	List<Project> getAllProjectss();
	
	
	    @Query(value = "SELECT p.project_id AS projectId, p.project_title AS projectTitle,"
	    		+ " p.customer_id AS customerId, p.contact, p.location, p.total_duration AS totalDuration, "
	    		+ " p.from_date AS fromDate, p.to_date AS toDate, p.total_project_amount AS totalProjectAmount,"
	    		+ " p.status, GROUP_CONCAT(d.designation_name) AS designationName, GROUP_CONCAT(d.designation_id) AS designationId, c.name AS customerName, c.city, c.phone_no1 AS phoneNo1, "
	    		+ "c.phone_no2 AS phoneNo2 FROM projectss AS p LEFT JOIN designation d ON FIND_IN_SET(d.designation_id, p.designation_id) LEFT JOIN customer c ON c.customer_id = p.customer_id GROUP BY p.project_id", nativeQuery = true)
	    List<Object[]> getAllProjects();
	    
	    
	    @Query(value = "SELECT p.project_id, p.contact, p.customer_id, p.designation_id, p.from_date, p.location, p.project_title, p.status, p.to_date, p.total_duration, p.total_project_amount, GROUP_CONCAT(d.designation_name), GROUP_CONCAT(d.designation_id) FROM projectss AS p LEFT JOIN designation d ON FIND_IN_SET(d.designation_id, p.designation_id) GROUP BY p.project_id", nativeQuery = true)
	    List<Object[]> getAllProjects1();
	    
	    @Query(value ="SELECT p.*, "
				+ "       ( select group_concat(d.designation_name) from designation d where find_in_set(d.designation_id, p.designation_id)) AS designation_name,"
				+ "       ( SELECT c.name FROM customer c WHERE c.customer_id = p.customer_id) AS customer_name,"
				+ "       ( SELECT c.city FROM customer c WHERE c.customer_id = p.customer_id) AS city,"
				+ "       ( SELECT c.phone_no1 FROM customer c WHERE c.customer_id = p.customer_id) AS phone_no1,"
				+ "       ( SELECT c.phone_no2 FROM customer c WHERE c.customer_id = p.customer_id) AS phone_no2"
				+ "  FROM projectss AS p ",
				nativeQuery = true)
		List<Map<String ,Object>> getAllProjectss1();
	    
	    @Query(value = "    SELECT p.project_id AS projectId, "
	    		+ "       p.project_title AS projectTitle,"
	    		+ "       p.customer_id AS customerId, "
	    		+ "       p.contact, "
	    		+ "       p.location, "
	    		+ "       p.project_status,"
	    		+ "       p.total_duration AS totalDuration, "
	    		+ "       p.from_date AS fromDate, "
	    		+ "       p.to_date AS toDate, "
	    		+ "       p.total_project_amount AS totalProjectAmount,"
	    		+ "       p.status, "
	    		+ "       GROUP_CONCAT(d.designation_name) AS designationName, "
	    		+ "       GROUP_CONCAT(d.designation_id) AS designationId, "
	    		+ "       c.name AS customerName, "
	    		+ "       c.city, "
	    		+ "       c.phone_no1 AS phoneNo1, "
	    		+ "       c.phone_no2 AS phoneNo2 "
	    		+ " FROM projectss AS p "
	    		+ " LEFT JOIN designation d ON FIND_IN_SET(d.designation_id, p.designation_id) "
	    		+ " LEFT JOIN customer c ON c.customer_id = p.customer_id "
	    		+ " WHERE p.project_status = true"
	    		+ " GROUP BY p.project_id;"
	    		+ "", nativeQuery = true)
	    List<Map<String, Object>> getAllProjects2();
	    
	    
	    @Query(value =
	            "SELECT "
	            + "    p.*,"
	            + "    (SELECT GROUP_CONCAT(d.designation_name) FROM designation d WHERE FIND_IN_SET(d.designation_id, p.designation_id)) AS designation_name,\r\n"
	            + "    c.name AS customer_name,"
	            + "    c.city,"
	            + "    c.phone_no1,"
	            + "    c.phone_no2"
	            + " FROM "
	            + "    projectss AS p"
	            + " LEFT JOIN"
	            + "    customer AS c ON p.customer_id = c.customer_id"
	            + " WHERE "
	            + "    p.from_date BETWEEN :fromdate AND :todate",
	            nativeQuery = true)
		List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("fromdate")LocalDate fromdate, @Param("todate")LocalDate todate);
	}

	





