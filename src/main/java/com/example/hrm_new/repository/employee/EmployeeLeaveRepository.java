package com.example.hrm_new.repository.employee;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.EmployeeLeave;

public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Long>{
	
	
	
	@Query(value = "select e.first_name ,e.last_name ,l.*,lt.leave_type"
			+ " from employeeleave as l"
			+ " join employee as e on e.employee_id=l.employee_id "
			+ " join leavetype as lt on lt.leave_type_id=l.leave_type_id", nativeQuery = true)
	List<Map<String, Object>> getAllProjectWork();
	
	@Query(value = "select e.first_name ,e.last_name ,l.*,lt.leave_type"
			+ " from employeeleave as l"
			+ " join employee as e on e.employee_id=l.employee_id "
			+ " join leavetype as lt on lt.leave_type_id=l.leave_type_id"
			+ " where l.employee_id=:employee_id", nativeQuery = true)
	List<Map<String, Object>> Allemployeeleave(@Param("employee_id") Long employee_id);

	
	@Query(value = "SELECT e.first_name, el.employee_id, lt.leave_type, sum(el.total_day) AS leave_count"
			+ " FROM employeeleave AS el"
			+ " JOIN employee AS e ON e.employee_id = el.employee_id "
			+ " JOIN leavetype AS lt ON lt.leave_type_id = el.leave_type_id"
			+ " WHERE el.employee_id = :employee_id AND el.date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) AND el.date <= CURRENT_DATE"
			+ " GROUP BY e.first_name, el.employee_id, lt.leave_type;", nativeQuery = true)
	List<Map<String, Object>> getAllProject(@Param("employee_id") long employee_id);
	
}
