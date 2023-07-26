package com.example.hrm_new.service.organization;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.organization.Announcements;
import com.example.hrm_new.repository.organization.AnnouncementsRepository;

@Service
public class AnnouncementsService {

	@Autowired
	private AnnouncementsRepository announcementsRepository;

	public Iterable<Announcements> listAll() {
		return this.announcementsRepository.findAll();

	}

	public void SaveorUpdate(Announcements announcement) {
		announcementsRepository.save(announcement);
	}

	public void save(Announcements announcement) {
		announcementsRepository.save(announcement);

	}

	public Announcements findById(Long announcement_id) {
		return announcementsRepository.findById(announcement_id).get();

	}

	public void deleteAnnouncementsIdById(Long announcement_id) {
		announcementsRepository.deleteById(announcement_id);
	}

	public Optional<Announcements> getAnnouncementsById(Long announcement_id) {
		return announcementsRepository.findById(announcement_id);

	}

	public List<Map<String, Object>> allAnnouncementsDetails() {
		return announcementsRepository.allAnnouncementsDetails();
	}

	public List<Map<String, Object>> allAnnouncementsDetailsByFromDate(LocalDate startdate) {
		return announcementsRepository.allAnnouncementsDetailsByFromDate(startdate);
	}

	public List<Map<String, Object>> findAllByAnnouncementsId(Long announcement_id) {
		return announcementsRepository.allDetailsOfAnnouncements(announcement_id);
	}
	
	public List<Map<String, Object>> allAnnouncementsDetailsByToDate(LocalDate enddate) {
		return announcementsRepository.allAnnouncementsDetailsByToDate(enddate);
	}
	
	public List<Map<String, Object>> allAnnouncementsDetailsByFromDateAndToDate(LocalDate startdate, LocalDate enddate) {
		return announcementsRepository.allAnnouncementsDetailsByFromDateAndToDate(startdate, enddate);
	}

}
