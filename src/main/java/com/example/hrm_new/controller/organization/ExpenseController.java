package com.example.hrm_new.controller.organization;

import java.sql.Date;
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

import com.example.hrm_new.entity.organization.Expense;
import com.example.hrm_new.repository.organization.ExpenseRepository;
import com.example.hrm_new.service.organization.ExpenseService;


@RestController
@CrossOrigin
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseRepository repo;

	@GetMapping("/expense")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<Expense> expenseDetails = expenseService.listAll();

			return new ResponseEntity<>(expenseDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/expense/save")

	public ResponseEntity<?> saveBank(@RequestBody Expense expense) {

		try {
			expense.setStatus(true);
			expenseService.SaveorUpdate(expense);

			return ResponseEntity.status(HttpStatus.CREATED).body("expense details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving expense details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}

	@RequestMapping("/expense/{expenseId}")

	private Optional<Expense> getExpense(@PathVariable(name = "expenseId") long expenseId) {

		return expenseService.getExpenseById(expenseId);

	}

	@PutMapping("/expense/editdeexpense/{expenseId}")

	public ResponseEntity<Expense> updateExpense(@PathVariable("expenseId") Long expenseId,
			@RequestBody Expense expenseDetails) {

		try {

			Expense existingexpense = expenseService.findById(expenseId);

			if (existingexpense == null) {

				return ResponseEntity.notFound().build();

			}

			existingexpense.setExpenseTypeId(expenseDetails.getExpenseTypeId());
			existingexpense.setExpenseName(expenseDetails.getExpenseName());
			existingexpense.setDate(expenseDetails.getDate());
			existingexpense.setCompanyId(expenseDetails.getCompanyId());
			existingexpense.setDescription(expenseDetails.getDescription());
			existingexpense.setAmount(expenseDetails.getAmount());
			existingexpense.setStatus(expenseDetails.isStatus());

			expenseService.save(existingexpense);

			return ResponseEntity.ok(existingexpense);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("/expense/expensedelete/{expenseId}")

	public ResponseEntity<String> deleteAmount(@PathVariable("expenseId") Long expenseId) {

		expenseService.deleteExpenseIdById(expenseId);

		return ResponseEntity.ok("expense deleted successfully");

	}

	@GetMapping("/expensedetails/view")
	public List<Map<String, Object>> allDeptDetails() {
		return expenseService.allExpenseDetails();

	}

	@GetMapping("/expensedetails/expenseid")
	private List<Map<String, Object>> expenseid_based_expense() {
		List<Map<String, Object>> expenselist = new ArrayList<>();
		List<Map<String, Object>> list = expenseService.allExpenseDetails();
		Map<String, List<Map<String, Object>>> expenseGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("expense_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : expenseGroupMap.entrySet()) {
			Map<String, Object> expenseMap = new HashMap<>();
			expenseMap.put("ExpenseId", totalList.getKey());
			expenseMap.put("ExpenseType", totalList.getValue().get(0).get("expense_type"));
			expenseMap.put("Expense Details", totalList.getValue());
			expenselist.add(expenseMap);

		}
		return expenselist;

	}

	@GetMapping("/expensedetails1/{expense_id}")
	private List<Map<String, Object>> idbasedExpense(@PathVariable Long expense_id) {
		List<Map<String, Object>> expenselist = new ArrayList<>();
		List<Map<String, Object>> list = expenseService.findAllByExpenseId(expense_id);
		Map<String, List<Map<String, Object>>> expenseGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("expense_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : expenseGroupMap.entrySet()) {
			Map<String, Object> expenseMap = new HashMap<>();
			expenseMap.put("ExpenseId", totalList.getKey());
			expenseMap.put("ExpenseType", totalList.getValue().get(0).get("expense_type"));
			expenseMap.put("Expense Details", totalList.getValue());
			expenselist.add(expenseMap);

		}
		return expenselist;

	}
	
	@PostMapping("/expense/date")
	public List<Map<String, Object>> allExpenseDetailsByDate(
			@RequestBody Map<String, Object> requestBody) {
		LocalDate date = LocalDate.parse(requestBody.get("date").toString(), DateTimeFormatter.ISO_DATE);
		return repo.allExpenseDetailsByDate(date);
	}
	
	@GetMapping("/currentdateexpense")
	public List<Map<String, Object>> dailyExpenseByDate(){
		return expenseService.dailyExpenseByCurrentDate();
	}
	@GetMapping("/monthlyexpensedetails")
	public List<Map<String, Object>> mothlyExpenseDetails(){
		return repo.mothlyExpenseDetails();
	}
	@GetMapping("/monthlyexpenseamount")
	public List<Map<String, Object>> monthlyexpense(){
		return repo.monthlyExpense();
	}
	@GetMapping("/yearlyexpensedetails")
	public List<Map<String, Object>> yearlyExpenseDetails(){
		return repo.yearlyExpenseDetails();
	}
	
	@GetMapping("/yearlyexpenseamount")
	public List<Map<String, Object>> yearlyexpense(){
		return repo.yearlyExpense();
	}
	@PutMapping("/expense/or/{expenseId}")

	public ResponseEntity<Boolean> toggleExpenseStatus(@PathVariable(name = "expenseId") long expenseId) {

	try {

		Expense expense = expenseService.findById(expenseId);

	if (expense != null) {

	// Toggle the status

	boolean currentStatus = expense.isStatus();

	expense.setStatus(!currentStatus);

	expenseService.SaveorUpdate(expense); // Save the updated company

	} else {

	return ResponseEntity.ok(false); // company with the given ID does not exist, return false

	}

	return ResponseEntity.ok(expense.isStatus()); // Return the new status (true or false)

	} catch (Exception e) {

	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

	.body(false); // Set response to false in case of an error

	}
	}
}
