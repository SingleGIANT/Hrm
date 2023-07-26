package com.example.hrm_new.service.training;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.training.TraineeDetails;
import com.example.hrm_new.repository.training.TraineeDetailsRepository;

@Service
public class TraineeDetailsService {
	
	@Autowired
	private TraineeDetailsRepository repo;
	
	public Iterable<TraineeDetails> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(TraineeDetails traineeDetails) {
		repo.save(traineeDetails);
	}
	
	
	public void save(TraineeDetails traineeDetails) {
		repo.save(traineeDetails);

		}
	
	public TraineeDetails findById(Long traineeDetailsId) {
		return repo.findById(traineeDetailsId).get();

		}
	
	public void deleteTraineeDetailsById(Long traineeDetailsId) {
		repo.deleteById(traineeDetailsId);
	}
	

	public Optional<TraineeDetails> getTraineeDetailsById(Long traineeDetailsId) {
		return	repo.findById(traineeDetailsId);
		 
	}
	

}
