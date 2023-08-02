package com.example.hrm_new.service.employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Awards;
import com.example.hrm_new.repository.employee.AwardsRepository;

@Service
public class AwardsService {
	
	
	 @Autowired
	    private AwardsRepository awardsRepository;

	    public void create(Awards awards) {
	        awardsRepository.save(awards);
	    }
	    
	    public Optional<Awards> findById(long awardsId) {
	        return awardsRepository.findById(awardsId);
	    }


	    public Awards createAwards(Awards awards) {
	        return awardsRepository.save(awards);
	    }

	    public Optional<Awards> getAwardsById(long awardsId) {
	        return awardsRepository.findById(awardsId);
	    }

	    public List<Awards> getAllAwards() {
	        return awardsRepository.findAll();
	    }

	    public Awards update(Awards awards) {
	        return awardsRepository.save(awards);
	    }


}
