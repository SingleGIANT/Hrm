package com.example.hrm_new.controller.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.admin.LoginRequest;
import com.example.hrm_new.entity.attendance.AttendanceList;
import com.example.hrm_new.entity.employee.ProjectWork;
import com.example.hrm_new.entity.organization.FinancialSummary;
import com.example.hrm_new.entity.organization.IncomeData;
import com.example.hrm_new.repository.attendance.AttendanceListRdepository;
import com.example.hrm_new.repository.attendance.AttendanceRepository;
import com.example.hrm_new.repository.employee.ComplaintsRepository;
import com.example.hrm_new.repository.employee.EmployeeExitRepository;
import com.example.hrm_new.repository.employee.EmployeeRepository;
import com.example.hrm_new.repository.employee.ProjectReportRepository;
import com.example.hrm_new.repository.employee.ProjectWorkReposirory;
import com.example.hrm_new.repository.employee.PromotionsRepository;
import com.example.hrm_new.repository.employee.ResignationsRepository;
import com.example.hrm_new.repository.employee.TerminationsRepository;
import com.example.hrm_new.repository.organization.ExpenseRepository;
import com.example.hrm_new.repository.payroll.PayRollRepository;
import com.example.hrm_new.repository.project.ProjectRepository;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	private EmployeeRepository ser;
	@Autowired 
	private ExpenseRepository exp;
	@Autowired
	private ProjectRepository ser1;
	@Autowired
	private AttendanceRepository ser2;
	@Autowired
	private PayRollRepository pay;
	@Autowired
	private TerminationsRepository repo;
	@Autowired
	private ResignationsRepository repo1;
	@Autowired
	private PromotionsRepository repo2;
	@Autowired
	private ProjectWorkReposirory repo3;
	@Autowired
	private ProjectReportRepository repo4;
	@Autowired
	private ComplaintsRepository repo5;

	@Autowired
	private EmployeeExitRepository exit;
	@Autowired
	private AttendanceListRdepository ser0;

	@Value("${app.email}")
	private String email;

	@Value("${app.password}")
	private String password;

	@PostMapping("/admin/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		System.out.println("email from properties file: " + email);
		System.out.println("password from properties file: " + password);
		System.out.println("email from login request: " + loginRequest.getEmail());
		System.out.println("password from login request: " + loginRequest.getPassword());

		if (loginRequest != null && loginRequest.getEmail() != null && loginRequest.getEmail().equals(email)
				&& loginRequest.getPassword().equals(password)) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("token",
					"2wCEAAkGBwgHBgkIBwgKCZCSAcgkLDRYPDQwMDRsUFRAWIB0iIiAdHx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys");
			responseData.put("email", email);
			responseData.put("login_status", "success");
			return ResponseEntity.ok(responseData);
		} else {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Email and password are incorrect");
			errorResponse.put("login_status", "Failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}

	@PostMapping("/count")
	public Map<String, List<Map<String, Object>>> getAllDataBetweenDates(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();

		resultMap.put("Terminations", repo.getAllpromotionsBetweenDates(startDate, endDate));
		resultMap.put("Resignations", repo1.getAllReceiptBetweenDate(startDate, endDate));
		resultMap.put("Promotions", repo2.getAllpromotionsBetweenDates(startDate, endDate));
		resultMap.put("ProjectWork", repo3.getAllpromotionsBetweenDates(startDate, endDate));
		resultMap.put("ProjectReport", repo4.getAllpromotionsBetweenDates(startDate, endDate));
		resultMap.put("Complaints", repo5.getAllpromotionsBetweenDates(startDate, endDate));

		return resultMap;
	}

//	  @GetMapping("/dashboard")
//		public Map<String, Object> getAllDetails() {
//			Map<String, Object> productMap = new HashMap<>();
//			productMap.put("employee", ser.count());
//			productMap.put("project", ser1.count());
//			productMap.put("Attendance", ser2.getAllpresent());
//			return productMap;
//
//		}
//	@GetMapping("/dashboard")
//	public Map<String, Object> getAllDetails() {
//		Map<String, Object> productMap = new HashMap<>();
//
//		// Get attendance data
//		List<Map<String, Object>> attendanceData = ser2.getAllpresent();
//		List<Map<String, Object>> attendanceData2 = exp.dailyExpenseByCurrentDate1();
//		
//		if (!attendanceData.isEmpty()) {
//			productMap.put("present_count", attendanceData.get(0).get("present_count"));
//			productMap.put("absent_count", attendanceData.get(0).get("absent_count"));
//		} else {
//			productMap.put("present_count", null);
//			productMap.put("absent_count", null);
//		}
//
//		// Get other metrics
//		productMap.put("project", ser1.count());
//		productMap.put("employee", ser.count());
//
//		return productMap;
//	}
	
	@GetMapping("/dashboard")
	public Map<String, Object> getAllDetails() {
	    Map<String, Object> productMap = new HashMap<>();

	   
	    List<Map<String, Object>> attendanceData = ser2.getAllpresent();
	    
	    if (!attendanceData.isEmpty()) {
	        productMap.put("present_count", attendanceData.get(0).get("present_count"));
	        productMap.put("absent_count", attendanceData.get(0).get("absent_count"));
	    } else {
	        productMap.put("present_count", null);
	        productMap.put("absent_count", null);
	    }

	 
	    List<Map<String, Object>> dailyExpenseData = exp.dailyExpenseByCurrentDate1();
	    
	    if (!dailyExpenseData.isEmpty()) {

	        productMap.put("expensetotal", dailyExpenseData.get(0).get("sum(amount)"));
	    } else {
	        productMap.put("daily_expense_total", null);
	    }
	    List<Map<String, Object>> payroll = pay.totalSalaryByMonth3();

	    if (!payroll.isEmpty()) {

	        productMap.put("monthsalary", payroll.get(0).get("monthsalary"));
	    } else {
	        productMap.put("monthsalary", null);
	    }  
	    productMap.put("project", ser1.count());
	    productMap.put("employee", ser.count());
	    productMap.put("Terminations", repo.count());
	    productMap.put("employeeexit", exit.count());

	    return productMap;
	}


	@GetMapping("/filter")
	public List<Map<String, Object>> totalSalaryByMonth() {
		return pay.findByIncome();
	}
	
	@GetMapping("/filter1")
	public List<Map<String, Object>> totalSalaryByMonth1() {
		return pay.findByExpense();
	}
	
	@GetMapping("/birthday")
	public List<Map<String, Object>> totalSalaryByMonth6() {
		return ser.AllEmployee5();
	}
	
//	  @GetMapping("/filter")
//	    public Map<String, List<Double>> getFinancialSummary() {
//	        List<Double> income = pay.findByIncome().stream()
//	                .map(IncomeData::getAmount)
//	                .collect(Collectors.toList());
//
////	        List<Double> expense = pay.findByExpense().stream()
////	                .map(ExpenseData::getAmount)
////	                .collect(Collectors.toList());
//
//	        Map<String, List<Double>> financialSummary = new HashMap<>();
//	        financialSummary.put("income", income);
////	        financialSummary.put("expense", expense);
//
//	        return financialSummary;
//	    }
	



	@GetMapping("/project/level")
	public List<Integer> getProjectWorkLevelCounts() {
		List<Integer> levelCounts = new ArrayList<>();

		Integer onProcessCount = repo3.countOnProcess();
//		Integer startedCount = repo3.countStarted();
		Integer completedCount = repo3.countCompleted();
		Integer holdCount = repo3.countHold();
		Integer pendingCount = repo3.countPending();

		levelCounts.add(onProcessCount);
//		levelCounts.add(startedCount);
		levelCounts.add(completedCount);
		levelCounts.add(holdCount);
		levelCounts.add(pendingCount);

		return levelCounts;
	}

	@GetMapping("/project/level1")
	public Map<String, Object> getAllProjectWorklevalMap() {
		Map<String, Object> totalMap = new HashMap<>();
		Map<String, Object> detailMap = repo3.getAllProjectWorklevalMap();
		totalMap.put("on_process", detailMap.get("on_process"));
		totalMap.put("started", detailMap.get("started"));
		totalMap.put("completed", detailMap.get("completed"));
		totalMap.put("hold", detailMap.get("hold"));
		return totalMap;
	}
}
