package com.example.hrm_new.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.customer.FormType;
import com.example.hrm_new.repository.customer.FormTypeRepository;



@Service
public class FormTypeService {
		

	@Autowired
    private FormTypeRepository repo;
	
	  public List<FormType> listAll() {
	        return repo.findAll();
	    }

	    public void saveOrUpdate(FormType FormType) {
	        repo.save(FormType);
	    }

	    public FormType getById(long id) {
	        return repo.findById(id).get();
	    }

	    public void deleteById(long id) {
	        repo.deleteById(id);
	    }

}
