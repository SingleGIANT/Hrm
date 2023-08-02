package com.example.hrm_new.entity.employee;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "awards")
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long awardsId;   
    private String description;
    private String gift;
    private Date date;
    private int cash;
    private long employeeId;
    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fkawardsId", referencedColumnName = "awardsId")
    private List<AwardsPhoto> awardsPhotos;
    private boolean status;  
	
	
	public Awards() {
		super();
	}


	public long getAwardsId() {
		return awardsId;
	}


	public void setAwardsId(long awardsId) {
		this.awardsId = awardsId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getGift() {
		return gift;
	}


	public void setGift(String gift) {
		this.gift = gift;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getCash() {
		return cash;
	}


	public void setCash(int cash) {
		this.cash = cash;
	}


	public long getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}


	public List<AwardsPhoto> getAwardsPhotos() {
		return awardsPhotos;
	}


	public void setAwardsPhotos(List<AwardsPhoto> awardsPhotos) {
		this.awardsPhotos = awardsPhotos;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
}
