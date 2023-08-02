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
	private String employeeId;
	private Blob ten;	
	private Blob aadhar;
	private String highestQualification;	
	private Blob degree;	
	private Blob bankBook;
	private Blob twelve;
	private Blob panno;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
