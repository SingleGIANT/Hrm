package com.example.hrm_new.controller.employee;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.hrm_new.entity.employee.Qualifications;
import com.example.hrm_new.repository.employee.QualificationsRepository;
import com.example.hrm_new.service.employee.QualificationsService;

@RestController
@CrossOrigin
public class QualificationsController {

	@Autowired
	private QualificationsService service;
	@Autowired
	private QualificationsRepository repo;
//
//	@GetMapping("/qualifications")
//	public ResponseEntity<List<Qualifications>> getAllQualifications() {
//	    List<Qualifications> qualificationsList = service.getAllQualifications();
//	    for (Qualifications qualification : qualificationsList) {
//	        String photoUrl = getImageUrl(qualification.getPhoto());
//	        qualification.setPhoto(photoUrl);
//	        String resumeUrl = getImageUrl(qualification.getResume());
//	        qualification.setResume(resumeUrl);
//	        String tenUrl = getImageUrl(qualification.getTen());
//	        qualification.setTen(tenUrl);
//	        String aadharUrl = getImageUrl(qualification.getAadhar());
//	        qualification.setAadhar(aadharUrl);
//	        String degreeUrl = getImageUrl(qualification.getDegree());
//	        qualification.setDegree(degreeUrl);
//	        String pannoUrl = getImageUrl(qualification.getPanno());
//	        qualification.setPanno(pannoUrl);
//	        String bankBookUrl = getImageUrl(qualification.getBankBook());
//	        qualification.setBankBook(bankBookUrl);
//	        String twelveUrl = getImageUrl(qualification.getTwelve());
//	        qualification.setTwelve(twelveUrl);
//	    }
//	    return ResponseEntity.ok().body(qualificationsList);
//	}
//
//
//	private String getImageUrl(String fileName) {
//    String serverContextPath = "/static/image/" + fileName;
//	    return serverContextPath ;
//	}

	
	@GetMapping("/qualifications")
	public ResponseEntity<List<Qualifications>> getAllQualifications() {
	    List<Qualifications> qualificationsList = service.getAllQualifications();
	    for (Qualifications qualification : qualificationsList) {
	        String photoUrl = getImageUrl(qualification.getPhoto());
	        qualification.setPhoto(photoUrl);
	        String resumeUrl = getImageUrl(qualification.getResume());
	        qualification.setResume(resumeUrl);
	        String tenUrl = getImageUrl(qualification.getTen());
	        qualification.setTen(tenUrl);
	        String aadharUrl = getImageUrl(qualification.getAadhar());
	        qualification.setAadhar(aadharUrl);
	        String degreeUrl = getImageUrl(qualification.getDegree());
	        qualification.setDegree(degreeUrl);
	        String pannoUrl = getImageUrl(qualification.getPanno());
	        qualification.setPanno(pannoUrl);
	        String bankBookUrl = getImageUrl(qualification.getBankBook());
	        qualification.setBankBook(bankBookUrl);
	        String twelveUrl = getImageUrl(qualification.getTwelve());
	        qualification.setTwelve(twelveUrl);
	    }
	    return ResponseEntity.ok().body(qualificationsList);
	}

	private String getImageUrl(String fileName) {
	    if (fileName != null) {
	        String serverContextPath = "/image/" + fileName;
	        return serverContextPath;
	    }
	    return null;
	}




	private int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(1000);
	}


	 @GetMapping("/qualifications/view")
	    public List<HashMap<String, Object>> getAllQualificationsWithUrls() {
	        List<Object[]> qualificationsList = repo.getAllQualificationsWithUrls();
	        List<HashMap<String, Object>> mappedQualifications = new ArrayList<>();

	        // Convert each Object[] to HashMap<String, Object>
	        for (Object[] row : qualificationsList) {
	            HashMap<String, Object> qualification = new HashMap<>();
	            qualification.put("first_name", row[0]);
	            qualification.put("last_name", row[1]);
	            qualification.put("qualification_id", row[2]);
	            qualification.put("employee_id", row[3]);
	            qualification.put("twelve", getImageUrl(row[4].toString()));
	            qualification.put("ten", getImageUrl(row[5].toString()));
	            qualification.put("resume", getImageUrl(row[6].toString()));
	            qualification.put("bank_book", getImageUrl(row[7].toString()));
	            qualification.put("aadhar", getImageUrl(row[8].toString()));
	            qualification.put("highest_qualification", row[9]);
	            qualification.put("degree", getImageUrl(row[10].toString()));
	            qualification.put("panno", getImageUrl(row[11].toString()));
	            qualification.put("photo", getImageUrl(row[12].toString()));

	            mappedQualifications.add(qualification);
	        }

	        return mappedQualifications;
	    }



	
	

		@PostMapping("/qualifications/save")
		public ResponseEntity<String> createQualification(@RequestParam("employeeId") String employeeId,
		                                                  @RequestParam("resume") MultipartFile resumeFile,
		                                                  @RequestParam("photo") MultipartFile photoFile,
		                                                  @RequestParam("ten") MultipartFile tenFile,
		                                                  @RequestParam("aadhar") MultipartFile aadharFile,
		                                                  @RequestParam("degree") MultipartFile degreeFile,
		                                                  @RequestParam("bankBook") MultipartFile bankBookFile,
		                                                  @RequestParam("twelve") MultipartFile twelveFile,
		                                                  @RequestParam("panno") MultipartFile pannoFile,
		                                                  @RequestParam("highestQualification") String highestQualification) {

		    int resumeRandomNumber = generateRandomNumber();
		    int photoRandomNumber = generateRandomNumber();
		    int tenRandomNumber = generateRandomNumber();
		    int aadharRandomNumber = generateRandomNumber();
		    int degreeRandomNumber = generateRandomNumber();
		    int bankBookRandomNumber = generateRandomNumber();
		    int twelveRandomNumber = generateRandomNumber();
		    int pannoRandomNumber = generateRandomNumber();
		    String resumeUrl = !resumeFile.isEmpty() ? saveFileAndGetUrl(resumeFile, resumeRandomNumber) : null;
		    String photoUrl = !photoFile.isEmpty() ? saveFileAndGetUrl(photoFile, photoRandomNumber) : null;
		    String tenUrl = !tenFile.isEmpty() ? saveFileAndGetUrl(tenFile, tenRandomNumber) : null;
		    String aadharUrl = !aadharFile.isEmpty() ? saveFileAndGetUrl(aadharFile, aadharRandomNumber) : null;
		    String degreeUrl = !degreeFile.isEmpty() ? saveFileAndGetUrl(degreeFile, degreeRandomNumber) : null;
		    String bankBookUrl = !bankBookFile.isEmpty() ? saveFileAndGetUrl(bankBookFile, bankBookRandomNumber) : null;
		    String twelveUrl = !twelveFile.isEmpty() ? saveFileAndGetUrl(twelveFile, twelveRandomNumber) : null;
		    String pannoUrl = !pannoFile.isEmpty() ? saveFileAndGetUrl(pannoFile, pannoRandomNumber) : null;
		    Qualifications qualification = new Qualifications();
		    qualification.setEmployeeId(employeeId);
		    qualification.setResume(resumeUrl);
		    qualification.setPhoto(photoUrl);
		    qualification.setTen(tenUrl);
		    qualification.setAadhar(aadharUrl);
		    qualification.setDegree(degreeUrl);
		    qualification.setBankBook(bankBookUrl);
		    qualification.setTwelve(twelveUrl);
		    qualification.setPanno(pannoUrl);
		    qualification.setHighestQualification(highestQualification);
		    qualification.setStatus(true);
		    Qualifications createdQualification = service.saveQualification(qualification);
		    long id = createdQualification.getQualificationId();
		    return ResponseEntity.ok().body("Images added successfully. Qualification ID: " + id);
		}

		 private String saveFileAndGetUrl(MultipartFile file, long qualificationId) {
			    if (!file.isEmpty()) {
			        try {
			        	  //  	String directoryPath = "C:\\Users\\ideau\\OneDrive\\Documents\\sri anjaneya\\img\\hrm_new\\src\\main\\resources\\static\\image";
			           String directoryPath = new ClassPathResource("static\\image").getFile().getAbsolutePath();
			            File directory = new File(directoryPath);
			            if (!directory.exists()) {
			                directory.mkdirs();
			            }
			            String filename = qualificationId + "_" + file.getOriginalFilename();
			            String filePath = directoryPath + "/" + filename;
			            File dest = new File(filePath);
			            file.transferTo(dest);
			            return filename;
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
			    return null;
			}
	
	
	
	
	
		 @PutMapping("/qualifications/edit/{qualificationId}")
		    public ResponseEntity<String> updateQualification(
		            @PathVariable Long qualificationId,
		            @RequestParam("employeeId") String employeeId,
		            @RequestParam("resume") MultipartFile resumeFile,
		            @RequestParam("photo") MultipartFile photoFile,
		            @RequestParam("ten") MultipartFile tenFile,
		            @RequestParam("aadhar") MultipartFile aadharFile,
		            @RequestParam("degree") MultipartFile degreeFile,
		            @RequestParam("bankBook") MultipartFile bankBookFile,
		            @RequestParam("twelve") MultipartFile twelveFile,
		            @RequestParam("panno") MultipartFile pannoFile,
		            @RequestParam("highestQualification") String highestQualification) {

		        Qualifications qualification = service.getQualificationById(qualificationId);
		        if (qualification == null) {
		            return ResponseEntity.notFound().build();
		        }

		        qualification.setEmployeeId(employeeId);
		        qualification.setHighestQualification(highestQualification);

		        if (!resumeFile.isEmpty()) {
		            String resumeUrl = saveFileAndGetUrl(resumeFile, qualificationId);
		            qualification.setResume(resumeUrl);
		        }
		        if (!photoFile.isEmpty()) {
		            String photoUrl = saveFileAndGetUrl(photoFile, qualificationId);
		            qualification.setPhoto(photoUrl);
		        }
		        if (!tenFile.isEmpty()) {
		            String tenUrl = saveFileAndGetUrl(tenFile, qualificationId);
		            qualification.setTen(tenUrl);
		        }
		        if (!aadharFile.isEmpty()) {
		            String aadharUrl = saveFileAndGetUrl(aadharFile, qualificationId);
		            qualification.setAadhar(aadharUrl);
		        }
		        if (!degreeFile.isEmpty()) {
		            String degreeUrl = saveFileAndGetUrl(degreeFile, qualificationId);
		            qualification.setDegree(degreeUrl);
		        }
		        if (!bankBookFile.isEmpty()) {
		            String bankBookUrl = saveFileAndGetUrl(bankBookFile, qualificationId);
		            qualification.setBankBook(bankBookUrl);
		        }
		        if (!twelveFile.isEmpty()) {
		            String twelveUrl = saveFileAndGetUrl(twelveFile, qualificationId);
		            qualification.setTwelve(twelveUrl);
		        }
		        if (!pannoFile.isEmpty()) {
		            String pannoUrl = saveFileAndGetUrl(pannoFile, qualificationId);
		            qualification.setPanno(pannoUrl);
		        }
		        // Add other fields as needed

		        Qualifications updatedQualification = service.saveQualification(qualification);

		        return ResponseEntity.ok().body("Qualification updated successfully. Qualification ID: " + updatedQualification.getQualificationId());
		    }

	
	
//	@GetMapping("/qualifications/{id}")
//	public ResponseEntity<List<Qualifications>> getQualificationsByEmployeeId(@PathVariable("id") long employeeId) {
//	    // Retrieve the qualifications from the database for the given employeeId
//	    List<Qualifications> qualificationsList = service.getQualificationById(employeeId);
//
//	    if (!qualificationsList.isEmpty()) {
//	        for (Qualifications qualification : qualificationsList) {
//	            // Set the URLs for each image individually
//	            String photoUrl = getImageUrl(qualification.getPhoto());
//	            qualification.setPhoto(photoUrl);
//
//	            String resumeUrl = getImageUrl(qualification.getResume());
//	            qualification.setResume(resumeUrl);
//
//	            String tenUrl = getImageUrl(qualification.getTen());
//	            qualification.setTen(tenUrl);
//
//	            String aadharUrl = getImageUrl(qualification.getAadhar());
//	            qualification.setAadhar(aadharUrl);
//
//	            String degreeUrl = getImageUrl(qualification.getDegree());
//	            qualification.setDegree(degreeUrl);
//
//	            String pannoUrl = getImageUrl(qualification.getPanno());
//	            qualification.setPanno(pannoUrl);
//
//	            String bankBookUrl = getImageUrl(qualification.getBankBook());
//	            qualification.setBankBook(bankBookUrl);
//
//	            String twelveUrl = getImageUrl(qualification.getTwelve());
//	            qualification.setTwelve(twelveUrl);
//	        }
//
//	        return ResponseEntity.ok().body(qualificationsList);
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}

//	@PostMapping("/qualifications5")
//	public ResponseEntity<String> createQualification5(
//	        @RequestParam("employeeId") String employeeId,
//	        @RequestParam("resume") MultipartFile resumeFile,
//	        @RequestParam("photo") MultipartFile photoFile,
//	        @RequestParam("ten") MultipartFile tenFile,
//	        @RequestParam("aadhar") MultipartFile aadharFile,
//	        @RequestParam("degree") MultipartFile degreeFile,
//	        @RequestParam("bankBook") MultipartFile bankBookFile,
//	        @RequestParam("twelve") MultipartFile twelveFile,
//	        @RequestParam("panno") MultipartFile pannoFile,
//	        @RequestParam("highestQualification") String highestQualification) {
//
//	    // Save the files and set the URLs individually
//	    String resumeUrl = savePrivateFileAndGetUrl(resumeFile);
//	    String photoUrl = savePrivateFileAndGetUrl(photoFile);
//	    String tenUrl = savePrivateFileAndGetUrl(tenFile);
//	    String aadharUrl = savePrivateFileAndGetUrl(aadharFile);
//	    String degreeUrl = savePrivateFileAndGetUrl(degreeFile);
//	    String bankBookUrl = savePrivateFileAndGetUrl(bankBookFile);
//	    String twelveUrl = savePrivateFileAndGetUrl(twelveFile);
//	    String pannoUrl = savePrivateFileAndGetUrl(pannoFile);
//
//	    // Create a new Qualification object
//	    Qualifications qualification = new Qualifications();
//	    qualification.setEmployeeId(employeeId);
//	    qualification.setResume(resumeUrl);
//	    qualification.setPhoto(photoUrl);
//	    qualification.setTen(tenUrl);
//	    qualification.setAadhar(aadharUrl);
//	    qualification.setDegree(degreeUrl);
//	    qualification.setBankBook(bankBookUrl);
//	    qualification.setTwelve(twelveUrl);
//	    qualification.setPanno(pannoUrl);
//	    qualification.setHighestQualification(highestQualification);
//
//	    // Save the qualification
//	    Qualifications createdQualification = service.saveQualification(qualification);
//
//	    long id = createdQualification.getQualificationId();
//	    return ResponseEntity.ok().body("Images added successfully. Qualification ID: " + id);
//	}
//
//	private String savePrivateFileAndGetUrl(MultipartFile file) {
//	    try {
//	        String bucketName = "your-bucket-name"; // Replace with your S3 bucket name
//	        String folderName = "your-folder-name"; // Replace with the desired folder name
//
//	        // Generate a unique file name to avoid conflicts
//	        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
//
//	        // Create a PutObjectRequest with private ACL
//	        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + "/" + fileName, file.getInputStream(), null)
//	                .withCannedAcl(CannedAccessControlList.Private);
//
//	        // Upload the file to Amazon S3
//	        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
//	        s3Client.putObject(putObjectRequest);
//
//	        // Generate a pre-signed URL with private access control
//	        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, folderName + "/" + fileName)
//	                .withMethod(HttpMethod.GET)
//	                .withExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)));
//
//	        URL fileUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
//
//	        return fileUrl.toString();
//	    } catch (IOException e) {
//	        // Handle the exception appropriately
//	        e.printStackTrace();
//	        return null;
//	    }
}
