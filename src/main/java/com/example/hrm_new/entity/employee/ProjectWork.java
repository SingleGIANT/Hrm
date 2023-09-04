package com.example.hrm_new.entity.employee;

import java.sql.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.hrm_new.entity.LongListConverter;
import com.example.hrm_new.entity.converter;

@Entity
@Table(name = "projectworks")
public class ProjectWork {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectWorkId;
	private long projectId;
	private Date date;
	private String duration;
	private String description;
	@Convert(converter = LongListConverter.class)
	private List<Long> employeeId;
	@Convert(converter = LongListConverter.class)
	private List<Long> designationId;
	@Convert(converter = LongListConverter.class)
	private List<Long> departmentId;
	private boolean status;
	private boolean onProcess;
	private boolean hold;
	private boolean started;
	private boolean completed;	
	private String work;	
	private Date dateCompleted;  
	private String holdReson;
	private boolean pending;
		

	public List<Long> getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(List<Long> departmentId) {
		this.departmentId = departmentId;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public String getHoldReson() {
		return holdReson;
	}

	public void setHoldReson(String holdReson) {
		this.holdReson = holdReson;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public boolean isOnProcess() {
		return onProcess;
	}

	public void setOnProcess(boolean onProcess) {
		this.onProcess = onProcess;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Long getProjectWorkId() {
		return projectWorkId;
	}

	public void setProjectWorkId(Long projectWorkId) {
		this.projectWorkId = projectWorkId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Long> getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(List<Long> employeeId) {
		this.employeeId = employeeId;
	}

	public List<Long> getDesignationId() {
		return designationId;
	}

	public void setDesignationId(List<Long> designationId) {
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
