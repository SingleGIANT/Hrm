package com.example.hrm_new.repository.customer;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.customer.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	
	@Query(value=
			  "  select c.*,f.form_type_name "
			  + "			   from customer as c"
			  + "			 join formtype as f on f.form_type_id=c.form_type_id"
			  + "  order by c.customer_id desc",nativeQuery = true)
	List <Map<String,Object>>  allDetails();
	
	@Query(value = " select c.*, f.form_type_name "
            + " from customer as c "
            + "  join formtype as f on f.form_type_id = c.form_type_id "
            + " where c.customer_id = :customer_id", nativeQuery = true)
List<Map<String, Object>> Allemployeeleave(@Param("customer_id")long customer_id);



}
