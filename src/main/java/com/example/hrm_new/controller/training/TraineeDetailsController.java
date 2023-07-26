package com.example.hrm_new.controller.training;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.example.hrm_new.entity.training.TraineeDetails;
import com.example.hrm_new.entity.worksheet.WorkSheet;
import com.example.hrm_new.repository.training.TraineeDetailsRepository;
import com.example.hrm_new.service.training.TraineeDetailsService;
import com.example.hrm_new.service.worksheet.WorkSheetService;

@RestController
@CrossOrigin
public class TraineeDetailsController {
	

	@Autowired
	private TraineeDetailsService traineeDetailsservice;
	
	@Autowired
	private TraineeDetailsRepository repo;
	
	@GetMapping("/traineeDetails")

	public ResponseEntity<?> getDetails() {

		try {

			Iterable<TraineeDetails> traineeDetails = traineeDetailsservice.listAll();

			return new ResponseEntity<>(traineeDetails, HttpStatus.OK);

		} catch (Exception e) {

			String errorMessage = "An error occurred while retrieving l details.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/traineeDetails/save")

	public ResponseEntity<?> saveBank(@RequestBody TraineeDetails traineeDetails) {

		try {
			
			traineeDetails.setStatus(true);
			traineeDetailsservice.SaveorUpdate(traineeDetails);

			return ResponseEntity.status(HttpStatus.CREATED).body("TraineeDetails saved successfully.");

		} catch (Exception e) {

			String errorMessage = "An error occurred while saving company details.";

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

		}

	}



	@RequestMapping("/TraineeDetails/{traineeDetailsId}")

	private Optional<TraineeDetails> getTraineeDetails(@PathVariable(name = "traineeDetailsId") long traineeDetailsId) {

		return traineeDetailsservice.getTraineeDetailsById(traineeDetailsId);

	}
		
	@PutMapping("/TraineeDetails/editTraineeDetails/{traineeDetailsId}")

	public ResponseEntity<TraineeDetails> updateTraineeDetails(@PathVariable("traineeDetailsId") Long traineeDetailsId, @RequestBody TraineeDetails workSheetDetails) {

		try {

			TraineeDetails existingTraineeDetails= traineeDetailsservice.findById(traineeDetailsId);

			if (existingTraineeDetails == null) {

				return ResponseEntity.notFound().build();

			}

			existingTraineeDetails.setName(workSheetDetails.getName());
			existingTraineeDetails.setNameId(workSheetDetails.getNameId());
			existingTraineeDetails.setPhoneNumber(workSheetDetails.getPhoneNumber());
			existingTraineeDetails.setCountry(workSheetDetails.getCountry());
			existingTraineeDetails.setEmail(workSheetDetails.getEmail());
			existingTraineeDetails.setAddress(workSheetDetails.getAddress());
			existingTraineeDetails.setLocation(workSheetDetails.getLocation());
			existingTraineeDetails.setState(workSheetDetails.getState());
			existingTraineeDetails.setGender(workSheetDetails.getGender());
			existingTraineeDetails.setEmail(workSheetDetails.getEmail());
		
			existingTraineeDetails.setStatus(workSheetDetails.isStatus());
			
			traineeDetailsservice.save(existingTraineeDetails);

			return ResponseEntity.ok(existingTraineeDetails);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}
	
	@DeleteMapping("/TraineeDetails/TraineeDetailsdelete/{traineeDetailsId}")

	public ResponseEntity<String> deleteTraineeDetails(@PathVariable("traineeDetailsId") Long traineeDetailsId) {

		traineeDetailsservice.deleteTraineeDetailsById(traineeDetailsId);

		return ResponseEntity.ok("TraineeDetails deleted successfully");

	}
	@PostMapping("/countoftrainees")
	public 	List <Map<String,Object>> findByStatus(@Param("status") boolean status){
		return repo.findByStatus(status);

}
	@PostMapping("/worksheetbycountryandstate")
	public List<TraineeDetails> findByStatus(@Param("country")String country,@Param("state")String state){
		return repo.findByCountryAndState(country,state);

  }
	

}
