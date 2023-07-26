package com.example.hrm_new.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrm_new.entity.organization.ExpenseType;

public interface ExpenseTypeRepository extends  JpaRepository<ExpenseType, Long>{

}
