package com.example.hrm_new.repository.payroll;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.payroll.PayRoll;


public interface PayRollRepository extends JpaRepository<PayRoll , Long>{
	
	 @Query(value=
			  
			 " select pr.*,pt.payment_type "
			+ " from pay_roll as pr "
			+ " join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
			
				,nativeQuery = true)
	List<Map<String, Object>> allPayRollDetails();
	   
		@Query(value=
				 " select pr.*,pt.payment_type "
							+ " from pay_roll as pr "
							+ " join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
							+" where pr.pay_roll_id=:pay_roll_id "
				
				,nativeQuery = true)
		List <Map<String,Object>>  allDetailsOfPayRoll(@Param("pay_roll_id")Long pay_roll_id);
		
		@Query(value=
				 " select pr.*,pt.payment_type "
							+ " from pay_roll as pr "
							+ " join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
							+" where pr.employee_name_id=:employee_name_id "
				
				,nativeQuery = true)
		List <Map<String,Object>>  DetailsOfPayRollByEmployeeId(@Param("employee_name_id")Long employee_name_id);
		@Query(value=
				" select employee_name_id,date,current_salary "
		        + " from pay_roll where month(date)=:month"
		        + " order by  current_salary DESC "

				,nativeQuery = true)
		List <Map<String,Object>>  findByMonth(@Param("month")int month);
		
		
		
		@Query(value=
				" select pr.current_salary,pr.date,pr.employee_name_id "
				+ " from pay_roll as pr " 
				,nativeQuery = true)
		List <Map<String,Object>>  allDetailsOfPayRollByDate();
		
		
		@Query(value=
				" select  year(date),month(date) ,sum(current_salary) "
				+ " from pay_roll "
			    + " group by year(date),month(date) "

				,nativeQuery = true)
		List <Map<String,Object>>  totalSalaryByMonth();
		
		@Query(value=
				" select  year(date),month(date) ,sum(current_salary) "
				+ " from pay_roll where year(date)=:year and month(date)=:month "
			    + " group by year(date),month(date) "

				,nativeQuery = true)
		List <Map<String,Object>>  findByYearAndMonth(@Param("year")int year,@Param("month")int month);
		
	
		
		
}
