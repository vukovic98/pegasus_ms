package com.ftn.uns.ac.rs.hospitalapp.dto;

import java.util.Date;

public class ReportDTO {

	private Date startDate;
	private Date endDate;

	public ReportDTO() {
		super();
	}

	public ReportDTO(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
