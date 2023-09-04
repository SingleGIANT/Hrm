package com.example.hrm_new.repository.attendance;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.attendance.Attendance;
import com.example.hrm_new.entity.attendance.AttendanceList;





public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	@Query(value = "select a.*,al.*,e.first_name,e.last_name,designation_name"
			+ "			 from attendance as a"
			+ "					 join attendancelist as al on al.fk_attendance_id = a.attendance_id"
			+ "			        join employee as e on e.employee_id=al.employee_id"
			+ "                    join designation d on d.designation_id=e.designation_id"
			+ "	;", nativeQuery = true)
	List<Map<String, Object>> getAllMemberDetails();

	@Query(value = " select month(a.attendance_date) as month, year(attendance_date) as year,sum(al.present) as present_count,sum(al.absent) as absent_count,sum(al.full_day) as full_day,sum(al.half_day) as half_day"
			+ "				from attendance as a"
			+ "                join attendancelist as al on al.fk_attendance_id=a.attendance_id"
			+ "			 join employee as e on e.employee_id=al.employee_id"
			+ "				where month(a.attendance_date) = month(curdate()) and year(a.attendance_date) = year(current_date()) and e.employee_id=:employeeid"
			+ "				group by  month(a.attendance_date) , year(attendance_date)", nativeQuery = true)
	List<Map<String, Object>> getAllMemberDetailsByMemberId(@Param("employeeid") Long employeeid);

	@Query(value = " select sum(al.present) as present_count, sum(al.absent) as absent_count,current_date() as today_date from attendance as a"
			+ "  join attendancelist as al on al.fk_attendance_id= a.attendance_id"
			+ "	 where a.attendance_date = current_date()", nativeQuery = true)
	List<Map<String, Object>> getAllMemberDetailsByMemberByDate();

	Optional<Attendance> findByAttendanceDate(Date attendanceDate);
	
	
	@Query(value = "select a.*,al.*,e.first_name,e.last_name,designation_name"
			+ "	 from attendance as a"
			+ "		 join attendancelist as al on al.fk_attendance_id = a.attendance_id"
			+ "		  join employee as e on e.employee_id=al.employee_id"
			+ "  join designation d on d.designation_id=e.designation_id"
			+ "     where a.attendance_date =current_date();", nativeQuery = true)
	List<Map<String, Object>> getAllemployeeDetails();
	
	@Query(value = "select a.*, al.*, e.first_name, e.last_name, d.designation_name,de.department_name"
			+ " from attendance as a"
			+ " join attendancelist as al on al.fk_attendance_id = a.attendance_id"
			+ " join employee as e on e.employee_id = al.employee_id"
			+ " join designation as d on d.designation_id = e.designation_id "
			+ " JOIN department AS de ON de.department_id = e.department_id"
			+ " where date(a.attendance_date) = curdate() and al.absent = true;", nativeQuery = true)
	List<Map<String, Object>> getAllemployeeDetails2();

	
	@Query(value = "SELECT"
			+ "    p.employee_id,"
			+ "    e.first_name,"
			+ "    e.last_name,"
			+ "    d.designation_name,"
			+ "    de.department_name,"
			+ "    IFNULL(p.present_count, 0) AS present_count,"
			+ "    IFNULL(a.absent_count, 0) AS absent_count"
			+ "  FROM"
			+ "    employee AS e"
			+ "    JOIN designation AS d ON d.designation_id = e.designation_id"
			+ "    JOIN department AS de ON de.department_id = e.department_id"
			+ "    LEFT JOIN ("
			+ "        SELECT"
			+ "            al.employee_id,"
			+ "            COUNT(al.present) AS present_count"
			+ "        FROM"
			+ "            attendance AS a"
			+ "            JOIN attendancelist AS al ON al.fk_attendance_id = a.attendance_id"
			+ "        WHERE"
			+ "            YEAR(a.attendance_date) = YEAR(CURDATE())"
			+ "            AND MONTH(a.attendance_date) = MONTH(CURDATE())"
			+ "            AND al.present = true"
			+ "        GROUP BY"
			+ "            al.employee_id"
			+ "    ) AS p ON e.employee_id = p.employee_id"
			+ "    LEFT JOIN ("
			+ "        SELECT"
			+ "            al.employee_id,"
			+ "            COUNT(al.absent) AS absent_count"
			+ "        FROM\r\n"
			+ "            attendance AS a"
			+ "            JOIN attendancelist AS al ON al.fk_attendance_id = a.attendance_id"
			+ "        WHERE\r\n"
			+ "            YEAR(a.attendance_date) = YEAR(CURDATE())"
			+ "            AND MONTH(a.attendance_date) = MONTH(CURDATE())"
			+ "            AND al.absent = true"
			+ "        GROUP BY"
			+ "            al.employee_id"
			+ "    ) AS a ON e.employee_id = a.employee_id;"
			, nativeQuery = true)
	List<Map<String, Object>> getAllemployeeDetails5();

	@Query(value = "   select sum(al.present) as present_count , sum(al.absent) as absent_count from attendance as a"
			+ "		  join attendancelist as al on al.fk_attendance_id= a.attendance_id"
			+ "			 where a.attendance_date = current_date()", nativeQuery = true)
	List<Map<String, Object>> getAllpresent();
	
	@Query(value = "   select a.*,al.*,e.first_name,e.last_name,designation_name"
			+ "			 from attendance as a"
			+ "				 join attendancelist as al on al.fk_attendance_id = a.attendance_id"
			+ "			  join employee as e on e.employee_id=al.employee_id"
			+ "		 join designation d on d.designation_id=e.designation_id"
			+ "		    where a.attendance_date = :startDate ", nativeQuery = true)
	List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate);

}
