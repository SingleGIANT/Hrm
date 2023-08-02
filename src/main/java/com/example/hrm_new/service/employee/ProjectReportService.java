package com.example.hrm_new.service.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.ProjectReport;
import com.example.hrm_new.repository.employee.ProjectReportRepository;


@Service
public class ProjectReportService {
	
	
	@Autowired
    private ProjectReportRepository repo;
    
    public List<ProjectReport> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(ProjectReport ProjectReport) {
        repo.save(ProjectReport);
    }

    public ProjectReport getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

}
