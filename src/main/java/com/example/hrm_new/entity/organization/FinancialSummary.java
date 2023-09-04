package com.example.hrm_new.entity.organization;

import java.util.List;

public class FinancialSummary {
	
    private List<Double> income;
    private List<Double> expense;
	public List<Double> getIncome() {
		return income;
	}
	public void setIncome(List<Double> income) {
		this.income = income;
	}
	public List<Double> getExpense() {
		return expense;
	}
	public void setExpense(List<Double> expense) {
		this.expense = expense;
	}
    
    
    

}
