package com.example.hrm_new.entity.organization;

import javax.persistence.*;

@Entity
	public class IncomeData {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id; // Assuming you have an ID field

	    private Integer year;
	    private Integer month;
	    private Double total_project_amount;
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Integer getYear() {
			return year;
		}
		public void setYear(Integer year) {
			this.year = year;
		}
		public Integer getMonth() {
			return month;
		}
		public void setMonth(Integer month) {
			this.month = month;
		}
		public Double getTotal_project_amount() {
			return total_project_amount;
		}
		public void setTotal_project_amount(Double total_project_amount) {
			this.total_project_amount = total_project_amount;
		}
		
	    
	    

	    
	    
}
