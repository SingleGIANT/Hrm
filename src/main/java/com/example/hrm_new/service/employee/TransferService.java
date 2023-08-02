package com.example.hrm_new.service.employee;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Transfer;
import com.example.hrm_new.repository.employee.TransferRepository;





@Service
public class TransferService {
	
	@Autowired
    private TransferRepository repo;
    
    public List<Transfer> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(Transfer transfer) {
        repo.save(transfer);
    }

    public Transfer getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

    public List<Map<String,Object>>AllTravel(){
    	return repo.AllTravel();
    }
}
