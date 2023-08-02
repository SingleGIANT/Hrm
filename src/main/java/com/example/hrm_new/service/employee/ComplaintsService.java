package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Complaints;
import com.example.hrm_new.repository.employee.ComplaintsRepository;



@Service
public class ComplaintsService {
	
	@Autowired
    private ComplaintsRepository repo;
    
    public List<Complaints> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(Complaints complaints) {
        repo.save(complaints);
    }

    public Complaints getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }



}
