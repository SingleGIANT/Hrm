package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Terminations;
import com.example.hrm_new.repository.employee.TerminationsRepository;



@Service
public class TerminationsService {
	
	
	@Autowired
    private TerminationsRepository repo;
    
    public List<Terminations> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(Terminations terminations) {
        repo.save(terminations);
    }

    public Terminations getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }



}
