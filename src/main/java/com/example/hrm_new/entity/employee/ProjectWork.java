package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projectwork")
public class ProjectWork {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectWorkId;
	private Long employeeId;
	private long projectId;
	private Date date;
	private String duration;
	private Long designationId;
	private boolean status;

	public Long getProjectWorkId() {
		return projectWorkId;
	}

	public void setProjectWorkId(Long projectWorkId) {
		this.projectWorkId = projectWorkId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ProjectWork() {
		super();
	}

}
