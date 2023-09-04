package com.example.hrm_new.repository.payroll;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.organization.IncomeData;
import com.example.hrm_new.entity.payroll.PayRoll;

public interface PayRollRepository extends JpaRepository<PayRoll, Long> {

	@Query(value =

	" select pr.*,pt.payment_type ,e.first_name,e.last_name,e.phone_number,e.bank_name,e.account_number"
			+ " from pay_roll as pr " + " join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
			+ " join employee as e on e.employee_id=pr.employee_id", nativeQuery = true)
	List<Map<String, Object>> allPayRollDetails();
	
	

	@Query(value =

	" select pr.*,pt.payment_type ,e.first_name,e.last_name,e.phone_number,e.bank_name,e.account_number"
	+ "			 from pay_roll as pr  join payment_type as pt on pt.payment_type_id=pr.payment_type_id"
	+ "			 join employee as e on e.employee_id=pr.employee_id"
	+ "              where MONTH(pr.date) = MONTH(now())"
	+ "       and YEAR(pr.date) = YEAR(now());", nativeQuery = true)
	List<Map<String, Object>> allPayRollDetails1();

	@Query(value = " select pr.*,pt.payment_type " + " from pay_roll as pr "
			+ " join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
			+ " where pr.pay_roll_id=:pay_roll_id "

			, nativeQuery = true)
	List<Map<String, Object>> allDetailsOfPayRoll(@Param("pay_roll_id") Long pay_roll_id);

	@Query(value = " select pr.*,pt.payment_type " + " from pay_roll as pr "
			+ " join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
			+ " where pr.employee_id=:employee_id "

			, nativeQuery = true)
	List<Map<String, Object>> DetailsOfPayRollByEmployeeId(@Param("employee_id") Long employee_id);

	@Query(value = " select employee_id,date,current_salary " + " from pay_roll where month(date)=:month"
			+ " order by  current_salary DESC "

			, nativeQuery = true)
	List<Map<String, Object>> findByMonth(@Param("month") int month);

//	@Query(value = " WITH MonthlyData AS ("
//			+ "    SELECT"
//			+ "        year,"
//			+ "        month,"
//			+ "        SUM(current_month_salary) AS total_current_month_salary,"
//			+ "        SUM(expense_amount) AS total_expense_amount,"
//			+ "        SUM(current_month_salary + expense_amount) AS Total,"
//			+ "        (SUM(current_month_salary + expense_amount) * 100.0) /"
//			+ "            (SELECT SUM(current_month_salary + expense_amount) FROM ("
//			+ "                SELECT"
//			+ "                    YEAR(date) AS year,"
//			+ "                    MONTH(date) AS month,"
//			+ "                    SUM(current_salary) AS current_month_salary,"
//			+ "                    0 AS expense_amount"
//			+ "                FROM pay_roll"
//			+ "                GROUP BY YEAR(date), MONTH(date)"
//			+ "                UNION ALL"
//			+ "                SELECT"
//			+ "                    YEAR(date) AS year,"
//			+ "                    MONTH(date) AS month,"
//			+ "                    0 AS current_month_salary,"
//			+ "                    SUM(amount) AS expense_amount"
//			+ "                FROM expense"
//			+ "                GROUP BY YEAR(date), MONTH(date)"
//			+ "            ) AS subquery) AS percentage_of_orders"
//			+ "    FROM ("
//			+ "        SELECT"
//			+ "            YEAR(date) AS year,"
//			+ "            MONTH(date) AS month,"
//			+ "            SUM(current_salary) AS current_month_salary,"
//			+ "            0 AS expense_amount"
//			+ "        FROM pay_roll"
//			+ "        GROUP BY YEAR(date), MONTH(date)"
//			+ "        UNION ALL"
//			+ "        SELECT"
//			+ "            YEAR(date) AS year,"
//			+ "            MONTH(date) AS month,"
//			+ "            0 AS current_month_salary,"
//			+ "            SUM(amount) AS expense_amount"
//			+ "        FROM expense"
//			+ "        GROUP BY YEAR(date), MONTH(date)"
//			+ "    ) AS combined_data"
//			+ "    GROUP BY year, month"
//			+ "),"
//			+ "ProjectData AS ("
//			+ "    SELECT"
//			+ "        YEAR(from_date) AS year,"
//			+ "        MONTH(from_date) AS month,"
//			+ "        SUM(total_project_amount) AS currentmonthproject"
//			+ "    FROM projectss"
//			+ "    WHERE MONTH(from_date) = MONTH(NOW()) AND YEAR(from_date) = YEAR(NOW())"
//			+ "    GROUP BY YEAR(from_date), MONTH(from_date)"
//			+ " )"			
//			+ "  SELECT"
//			+ "    MD.year,"
//			+ "    MD.month,"
//			+ "    MD.total_current_month_salary,"
//			+ "    MD.total_expense_amount,"
//			+ "    MD.Total,"
//			+ "    MD.percentage_of_orders,"
//			+ "    PD.currentmonthproject"
//			+ "   FROM MonthlyData MD"
//			+ " LEFT JOIN ProjectData PD"
//			+ "    ON MD.year = PD.year AND MD.month = PD.month"
//			+ "  ORDER BY MD.year, MD.month;", nativeQuery = true)
//	List<Map<String, Object>> findBy();

	
	
      @Query(value="                       SELECT YEAR(p.from_date) AS year,"
      		+ "       MONTH(p.from_date) AS month,"
      		+ "       SUM(p.total_project_amount) AS total_project_amount"
      		+ " FROM projectss AS p"
      		+ " WHERE YEAR(p.from_date) = YEAR(CURRENT_DATE())"
      		+ " GROUP BY YEAR(p.from_date), MONTH(p.from_date);;", nativeQuery = true) 
	    List<Map<String, Object>> findByIncome();

      @Query(value="    WITH MonthlyData AS ("
      		+ "    SELECT"
      		+ "        YEAR(date) AS year,"
      		+ "        MONTH(date) AS month,"
      		+ "        SUM(total_salary) AS total_salary,"
      		+ "        0 AS total_expense"
      		+ "    FROM pay_roll"
      		+ "    WHERE YEAR(date) = YEAR(CURRENT_DATE())"
      		+ "    GROUP BY YEAR(date), MONTH(date)"
      		+ "    UNION ALL"
      		+ "    SELECT"
      		+ "        YEAR(date) AS year,"
      		+ "        MONTH(date) AS month,"
      		+ "        0 AS total_salary,"
      		+ "        SUM(amount) AS total_expense"
      		+ "    FROM expense"
      		+ "    WHERE YEAR(date) = YEAR(CURRENT_DATE())"
      		+ "    GROUP BY YEAR(date), MONTH(date)"
      		+ " )"
      		+ " SELECT"
      		+ "    year,"
      		+ "    month,"
      		+ "    SUM(total_expense + total_salary) AS expense"
      		+ " FROM MonthlyData"
      		+ " GROUP BY year, month"
      		+ " ORDER BY year, month;;", nativeQuery = true) 
	    List<Map<String, Object>> findByExpense();
	@Query(value = "select pr.current_salary,pr.date,e.* " + "	from pay_roll as pr"
			+ " join  employee as e on e.employee_id=pr.employee_id"
			+ " where month(pr.date) = month(current_date()) and"
			+ " year(pr.date) = year(current_date())", nativeQuery = true)
	List<Map<String, Object>> allDetailsOfPayRollByDate();

	@Query(value = " "
	        + " SELECT pr.current_salary, pr.date, e.first_name, e.company_name, d.designation_name, dt.department_name, pr.pay_roll_id, e.last_name"
	        + " FROM pay_roll AS pr"
	        + " JOIN employee AS e ON e.employee_id = pr.employee_id"
	        + " JOIN designation AS d ON d.designation_id = e.designation_id"
	        + " JOIN department AS dt ON dt.department_id = e.department_id"
	        + " WHERE MONTHNAME(pr.date) = :monthname AND YEAR(pr.date) = :year"
	        + " GROUP BY pr.current_salary, pr.date, e.first_name, e.company_name, d.designation_name, dt.department_name, pr.pay_roll_id, e.last_name", nativeQuery = true)
	List<Map<String, Object>> allDetailsOfPayRollByMonthAndYear(@Param("year") int year, @Param("monthname") String monthname);

	@Query(value = " select  year(date),month(date) ,sum(current_salary) " + " from pay_roll "
			+ " group by year(date),month(date) ", nativeQuery = true)
	List<Map<String, Object>> totalSalaryByMonth();
	
	@Query(value = "SELECT SUM(current_salary) AS monthsalary"
			+ " FROM pay_roll"
			+ " WHERE YEAR(date) = YEAR(CURRENT_DATE()) AND MONTH(date) = MONTH(CURRENT_DATE());", nativeQuery = true)
	List<Map<String, Object>> totalSalaryByMonth3();

	@Query(value = " select  year(date),month(date) ,sum(current_salary) "
			+ " from pay_roll where year(date)=:year and MONTHNAME(date)=:monthname "
			+ " group by year(date),month(date) ", nativeQuery = true)
	List<Map<String, Object>> findByYearAndMonth(@Param("year") int year, @Param("monthname") String monthname);



	@Query(value = "select pr.*,pt.payment_type ,e.first_name,e.last_name,e.phone_number,e.bank_name,e.account_number"
			+ "		 from pay_roll as pr "
			+ "         join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
			+ "		 join employee as e on e.employee_id=pr.employee_id"
			+ "         where pr.date between :startDate and :endDate ", nativeQuery = true)
	List<Map<String, Object>> getAllpromotionsBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);


	@Query(value = "select pr.*,pt.payment_type ,e.first_name,e.last_name,e.phone_number,e.bank_name,e.account_number"
			+ "		 from pay_roll as pr "
			+ "         join payment_type as pt on pt.payment_type_id=pr.payment_type_id "
			+ "		 join employee as e on e.employee_id=pr.employee_id"
			+ "         where pr.date = :startDate ", nativeQuery = true)
	List<Map<String, Object>> getAllpromotionsBetweenDates4(@Param("startDate") LocalDate startDate);



}
