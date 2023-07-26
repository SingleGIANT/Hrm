package com.example.hrm_new.repository.training;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.training.TraineeDetails;

public interface TraineeDetailsRepository extends JpaRepository<TraineeDetails , Long>{
	
	@Query(value =" select count(trainee_details_id),status from trainee_details "
			+ " where status=:status ",
			nativeQuery = true)
	List<Map<String, Object>> findByStatus(@Param ("status")boolean status);
	 
//	@Query(value = " select * from trainee_details where country=:country and  state=:state ",
//			nativeQuery = true)
	List<TraineeDetails>findByCountryAndState(@Param("country")String country,@Param("state")String state);

}
