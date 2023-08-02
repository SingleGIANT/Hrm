package com.example.hrm_new.entity.employee;

import javax.persistence.*;


@Entity
@Table(name="qualifications")
public class Qualifications {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long qualificationId;
	    private String employeeId;
	    private String highestQualification;
	    private String photo;
	    private String resume;
	    private String ten;
	    private String aadhar;
	    private String degree;
	    private String panno;
	    private String bankBook;
	    private String twelve;
	    private boolean status;
	    
	    
	    
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public long getQualificationId() {
			return qualificationId;
		}
		public void setQualificationId(long qualificationId) {
			this.qualificationId = qualificationId;
		}
		public String getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}
		public String getHighestQualification() {
			return highestQualification;
		}
		public void setHighestQualification(String highestQualification) {
			this.highestQualification = highestQualification;
		}
		public String getPhoto() {
			return photo;
		}
		public void setPhoto(String photo) {
			this.photo = photo;
		}
		public String getResume() {
			return resume;
		}
		public void setResume(String resume) {
			this.resume = resume;
		}
		public String getTen() {
			return ten;
		}
		public void setTen(String ten) {
			this.ten = ten;
		}
		public String getAadhar() {
			return aadhar;
		}
		public void setAadhar(String aadhar) {
			this.aadhar = aadhar;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		public String getPanno() {
			return panno;
		}
		public void setPanno(String panno) {
			this.panno = panno;
		}
		public String getBankBook() {
			return bankBook;
		}
		public void setBankBook(String bankBook) {
			this.bankBook = bankBook;
		}
		public String getTwelve() {
			return twelve;
		}
		public void setTwelve(String twelve) {
			this.twelve = twelve;
		}
		public Qualifications() {
			super();
		}
	    
	    


}
