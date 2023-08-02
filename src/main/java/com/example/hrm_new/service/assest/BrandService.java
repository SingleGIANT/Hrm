package com.example.hrm_new.service.assest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.Assest.Brand;
import com.example.hrm_new.repository.assest.BrandRepository;

@Service
public class BrandService {
	
	@Autowired
	private BrandRepository  Repo;

	public Iterable<Brand> listAll() {
		return this.Repo.findAll();

	}

	public void SaveorUpdate(Brand brand) {
		Repo.save(brand);
	}

	public void save(Brand brand) {
		Repo.save(brand);

	}

	public Brand findById(Long brand_id) {
		return Repo.findById(brand_id).get();

	}

	public void deleteBrandById(Long brand_id) {
		Repo.deleteById(brand_id);
	}

	public Optional<Brand> getBrandById(Long brand_id) {
		return Repo.findById(brand_id);

	}
	

}
