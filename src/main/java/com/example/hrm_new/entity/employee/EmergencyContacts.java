package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contacts")
public class EmergencyContacts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long emergencyContactsId;
	private Long employeeId;
	private String relatinoName;
	private String relationTypeId;
	private String address;
	private String city;
	private String state;
	private String country;
	private Long phoneNumber;
	private boolean status;
	public Long getEmergencyContactsId() {
		return emergencyContactsId;
	}
	public void setEmergencyContactsId(Long emergencyContactsId) {
		this.emergencyContactsId = emergencyContactsId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getRelatinoName() {
		return relatinoName;
	}
	public void setRelatinoName(String relatinoName) {
		this.relatinoName = relatinoName;
	}
	public String getRelationTypeId() {
		return relationTypeId;
	}
	public void setRelationTypeId(String relationTypeId) {
		this.relationTypeId = relationTypeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public EmergencyContacts() {
		super();
	}
	
	
	
	
}
