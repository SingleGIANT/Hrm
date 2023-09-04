package com.example.hrm_new.entity.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleId;
	private String roleName;
	

	



	public Role() {
		super();
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}


	
	
	
	

}
