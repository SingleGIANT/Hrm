package com.example.hrm_new.controller.employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Blob;
import java.sql.Date;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.hrm_new.entity.employee.Awards;
import com.example.hrm_new.entity.employee.AwardsPhoto;
import com.example.hrm_new.repository.employee.AwardsRepository;
import com.example.hrm_new.service.employee.AwardsPhotoService;
import com.example.hrm_new.service.employee.AwardsService;

@RestController
@CrossOrigin
public class AwardsController {

	@Autowired
	private AwardsService service;
	@Autowired
	private AwardsRepository repo;

	@Autowired
	private AwardsPhotoService awardsPhotoService;

	@PostMapping(value = "/awards/save", headers = ("content-type=multipart/form-data"), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addImagePost(@RequestPart("awardsPhoto") MultipartFile[] files,
			@RequestParam("description") String description, @RequestParam("gift") String gift,
			@RequestParam("date") Date date, @RequestParam("cash") int cash,
			@RequestParam("employeeId") long employeeId) {
		try {
			if (files == null || files.length == 0) {
				return ResponseEntity.badRequest().body("No files provided");
			}

			List<AwardsPhoto> awardsPhotos = new ArrayList<>();

			for (MultipartFile file : files) {
				if (file.isEmpty()) {
					continue;
				}

				byte[] bytes = file.getBytes();
				Blob blob = new SerialBlob(bytes);
				AwardsPhoto awardsPhoto = new AwardsPhoto();
				awardsPhoto.setAwardsPhoto(blob);
				awardsPhotos.add(awardsPhoto);
			}

			Awards awards = new Awards();
			awards.setDescription(description);
			awards.setGift(gift);
			awards.setDate(date);
			awards.setCash(cash);
			awards.setStatus(true);
			awards.setEmployeeId(employeeId);
			awards.setAwardsPhotos(awardsPhotos);

			service.create(awards);
			long id = awards.getAwardsId();

			return ResponseEntity.ok("Images added successfully. Awards ID: " + id);
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding images: " + e.getMessage());
		}
	}

	@PostMapping(value = "/add1", headers = ("content-type=multipart/form-data"), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addImagePost(@RequestParam("awardsPhoto") MultipartFile file,
			@RequestParam("description") String description, @RequestParam("gift") String gift,
			@RequestParam("date") Date date, @RequestParam("cash") int cash,
			@RequestParam("employeeId") long employeeId) {
		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("No image provided");
			}

			byte[] bytes = file.getBytes();
			Blob blob = new SerialBlob(bytes);

			AwardsPhoto awardsPhoto = new AwardsPhoto();
			awardsPhoto.setAwardsPhoto(blob);

			Awards awards = new Awards();
			awards.setDescription(description);
			awards.setGift(gift);
			awards.setDate(date);
			awards.setCash(cash);
			awards.setStatus(true);
			awards.setEmployeeId(employeeId);

			List<AwardsPhoto> awardsPhotos = new ArrayList<>();
			awardsPhotos.add(awardsPhoto);
			awards.setAwardsPhotos(awardsPhotos);

			service.create(awards);
			long id = awards.getAwardsId();

			return ResponseEntity.ok("Image added successfully. Awards ID: " + id);
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding image: " + e.getMessage());
		}
	}

	@GetMapping("/awards")
	public ResponseEntity<List<Awards>> getAllAwards() {
		try {
			List<Awards> awardsList = service.getAllAwards();
			for (Awards awards : awardsList) {
				List<AwardsPhoto> awardsPhotos = awards.getAwardsPhotos();
				for (AwardsPhoto awardsPhoto : awardsPhotos) {
					String imageUrl = "/image/" + generateRandomNumber() + "/" + awardsPhoto.getAwardsPhotoId();
					awardsPhoto.setUrl(imageUrl);
				}
			}
			return ResponseEntity.ok(awardsList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/awards/{awardsId}")
	public ResponseEntity<Awards> getAwardsById56(@PathVariable("awardsId") long awardsId) {
		try {
			Optional<Awards> awardsOptional = service.getAwardsById(awardsId);
			if (awardsOptional.isPresent()) {
				Awards awards = awardsOptional.get();
				List<AwardsPhoto> awardsPhotos = awards.getAwardsPhotos();

				for (AwardsPhoto awardsPhoto : awardsPhotos) {
					String imageUrl = "/image/" + generateRandomNumber() + "/" + awardsPhoto.getAwardsPhotoId();
					awardsPhoto.setUrl(imageUrl);
				}

				return ResponseEntity.ok(awards);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	 @PutMapping("/awards/or/{awardsId}")
	    public ResponseEntity<Awards> toggleAwardsStatusAndSetPhotoUrls(@PathVariable("awardsId") long awardsId) {
	        try {
	            Optional<Awards> awardsOptional = service.getAwardsById(awardsId);
	            if (awardsOptional.isPresent()) {
	                Awards awards = awardsOptional.get();
	                
	                // Toggle the status
	                boolean currentStatus = awards.isStatus();
	                awards.setStatus(!currentStatus);
	                service.update(awards); // Save the updated awards

	                // Update AwardsPhoto URLs
	                List<AwardsPhoto> awardsPhotos = awards.getAwardsPhotos();
	                for (AwardsPhoto awardsPhoto : awardsPhotos) {
	                    String imageUrl = "/image/" + generateRandomNumber() + "/" + awardsPhoto.getAwardsPhotoId();
	                    awardsPhoto.setUrl(imageUrl);
	                }

	                return ResponseEntity.ok(awards);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	private int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(1000);
	}

//	@GetMapping("/image/{randomNumber}/{id}")
//	public ResponseEntity<Resource> serveImage(@PathVariable("randomNumber") int randomNumber,
//			@PathVariable("id") Long id) {
//		Optional<AwardsPhoto> awardsPhotoOptional = awardsPhotoService.getAwardsPhotoById(id);
//		if (awardsPhotoOptional.isPresent()) {
//			AwardsPhoto awardsPhoto = awardsPhotoOptional.get();
//			byte[] imageBytes;
//			try {
//				imageBytes = awardsPhoto.getAwardsPhoto().getBytes(1, (int) awardsPhoto.getAwardsPhoto().length());
//			} catch (SQLException e) {
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//			}
//			ByteArrayResource resource = new ByteArrayResource(imageBytes);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.IMAGE_JPEG);
//			return ResponseEntity.ok().headers(headers).body(resource);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

	@GetMapping("/image/{randomNumber}/{id}")
	public ResponseEntity<Resource> serveFile(@PathVariable("randomNumber") int randomNumber,
	                                           @PathVariable("id") Long id) {
	    Optional<AwardsPhoto> awardsPhotoOptional = awardsPhotoService.getAwardsPhotoById(id);
	    if (awardsPhotoOptional.isPresent()) {
	        AwardsPhoto awardsPhoto = awardsPhotoOptional.get();
	        String filename = "file_" + randomNumber + "_" + id;
	        byte[] fileBytes;
	        try {
	            fileBytes = awardsPhoto.getAwardsPhoto().getBytes(1, (int) awardsPhoto.getAwardsPhoto().length());
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

	@PutMapping("/edit/{awardsId}")
	public ResponseEntity<String> editImage(@PathVariable("awardsId") long awardsId,
			@RequestParam(value = "images", required = false) MultipartFile[] files,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "gift", required = false) String gift,
			@RequestParam(value = "date", required = false) Date date,
			@RequestParam(value = "cash", required = false) int cash,
			@RequestParam(value = "employeeId", required = false) long employeeId) {
		try {
			Optional<Awards> awardsOptional = service.getAwardsById(awardsId);
			if (awardsOptional.isPresent()) {
				Awards awards = awardsOptional.get();

				if (description != null) {
					awards.setDescription(description);
				}
				if (gift != null) {
					awards.setGift(gift);
				}
				if (date != null) {
					awards.setDate(date);
				}
				if (cash != 0) {
					awards.setCash(cash);
				}
				if (employeeId != 0) {
					awards.setEmployeeId(employeeId);
				}

				if (files != null && files.length > 0) {
					List<AwardsPhoto> awardsPhotos = new ArrayList<>();

					for (MultipartFile file : files) {
						byte[] bytes = file.getBytes();
						Blob blob = new SerialBlob(bytes);
						AwardsPhoto awardsPhoto = new AwardsPhoto();
						awardsPhoto.setAwardsPhoto(blob);
						awardsPhotos.add(awardsPhoto);
					}

					awards.setAwardsPhotos(awardsPhotos);
				}

				service.update(awards);
				String imageUrl = "/image/" + generateRandomNumber() + "/" + awards.getAwardsId();

				return ResponseEntity.ok("Images and awards details updated successfully. Awards ID: " + awardsId);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (IOException | SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error editing images: " + e.getMessage());
		}
	}

	@GetMapping("/awards/view")
	public List<Map<String, Object>> allcompanyDetails() {
		List<Map<String, Object>> awardList = new ArrayList<>();
		List<Map<String, Object>> awardDetails = repo.AllEmployee();
		Map<String, List<Map<String, Object>>> awardGroupMap = StreamSupport.stream(awardDetails.spliterator(), false)
				.collect(Collectors.groupingBy(action -> action.get("awards_id").toString()));

		for (Entry<String, List<Map<String, Object>>> entry : awardGroupMap.entrySet()) {
			Map<String, Object> awardsMap = new HashMap<>();
			List<Map<String, Object>> awardsList = entry.getValue();

			awardsMap.put("awardsId", awardsList.get(0).get("awards_id"));
			awardsMap.put("description", awardsList.get(0).get("description"));
			awardsMap.put("gift", awardsList.get(0).get("gift"));
			awardsMap.put("date", awardsList.get(0).get("date"));
			awardsMap.put("cash", awardsList.get(0).get("cash"));
			awardsMap.put("status", awardsList.get(0).get("status"));
			awardsMap.put("employeeId", awardsList.get(0).get("employee_id"));
			awardsMap.put("firstName", awardsList.get(0).get("first_name"));
			awardsMap.put("lastName", awardsList.get(0).get("last_name"));

			List<Map<String, Object>> awardsPhotosList = new ArrayList<>();
			for (Map<String, Object> award : awardsList) {
				Map<String, Object> awardsPhotoMap = new HashMap<>();
				awardsPhotoMap.put("awardsPhotoId", award.get("awards_photo_id"));
				awardsPhotoMap.put("url", "/image/" + generateRandomNumber() + "/" + award.get("awards_id"));
				awardsPhotosList.add(awardsPhotoMap);
			}
			awardsMap.put("awardsPhotos", awardsPhotosList);

			awardList.add(awardsMap);
		}
		return awardList;
	}
///////13 //////

	@GetMapping("/awards/count/{employee_id}")
	private Map<String, Object> getTotalAwardsCount(@PathVariable("employee_id") Long employee_id) {
		List<Map<String, Object>> awardsList = repo.Allfilter(employee_id);
		Set<BigInteger> uniqueAwards = new HashSet<>();
		for (Map<String, Object> award : awardsList) {
			BigInteger awardIdBigInt = (BigInteger) award.get("awards_id");
			uniqueAwards.add(awardIdBigInt);
		}
		int totalAwardsCount = uniqueAwards.size();
		Map<String, Object> result = new HashMap<>();
		result.put("total_awards", totalAwardsCount);
		return result;
	}

//	 @PostMapping("/awards/date")
//	    public List<Awards> getEmployeeAwardsByDate(@Param("awardDate") String awardDate){	                                               
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	        java.util.Date parsedDate;
//	        try {
//	            parsedDate = dateFormat.parse(awardDate);
//	        } catch (ParseException e) {
//	            e.printStackTrace();
//	            return new ArrayList<>();
//	        }
//	        List<Awards> awardsList = repo.findAwardsByEmployeeIdAndDate(parsedDate);
//	        return awardsList;
//	    }

	///// 14 ///////

	@PostMapping("/awards/date")
	public List<Map<String, Object>> getEmployeeAwardsByDate1(@RequestParam("awardDate") String awardDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsedDate;
		try {
			parsedDate = dateFormat.parse(awardDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

		List<Awards> awardsList = repo.findAwardsByEmployeeIdAndDate(parsedDate);

		List<Map<String, Object>> result = new ArrayList<>();
		for (Awards award : awardsList) {
			Map<String, Object> awardsMap = new HashMap<>();
			awardsMap.put("awardsId", award.getAwardsId());
			awardsMap.put("description", award.getDescription());
			awardsMap.put("gift", award.getGift());
			awardsMap.put("date", award.getDate());
			awardsMap.put("cash", award.getCash());
			awardsMap.put("employeeId", award.getEmployeeId());
			awardsMap.put("status", award.isStatus());

			List<Map<String, Object>> awardsPhotosList = new ArrayList<>();
			for (AwardsPhoto awardsPhoto : award.getAwardsPhotos()) {
				Map<String, Object> awardsPhotoMap = new HashMap<>();
				awardsPhotoMap.put("awardsPhotoId", awardsPhoto.getAwardsPhotoId());
				awardsPhotoMap.put("url", "/image/" + generateRandomNumber() + "/" + awardsPhoto.getAwardsPhotoId());
				awardsPhotosList.add(awardsPhotoMap);
			}

			awardsMap.put("awardsPhotos", awardsPhotosList);
			result.add(awardsMap);
		}

		return result;
	}
               ////////   15  //////////////
	
	@GetMapping("/awards/count")
	public List<Map<String, Object>> getEmployeeAwardsCount() {
	    List<Object[]> results = repo.getEmployeeAwardsCount();

	    List<Map<String, Object>> employeeAwardsList = new ArrayList<>();
	    for (Object[] result : results) {
	        Long employeeId = ((BigInteger) result[0]).longValue();
	        String awardsCountStr = (String) result[1];

	        List<Awards> employeeAwards = repo.findByEmployeeId(employeeId);
	        List<Map<String, Object>> awardsPhotosList = new ArrayList<>();

	        for (Awards awards : employeeAwards) {
	            Map<String, Object> awardsMap = new HashMap<>();
	            awardsMap.put("awardsId", awards.getAwardsId());
	            awardsMap.put("description", awards.getDescription());
	            awardsMap.put("gift", awards.getGift());
	            awardsMap.put("date", awards.getDate());
	            awardsMap.put("cash", awards.getCash());
	            awardsMap.put("employeeId", awards.getEmployeeId());
	            awardsMap.put("status", awards.isStatus());

	            List<Map<String, Object>> awardsPhotos = new ArrayList<>();
	            for (AwardsPhoto awardsPhoto : awards.getAwardsPhotos()) {
	                Map<String, Object> awardsPhotoMap = new HashMap<>();
	                awardsPhotoMap.put("awardsPhotoId", awardsPhoto.getAwardsPhotoId());
	                awardsPhotoMap.put("url", "/image/" + generateRandomNumber() + "/" + awardsPhoto.getAwardsPhotoId());
	                awardsPhotos.add(awardsPhotoMap);
	            }

	            awardsMap.put("awardsPhotos", awardsPhotos);
	            awardsMap.put("awardsCount", awardsCountStr); 

	            employeeAwardsList.add(awardsMap);
	        }
	    }

	    return employeeAwardsList;
	}
	
	
}