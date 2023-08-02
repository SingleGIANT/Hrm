package com.example.hrm_new.entity.Assest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="keyboardBrand")
public class KeyboardBrand {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long keyboardBrandId;
	private String KeyboardBrandName;
	
	public KeyboardBrand() {
		super();
	}
	public long getKeyboardBrandId() {
		return keyboardBrandId;
	}
	public void setKeyboardBrandId(long keyboardBrandId) {
		this.keyboardBrandId = keyboardBrandId;
	}
	public String getKeyboardBrandName() {
		return KeyboardBrandName;
	}
	public void setKeyboardBrandName(String keyboardBrandName) {
		KeyboardBrandName = keyboardBrandName;
	}
	
}
