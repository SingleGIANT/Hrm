package com.example.hrm_new.entity.Assest;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assest")
public class Assest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long assestId;
	private long employeeId;
	private String productName;
	private long serialNumber;
	private Date purchaseDate;
	private long modelNumber;
	private long brandId;
	private long keyboardBrandId;
	private long mouseBrandId;
    private long countOfProducts;
    private boolean status;
    
    
	public Assest() {
		super();
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public long getAssestId() {
		return assestId;
	}
	public void setAssestId(long assestId) {
		this.assestId = assestId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public long getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(long modelNumber) {
		this.modelNumber = modelNumber;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public long getKeyboardBrandId() {
		return keyboardBrandId;
	}
	public void setKeyboardBrandId(long keyboardBrandId) {
		this.keyboardBrandId = keyboardBrandId;
	}
	public long getMouseBrandId() {
		return mouseBrandId;
	}
	public void setMouseBrandId(long mouseBrandId) {
		this.mouseBrandId = mouseBrandId;
	}
	public long getCountOfProducts() {
		return countOfProducts;
	}
	public void setCountOfProducts(long countOfProducts) {
		this.countOfProducts = countOfProducts;
	}
	
	

}
