package com.example.hrm_new.entity.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="relationtype")
public class RelationType {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long relationTypeId;
	private String  relationType;
	public Long getRelationTypeId() {
		return relationTypeId;
	}
	public void setRelationTypeId(Long relationTypeId) {
		this.relationTypeId = relationTypeId;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public RelationType() {
		super();
	}
	
	

}
