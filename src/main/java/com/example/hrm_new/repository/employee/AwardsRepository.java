package com.example.hrm_new.repository.employee;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.Awards;

public interface AwardsRepository extends JpaRepository<Awards, Long> {

	@Query(value = "SELECT a.awards_id, a.description, a.gift, a.date, a.cash, e.employee_id, e.first_name, e.last_name, ap.awards_photo_id, ap.awards_photo, a.status "
			+ "FROM awards AS a " + "  JOIN awardsphoto AS ap ON a.awards_id = ap.fkawards_id "
			+ "  JOIN employee AS e ON e.employee_id = a.employee_id", nativeQuery = true)
	List<Map<String, Object>> AllEmployee();

	@Query(value = "SELECT a.awards_id, a.description, a.gift, a.date, a.cash, e.employee_id, e.first_name, e.last_name, ap.awards_photo_id, ap.awards_photo, a.status "
			+ "FROM awards AS a " + "JOIN awardsphoto AS ap ON a.awards_id = ap.fkawards_id "
			+ "JOIN employee AS e ON e.employee_id = a.employee_id "
			+ "WHERE e.employee_id = :employee_id", nativeQuery = true)
	List<Map<String, Object>> Allfilter(@Param("employee_id") long employee_id);

	@Query(value = "SELECT a.awards_id, a.description, a.gift, a.date, a.cash, e.employee_id, e.first_name, e.last_name,ap.awards_photo_id, ap.awards_photo, a.status "
			+ " FROM Awards a "
			+ " JOIN employee AS e ON e.employee_id = a.employee_id"
			+ " JOIN awardsphoto AS ap ON a.awards_id = ap.fkawards_id"
			+ " WHERE a.date = :awardDate ",nativeQuery = true)
    List<Awards> findAwardsByEmployeeIdAndDate(@Param("awardDate") java.util.Date parsedDate);

	@Query(value = "SELECT a.awards_id, a.description, a.gift, a.date, a.cash, e.employee_id, e.first_name, e.last_name, ap.awards_photo_id, ap.awards_photo, a.status, count_sub.awardsCount " +
	        "FROM awards AS a " +
	        "JOIN awardsphoto AS ap ON a.awards_id = ap.fkawards_id " +
	        "JOIN employee AS e ON e.employee_id = a.employee_id " +
	        "JOIN (" +
	        "   SELECT employee_id, COUNT(awards_id) as awardsCount " +
	        "   FROM awards " +
	        "   GROUP BY employee_id" +
	        ") count_sub ON count_sub.employee_id = e.employee_id",nativeQuery = true)
	List<Object[]> getEmployeeAwardsCount();

	List<Awards> findByEmployeeId(Long employeeId);


	


	

	
	 
}

