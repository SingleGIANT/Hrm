package com.example.hrm_new.service.employee;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Role;
import com.example.hrm_new.repository.employee.RoleRepository;




@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repo;
	
	public Iterable<Role> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(Role role) {
		repo.save(role);
	}
	
	
	public void save(Role role) {
		repo.save(role);

		}
	
	public Role findById(Long role) {
		return repo.findById(role).get();

		}
	
	public void deleteDepartmentRollById(Long role) {
		repo.deleteById(role);
	}
	

	public Optional<Role> getdepartmentRollById(Long role) {
		return	repo.findById(role);
		 
	}
	public void deleteById(Long roleId) {
		repo.deleteById(roleId);
		
	}

}
