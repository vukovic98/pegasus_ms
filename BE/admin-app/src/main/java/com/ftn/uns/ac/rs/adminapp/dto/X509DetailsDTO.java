package com.ftn.uns.ac.rs.adminapp.dto;

public class X509DetailsDTO {

	private String serialNum;
	private String issuer;
	private String subject;
	private String issuedDate;
	private String validToDate;
	private boolean isRevoked;

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

	public boolean isRevoked() {
		return isRevoked;
	}

	public void setRevoked(boolean isRevoked) {
		this.isRevoked = isRevoked;
	}

}
