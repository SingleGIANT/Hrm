package com.example.hrm_new.service.employee;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.entity.employee.Promotions;
import com.example.hrm_new.repository.employee.PromotionsRepository;

@Service
public class PromotionsService {

	@Autowired
    private PromotionsRepository repo;
    
    public List<Promotions> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(Promotions Promotions) {
        repo.save(Promotions);
    }
  
    public Promotions getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

public List<Map<String,Object>>AllFine(){
	return repo.GoodAllWorks();
	
}

}
