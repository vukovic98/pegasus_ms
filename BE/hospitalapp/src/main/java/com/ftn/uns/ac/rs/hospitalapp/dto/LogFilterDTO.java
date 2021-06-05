package com.ftn.uns.ac.rs.hospitalapp.dto;

public class LogFilterDTO {

	private String regex;
	private String status;

	public LogFilterDTO() {
		super();
	}

	public LogFilterDTO(String regex, String status) {
		super();
		this.regex = regex;
		this.status = status;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
