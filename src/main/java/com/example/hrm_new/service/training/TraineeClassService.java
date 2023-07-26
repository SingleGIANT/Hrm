package com.example.hrm_new.service.training;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.hrm_new.entity.training.TraineeClass;
import com.example.hrm_new.repository.training.TraineeClassRepository;

@Service
public class TraineeClassService {
	
	@Autowired
	
private TraineeClassRepository repo;
	
	public Iterable<TraineeClass> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(TraineeClass traineeClass) {
		repo.save(traineeClass);
	}
	
	
	public void save(TraineeClass traineeClass) {
		repo.save(traineeClass);

		}
	
	public TraineeClass findById(Long traineeClassId) {
		return repo.findById(traineeClassId).get();

		}
	
	public void deleteTraineeClassById(Long traineeClassId) {
		repo.deleteById(traineeClassId);
	}
	

	public Optional<TraineeClass> getTraineeClassById(Long traineeClassId) {
		return	repo.findById(traineeClassId);
		 
	}
	public 	List <Map<String,Object>> allTraineeClassDetails(){
		return repo.allTraineeClassDetails();
	}
	
	public 	List <Map<String,Object>>  findAllByTraineeClassId(Long trainee_class_id){
		return repo.allDetailsOfTraineeClass(trainee_class_id);
		
	}
	
	

}
