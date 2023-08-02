package com.example.hrm_new.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrm_new.entity.employee.RelationType;

public interface RelationTypeRepository extends JpaRepository<RelationType, Long> {

}
