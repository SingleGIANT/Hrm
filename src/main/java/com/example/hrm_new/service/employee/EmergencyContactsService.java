package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.EmergencyContacts;
import com.example.hrm_new.repository.employee.EmergencyContactsRepository;


@Service
public class EmergencyContactsService {
	

	@Autowired
    private EmergencyContactsRepository repo;
    
    public List<EmergencyContacts> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(EmergencyContacts EmergencyContacts) {
        repo.save(EmergencyContacts);
    }

    public EmergencyContacts getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

}
