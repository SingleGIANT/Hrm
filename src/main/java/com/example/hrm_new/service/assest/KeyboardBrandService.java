package com.example.hrm_new.service.assest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.Assest.KeyboardBrand;
import com.example.hrm_new.repository.assest.KeyboardBrandRepository;

@Service
public class KeyboardBrandService {
	
	@Autowired
	private KeyboardBrandRepository  Repo;

	public Iterable<KeyboardBrand> listAll() {
		return this.Repo.findAll();

	}

	public void SaveorUpdate(KeyboardBrand keyboardBrand) {
		Repo.save(keyboardBrand);
	}

	public void save(KeyboardBrand keyboardBrand) {
		Repo.save(keyboardBrand);

	}

	public KeyboardBrand findById(Long keyboard_brand_id) {
		return Repo.findById(keyboard_brand_id).get();

	}

	public void deleteKeyboardBrandById(Long keyboard_brand_id) {
		Repo.deleteById(keyboard_brand_id);
	}

	public Optional<KeyboardBrand> getKeyboardBrandById(Long keyboard_brand_id) {
		return Repo.findById(keyboard_brand_id);

	}

}
