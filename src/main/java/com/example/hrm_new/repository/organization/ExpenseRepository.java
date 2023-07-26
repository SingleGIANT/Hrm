package com.example.hrm_new.repository.organization;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.organization.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	 @Query(value=
			  
			 " select e.*,et.expense_type "
			+ " from expense as e "
			+ " join expense_type as et on et.expense_type_id=e.expense_type_id "
			
				,nativeQuery = true)
	List<Map<String, Object>> allExpenseDetails();
	   
		@Query(value=
				 " select e.*,et.expense_type "
							+ " from expense as e "
							+ " join expense_type as et on et.expense_type_id=e.expense_type_id where e.expense_id=:expense_id"
				
				,nativeQuery = true)
		List <Map<String,Object>>  allDetailsOfExpense(@Param("expense_id")Long expense_id);
	
		@Query(value =
				" select e.*,c.company_name "
				+ " from expense as e "
				+ " join company as c on c.company_id=e.company_id where e.date=:date "
				, nativeQuery = true)
		List<Map<String, Object>> allExpenseDetailsByDate(LocalDate date);
		
		@Query(value =
				" select date,sum(amount)"
				+ " from expense where date=current_date()"
				+ " group by date "
			
				, nativeQuery = true)
		List<Map<String, Object>> dailyExpenseByCurrentDate();
		
		@Query(value =
				" select  e.*,month(date)"
				+ " from expense as e "
				
				, nativeQuery = true)
		List<Map<String, Object>> mothlyExpenseDetails();
		
		@Query(value =
				" select  month(date),sum(amount)"
				+ " from expense "
				+ " group by month(date)"
				+ " order by month(date) "
				
				, nativeQuery = true)
		List<Map<String, Object>> monthlyExpense();
		
		@Query(value =
				" select  e.*,year(date)"
				+ " from expense as e "
				
				, nativeQuery = true)
		List<Map<String, Object>> yearlyExpenseDetails();
	

		@Query(value =
				 " select year(date),sum(amount) "
				+ " from expense "
				+ " group by year(date) "
                + " order by  year(date) "
				
				, nativeQuery = true)
		List<Map<String, Object>> yearlyExpense();
		
	
}
