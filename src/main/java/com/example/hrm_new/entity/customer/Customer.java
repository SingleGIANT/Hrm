package com.example.hrm_new.entity.customer;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;
	private String city;
	private String state;
	private String country;
	private String address;
	private String name;
	private String email;
	private Date date;
	private String gender;
	private long phoneNo1;
	private long phoneNo2;
	private long formTypeId;
	private boolean status;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getPhoneNo1() {
		return phoneNo1;
	}

	public void setPhoneNo1(long phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public long getPhoneNo2() {
		return phoneNo2;
	}

	public void setPhoneNo2(long phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public long getFormTypeId() {
		return formTypeId;
	}

	public void setFormTypeId(long formTypeId) {
		this.formTypeId = formTypeId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Customer() {
		super();
	}

}