package com.example.hrm_new.entity.employee;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "qualification")
public class Qualification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long qualificationId;	
	private Blob resume;	
	private Blob photo;
	private long aadharNO;
	private String employeeId;
	private Blob ten;	
	private Blob aadhar;
	private String highestQualification;
	private String panCard;
	private Blob degree;	
	private Blob bankBook;
	private Blob twelve;
	private Blob panno;
	private String resumeurl;
	private String pannourl;
	private String photourl;
	private String tenurl;
	private String aadharurl;
	private String degreeurl;
	private String bankBookurl;
	private String twelveurl;
	private boolean status;
	
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getAadharNO() {
		return aadharNO;
	}

	public void setAadharNO(long aadharNO) {
		this.aadharNO = aadharNO;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	public String getResumeurl() {
		return resumeurl;
	}

	public void setResumeurl(String resumeurl) {
		this.resumeurl = resumeurl;
	}

	public String getPannourl() {
		return pannourl;
	}

	public void setPannourl(String pannourl) {
		this.pannourl = pannourl;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getTenurl() {
		return tenurl;
	}

	public void setTenurl(String tenurl) {
		this.tenurl = tenurl;
	}

	public String getAadharurl() {
		return aadharurl;
	}

	public void setAadharurl(String aadharurl) {
		this.aadharurl = aadharurl;
	}

	public String getDegreeurl() {
		return degreeurl;
	}

	public void setDegreeurl(String degreeurl) {
		this.degreeurl = degreeurl;
	}

	public String getBankBookurl() {
		return bankBookurl;
	}

	public void setBankBookurl(String bankBookurl) {
		this.bankBookurl = bankBookurl;
	}

	public String getTwelveurl() {
		return twelveurl;
	}

	public void setTwelveurl(String twelveurl) {
		this.twelveurl = twelveurl;
	}

	public long getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(long qualificationId) {
		this.qualificationId = qualificationId;
	}

	public Blob getResume() {
		return resume;
	}

	public void setResume(Blob resume) {
		this.resume = resume;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photoImageUrl) {
		this.photo = photoImageUrl;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Blob getTen() {
		return ten;
	}

	public void setTen(Blob ten) {
		this.ten = ten;
	}

	public Blob getAadhar() {
		return aadhar;
	}

	public void setAadhar(Blob aadhar) {
		this.aadhar = aadhar;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

	public Blob getDegree() {
		return degree;
	}

	public void setDegree(Blob degree) {
		this.degree = degree;
	}

	public Blob getBankBook() {
		return bankBook;
	}

	public void setBankBook(Blob bankBook) {
		this.bankBook = bankBook;
	}

	public Blob getTwelve() {
		return twelve;
	}

	public void setTwelve(Blob twelve) {
		this.twelve = twelve;
	}

	public Blob getPanno() {
		return panno;
	}

	public void setPanno(Blob panno) {
		this.panno = panno;
	}

	public Qualification() {
		super();
	}



}
