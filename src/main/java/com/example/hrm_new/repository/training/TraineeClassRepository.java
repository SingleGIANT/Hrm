package com.example.hrm_new.repository.training;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.entity.training.TraineeClass;

public interface TraineeClassRepository extends JpaRepository<TraineeClass , Long>{
	@Query(value=
			  
			" select tc.*,td.name "
			+ " from trainee_class as tc "
			+ " join trainee_details as td on td.trainee_details_id=tc.trainee_details_id "
			
				,nativeQuery = true)
	List<Map<String, Object>> allTraineeClassDetails();
	   
		@Query(value=
				" select tc.*,td.name "
						+ " from trainee_class as tc "
						+ " join trainee_details as td on td.trainee_details_id=tc.trainee_details_id  where tc.trainee_class_id=:trainee_class_id"
				,nativeQuery = true)
		List <Map<String,Object>>  allDetailsOfTraineeClass(@Param("trainee_class_id")Long trainee_class_id);
		
		List<TraineeClass> findByStartDateBetween(Date from, Date to);
	
	

}
