package com.example.hrm_new.controller.training;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrm_new.entity.project.Project;
import com.example.hrm_new.entity.training.TraineeClass;
import com.example.hrm_new.entity.training.TraineeDetails;
import com.example.hrm_new.repository.training.TraineeClassRepository;
import com.example.hrm_new.service.training.TraineeClassService;
import com.example.hrm_new.service.training.TraineeDetailsService;

@RestController
@CrossOrigin
public class TraineeClassController {
	

	@Autowired
	private TraineeClassService traineeClassservice;
	
	@Autowired
	private TraineeClassRepository repo;
	
	@GetMapping("/traineeClassDetails")
	public ResponseEntity<?> getDetails() {

		try {

			Iterable<TraineeClass> traineeDetails = traineeClassservice.listAll();
			return new ResponseEntity<>(traineeDetails, HttpStatus.OK);
		} catch (Exception e) {
			String errorMessage = "An error occurred while retrieving l details.";
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	
	@PostMapping("/traineeClassDetails/save")

	public ResponseEntity<?> saveTraineeClass(@RequestBody TraineeClass traineeClass) {

		try {
			
		
			traineeClassservice.SaveorUpdate(traineeClass);

			return ResponseEntity.status(HttpStatus.CREATED).body("TraineeClass details saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving company details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}



	@RequestMapping("/traineeClassDetails/{traineeClassId}")

	private Optional<TraineeClass> getTraineeClass(@PathVariable(name = "traineeClassId") long traineeClassId) {

		return traineeClassservice.getTraineeClassById(traineeClassId);

	}
		
	@PutMapping("/traineeClassDetails/edittraineeClassDetails/{traineeClassId}")

	public ResponseEntity<TraineeClass> updateTraineeClass(@PathVariable("traineeClassId") Long traineeClassId, @RequestBody TraineeClass traineeClassDetails) {

		try {

			TraineeClass existingTraineeClass= traineeClassservice.findById(traineeClassId);

			if (existingTraineeClass == null) {

				return ResponseEntity.notFound().build();

			}

			existingTraineeClass.setTrainerName(traineeClassDetails.getTrainerName());
			existingTraineeClass.setTraineeDetailsId(traineeClassDetails.getTraineeDetailsId());
			existingTraineeClass.setTotalDuration(traineeClassDetails.getTotalDuration());
			existingTraineeClass.setStartDate(traineeClassDetails.getStartDate());
			existingTraineeClass.setSectionName(traineeClassDetails.getSectionName());
			existingTraineeClass.setTotalModules(traineeClassDetails.getTotalModules());
		
			traineeClassservice.save(existingTraineeClass);

			return ResponseEntity.ok(existingTraineeClass);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	
	@DeleteMapping("/traineeClassDetails/traineeClassDetailsdelete/{traineeClassId}")

	public ResponseEntity<String> deleteTraineeClass(@PathVariable("traineeClassId") Long traineeClassId) {

		traineeClassservice.deleteTraineeClassById(traineeClassId);

		return ResponseEntity.ok("TraineeClass deleted successfully");

	}

	@GetMapping("/TraineeClassdetails/view")
	public List<Map<String, Object>> allTraineeClassDetails() {
		return traineeClassservice.allTraineeClassDetails();

	}

	@GetMapping("/TraineeClassdetails/trainee_class_id")
	private List<Map<String, Object>> expenseid_based_traineeClass() {
		List<Map<String, Object>> traineeClasslist = new ArrayList<>();
		List<Map<String, Object>> list = traineeClassservice.allTraineeClassDetails();
		Map<String, List<Map<String, Object>>> traineeClasssGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("trainee_class_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : traineeClasssGroupMap.entrySet()) {
			Map<String, Object> traineeClasssMap = new HashMap<>();
			traineeClasssMap.put("TraineeClassId", totalList.getKey());
			traineeClasssMap.put("TraineeClass Details", totalList.getValue());
			traineeClasslist.add(traineeClasssMap);

		}
		return traineeClasslist;

	}

	@GetMapping("/TraineeClassdetails1/{trainee_class_id}")
	private List<Map<String, Object>> idbasedExpense(@PathVariable Long trainee_class_id) {
		List<Map<String, Object>> traineeClasslist = new ArrayList<>();
		List<Map<String, Object>> list = traineeClassservice.findAllByTraineeClassId(trainee_class_id);
		Map<String, List<Map<String, Object>>> traineeClassGroupMap = StreamSupport.stream(list.spliterator(), false)
				.collect(Collectors.groupingBy(action -> String.valueOf(action.get("trainee_class_id"))));

		for (java.util.Map.Entry<String, List<Map<String, Object>>> totalList : traineeClassGroupMap.entrySet()) {
			Map<String, Object> traineeClassMap = new HashMap<>();
			traineeClassMap.put("TraineeClassId", totalList.getKey());
			traineeClassMap.put("TraineeClass Details", totalList.getValue());
			traineeClasslist.add(traineeClassMap);

		}
		return traineeClasslist;

	}
	@PostMapping("/findtrainingbydate")
	public List<TraineeClass> findByDateBetween(@RequestParam("fromdate") Date from, @RequestParam("todate") Date to) {
		return repo.findByStartDateBetween(from, to);

	}


}
