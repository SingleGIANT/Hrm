package com.example.hrm_new.entity.worksheet;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="workSheet")
public class WorkSheet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long worksheetId;
	private String moduleName;
	private long employeeNameId;
	private Date fromDate;
	private Date toDate;
	private int totalDuration;
	private String description;
	private long projectId;
	private boolean status;
	
	
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public WorkSheet() {
		super();
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getWorksheetId() {
		return worksheetId;
	}
	public void setWorksheetId(long worksheetId) {
		this.worksheetId = worksheetId;
	}

	public long getEmployeeNameId() {
		return employeeNameId;
	}
	public void setEmployeeNameId(long employeeNameId) {
		this.employeeNameId = employeeNameId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public int getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
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
	
	
}
