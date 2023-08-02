package com.example.hrm_new.service.employee;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.ProjectWork;
import com.example.hrm_new.repository.employee.ProjectWorkReposirory;


@Service
public class ProjectWorkService {
	
	@Autowired
    private ProjectWorkReposirory repo;
    
    public List<ProjectWork> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(ProjectWork projectWork) {
        repo.save(projectWork);
    }

    public ProjectWork getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

	

}
