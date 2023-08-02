package com.example.hrm_new.repository.employee;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.employee.EmergencyContacts;

public interface EmergencyContactsRepository extends JpaRepository<EmergencyContacts, Long>{

	
	
	
	@Query(value = "select e.first_name ,e.last_name ,c.*,r.relation_type"
			+ " from contacts as c"
			+ " join employee as e on e.employee_id=c.employee_id"
			+ " join relationtype as r on r.relation_type_id=c.relation_type_id", nativeQuery = true)
	List<Map<String, Object>> getAllProjectWork();
	@Query(value = "select e.first_name ,e.last_name ,c.*,r.relation_type"
			+ " from contacts as c"
			+ " join employee as e on e.employee_id=c.employee_id"
			+ " join relationtype as r on r.relation_type_id=c.relation_type_id"
			+ "  where c.employee_id=:employee_id", nativeQuery = true)
	List<Map<String, Object>> Allemergencycontacts(@Param("employee_id")long employee_id);
}
