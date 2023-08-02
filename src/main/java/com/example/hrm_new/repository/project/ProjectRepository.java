package com.example.hrm_new.repository.project;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.project.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByFromDateBetween(Date from, Date to);

	@Query(value = " SELECT * FROM project  " + "WHERE YEAR(from_date) = :year " + " AND MONTH(from_date) = :month "

			, nativeQuery = true)
	List<Map<String, Object>> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

	@Query(value = " select count(project_id) as total_projects,count(status) as incompleted_projects "
			+ " from  project ", nativeQuery = true)
	Map<String, Object> findByCountOfProjects();
	
	@Query(value = "select p.client_name, count(p.project_id) " + "from project as p "
			+ "group by p.client_name", nativeQuery = true)
	List<Object[]> getClientProjectCounts();
	

	@Query(value =" select p.total_duration,p.project_id  from project as p "
			 + " group by project_id ",
			nativeQuery = true)
	List<Map<String, Object>> findBytotalDuration();
	
	@Query(value = "select p.*,c.name"
			+ " from project as p"
			+ " join customer as c on c.customer_id=p.customer_id;", nativeQuery = true)
	List<Map<String, Object>> getAllProject();


}
