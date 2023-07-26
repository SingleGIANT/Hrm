package com.example.hrm_new.entity.organization;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="announcements")
public class Announcements {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long announcementId;
	private String title;
	private Date fromDate;
	private Date toDate;
	private long companyId;
    private String informedBy;
	private boolean status;
	
	
	
	public Announcements() {
		super();
	}
	public long getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(long announcementId) {
		this.announcementId = announcementId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getInformedBy() {
		return informedBy;
	}
	public void setInformedBy(String informedBy) {
		this.informedBy = informedBy;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
