package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="complaints")
public class Complaints {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long complaintsId;
	private long employeeId;
	private String complaintsTitle;
	private Date complaintsDate;
	private String complaintsAgainst;
	private String description;
	private boolean status;
	
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public long getComplaintsId() {
		return complaintsId;
	}
	public void setComplaintsId(long complaintsId) {
		this.complaintsId = complaintsId;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getComplaintsTitle() {
		return complaintsTitle;
	}
	public void setComplaintsTitle(String complaintsTitle) {
		this.complaintsTitle = complaintsTitle;
	}
	public Date getComplaintsDate() {
		return complaintsDate;
	}
	public void setComplaintsDate(Date complaintsDate) {
		this.complaintsDate = complaintsDate;
	}
	public String getComplaintsAgainst() {
		return complaintsAgainst;
	}
	public void setComplaintsAgainst(String complaintsAgainst) {
		this.complaintsAgainst = complaintsAgainst;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
