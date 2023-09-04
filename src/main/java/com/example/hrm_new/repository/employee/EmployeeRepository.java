package com.example.hrm_new.repository.employee;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.hrm_new.entity.employee.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	

	   @Query(value=
	     " select e.*,r.role_name,d.designation_name"
	     + "	      from employee as e"
	     + "	     join role as r on r.role_id=e.role_id"
	     + "	      join designation as d on d.designation_id=e.designation_id"
	     + "         join department as dd on dd.department_id= e.department_id",nativeQuery = true)
		List <Map<String,Object>>  AllEmployee();

	   @Query(value=
			     " select "
			     + "    e.employee_id,"
			     + "    e.last_name,"
			     + "    e.first_name,"
			     + "    date_add(e.dob, interval year(now()) - year(e.dob) year) as next_birthday,"
			     + "    case"
			     + "        when date_add(e.dob, interval year(now()) - year(e.dob) year) = date(now()) then 'today is their birthday!'"
			     + "        when date_add(e.dob, interval year(now()) - year(e.dob) year) = date(now() + interval 1 day) then 'tomorrow is their birthday'"
			     + "        when date_add(e.dob, interval year(now()) - year(e.dob) year) = date(now() + interval 2 day) then 'day after tomorrow is their birthday'"
			     + "        else 'no special message'"
			     + "    end as birthday_message"
			     + " from employee as e"
			     + " where date_add(e.dob, interval year(now()) - year(e.dob) year) between date(now()) and date(now() + interval 2 day)"
			     + " order by next_birthday asc;",nativeQuery = true)
				List <Map<String,Object>>  AllEmployee5();
	   
	   @Query(value=
			     " select e.*, r.role_name, d.designation_name"
			     + " from employee as e"
			     + " join role as r on r.role_id = e.role_id"
			     + " join designation as d on d.designation_id = e.designation_id"
			     + " where e.status = true;"
			     + "",nativeQuery = true)
				List <Map<String,Object>>  AllEmployees();
//		@Query(value = "select e from employee e where e.contact_no1=?1")
//		Employee checkEmployeeContactNumber1(long contact_no1);
//		
//		@Query(value = "select e from employee e where e.email=?1")
//		Employee checkCustomerByEmail(String email);
//		
//		@Query(value = "select e.password from employee e where e.email=?1")
//		String getEmployeePasswordByEmail(String email);
//		
//		@Query(value = "select e.valid from employee e where e.email=?1 and e.password=?2")
//		boolean findStatusEmployee(String email, String password);
//		
//		@Modifying(clearAutomatically = true)
//		@Query("update employee e set e.password =:password where e.email =:email")
//		void changePassword(@Param("email") String email, @Param("password") String password);
//		
//		@Query(value = "select e.id from employee e where e.email=?1")
//		Long findEmployeeId(String email);
//		
//		 Employee checkEmployeeByEmail(String email);
	
		
//		@Modifying(clearAutomatically = true)
//		@Query("update employee e set c.name =:name,c.address =:address,c.gender = :gender,c.phone =:phone,c.pinCode =:pinCode where c.id =:id")
//		void updateMyCustomer(@Param("id") Long id,@Param("name") String name, @Param("address") String address,@Param("gender") String gender, 
//				@Param("phone") String phone, @Param("pinCode") String pinCode);

//		Employee findByEmail(String email);

}
