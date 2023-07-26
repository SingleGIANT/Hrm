package com.example.hrm_new.entity.training;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="traineeDetails")
public class TraineeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long traineeDetailsId;
	private Long phoneNumber;
	private String Name;
	private String country;
	private String email;
	private String address;
	private String location;
	private String state;
	private String gender;
	private boolean status;
	private String nameId;
	
	

	public TraineeDetails() {
		super();
	}
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getTraineeDetailsId() {
		return traineeDetailsId;
	}
	public void setTraineeDetailsId(long traineeDetailsId) {
		this.traineeDetailsId = traineeDetailsId;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	

}
