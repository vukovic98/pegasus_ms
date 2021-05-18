package com.ftn.uns.ac.rs.adminapp.beans;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Log {

	@Id
	private String id;
	
	private Date date;
	
	private String user;
	
	private String message;
	
	private String status;

	public Log() {
		super();
	}

	public Log(Date date, String user, String message, String status) {
		super();
		this.id = UUID.randomUUID().toString();
		this.date = date;
		this.user = user;
		this.message = message;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
