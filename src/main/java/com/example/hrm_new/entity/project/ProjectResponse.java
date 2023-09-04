package com.example.hrm_new.entity.project;

import java.util.List;

public class ProjectResponse {
    private Long projectId;
    private String projectTitle;
    private Long customerId;
    private Long contact;
    private String location;
    private Integer totalDuration;
    private String fromDate;
    private String toDate;
    private Double totalProjectAmount;
    private Boolean status;
  	private boolean projectStatus;
    private List<String> designationName;
    private List<Long> designationId;
    private String customerName;
    private String city;
    private Long phoneNo1;
    private Long phoneNo2;
    
    
	public boolean isProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(boolean projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getPhoneNo1() {
		return phoneNo1;
	}
	public void setPhoneNo1(Long phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}
	public Long getPhoneNo2() {
		return phoneNo2;
	}
	public void setPhoneNo2(Long phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getContact() {
		return contact;
	}
	public void setContact(Long contact) {
		this.contact = contact;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Double getTotalProjectAmount() {
		return totalProjectAmount;
	}
	public void setTotalProjectAmount(Double totalProjectAmount) {
		this.totalProjectAmount = totalProjectAmount;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<String> getDesignationName() {
		return designationName;
	}
	public void setDesignationName(List<String> designationName) {
		this.designationName = designationName;
	}
	public List<Long> getDesignationId() {
		return designationId;
	}
	public void setDesignationId(List<Long> designationId) {
		this.designationId = designationId;
	}
    
    
    

}

