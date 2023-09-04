package com.example.hrm_new.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrm_new.entity.employee.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
