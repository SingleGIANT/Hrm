package com.example.hrm_new.entity.Assest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mouseBrand")
public class MouseBrand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long mouseBrandId;
	private String mouseBrandName;
	
	public MouseBrand() {
		super();
	}
	public long getMouseBrandId() {
		return mouseBrandId;
	}
	public void setMouseBrandId(long mouseBrandId) {
		this.mouseBrandId = mouseBrandId;
	}
	public String getMouseBrandName() {
		return mouseBrandName;
	}
	public void setMouseBrandName(String mouseBrandName) {
		this.mouseBrandName = mouseBrandName;
	}
	
	
}
