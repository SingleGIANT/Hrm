package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.EmployeeLeave;
import com.example.hrm_new.repository.employee.EmployeeLeaveRepository;


@Service
public class EmployeeLeaveService {
	
	@Autowired
    private EmployeeLeaveRepository repo;
    
    public List<EmployeeLeave> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(EmployeeLeave EmployeeLeave) {
        repo.save(EmployeeLeave);
    }

    public EmployeeLeave getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

}
