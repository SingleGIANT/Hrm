package com.example.hrm_new.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrm_new.entity.employee.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long>{

}
