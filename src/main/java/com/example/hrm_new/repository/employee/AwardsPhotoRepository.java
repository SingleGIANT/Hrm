package com.example.hrm_new.repository.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrm_new.entity.employee.AwardsPhoto;

public interface AwardsPhotoRepository extends JpaRepository<AwardsPhoto, Long>{

	List<AwardsPhoto> findByAwardsAwardsId(long awardsId);

}
