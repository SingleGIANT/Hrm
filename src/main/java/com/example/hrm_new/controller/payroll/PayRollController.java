package com.example.hrm_new.controller.payroll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.hrm_new.entity.payroll.PayRoll;
import com.example.hrm_new.entity.payroll.SalaryRequest;
import com.example.hrm_new.repository.payroll.PayRollRepository;
import com.example.hrm_new.service.payroll.PayRollService;

@RestController
@CrossOrigin
public class PayRollController {

	@Autowired
	private PayRollService payRollService;

	@Autowired
	private PayRollRepository repo;

	@GetMapping("/payroll")
	public ResponseEntity<?> getDetails() {

		try {

			Iterable<PayRoll> payRollDetails = payRollService.listAll();

			return new ResponseEntity<>(payRollDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/payroll/save")
	public ResponseEntity<?> savePayRoll(@RequestBody PayRoll payRoll) {
		try {

			long totalSalary = payRoll.getTotalSalary();
			long totalDeductions = payRoll.getTotalDeductions();
			long allowance = payRoll.getAllowance();
			long currentSalary = totalSalary - totalDeductions + allowance;
			payRoll.setCurrentSalary(currentSalary);
			payRoll.setStatus(true);

			payRollService.SaveorUpdate(payRoll);
			return ResponseEntity.status(HttpStatus.CREATED).body("Payroll details saved successfully.");
		} catch (Exception e) {
			String errorMessage = "An error occurred while saving payroll details.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@RequestMapping("/payroll/{payRollId}")

	private Optional<PayRoll> getPayRoll(@PathVariable(name = "payRollId") long payRollId) {

		return payRollService.getPayRolltById(payRollId);

	}

	@PutMapping("/payroll/or/{payRollId}")
	public ResponseEntity<Boolean> toggleCustomerStatus(@PathVariable(name = "payRollId") long payRollId) {
		try {
			PayRoll payRoll = payRollService.findById(payRollId);
			if (payRoll != null) {

				boolean currentStatus = payRoll.isStatus();
				payRoll.setStatus(!currentStatus);
				payRollService.SaveorUpdate(payRoll);
			} else {

				return ResponseEntity.ok(false);
			}

			return ResponseEntity.ok(payRoll.isStatus()); // Return the new status (true or false)
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false); // Set response to false in case
																						// of an error
		}
	}

	@PutMapping("/payroll/editpayroll/{payRollId}")
	public ResponseEntity<PayRoll> updatePayRoll(@PathVariable("payRollId") Long payRollId,
			@RequestBody PayRoll payRollDetails) {

		try {
			PayRoll existingPayRoll = payRollService.findById(payRollId);

			if (existingPayRoll == null) {
				return ResponseEntity.notFound().build();
			}

			// Update the existing PayRoll properties with the values from payRollDetails
			existingPayRoll.setEmployeeId(payRollDetails.getEmployeeId());
			existingPayRoll.setDate(payRollDetails.getDate());
			existingPayRoll.setTotalSalary(payRollDetails.getTotalSalary());
			existingPayRoll.setPaymentTypeId(payRollDetails.getPaymentTypeId());
			existingPayRoll.setTotalDeductions(payRollDetails.getTotalDeductions());
			existingPayRoll.setAllowance(payRollDetails.getAllowance());
			existingPayRoll.setNoOfDaysWorkingInaMonth(payRollDetails.getNoOfDaysWorkingInaMonth());
			existingPayRoll.setStatus(payRollDetails.isStatus());

			// Calculate the current salary using the provided logic
			long totalSalary = existingPayRoll.getTotalSalary();
			long totalDeductions = existingPayRoll.getTotalDeductions();
			long allowance = existingPayRoll.getAllowance();
			long currentSalary = totalSalary - totalDeductions + allowance;
			existingPayRoll.setCurrentSalary(currentSalary);

			// Save the updated PayRoll object back to the database
			payRollService.save(existingPayRoll);

			return ResponseEntity.ok(existingPayRoll);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/payrolldetails/view")
	public List<Map<String, Object>> payRollDetails() {
		return payRollService.allPayRollDetails();

	}
	
	@GetMapping("/payrolldetails/show")
	public List<Map<String, Object>> payRollDetails2() {
		return repo.allPayRollDetails1();

	}

	@GetMapping("/payrolldetails/payrollid")
	private List<Map<String, Object>> payrollid_based_details() {
		List<Map<String, Object>> payRollist = new ArrayList<>();
		List<Map<String, Object>> list = payRollService.allPayRollDetails();
		Map<String, List<Map<String, Object>>> expenseGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("pay_roll_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : expenseGroupMap.entrySet()) {
			Map<String, Object> payRollMap = new HashMap<>();
			payRollMap.put("PayRollId", totalList.getKey());
			payRollMap.put("PayRoll Details", totalList.getValue());
			payRollist.add(payRollMap);

		}
		return payRollist;

	}

	@GetMapping("/payrolldetails1/{pay_roll_id}")
	private List<Map<String, Object>> idsbasedoncpayroll(@PathVariable Long pay_roll_id) {
		List<Map<String, Object>> payRollist = new ArrayList<>();
		List<Map<String, Object>> list = payRollService.findAllByPayRollId(pay_roll_id);
		Map<String, List<Map<String, Object>>> payRollGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("pay_roll_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : payRollGroupMap.entrySet()) {
			Map<String, Object> payRollMap = new HashMap<>();
			payRollMap.put("payrollId", totalList.getKey());
			payRollMap.put("payroll Details", totalList.getValue());
			payRollist.add(payRollMap);

		}
		return payRollist;

	}

	@GetMapping("/payrolldetails/{employee_id}")
	private List<Map<String, Object>> employeeIdPayroll(@PathVariable Long employee_id) {
		List<Map<String, Object>> payRollist1 = new ArrayList<>();
		List<Map<String, Object>> list1 = payRollService.findAllByEmployeeId(employee_id);
		Map<String, List<Map<String, Object>>> payRollGroupMap1 = StreamSupport.stream(list1.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : payRollGroupMap1.entrySet()) {
			Map<String, Object> payRollMap = new HashMap<>();
			payRollMap.put("EmployeeId", totalList.getKey());
			payRollMap.put("payroll Details", totalList.getValue());
			payRollist1.add(payRollMap);

		}
		return payRollist1;

	}
	
	 @PostMapping("/payrolldetails/date")
	    public List<Map<String, Object>> getAllVoucherBetweenDates(
				@RequestBody Map<String, Object> requestBody) {
			LocalDate startDate = LocalDate.parse(requestBody.get("startDate").toString(), DateTimeFormatter.ISO_DATE);
			LocalDate endDate = LocalDate.parse(requestBody.get("endDate").toString(), DateTimeFormatter.ISO_DATE);
	        return repo.getAllpromotionsBetweenDates(startDate, endDate);
	    }
	 
	 @PostMapping("/payrolldetails/date/one")
	    public List<Map<String, Object>> getAllVoucherBetweenDates5(
				@RequestBody Map<String, Object> requestBody) {
			LocalDate startDate = LocalDate.parse(requestBody.get("startDate").toString(), DateTimeFormatter.ISO_DATE);
			
	        return repo.getAllpromotionsBetweenDates4(startDate );
	    }

	@GetMapping("/currentsalarybydate")
	public List<Map<String, Object>> dailyExpenseByDate() {
		return repo.allDetailsOfPayRollByDate();
	}

	@GetMapping("/totalsalarybymonth")
	public List<Map<String, Object>> totalSalaryByMonth() {
		return repo.totalSalaryByMonth();
	}

//	@PostMapping("/totalsalarybymonth1")
//	public List<Map<String, Object>> totalSalaryByMonth1(@RequestBody("year") int year, @RequestBody("monthname") String monthname) {
//		return repo.findByYearAndMonth(year, monthname);
//	}
	
//	@PostMapping("/totalsalarybymonth1")
//    public List<Map<String, Object>> totalSalaryByMonth1(@RequestBody Map<String, Object> request) {
//        int year = (int) request.get("year");
//        String monthname = (String) request.get("monthname");
//        return repo.findByYearAndMonth(year, monthname);
//    }
	
	@PostMapping("/totalsalarybymonth1")
	public List<Map<String, Object>> totalSalaryByMonth1(@RequestBody SalaryRequest request) {
	    Integer year = request.getYear();
	    String monthname = request.getMonthname();
	    return repo.findByYearAndMonth(year, monthname);
	}


	@PostMapping("/totalsalary/month/year")
	public List<Map<String, Object>> totalSalaryByMonth4(@RequestBody SalaryRequest request) {
	    Integer year = request.getYear();
	    String monthname = request.getMonthname();
	    return repo.allDetailsOfPayRollByMonthAndYear(year, monthname);
	}


//	@PostMapping("/totalsalary/month/year")
//	public List<Map<String, Object>> salaryByMonthAndYear(int month, int year) {
//		return repo.allDetailsOfPayRollByMonthAndYear(month, year);
//	}

	@PostMapping("/highestsalarybymonth")
	public List<Map<String, Object>> findByMonthb(@Param("month") int month) {
		return repo.findByMonth(month);

	}
}
