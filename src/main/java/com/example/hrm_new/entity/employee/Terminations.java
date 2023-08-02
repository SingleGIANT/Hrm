package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="terminations")
public class Terminations {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long terminationsId;
	private long employeeId;
	private String terminationsType;
	private Date terminationsDate;

	private String description;
	private boolean status;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public long getTerminationsId() {
		return terminationsId;
	}
	public void setTerminationsId(long terminationsId) {
		this.terminationsId = terminationsId;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getTerminationsType() {
		return terminationsType;
	}
	public void setTerminationsType(String terminationsType) {
		this.terminationsType = terminationsType;
	}
	public Date getTerminationsDate() {
		return terminationsDate;
	}
	public void setTerminationsDate(Date terminationsDate) {
		this.terminationsDate = terminationsDate;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	 	
}
