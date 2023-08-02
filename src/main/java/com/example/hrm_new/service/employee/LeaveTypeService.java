package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.LeaveType;
import com.example.hrm_new.repository.employee.LeaveTypeRepository;



@Service
public class LeaveTypeService {
	
	@Autowired
	private LeaveTypeRepository repo;

	public List<LeaveType> listAll() {
		return repo.findAll();
	}

	public void saveOrUpdate(LeaveType LeaveType) {
		repo.save(LeaveType);
	}

	public LeaveType getById(long id) {
		return repo.findById(id).get();
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

}
