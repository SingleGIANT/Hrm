package com.example.hrm_new.service.project;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.Employee;
import com.example.hrm_new.entity.payroll.PaymentType;
import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.repository.project.ProjectRepository;


@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public List<Project> listAll(){
		return  projectRepository.findAll();
	}
	
	public Project SaveorUpdate(Project project) {
		return projectRepository.save(project);
	}
	
	
	public void save(Project project) {
		projectRepository.save(project);

		}
	
	public Project findById(Long projectId) {
		return projectRepository.findById(projectId).get();

		}
	
	public void deleteProjectById(Long projectId) {
		projectRepository.deleteById(projectId);
	}
	

	public Optional<Project> getProjectById(Long projectId) {
		return	projectRepository.findById(projectId);
		 
	}
	
	public Project getprojectById(Long ProjectId) {
		return projectRepository.findById(ProjectId).orElse(null);
	}
}
