package com.example.hrm_new.controller.employee;

import java.io.IOException;

import java.sql.Blob;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.example.hrm_new.entity.customer.Customer;
import com.example.hrm_new.entity.employee.Awards;
import com.example.hrm_new.entity.employee.AwardsPhoto;
import com.example.hrm_new.entity.employee.Qualification;
import com.example.hrm_new.repository.employee.QualificationRepository;
import com.example.hrm_new.service.employee.QualificationService;

@RestController
@CrossOrigin
public class QualificationController {

	@Autowired
	private QualificationService service;


	@PostMapping("/qualification/save")
	public ResponseEntity<String> addImagePost(@RequestParam("employeeId") String employeeId,
			@RequestParam("resume") MultipartFile resumeFile, @RequestParam("photo") MultipartFile photoFile,
			@RequestParam("ten") MultipartFile tenFile, @RequestParam("aadhar") MultipartFile aadharFile,
			@RequestParam("degree") MultipartFile degreeFile, @RequestParam("bankBook") MultipartFile bankBookFile,
			@RequestParam("twelve") MultipartFile twelveFile, @RequestParam("panno") MultipartFile pannoFile,
			@RequestParam("highestQualification") String highestQualification,@RequestParam("panCard") String panCard,
			@RequestParam("aadharNO")long aadharNO) {
		try {
			Qualification qualification = new Qualification();
			qualification.setEmployeeId(employeeId);
			qualification.setStatus(true);	
			qualification.setHighestQualification(highestQualification);
			qualification.setAadharNO(aadharNO);
			qualification.setPanCard(panCard);
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
	    if (file != null && !file.isEmpty()) {
	        byte[] bytes = file.getBytes();
	        return new javax.sql.rowset.serial.SerialBlob(bytes);
	    } else {
	        return null; // Handle the case where the file is null or empty
	    }
	}


	@GetMapping("/qualification")
	public ResponseEntity<List<Qualification>> getAllQualifications1() {
		try {
			List<Qualification> qualifications = service.getAllQualifications();
			List<Qualification> qualificationResponses = new ArrayList<>();

			for (Qualification qualification : qualifications) {
				// Generate random number for each image
				int photoRandomNumber = generateRandomNumber();
				int resumeRandomNumber = generateRandomNumber();
				int tenRandomNumber = generateRandomNumber();
				int aadharRandomNumber = generateRandomNumber();
				int degreeRandomNumber = generateRandomNumber();
				int pannoRandomNumber = generateRandomNumber();
				int bankBookRandomNumber = generateRandomNumber();
				int twelveRandomNumber = generateRandomNumber();

				// Construct the image URLs with different random numbers
				String photoUrl = "/photoUrl/" + photoRandomNumber + "/" + qualification.getQualificationId();
				String resumeUrl = "/resumeUrl/" + resumeRandomNumber + "/" + qualification.getQualificationId();
				String tenUrl = "/tenUrl/" + tenRandomNumber + "/" + qualification.getQualificationId();
				String aadharUrl = "/aadharUrl/" + aadharRandomNumber + "/" + qualification.getQualificationId();
				String degreeUrl = "/degreeUrl/" + degreeRandomNumber + "/" + qualification.getQualificationId();
				String pannoUrl = "/pannoUrl/" + pannoRandomNumber + "/" + qualification.getQualificationId();
				String bankBookUrl = "/bankBookUrl/" + bankBookRandomNumber + "/" + qualification.getQualificationId();
				String twelveUrl = "/twelveUrl/" + twelveRandomNumber + "/" + qualification.getQualificationId();

				// Create a new Qualification object to hold the modified data
				Qualification qualificationResponse = new Qualification();
				qualificationResponse.setQualificationId(qualification.getQualificationId());
				qualificationResponse.setEmployeeId(qualification.getEmployeeId());
				qualificationResponse.setStatus(qualification.isStatus());
				qualificationResponse.setHighestQualification(qualification.getHighestQualification());
				qualificationResponse.setPanCard(qualification.getPanCard());
				qualificationResponse.setAadharNO(qualification.getAadharNO());
				qualificationResponse.setPhotourl(photoUrl);
				qualificationResponse.setResumeurl(resumeUrl);
				qualificationResponse.setTenurl(tenUrl);
				qualificationResponse.setAadharurl(aadharUrl);
				qualificationResponse.setDegreeurl(degreeUrl);
				qualificationResponse.setPannourl(pannoUrl);
				qualificationResponse.setBankBookurl(bankBookUrl);
				qualificationResponse.setTwelveurl(twelveUrl);

				qualificationResponses.add(qualificationResponse);
			}

			return ResponseEntity.ok(qualificationResponses);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	private int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(10000);

	}

	@GetMapping("/photoUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile1(@PathVariable("randomNumber") int randomNumber,
	        @PathVariable("id") Long id) {
	    Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
	    if (awardsPhotoOptional.isPresent()) {
	        Qualification awardsPhoto = awardsPhotoOptional.get();
	        Blob photoBlob = awardsPhoto.getPhoto(); // Get the Blob object

	        if (photoBlob != null) { // Check for null before accessing methods
	            String filename = "file_" + randomNumber + "_" + id;
	            byte[] fileBytes;
	            try {
	                fileBytes = photoBlob.getBytes(1, (int) photoBlob.length());

	            } catch (SQLException e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	            }

	            // Determine the media type based on the file's content
	            String extension = determineFileExtension(fileBytes);
	            MediaType mediaType = determineMediaType(extension);

	            // Create the resource and set headers
	            ByteArrayResource resource = new ByteArrayResource(fileBytes);
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(mediaType);
	            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
	            return ResponseEntity.ok().headers(headers).body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}


	@GetMapping("/aadharUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile2(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getAadhar().getBytes(1, (int) awardsPhoto.getAadhar().length());

			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/bankBookUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile3(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getBankBook().getBytes(1, (int) awardsPhoto.getBankBook().length());

			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/tenUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile4(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getTen().getBytes(1, (int) awardsPhoto.getTen().length());
			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/twelveUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile5(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getTwelve().getBytes(1, (int) awardsPhoto.getTwelve().length());

			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/degreeUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile6(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getDegree().getBytes(1, (int) awardsPhoto.getDegree().length());

			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/pannoUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile7(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getPanno().getBytes(1, (int) awardsPhoto.getPanno().length());

			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/resumeUrl/{randomNumber}/{id}")
	public ResponseEntity<ByteArrayResource> serveFile8(@PathVariable("randomNumber") int randomNumber,
			@PathVariable("id") Long id) {
		Optional<Qualification> awardsPhotoOptional = service.getAwardsPhotoById(id);
		if (awardsPhotoOptional.isPresent()) {
			Qualification awardsPhoto = awardsPhotoOptional.get();
			String filename = "file_" + randomNumber + "_" + id;
			byte[] fileBytes;
			try {

				fileBytes = awardsPhoto.getResume().getBytes(1, (int) awardsPhoto.getResume().length());
			} catch (SQLException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

			// Determine the media type based on the file's content
			String extension = determineFileExtension(fileBytes);
			MediaType mediaType = determineMediaType(extension);

			// Create the resource and set headers
			ByteArrayResource resource = new ByteArrayResource(fileBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + "." + extension);
			return ResponseEntity.ok().headers(headers).body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	private String determineFileExtension(byte[] fileBytes) {
		try {
			// Inspect the first few bytes of the file to determine its type
			String fileSignature = bytesToHex(Arrays.copyOfRange(fileBytes, 0, 4));
			if (fileSignature.startsWith("89504E47")) {
				return "png";
			} else if (fileSignature.startsWith("FFD8FF")) {
				return "jpg";
			} else if (fileSignature.startsWith("25504446")) {
				return "pdf";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "unknown";
	}

	private MediaType determineMediaType(String extension) {
		switch (extension) {
		case "png":
			return MediaType.IMAGE_PNG;
		case "jpg":
			return MediaType.IMAGE_JPEG;
		case "pdf":
			return MediaType.APPLICATION_PDF;
		default:
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
	
	 @PutMapping("/qualification/or/{id}")
	    public ResponseEntity<Boolean> toggleQualificationStatus(@PathVariable(name = "id") long id) {
	        try {
	            Optional<Qualification> qualificationOptional = service.getQualificationById(id);
                Qualification qualification = qualificationOptional.get();
	            if (qualificationOptional.isPresent()) {

	                boolean currentStatus = qualification.isStatus();
	                qualification.setStatus(!currentStatus);
	                service.update(qualification); 
	                
	                

	                // Generate random numbers
	                int photoRandomNumber = generateRandomNumber();
	                int resumeRandomNumber = generateRandomNumber();
	                int tenRandomNumber = generateRandomNumber();
	                int aadharRandomNumber = generateRandomNumber();
	                int degreeRandomNumber = generateRandomNumber();
	                int pannoRandomNumber = generateRandomNumber();
	                int bankBookRandomNumber = generateRandomNumber();
	                int twelveRandomNumber = generateRandomNumber();
	           
	                String photoUrl = "/photoUrl/" + photoRandomNumber + "/" + qualification.getQualificationId();
	                String resumeUrl = "/resumeUrl/" + resumeRandomNumber + "/" + qualification.getQualificationId();
	                String tenUrl = "/tenUrl/" + tenRandomNumber + "/" + qualification.getQualificationId();
	                String aadharUrl = "/aadharUrl/" + aadharRandomNumber + "/" + qualification.getQualificationId();
	                String degreeUrl = "/degreeUrl/" + degreeRandomNumber + "/" + qualification.getQualificationId();
	                String pannoUrl = "/pannoUrl/" + pannoRandomNumber + "/" + qualification.getQualificationId();
	                String bankBookUrl = "/bankBookUrl/" + bankBookRandomNumber + "/" + qualification.getQualificationId();
	                String twelveUrl = "/twelveUrl/" + twelveRandomNumber + "/" + qualification.getQualificationId();	       
	                // Create a new Qualification object to hold the modified data
	                Qualification qualificationResponse = new Qualification();
	                qualificationResponse.setQualificationId(qualification.getQualificationId());
	                qualificationResponse.setEmployeeId(qualification.getEmployeeId());
	                qualificationResponse.setHighestQualification(qualification.getHighestQualification());
	                qualificationResponse.setPanCard(qualification.getPanCard());
	                qualificationResponse.setAadharNO(qualification.getAadharNO());
	                qualificationResponse.setPhotourl(photoUrl);
	                qualificationResponse.setResumeurl(resumeUrl);
	                qualificationResponse.setTenurl(tenUrl);
	                qualificationResponse.setAadharurl(aadharUrl);
	                qualificationResponse.setDegreeurl(degreeUrl);
	                qualificationResponse.setPannourl(pannoUrl);
	                qualificationResponse.setBankBookurl(bankBookUrl);
	                qualificationResponse.setTwelveurl(twelveUrl);

	                List<Qualification> qualificationResponses = new ArrayList<>();
	                qualificationResponses.add(qualificationResponse);


	                return ResponseEntity.ok(qualification.isStatus());
	            } else {
	                return ResponseEntity.ok(false);
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(false);
	        }
	    }

	
	                
	@PutMapping("/qualification/update/{id}")
	public ResponseEntity<String> updateQualification(@PathVariable("id") Long id,
			@RequestParam(value = "employeeId", required = false) String employeeId, @RequestParam(value ="resume", required = false) MultipartFile resumeFile,
			@RequestParam(value ="photo", required = false) MultipartFile photoFile, @RequestParam(value ="ten", required = false) MultipartFile tenFile,
			@RequestParam(value ="aadhar", required = false) MultipartFile aadharFile, @RequestParam(value ="degree", required = false) MultipartFile degreeFile,
			@RequestParam(value ="bankBook", required = false) MultipartFile bankBookFile, @RequestParam(value ="twelve", required = false) MultipartFile twelveFile,
			@RequestParam(value ="panno", required = false) MultipartFile pannoFile,
			@RequestParam(value ="highestQualification", required = false) String highestQualification,@RequestParam(value ="panCard", required = false) String panCard,
			@RequestParam(value ="aadharNO", required = false)long aadharNO) {
		try {
			Optional<Qualification> qualificationOptional = service.getQualificationById(id);

			if (qualificationOptional.isPresent()) {
				Qualification qualification = qualificationOptional.get();
				qualification.setEmployeeId(employeeId);
				qualification.setHighestQualification(highestQualification);
				qualification.setPanCard(panCard);
				qualification.setAadharNO(aadharNO);

				if (resumeFile != null && !resumeFile.isEmpty()) {
					int resumeRandomNumber = generateRandomNumber();
					String resumeUrl = "/resumeUrl/" + resumeRandomNumber + "/" + qualification.getQualificationId();
					qualification.setResume(convertToBlob(resumeFile));
					qualification.setResumeurl(resumeUrl);
				}

				if (photoFile != null && !photoFile.isEmpty()) {
					int photoRandomNumber = generateRandomNumber();
					String photoUrl = "/photoUrl/" + photoRandomNumber + "/" + qualification.getQualificationId();
					qualification.setPhoto(convertToBlob(photoFile));
					qualification.setPhotourl(photoUrl);
				}
				if (aadharFile != null && !aadharFile.isEmpty()) {
					int aadharRandomNumber = generateRandomNumber();
					String aadharUrl = "/aadharUrl/" + aadharRandomNumber + "/" + qualification.getQualificationId();
					qualification.setAadhar(convertToBlob(aadharFile));
					qualification.setAadharurl(aadharUrl);
				}
				if (bankBookFile != null && !bankBookFile.isEmpty()) {
					int bankBookRandomNumber = generateRandomNumber();
					String bankBookUrl = "/bankBookUrl/" + bankBookRandomNumber + "/" + qualification.getQualificationId();
					qualification.setBankBook(convertToBlob(bankBookFile));
					qualification.setBankBookurl(bankBookUrl);
				}
				if (pannoFile != null && !pannoFile.isEmpty()) {
					int pannoRandomNumber = generateRandomNumber();
					String pannoUrl = "/pannoUrl/" + pannoRandomNumber + "/" + qualification.getQualificationId();
					qualification.setPanno(convertToBlob(pannoFile));
					qualification.setPannourl(pannoUrl);
				}
				if (twelveFile != null && !twelveFile.isEmpty()) {
					int twelveRandomNumber = generateRandomNumber();
					String twelveUrl = "/twelveUrl/" + twelveRandomNumber + "/" + qualification.getQualificationId();
					qualification.setTwelve(convertToBlob(twelveFile));
					qualification.setTwelveurl(twelveUrl);
				}

				if (tenFile != null && !tenFile.isEmpty()) {
					int tenRandomNumber = generateRandomNumber();
					String tenUrl = "/tenUrl/" + tenRandomNumber + "/" + qualification.getQualificationId();
					qualification.setTen(convertToBlob(tenFile));
					qualification.setTenurl(tenUrl);
				}

				if (degreeFile != null && !degreeFile.isEmpty()) {
					int degreeRandomNumber = generateRandomNumber();
					String degreeUrl = "/degreeUrl/" + degreeRandomNumber + "/" + qualification.getQualificationId();
					qualification.setDegree(convertToBlob(degreeFile));
					qualification.setDegreeurl(degreeUrl);
				}
				service.update(qualification);

				return ResponseEntity.ok("Qualification with ID  updated successfully." + id);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating qualification: " + e.getMessage());
		}
	}
	
	
	@Autowired
	private QualificationService repo1;
 
 
 @GetMapping("/qualification/view")
	public ResponseEntity<List<Map<String, Object>>> getAllQualificationss() {
	    try {
	        List<Map<String, Object>> qualifications = repo1.getAllQualificationsByImage();
	        List<Map<String, Object>> qualificationResponses = new ArrayList<>();
	        Map<String, List<Map<String, Object>>> qualificationsGroupMap = StreamSupport.stream(qualifications.spliterator(), false)
		            .collect(Collectors.groupingBy(action -> String.valueOf(action.get("qualification_id"))));
	        for (Entry<String, List<Map<String, Object>>> qualification : qualificationsGroupMap.entrySet()) {
	        	 int photoRandomNumber = generateRandomNumber();
		            int resumeRandomNumber = generateRandomNumber();
		            int tenRandomNumber = generateRandomNumber();
		            int aadharRandomNumber = generateRandomNumber();
		            int degreeRandomNumber = generateRandomNumber();
		            int pannoRandomNumber = generateRandomNumber();
		            int bankBookRandomNumber = generateRandomNumber();
		            int twelveRandomNumber = generateRandomNumber();


	            String photoUrl = "/photoUrl/" + photoRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String resumeUrl = "/resumeUrl/" + resumeRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String tenUrl = "/tenUrl/" + tenRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String aadharUrl = "/aadharUrl/" + aadharRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String degreeUrl = "/degreeUrl/" + degreeRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String pannoUrl = "/pannoUrl/" + pannoRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String bankBookUrl = "/bankBookUrl/" + bankBookRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");
	            String twelveUrl = "/twelveUrl/" + twelveRandomNumber + "/" + qualification.getValue().get(0).get("qualification_id");

	            Map<String, Object> qualificationResponse = new HashMap<>();
	            qualificationResponse.put("qualificationId", qualification.getValue().get(0).get("qualification_id"));
	            qualificationResponse.put("status", qualification.getValue().get(0).get("status"));
	            qualificationResponse.put("employeeId", qualification.getValue().get(0).get("employee_id"));
	            qualificationResponse.put("highestQualification", qualification.getValue().get(0).get("highest_qualification"));
	            qualificationResponse.put("aadharno", qualification.getValue().get(0).get("aadharno"));
	            qualificationResponse.put("pancard", qualification.getValue().get(0).get("pan_card"));
	            qualificationResponse.put("photourl", photoUrl);
	            qualificationResponse.put("resumeurl", resumeUrl);
	            qualificationResponse.put("tenUrl", tenUrl);
	            qualificationResponse.put("aadharUrl", aadharUrl);
	            qualificationResponse.put("degreeUrl", degreeUrl);
	            qualificationResponse.put("pannoUrl", pannoUrl);
	            qualificationResponse.put("bankBookUrl", bankBookUrl);
	            qualificationResponse.put("twelveUrl", twelveUrl);
	            qualificationResponse.put("firstName", qualification.getValue().get(0).get("first_name"));
	            qualificationResponse.put("lastName", qualification.getValue().get(0).get("last_name"));
	         

	            qualificationResponses.add(qualificationResponse);
	        }

	        return ResponseEntity.ok(qualificationResponses);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
 }
	

}
