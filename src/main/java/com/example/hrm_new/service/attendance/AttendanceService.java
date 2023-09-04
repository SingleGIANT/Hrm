package com.example.hrm_new.service.attendance;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrm_new.entity.attendance.Attendance;
import com.example.hrm_new.entity.attendance.AttendanceList;
import com.example.hrm_new.repository.attendance.AttendanceRepository;


@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository repo;


	public Iterable<Attendance> listAll() {
		return this.repo.findAll();
	}

//	public void Save(Attendance attendance) {
//		repo.save(attendance);
//	}

	public Attendance getUserById(long id) {
		return repo.findById(id).get();
	}

	public void update(Attendance attendance, long AttendanceId) {
		repo.save(attendance);
	}

////////delete
	public void deleteMemberById(Long id) {
		repo.deleteById(id);

	}

	/////// edit
	public void save(Attendance existingAttendance) {
		repo.save(existingAttendance);
	}




	public Attendance findById(Long id) {
		return repo.findById(id).get();
	}

	
	
	
	
	

//	public List<Attendance> filterAttendance(Date attendanceDate, Month month, boolean fullDay, boolean halfDay,
//			boolean present, boolean absent) {
//		return repo.findByAttendanceDateAndMonthAndFullDayAndHalfDayAndPresentAndAbsent(attendanceDate, month, fullDay,
//				halfDay, present, absent);
//	}
//
//	public List<Attendance> getAttendanceByMemberid(long memberid) {
//		return repo.findByMemberid(memberid);
//	}
//
//	public List<Attendance> getAttendanceByFilters(long memberid, Date attendanceDate, Month month, boolean present,
//			boolean absent, boolean fullDay, boolean halfDay) {
//		return repo.findByMemberidAndAttendanceDateAndMonthAndPresentAndAbsentAndFullDayAndHalfDay(memberid,
//				attendanceDate, month, present, absent, fullDay, halfDay);
//	}

}
