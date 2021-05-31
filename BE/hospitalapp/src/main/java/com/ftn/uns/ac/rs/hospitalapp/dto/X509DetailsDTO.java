package com.ftn.uns.ac.rs.hospitalapp.dto;

import java.security.cert.X509Certificate;

import javax.validation.constraints.NotBlank;

public class X509DetailsDTO {

	@NotBlank(message = "Serial number can not be empty.")
	private String serialNum;
	
	@NotBlank(message = "Issuer can not be empty.")
	private String issuer;
	
	@NotBlank(message = "Subject can not be empty.")
	private String subject;
	
	@NotBlank(message = "Issued date can not be empty.")
	private String issuedDate;
	
	@NotBlank(message = "Valid to date can not be empty.")
	private String validToDate;

	public X509DetailsDTO() {
		super();
	}

	public X509DetailsDTO(String ser, String issuer, String subject, String issuedDate, String validToDate) {
		super();
		this.serialNum = ser;
		this.issuer = issuer;
		this.subject = subject;
		this.issuedDate = issuedDate;
		this.validToDate = validToDate;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

}
