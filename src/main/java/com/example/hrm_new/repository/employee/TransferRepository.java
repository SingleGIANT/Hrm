package com.example.hrm_new.repository.employee;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.hrm_new.entity.employee.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

	@Query(value = " select e.first_name ,t.*" + " from transfer as t"			
			+ " join employee as e on e.employee_id=t.employee_id;", nativeQuery = true)
	List<Map<String, Object>> AllTravel();

	@Query(value = "  select e.first_name  ,t.*"
			+ "			   from transfer as t" 				
			+ "		 join employee as e on e.employee_id=t.employee_id"
			+ "         where t.employee_id=:employee_id ", nativeQuery = true)
	List<Map<String, Object>> allDetailsOfAnnouncement(@Param("employee_id") Long employee_id);

//	List<Transfer> findByDate(Date date);
	@Query(value = "select e.first_name, t.* "
			+ "from transfer as t "
			+ "join employee as e on e.employee_id = t.employee_id "
			+ "WHERE t.date BETWEEN :startDate AND :endDate", nativeQuery = true)
List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	@Query(value = "select year(date) as transfer_year, count(*) as transfer_count"
			+ " from transfer"
			+ " group by transfer_year"
			+ " order by transfer_year desc;", nativeQuery = true)
	List<Map<String, Object>>getEmployeeTransferCountByYear();

}
