package com.example.hrm_new.service.assest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.Assest.KeyboardBrand;
import com.example.hrm_new.entity.Assest.MouseBrand;
import com.example.hrm_new.repository.assest.KeyboardBrandRepository;
import com.example.hrm_new.repository.assest.MouseBrandRepository;
@Service
public class MouseBrandService {

	@Autowired
	private MouseBrandRepository  Repo;

	public Iterable<MouseBrand> listAll() {
		return this.Repo.findAll();

	}

	public void SaveorUpdate(MouseBrand mouseBrand) {
		Repo.save(mouseBrand);
	}

	public void save(MouseBrand keyboardBrand) {
		Repo.save(keyboardBrand);

	}

	public MouseBrand findById(Long mouse_brand_id) {
		return Repo.findById(mouse_brand_id).get();

	}

	public void deleteMouseBrandById(Long mouse_brand_id) {
		Repo.deleteById(mouse_brand_id);
	}

	public Optional<MouseBrand> getMouseBrandById(Long mouse_brand_id) {
		return Repo.findById(mouse_brand_id);

	}


}
