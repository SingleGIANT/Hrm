package com.example.hrm_new.controller.payroll;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.payroll.PayRoll;
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

//	@PostMapping("/payroll/save")
//	public ResponseEntity<?> saveBank(@RequestBody PayRoll payRoll) {
//
//		try {
//			long totalSalary =payRoll.getTotalSalary();
//			long totalDeductions=payRoll.getTotalDeductions();
//			long allowance = payRoll .getAllowance();
//			
//			payRoll.setCurrentSalary(totalSalary-totalDeductions+allowance);
//			
//			payRoll.setStatus(true);
//			payRollService.SaveorUpdate(payRoll);
//
//			return ResponseEntity.status(HttpStatus.CREATED).body("payroll details saved successfully.");
//
//		} catch (Exception e) {
//
//			String errorMessage = "An error occurred while saving payroll details.";
//
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//
//		}
//
//	}

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

	@PutMapping("/payroll/editpayroll/{payRollId}")

	public ResponseEntity<PayRoll> updatePayRoll(@PathVariable("payRollId") Long payRollId,
			@RequestBody PayRoll payRollDetails) {

		try {

			PayRoll existingPayRoll = payRollService.findById(payRollId);

			if (existingPayRoll == null) {

				return ResponseEntity.notFound().build();

			}

			existingPayRoll.setEmployeeNameId(payRollDetails.getEmployeeNameId());
			existingPayRoll.setDate(payRollDetails.getDate());
			existingPayRoll.setTotalSalary(payRollDetails.getTotalSalary());
			existingPayRoll.setPaymentTypeId(payRollDetails.getPaymentTypeId());
			existingPayRoll.setTotalDeductions(payRollDetails.getTotalDeductions());
			existingPayRoll.setCurrentSalary(payRollDetails.getCurrentSalary());
			existingPayRoll.setAllowance(payRollDetails.getAllowance());
			existingPayRoll.setNoOfDaysWorkingInaMonth(payRollDetails.getNoOfDaysWorkingInaMonth());
			existingPayRoll.setStatus(payRollDetails.isStatus());

			payRollService.save(existingPayRoll);

			return ResponseEntity.ok(existingPayRoll);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("/payroll/payrolldelete/{payRollId}")

	public ResponseEntity<String> deletePayRoll(@PathVariable("payRollId") Long payRollId) {

		payRollService.deletePayRollIdById(payRollId);

		return ResponseEntity.ok("payroll deleted successfully");

	}

	@GetMapping("/payrolldetails/view")
	public List<Map<String, Object>> payRollDetails() {
		return payRollService.allPayRollDetails();

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

	@GetMapping("/payrolldetails/{employee_name_id}")
	private List<Map<String, Object>> employeeIdPayroll(@PathVariable Long employee_name_id) {
		List<Map<String, Object>> payRollist1 = new ArrayList<>();
		List<Map<String, Object>> list1 = payRollService.findAllByEmployeeId(employee_name_id);
		Map<String, List<Map<String, Object>>> payRollGroupMap1 = StreamSupport.stream(list1.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("employee_name_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : payRollGroupMap1.entrySet()) {
			Map<String, Object> payRollMap = new HashMap<>();
			payRollMap.put("EmployeeId", totalList.getKey());
			payRollMap.put("payroll Details", totalList.getValue());
			payRollist1.add(payRollMap);

		}
		return payRollist1;

	}

	@GetMapping("/currentsalarybydate")
	public List<Map<String, Object>> dailyExpenseByDate() {
		return repo.allDetailsOfPayRollByDate();
	}

	@GetMapping("/totalsalarybymonth")
	public List<Map<String, Object>> totalSalaryByMonth() {
		return repo.totalSalaryByMonth();
	}

	@PostMapping("/totalsalarybymonth1")
	public List<Map<String, Object>> totalSalaryByMonth1(@Param("year") int year, @Param("month") int month) {
		return repo.findByYearAndMonth(year, month);
	}

	@PostMapping("/highestsalarybymonth")
	public List<Map<String, Object>> findByMonthb(@Param("month") int month) {
		return repo.findByMonth(month);

	}
}
