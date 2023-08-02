package com.example.hrm_new.service.customer;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.customer.Customer;
import com.example.hrm_new.repository.customer.CustomerRepository;




@Service
public class CustomerService {

	
	@Autowired
    private CustomerRepository repo;
    
    public List<Customer> listAll() {
        return repo.findAll();
    }

    public void saveOrUpdate(Customer customer) {
        repo.save(customer);
    }

    public Customer getById(long id) {
        return repo.findById(id).get();
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }
    public 	List <Map<String,Object>>  allCustomer(){
		return repo.allDetails();
	}
    
 
 
	

}
