package com.example.hrm_new.service.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Qualification;
import com.example.hrm_new.entity.employee.Qualifications;
import com.example.hrm_new.repository.employee.QualificationsRepository;

@Service
public class QualificationsService {
	@Autowired
	private QualificationsRepository repo;
	
	
	 public List< Qualifications> getAllQualifications() {
        return repo.findAll();
    }

    public  Qualifications saveQualification( Qualifications qualifications) {
        return repo.save(qualifications);
    }

    public List<Qualifications> getAllQualifications1() {
        return repo.findAll();
    }

    public Qualifications getQualification(long qualificationId) {
        // Implement the logic to retrieve a qualification by ID
        return repo.findById(qualificationId).orElse(null);
    }
    public Optional<Qualifications> getQualificationById(long id) {
        return repo.findById(id);
    }
    
    public Qualifications getQualificationById(Long qualificationId) {
        return repo.findById(qualificationId).orElse(null);
    }



   
}
