package com.example.hrm_new.repository.employee;

import java.util.*;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hrm_new.entity.employee.Qualifications;

public interface QualificationsRepository extends JpaRepository<Qualifications, Long>{
	
	 @Query(value =
		        "SELECT e.first_name, e.last_name, q.qualification_id, q.employee_id, q.twelve, q.ten, q.resume, q.bank_book, q.aadhar, q.highest_qualification, q.degree, q.panno, q.photo " +
		        "FROM qualifications AS q " +
		        "JOIN employee AS e ON e.employee_id = q.employee_id", nativeQuery = true)
	 List<Object[]> getAllQualificationsWithUrls();

}
