package com.example.hrm_new.entity.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "formtype")
public class FormType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long formTypeId;
	private String formTypeName;
	public long getFormTypeId() {
		return formTypeId;
	}
	public void setFormTypeId(long formTypeId) {
		this.formTypeId = formTypeId;
	}
	public String getFormTypeName() {
		return formTypeName;
	}
	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}
	public FormType() {
		super();
	}
	
	
}
