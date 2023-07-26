package com.example.hrm_new.service.organization;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.organization.Company;
import com.example.hrm_new.repository.organization.CompanyRepository;

@Service
public class CompanyService {

@Autowired
	
	private CompanyRepository repo;
	
	
	public List<Company> listAll(){
		return  this.repo.findAll();
		
		
	}
	public void SaveorUpdate(Company company) {
		repo.save(company);
	}
	
	
	public void save(Company company) {
		repo.save(company);

		}
	
	public Company findById(Long companyId) {
		return repo.findById(companyId).get();

		}
	
	public void deleteCompanyById(Long companyId) {
		repo.deleteById(companyId);
	}
	

	public Optional<Company> getCompanyById(Long companyId) {
		return	repo.findById(companyId);
		 
	}

}
