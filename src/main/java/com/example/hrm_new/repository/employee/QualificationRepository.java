package com.example.hrm_new.repository.employee;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hrm_new.entity.employee.Qualification;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {

	@Query(value = "select q.qualification_id, q.employee_id, q.highest_qualification,"
			+ "		 q.photourl, q.resumeurl, q.tenurl, q.aadharurl,"
			+ "		 q.degreeurl, q.pannourl, q.twelveurl,q.aadharno,q.pan_card,q.status,"
			+ "		 e.first_name, e.last_name"
			+ "			 from qualification as q"
			+ "		join employee as e on e.employee_id = q.employee_id", nativeQuery = true)
	List<Map<String, Object>> getAllQualificationsByImage();

}
