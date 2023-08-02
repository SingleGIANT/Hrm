package com.example.hrm_new.entity.employee;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "promotions")
public class Promotions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long promotionsId;
	private long employeeId;
	private String description;
	private Date date;
	private long roleId;
	private String promotionsBy;
	private boolean status;

	public long getPromotionsId() {
		return promotionsId;
	}

	public void setPromotionsId(long promotionsId) {
		this.promotionsId = promotionsId;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getPromotionsBy() {
		return promotionsBy;
	}

	public void setPromotionsBy(String promotionsBy) {
		this.promotionsBy = promotionsBy;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Promotions() {
		super();
	}

}
