package com.example.hrm_new.entity.payroll;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payRoll")
public class PayRoll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long payRollId;
	private long employeeNameId;
	private Date date;
	private long totalSalary;
	private long paymentTypeId;
	private long totalDeductions;
	private long currentSalary;
	private long allowance;
	private int noOfDaysWorkingInaMonth;
	private boolean status;
	
	
	
	public PayRoll() {
		super();
	}
	public long getPayRollId() {
		return payRollId;
	}
	public void setPayRollId(long payRollId) {
		this.payRollId = payRollId;
	}
	public long getEmployeeNameId() {
		return employeeNameId;
	}
	public void setEmployeeNameId(long employeeNameId) {
		this.employeeNameId = employeeNameId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(long totalSalary) {
		this.totalSalary = totalSalary;
	}
	public long getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public long getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(long totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	public long getCurrentSalary() {
		return currentSalary;
	}
	public void setCurrentSalary(long currentSalary) {
		this.currentSalary = currentSalary;
	}
	public long getAllowance() {
		return allowance;
	}
	public void setAllowance(long allowance) {
		this.allowance = allowance;
	}
	public int getNoOfDaysWorkingInaMonth() {
		return noOfDaysWorkingInaMonth;
	}
	public void setNoOfDaysWorkingInaMonth(int noOfDaysWorkingInaMonth) {
		this.noOfDaysWorkingInaMonth = noOfDaysWorkingInaMonth;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
