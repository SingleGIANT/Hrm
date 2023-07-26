package com.example.hrm_new.repository.payroll;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrm_new.entity.payroll.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType , Long>{

}
