package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.EmployeeExit;
import com.example.hrm_new.repository.employee.EmployeeExitRepository;

@Service
public class EmployeeExitService {
	
	@Autowired
    private EmployeeExitRepository repo;
    
    public List<EmployeeExit> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(EmployeeExit EmployeeExit) {
        repo.save(EmployeeExit);
    }

    public EmployeeExit getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

}
