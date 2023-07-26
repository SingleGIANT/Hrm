package com.example.hrm_new.repository.worksheet;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.worksheet.WorkSheet;

public interface WorkSheetRepository extends JpaRepository<WorkSheet, Long>{

	List<WorkSheet> findByFromDateBetween(Date from, Date to);
	
	@Query(value = " SELECT * FROM work_sheet  WHERE YEAR(from_date) = :year  AND MONTH(from_date) = :month "

			, nativeQuery = true)
	List<Map<String, Object>> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

	
	 @Query(value=
			 "select w.* from work_sheet as w "
			 + " join project as p on p.project_id=w.project_id "	
				,nativeQuery = true)
	List<Map<String, Object>> allWorkSheetDetails();
	 
	 @Query(value=
			 " SELECT count(employee_name_id) as employeescount,project_id "
			 + " from work_sheet group by project_id "
				,nativeQuery = true)
	List<Map<String, Object>> employreeCount();
	 

		@Query(value =" select w.total_duration,w.project_id  from work_sheet as w ",
				nativeQuery = true)
		List<Map<String, Object>> findBytotalDuration();
		  
		 @Query(value=
				 " select w.* from work_sheet as w "
				 + " where project_title=:project_title "
					,nativeQuery = true)
		List<Map<String, Object>> findByProjectTitle(@Param("project_title")String project_title);
}
