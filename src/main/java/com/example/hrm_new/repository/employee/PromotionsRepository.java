package com.example.hrm_new.repository.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.Promotions;

public interface PromotionsRepository extends JpaRepository<Promotions, Long> {

		@Query(value = "select e.first_name ,e.last_name ,p.* ,r.role_name"
				+ " from promotions as p"
				 + "	     join role as r on r.role_id=p.role_id"
				+ " join employee as e on e.employee_id=p.employee_id;",nativeQuery = true)
	List<Map<String,Object>>GoodAllWorks();


		@Query(value = "select e.first_name ,e.last_name ,p.*,r.role_name"
				+ " from promotions as p"
				+ " join employee as e on e.employee_id=p.employee_id"
				 + "	     join role as r on r.role_id=p.role_id"
				+ " where p.employee_id=:employee_id",nativeQuery = true)
		List<Map<String, Object>> Allpromotions(@Param ("employee_id")long  employee_id);
		
		@Query(value = "SELECT e.first_name, e.last_name, p.*,r.role_name "
				+ "FROM promotions AS p "
				+ " JOIN employee AS e ON e.employee_id = p.employee_id "
				 + "	     join role as r on r.role_id=p.role_id"
				+ " WHERE p.date BETWEEN :startDate AND :endDate", nativeQuery = true)
List<Map<String, Object>> getAllpromotionsBetweenDates(  @Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
}
