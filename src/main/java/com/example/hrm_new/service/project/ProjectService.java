package com.example.hrm_new.service.project;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.repository.project.ProjectRepository;


@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Iterable<Project> listAll(){
		return  this.projectRepository.findAll();
		
		
	}
	public void SaveorUpdate(Project project) {
		projectRepository.save(project);
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

}
