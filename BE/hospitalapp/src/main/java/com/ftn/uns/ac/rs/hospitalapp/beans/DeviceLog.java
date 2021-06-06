package com.ftn.uns.ac.rs.hospitalapp.beans;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class DeviceLog {

	@Id
	private String id;
	
	private Date date;
	
	private String user;
	
	private String message;

	public DeviceLog() {
		super();
	}

	public DeviceLog(Date date, String user, String message) {
		super();
		this.id = UUID.randomUUID().toString();
		this.date = date;
		this.user = user;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
