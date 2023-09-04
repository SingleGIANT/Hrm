package com.example.hrm_new.controller.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.hrm_new.entity.employee.Role;
import com.example.hrm_new.repository.employee.RoleRepository;
import com.example.hrm_new.service.employee.RoleService;

@RestController
@CrossOrigin
public class RoleController {

	@Autowired
	private RoleService RoleService;
	
	@Autowired
	private RoleRepository repo;

	@GetMapping("/role")
	public ResponseEntity<?> getDetails() {

		try {

			Iterable<Role> RoleDetails = RoleService.listAll();

			return new ResponseEntity<>(RoleDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/role/save")

	public ResponseEntity<?> saveRole(@RequestBody Role Role) {

		try {

			RoleService.SaveorUpdate(Role);

			return ResponseEntity.status(HttpStatus.CREATED).body("Role details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving Role details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}
	


	@PutMapping("/role/edit/{roleId}")

	public ResponseEntity<Role> updateRoleId(@PathVariable("roleId") Long RoleId, @RequestBody Role RoleIdDetails) {

		try {

			Role existingRole = RoleService.findById(RoleId);

			if (existingRole == null) {

				return ResponseEntity.notFound().build();

			}

			existingRole.setRoleName(RoleIdDetails.getRoleName());

			RoleService.save(existingRole);

			return ResponseEntity.ok(existingRole);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

	@DeleteMapping("/role/delete/{roleId}")

	public ResponseEntity<String> deletRoleName(@PathVariable("roleId") Long roleId) {

		RoleService.deleteById(roleId);

		return ResponseEntity.ok("Role deleted successfully");

	}

	@GetMapping("/role/employee/designation")
	private List<Map<String, Object>> findAllBYCategoryAndProduct() {
		List<Map<String, Object>> orderList = new ArrayList<>();
		List<Map<String, Object>> iterable = repo.findAllProductsByCategory();
		Map<String, List<Map<String, Object>>> categoryGroupMap = StreamSupport.stream(iterable.spliterator(), false)
				.collect(Collectors.groupingBy(action -> action.get("role_id").toString()));
		System.out.println(categoryGroupMap);
		for (Entry<String, List<Map<String, Object>>> list : categoryGroupMap.entrySet()) {
			Map<String, Object> categoryMap = new HashMap<>();
			categoryMap.put("role_id", list.getKey());
			categoryMap.put("role_name", list.getValue().get(0).get("role_name"));
			categoryMap.put("employee", categoryGroupMap.get(list.getKey()));
			orderList.add(categoryMap);
		}
		return orderList;
	}


}
