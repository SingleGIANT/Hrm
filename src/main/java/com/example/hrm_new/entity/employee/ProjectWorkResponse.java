package com.example.hrm_new.entity.employee;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ProjectWorkResponse {
	
	    private Long projectWorkId;
	    private Long projectId;
	    private String projectTitle;
	    private String description;
	    private String date;
	    private String duration;
	    private List<Long> employeeId;
	    private List<String> firstName;
	    private List<String> lastName;
	    private List<String> designationName;
	    private List<Long> designationId;
	    private List<Long>departmentId;
	  
	    private boolean onProcess;
		private boolean hold;
		private boolean started;
		private boolean completed;	
		private String work;	
		private LocalDate  dateCompleted;  
		private String holdReson;
		private boolean pending;
		

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
		public String getWork() {
			return work;
		}
		public void setWork(String work) {
			this.work = work;
		}	
		public LocalDate getDateCompleted() {
			return dateCompleted;
		}
		public void setDateCompleted(LocalDate dateCompleted) {
			this.dateCompleted = dateCompleted;
		}
		public String getHoldReson() {
			return holdReson;
		}
		public void setHoldReson(String holdReson) {
			this.holdReson = holdReson;
		}
		public boolean isPending() {
			return pending;
		}
		public void setPending(boolean pending) {
			this.pending = pending;
		}
		private List<String> departmentName;
	    private Boolean status;
		public Long getProjectWorkId() {
			return projectWorkId;
		}
		public void setProjectWorkId(Long projectWorkId) {
			this.projectWorkId = projectWorkId;
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
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
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
	
		public List<String> getFirstName() {
			return firstName;
		}
		public void setFirstName(List<String> firstName) {
			this.firstName = firstName;
		}
		public List<String> getLastName() {
			return lastName;
		}
		public void setLastName(List<String> lastName) {
			this.lastName = lastName;
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
		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		public List<Long> getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(List<Long> departmentId) {
			this.departmentId = departmentId;
		}
		public List<String> getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(List<String> departmentName) {
			this.departmentName = departmentName;
		}
	

	

	    
	


}
