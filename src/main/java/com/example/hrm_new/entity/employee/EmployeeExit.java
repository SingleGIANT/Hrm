package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employeeexit")
public class EmployeeExit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeExitId;
	private Long employeeId;
	private  Date date;
	private String description;
	private boolean status;
	public Long getEmployeeExitId() {
		return employeeExitId;
	}
	public void setEmployeeExitId(Long employeeExitId) {
		this.employeeExitId = employeeExitId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public EmployeeExit() {
		super();
	}
	
	
	
}
