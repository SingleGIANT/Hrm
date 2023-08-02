package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projectreport")
public class ProjectReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectReportId;
	private Long employeeId;
	private long projectId;
	private Date dateGive;
	private int duration;
	private Date extendedDate;
	private boolean status;

	public Long getProjectReportId() {
		return projectReportId;
	}

	public void setProjectReportId(Long projectReportId) {
		this.projectReportId = projectReportId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Date getDateGive() {
		return dateGive;
	}

	public void setDateGive(Date dateGive) {
		this.dateGive = dateGive;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getExtendedDate() {
		return extendedDate;
	}

	public void setExtendedDate(Date extendedDate) {
		this.extendedDate = extendedDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ProjectReport() {
		super();
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

}
