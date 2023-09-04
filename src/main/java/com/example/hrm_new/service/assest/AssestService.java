package com.example.hrm_new.service.assest;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hrm_new.entity.Assest.Assest;
import com.example.hrm_new.repository.assest.AssestRepository;

@Service
public class AssestService {
	
	@Autowired
	private AssestRepository  Repo;

	public Iterable<Assest> listAll() {
		return this.Repo.findAll();

	}

	public void SaveorUpdate(Assest assest) {
		Repo.save(assest);
	}

	public void save(Assest assest) {
		Repo.save(assest);

	}

	public Assest findById(Long assest_id) {
		return Repo.findById(assest_id).get();

	}

	public void deleteAssestIdById(Long assest_id) {
		Repo.deleteById(assest_id);
	}

	 public Assest getById(long id) {
	        return Repo.findById(id).get();
	    }

	public List<Map<String, Object>> allAssestDetails() {
		return Repo.allAsesstDetails();
	}

	
	

}
