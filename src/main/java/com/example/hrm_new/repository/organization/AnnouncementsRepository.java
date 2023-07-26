package com.example.hrm_new.repository.organization;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hrm_new.entity.organization.Announcements;

public interface AnnouncementsRepository extends JpaRepository<Announcements, Long> {

	@Query(value = "select an.*,c.company_name,datediff(an.to_date,an.from_date) as total_days_of_annoucement  from announcements as an"
			+ " join company as c on c.company_id=an.company_id", nativeQuery = true)
	List<Map<String, Object>> allAnnouncementsDetails();

	@Query(value = " select an.*,c.company_name,datediff(an.to_date,an.from_date) as total_days_of_annoucement from announcements as an "
			+ " join company as c on c.company_id=an.company_id "
			+ " where an.announcement_id=:announcement_id ", nativeQuery = true)
	List<Map<String, Object>> allDetailsOfAnnouncements(@Param("announcement_id") Long announcement_id);

	List<Announcements> findByStatusTrue();

	@Query(value = "select an.*,c.company_name,datediff(an.to_date,an.from_date) as total_days_of_annoucement  from announcements as an"
			+ " join company as c on c.company_id=an.company_id"
			+ " where an.from_date =:startdate", nativeQuery = true)
	List<Map<String, Object>> allAnnouncementsDetailsByFromDate(LocalDate startdate);

	@Query(value = "select an.*,c.company_name,datediff(an.to_date,an.from_date) as total_days_of_annoucement  from announcements as an"
			+ " join company as c on c.company_id=an.company_id" + " where an.to_date =:enddate", nativeQuery = true)
	List<Map<String, Object>> allAnnouncementsDetailsByToDate(LocalDate enddate);

	@Query(value = "select an.*,c.company_name,datediff(an.to_date,an.from_date) as total_days_of_annoucement  from announcements as an"
			+ " join company as c on c.company_id=an.company_id"
			+ " where an.from_date =:startdate and an.to_date=:enddate", nativeQuery = true)
	List<Map<String, Object>> allAnnouncementsDetailsByFromDateAndToDate(LocalDate startdate, LocalDate enddate);
}
