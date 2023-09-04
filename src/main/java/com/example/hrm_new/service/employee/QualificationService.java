package com.example.hrm_new.service.employee;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Qualification;
import com.example.hrm_new.repository.employee.QualificationRepository;

@Service
public class QualificationService {
	@Autowired
	private  QualificationRepository repo;

	 public void create(Qualification qualification) {
		 repo.save(qualification);
	    }

	    public List<Qualification> getAllQualifications() {
	        return repo.findAll();
	    }

	    public Optional<Qualification> getQualificationById(long id) {
	        return repo.findById(id);
	    }

	    public Optional<Qualification> getQualificationsByEmployeeId(long employeeId) {
	        return repo.findById(employeeId);
	    }

		public Optional<Qualification> getAwardsPhotoById(Long id) {
			return repo.findById(id);
		}

		public Qualification update(Qualification qualification) {
			return repo.save(qualification);
			
		}
		
		public List<Map<String, Object>> getAllQualificationsByImage(){
			return repo.getAllQualificationsByImage();
		}

	    public Optional<Qualification> getQualificationById1(long id) {
	        return repo.findById(id);
	    }
}
