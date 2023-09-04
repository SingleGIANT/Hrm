package com.example.hrm_new.service.payroll;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.payroll.PayRoll;
import com.example.hrm_new.repository.payroll.PayRollRepository;

@Service
public class PayRollService {
	

	@Autowired
	private PayRollRepository repo;
	
	
	public Iterable<PayRoll> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(PayRoll payRoll) {
		repo.save(payRoll);
	}
	
	
	public void save(PayRoll payRoll) {
		repo.save(payRoll);

		}
	
	public PayRoll findById(Long payRollId) {
		return repo.findById(payRollId).get();

		}
	
	public void deletePayRollIdById(Long payRollId) {
		repo.deleteById(payRollId);
	}
	

	public Optional<PayRoll> getPayRolltById(Long payRollId) {
		return	repo.findById(payRollId);
		 
	}
	
	public 	List <Map<String,Object>>  allPayRollDetails(){
		return repo.allPayRollDetails();
	}

	
	public 	List <Map<String,Object>>  findAllByPayRollId(Long payRollId){
		return repo.allDetailsOfPayRoll(payRollId);
		
	}
	public 	List <Map<String,Object>>  findAllByEmployeeId(Long employee_id){
		return repo.DetailsOfPayRollByEmployeeId(employee_id);
		
	}
//	public 	List <Map<String,Object>>  highestSlaryByMonth(Month month){
//		return repo.highestSlaryByMonth(month);
//		
//	}

}
