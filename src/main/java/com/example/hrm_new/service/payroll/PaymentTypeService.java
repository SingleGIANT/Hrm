package com.example.hrm_new.service.payroll;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.payroll.PaymentType;
import com.example.hrm_new.repository.payroll.PaymentTypeRepository;
@Service
public class PaymentTypeService {
	
	@Autowired
	private PaymentTypeRepository repo;
	
	
	public Iterable<PaymentType> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(PaymentType paymentType) {
		repo.save(paymentType);
	}
	
	
	public void save(PaymentType paymentType) {
		repo.save(paymentType);

		}
	
	public PaymentType findById(Long paymentTypeId) {
		return repo.findById(paymentTypeId).get();

		}
	
	public void deletePaymentTypeIdById(Long paymentTypeId) {
		repo.deleteById(paymentTypeId);
	}
	

	public Optional<PaymentType> getPaymentTypeById(Long paymentTypeId) {
		return	repo.findById(paymentTypeId);
		 
	}

}
