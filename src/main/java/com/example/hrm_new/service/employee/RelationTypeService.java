package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.RelationType;
import com.example.hrm_new.repository.employee.RelationTypeRepository;

@Service
public class RelationTypeService {
	
	@Autowired
    private RelationTypeRepository repo;
    
    public List<RelationType> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(RelationType RelationType) {
        repo.save(RelationType);
    }

    public RelationType getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

}
