package com.example.hrm_new.service.worksheet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.worksheet.WorkSheet;
import com.example.hrm_new.repository.worksheet.WorkSheetRepository;


@Service
public class WorkSheetService {
	
	@Autowired
	private WorkSheetRepository repo;
	
	public Iterable<WorkSheet> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(WorkSheet workSheet) {
		repo.save(workSheet);
	}
	
	
	public void save(WorkSheet workSheet) {
		repo.save(workSheet);

		}
	
	public WorkSheet findById(Long workSheetId) {
		return repo.findById(workSheetId).get();

		}
	
	public void deleteWorkSheetById(Long workSheetId) {
		repo.deleteById(workSheetId);
	}
	

	public Optional<WorkSheet> getWorkSheetById(Long workSheetId) {
		return	repo.findById(workSheetId);
		 
	}
	
	public 	List<Map<String,Object>>  allWorkSheetDetails(){
		return repo.allWorkSheetDetails();
	}


}
