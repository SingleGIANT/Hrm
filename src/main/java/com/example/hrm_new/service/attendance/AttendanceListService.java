package com.example.hrm_new.service.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.attendance.AttendanceList;
import com.example.hrm_new.repository.attendance.AttendanceListRdepository;

@Service
public class AttendanceListService {
	
	@Autowired
	private AttendanceListRdepository  repo;
	
	
	public void save(AttendanceList attendancelist) {
		repo.save(attendancelist);
	}
	public AttendanceList findById(Long attendanceListId) {
		return repo.findById(attendanceListId).get();
	}
	
	

}
