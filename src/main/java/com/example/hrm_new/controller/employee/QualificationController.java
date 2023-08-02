package com.example.hrm_new.controller.employee;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;
import com.example.hrm_new.entity.employee.Qualification;
import com.example.hrm_new.service.employee.QualificationService;

@RestController
@CrossOrigin
public class QualificationController {

	@Autowired
	private QualificationService service;

	@PostMapping("/qualification")
	public ResponseEntity<String> addImagePost(@RequestParam("employeeId") String employeeId,
			@RequestParam("resume") MultipartFile resumeFile, @RequestParam("photo") MultipartFile photoFile,
			@RequestParam("ten") MultipartFile tenFile, @RequestParam("aadhar") MultipartFile aadharFile,
			@RequestParam("degree") MultipartFile degreeFile, @RequestParam("bankBook") MultipartFile bankBookFile,
			@RequestParam("twelve") MultipartFile twelveFile, @RequestParam("panno") MultipartFile pannoFile,
			@RequestParam("highestQualification") String highestQualification) {
		try {
			Qualification qualification = new Qualification();
			qualification.setEmployeeId(employeeId);
			qualification.setHighestQualification(highestQualification);

			qualification.setResume(convertToBlob(resumeFile));
			qualification.setPhoto(convertToBlob(photoFile));
			qualification.setTen(convertToBlob(tenFile));
			qualification.setAadhar(convertToBlob(aadharFile));
			qualification.setDegree(convertToBlob(degreeFile));
			qualification.setBankBook(convertToBlob(bankBookFile));
			qualification.setTwelve(convertToBlob(twelveFile));
			qualification.setPanno(convertToBlob(pannoFile));

			service.create(qualification);
			long qualificationId = qualification.getQualificationId();

			return ResponseEntity.ok("Images added successfully. Qualification ID: " + qualificationId);
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding images: " + e.getMessage());
		}
	}

	private Blob convertToBlob(MultipartFile file) throws IOException, SQLException {
		byte[] bytes = file.getBytes();
		return new javax.sql.rowset.serial.SerialBlob(bytes);
	}

	
	
	
	
//	@GetMapping("/display1")
//	public ResponseEntity<List<Qualification>> getAllQualifications() {
//	    List<Qualification> qualifications = service.getAllQualifications();
//	    List<Qualification> qualificationResponses = new ArrayList<>();
//
//	    for (Qualification qualification : qualifications) {
//	        // Generate random number for each qualification
//	        int randomNumber = generateRandomNumber();
//
//	        // Construct the image URL
//	        String imageUrl = "/image/" + randomNumber + "/" + qualification.getQualificationId();
//
//	        // Create a new Qualification object to hold the modified data
//	        Qualification qualificationResponse = new Qualification();
//	        qualificationResponse.setQualificationId(qualification.getQualificationId());
//	        qualificationResponse.setEmployeeId(qualification.getEmployeeId());
//	        qualificationResponse.setHighestQualification(qualification.getHighestQualification());
//	        qualificationResponse.setPhoto(qualification.getPhoto(imageUrl));
//	        qualificationResponse.setResume(qualification.getResume(imageUrl));
//	        qualificationResponse.setTen(qualification.getTen(imageUrl));
//	        qualificationResponse.setAadhar(qualification.getAadhar(imageUrl));
//	        qualificationResponse.setDegree(qualification.getDegree(imageUrl));
//	        qualificationResponse.setPanno(qualification.getPanno(imageUrl));
//	        qualificationResponse.setBankBook(qualification.getBankBook(imageUrl));
//	        qualificationResponse.setTwelve(qualification.getTwelve(imageUrl));
//
//	        qualificationResponses.add(qualificationResponse);
//	    }
//
//	    return ResponseEntity.ok().body(qualificationResponses);
//	}
//
//	private int generateRandomNumber() {
//	    Random random = new Random();
//	    return random.nextInt(1000);
//	}
//
//	
//	@GetMapping("/display")
//	public ResponseEntity<List<Qualification>> getAllQualifications1() {
//	    List<Qualification> qualifications = service.getAllQualifications();
//	    List<Qualification> qualificationResponses = new ArrayList<>();
//
//	    for (Qualification qualification : qualifications) {
//	        // Generate random number for each qualification
//	        int randomNumber = generateRandomNumber();
//
//	        // Construct the image URL
//	        String imageUrl = "/image/" + randomNumber + "/" + qualification.getQualificationId();
//
//	        // Create a new Qualification object to hold the modified data
//	        Qualification qualificationResponse = new Qualification();
//	        qualificationResponse.setQualificationId(qualification.getQualificationId());
//	        qualificationResponse.setEmployeeId(qualification.getEmployeeId());
//	        qualificationResponse.setHighestQualification(qualification.getHighestQualification());
//	        qualificationResponse.setPhoto(imageUrl);
//	        qualificationResponse.setResume(imageUrl);
//	        qualificationResponse.setTen(imageUrl);
//	        qualificationResponse.setAadhar(imageUrl);
//	        qualificationResponse.setDegree(imageUrl);
//	        qualificationResponse.setPanno(imageUrl);
//	        qualificationResponse.setBankBook(imageUrl);
//	        qualificationResponse.setTwelve(imageUrl);
//
//	        qualificationResponses.add(qualificationResponse);
//	    }
//
//	    return ResponseEntity.ok().body(qualificationResponses);
//	}


//	@GetMapping("/qualification/{id}")
//	public ResponseEntity<byte[]> getQualification(@PathVariable("id") long id) {
//	    // Retrieve the qualification from the database
//	    Optional<Qualification> optionalQualification = service.getQualificationById(id);
//
//	    if (optionalQualification.isPresent()) {
//	        Qualification qualification = optionalQualification.get();
//
//	        // Set the appropriate content type for the response
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_PDF); // Adjust the media type according to your file type
//
//	        try {
//	            // Retrieve the byte array data from the Blob fields
//	            byte[] resumeData = qualification.getResume().getBytes(1, (int) qualification.getResume().length());
//	            byte[] photoData = qualification.getPhoto().getBytes(1, (int) qualification.getPhoto().length());
//
//	            // Set the byte array data and headers in the ResponseEntity
//	            return ResponseEntity.ok()
//	                    .headers(headers)
//	                    .body(resumeData);
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	        }
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}


//	@GetMapping("/qualification/{id}")
//	public ResponseEntity<Qualification> getQualification(@PathVariable("id") long id) {
//	    // Retrieve the qualification from the database
//	    Optional<Qualification> optionalQualification = service.getQualificationById(id);
//
//	    if (optionalQualification.isPresent()) {
//	        Qualification qualification = optionalQualification.get();
//
//	        // Set the URLs for each image individually
//	        String photoUrl = getImageUrl(qualification.getPhoto());
//	        qualification.setPhoto(photoUrl);
//
//	        String resumeUrl = getImageUrl(qualification.getResume());
//	        qualification.setResume(resumeUrl);
//
//	        String tenUrl = getImageUrl(qualification.getTen());
//	        qualification.setTen(tenUrl);
//
//	        String aadharUrl = getImageUrl(qualification.getAadhar());
//	        qualification.setAadhar(aadharUrl);
//
//	        String degreeUrl = getImageUrl(qualification.getDegree());
//	        qualification.setDegree(degreeUrl);
//
//	        String pannoUrl = getImageUrl(qualification.getPanno());
//	        qualification.setPanno(pannoUrl);
//
//	        String bankBookUrl = getImageUrl(qualification.getBankBook());
//	        qualification.setBankBook(bankBookUrl);
//
//	        String twelveUrl = getImageUrl(qualification.getTwelve());
//	        qualification.setTwelve(twelveUrl);
//
//	        return ResponseEntity.ok().body(qualification);
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}

//	private String getImageUrl(Blob blob) {
//	    try {
//	        // Convert the Blob to byte array
//	        byte[] data = blob.getBytes(1, (int) blob.length());
//
//	        // Generate a unique file name or ID to avoid conflicts
//	        String fileName = UUID.randomUUID().toString();
//
//	        // Save the byte array as a file on your server
//	        // Replace the "/path/to/your/uploads/directory" with the path to your desired uploads directory
//	        Path filePath = Path.of("/path/to/your/uploads/directory", fileName);
//	        Files.write(filePath, data);
//
//	        // Construct the absolute URL of the uploaded file
//	        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//	                .path("/uploads/image/")
//	                .path(fileName)
//	                .toUriString();
//
//	        return fileUrl;
//	    } catch (SQLException | IOException e) {
//	        e.printStackTrace();
//	        return null;
//	    }
//	}





	

	

	





   
}
