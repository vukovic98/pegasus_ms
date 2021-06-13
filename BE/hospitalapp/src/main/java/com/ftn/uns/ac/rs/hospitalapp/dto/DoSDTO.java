package com.ftn.uns.ac.rs.hospitalapp.dto;

public class DoSDTO {

	private String ipAddress;
	private int noRequests;

	public DoSDTO() {
		super();
	}

	public DoSDTO(String ipAddress, int noRequests) {
		super();
		this.ipAddress = ipAddress;
		this.noRequests = noRequests;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getNoRequests() {
		return noRequests;
	}

	public void setNoRequests(int noRequests) {
		this.noRequests = noRequests;
	}

}
