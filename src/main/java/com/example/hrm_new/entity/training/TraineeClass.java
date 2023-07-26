package com.example.hrm_new.entity.training;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="traineeClass")
public class TraineeClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long traineeClassId;
	private String trainerName;
	private long traineeDetailsId;
	private int totalDuration;
	private Date startDate;
	private String sectionName;
	private int totalModules;
	
	
	
	public TraineeClass() {
		super();
	}

	public long getTraineeDetailsId() {
		return traineeDetailsId;
	}

	public void setTraineeDetailsId(long traineeDetailsId) {
		this.traineeDetailsId = traineeDetailsId;
	}

	public long getTraineeClassId() {
		return traineeClassId;
	}
	public void setTraineeClassId(long traineeClassId) {
		this.traineeClassId = traineeClassId;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}


	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public int getTotalModules() {
		return totalModules;
	}
	public void setTotalModules(int totalModules) {
		this.totalModules = totalModules;
	}
	
}
