package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resignations")
public class Resignations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long resignationsId;
	private String reason ;
	private Date resignationsDate;
	private Date noticeDate;
	private Date toDate;
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	private int durations;
	private long employeeId;
	private boolean status;
	
	public long getResignationsId() {
		return resignationsId;
	}
	public void setResignationsId(long resignationsId) {
		this.resignationsId = resignationsId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getResignationsDate() {
		return resignationsDate;
	}
	public void setResignationsDate(Date resignationsDate) {
		this.resignationsDate = resignationsDate;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getDurations() {
		return durations;
	}
	public void setDurations(int durations) {
		this.durations = durations;
	}
	public Resignations() {
		super();
	}
	
	

}
