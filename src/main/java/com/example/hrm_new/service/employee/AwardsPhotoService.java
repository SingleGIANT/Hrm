package com.example.hrm_new.service.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.employee.AwardsPhoto;
import com.example.hrm_new.repository.employee.AwardsPhotoRepository;

@Service
public class AwardsPhotoService {
	
	
	@Autowired
    private AwardsPhotoRepository awardsPhotoRepository;

    public void create(AwardsPhoto awardsPhoto) {
        awardsPhotoRepository.save(awardsPhoto);
    }

    public List<AwardsPhoto> findByAwardsId(long awardsId) {
        return awardsPhotoRepository.findByAwardsAwardsId(awardsId);
    }

	public AwardsPhoto createAwardsPhoto(AwardsPhoto awardsPhoto) {
		 return awardsPhotoRepository.save(awardsPhoto);
		
	}

	public List<AwardsPhoto> getAwardsPhotosByAwardsId(long awardsId) {
        return awardsPhotoRepository.findByAwardsAwardsId(awardsId);
    }

	  public Optional<AwardsPhoto> getAwardsPhotoById(long awardsPhotoId) {
	        return awardsPhotoRepository.findById(awardsPhotoId);
	    }

}
