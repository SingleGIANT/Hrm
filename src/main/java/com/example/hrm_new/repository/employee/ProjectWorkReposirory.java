package com.example.hrm_new.repository.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.ProjectWork;

public interface ProjectWorkReposirory extends JpaRepository<ProjectWork, Long> {

	@Query(value = "select  p.completed,p.date,p.date_completed,p.description,p.duration,p.hold,p.hold_reson,p.on_process,p.pending,p.project_id,p.started,"
			+ " p.status,p.work,p.project_work_id,p.designation_id,p.department_id,p.employee_id,"
			+ "					 (select group_concat(d.designation_name) from designation d where find_in_set(d.designation_id, p.designation_id)) as designation_name,"
			+ "			          (select group_concat(de.department_name) from department de where find_in_set(de.department_id, p.department_id)) as department_name,"
			+ "			 (select group_concat(e.first_name) from employee e where find_in_set(e.employee_id, p.employee_id)) as first_name,"
			+ "				  (select group_concat(e.last_name) from employee e where find_in_set(e.employee_id, p.employee_id)) as last_name,"
			+ "			 (select pp.project_title from projectss pp where pp.project_id = p.project_id) as project_title"
			+ "								from projectworks as p;", nativeQuery = true)
	List<Map<String, Object>> getAllProjectWork();

	@Query(value = "select e.first_name ,e.last_name ,p.*,d.designation_name,pp.project_title"
			+ " from projectworks as p" + "        join designation as d on d.designation_id=p.designation_id"
			+ " join projectss as pp on pp.project_id=p.project_id"
			+ "		 join employee as e on e.employee_id=p.employee_id"
			+ " where p.employee_id=:employee_id", nativeQuery = true)
	List<Map<String, Object>> Allprojectwork(@Param("employee_id") Long employee_id);

	@Query(value = "select  p.*,"
			+ " (select group_concat(d.designation_name) from designation d where find_in_set(d.designation_id, p.designation_id)) as designation_name,"
			+ " (select group_concat(de.department_name) from department de where find_in_set(de.department_id, p.department_id)) as department_name,"
			+ "  (select group_concat(e.first_name) from employee e where find_in_set(e.employee_id, p.employee_id)) as first_name,"
			+ "  (select group_concat(e.last_name) from employee e where find_in_set(e.employee_id, p.employee_id)) as last_name,"
			+ " (select pp.project_title from projectss pp where pp.project_id = p.project_id) as project_title"
			+ "				from projectworks as p"
			+ "  WHERE p.date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query(value = "select e.first_name, e.last_name, p.*, d.designation_name ,pp.project_title ,count(p.project_work_id) as project_count"
			+ "            from projectworks as p"
			+ "            join designation as d on d.designation_id = p.designation_id"
			+ "   join projectss as pp on pp.project_id=p.project_id"
			+ "            join employee as e on e.employee_id = p.employee_id "
			+ "            where p.employee_id = :employee_id"
			+ "            group by e.first_name, e.last_name, p.project_work_id, d.designation_name;", nativeQuery = true)
	List<Map<String, Object>> getEmployeeProjectsDetails(@Param("employee_id") Long employee_id);

	@Query(value = "SELECT e.first_name, e.last_name, pp.project_title, COUNT(p.project_work_id) AS total_projectwork_count "
			+ "			 FROM projectworks AS p " + "		join projectss as pp on pp.project_id=p.project_id"
			+ "			 JOIN employee AS e ON e.employee_id = p.employee_id "
			+ "			 WHERE p.employee_id = :employeeId "
			+ "		 GROUP BY e.first_name, e.last_name, pp.project_title", nativeQuery = true)
	List<Map<String, Object>> getEmployeeProjectWorkDetails(@Param("employeeId") Long employeeId);

	@Query(value = "select e.first_name, e.last_name, count(p.project_work_id) as total_projectwork_count"
			+ "	from projectworks as p join employee as e on e.employee_id = p.employee_id "
			+ " join projectss as pp on pp.project_id=p.project_id"
			+ "	where p.employee_id = :employee_id", nativeQuery = true)
	List<Map<String, Object>> getEmployeeProjectWorkDetailsss(@Param("employee_id") Long employee_id);

	@Query(value = "     select p.*,"
			+ "    (select group_concat(d.designation_name) from designation d where find_in_set(d.designation_id, p.designation_id)) as designation_name,"
			+ "   (select group_concat(de.department_name) from department de where find_in_set(de.department_id, p.department_id)) as department_name,  "
			+ " (select group_concat(e.first_name) from employee e where find_in_set(e.employee_id, p.employee_id)) as first_name,"
			+ "    (select group_concat(e.last_name) from employee e where find_in_set(e.employee_id, p.employee_id)) as last_name,"
			+ "    (select pp.project_title from projectss pp where pp.project_id = p.project_id) as project_title"
			+ " from projectworks as p" + " join projectss as pp on pp.project_id = p.project_id"
			+ " where pp.project_title = :project_name", nativeQuery = true)
	List<Map<String, Object>> getAllProjectNamelll(@Param("project_name") String project_name);

	@Query(value = "select sum(completed) as completed,sum(hold) as hold, sum(on_process) as on_process, sum(started) as started "
			+ "  from projectworks;", nativeQuery = true)
	List<Integer> getAllProjectWorkleval();

	@Query(value = "select sum(completed) as completed,sum(hold) as hold, sum(on_process) as on_process, sum(started) as started "
			+ "  from projectworks;", nativeQuery = true)
	Map<String, Object> getAllProjectWorklevalMap();

//	List<ProjectWork> countByPendingTrue();
//	List<ProjectWork> countByCompletedTrue();
//	List<ProjectWork> countByOnProcessTrue();
//	List<ProjectWork> countByHoldTrue();
//	List<ProjectWork> countByStartedTrue();

	@Query(value="SELECT COUNT(p.pending) FROM ProjectWorks p WHERE p.pending = true",nativeQuery = true)
	Integer countPending();

	@Query(value="SELECT COUNT(p.completed) FROM ProjectWorks p WHERE p.completed = true",nativeQuery = true)
	Integer countCompleted();

	@Query(value="SELECT COUNT(p.on_process) FROM ProjectWorks p WHERE p.on_process = true",nativeQuery = true)
	Integer countOnProcess();

	@Query(value="SELECT COUNT(p.hold) FROM ProjectWorks p WHERE p.hold = true",nativeQuery = true)
	Integer countHold();

//	@Query(value="SELECT COUNT(p.started) FROM ProjectWorks p WHERE p.started = true",nativeQuery = true)
//	Integer countStarted();
}
