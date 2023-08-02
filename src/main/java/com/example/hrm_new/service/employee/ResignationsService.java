package com.example.hrm_new.service.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Resignations;
import com.example.hrm_new.repository.employee.ResignationsRepository;





@Service
public class ResignationsService {
	

	@Autowired
    private ResignationsRepository repo;
    
    public List<Resignations> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(Resignations resignations) {
        repo.save(resignations);
    }

    public Resignations getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

public List<Map<String,Object>>ALLOver(){
	return repo.AllGoat();
}

}
