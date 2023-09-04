package com.example.hrm_new.entity.project;

import java.sql.Date;

import java.util.List;


import javax.persistence.Convert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.Table;
import com.example.hrm_new.entity.LongListConverter;


@Entity
@Table(name = "projectss")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long projectId;
	private String projectTitle;
	private long customerId;
	private long contact;
	private String location;
	private int totalDuration;
	private Date fromDate;
	private Date toDate;
	private long totalProjectAmount;
	private boolean status;	
	@Convert(converter = LongListConverter.class)
	private List<Long> designationId;	
	private boolean projectStatus;	

	
	
	public boolean isProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(boolean projectStatus) {
		this.projectStatus = projectStatus;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public List<Long> getDesignationId() {
		return designationId;
	}

	
	
	

	public Project(long projectId, String projectTitle, long customerId, long contact, String location,
			int totalDuration, Date fromDate, Date toDate, long totalProjectAmount, boolean status,
			List<Long> designationId) {
		super();
		this.projectId = projectId;
		this.projectTitle = projectTitle;
		this.customerId = customerId;
		this.contact = contact;
		this.location = location;
		this.totalDuration = totalDuration;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.totalProjectAmount = totalProjectAmount;
		this.status = status;
		this.designationId = designationId;
	}

	public void setDesignationId(List<Long> designationId) {
		this.designationId = designationId;
	}

	public Project() {
		super();
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

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	public long getTotalProjectAmount() {
		return totalProjectAmount;
	}

	public void setTotalProjectAmount(long totalProjectAmount) {
		this.totalProjectAmount = totalProjectAmount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectTitle=" + projectTitle + ", customerId=" + customerId
				+ ", contact=" + contact + ", location=" + location + ", totalDuration=" + totalDuration + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", totalProjectAmount=" + totalProjectAmount + ", status=" + status
				+ ", designationId=" + designationId + "]";
	}

	
}
