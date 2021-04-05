package com.ftn.uns.ac.rs.adminapp.dto;

public class CertDetailsDTO {

	private String serialNum;
	
	private String subjectID;
	private String subjectCN;
	private String subjectEmail;
	private String subjectOU;
	
	private String issuerID;
	private String issuerCN;
	private String issuerEmail;
	private String issuerOU;
	
	
	private String issuedDate;
	private String validToDate;
	
	private boolean isRevoked;
	private String revokedReason;
	
	public CertDetailsDTO() {
		super();
	}

	public CertDetailsDTO(String serialNum, String subjectID, String subjectCN, String subjectEmail, String subjectOU,
			String issuerID, String issuerCN, String issuerEmail, String issuerOU, String issuedDate,
			String validToDate) {
		super();
		this.serialNum = serialNum;
		this.subjectID = subjectID;
		this.subjectCN = subjectCN;
		this.subjectEmail = subjectEmail;
		this.subjectOU = subjectOU;
		this.issuerID = issuerID;
		this.issuerCN = issuerCN;
		this.issuerEmail = issuerEmail;
		this.issuerOU = issuerOU;
		this.issuedDate = issuedDate;
		this.validToDate = validToDate;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getSubjectCN() {
		return subjectCN;
	}

	public void setSubjectCN(String subjectCN) {
		this.subjectCN = subjectCN;
	}

	public String getSubjectEmail() {
		return subjectEmail;
	}

	public void setSubjectEmail(String subjectEmail) {
		this.subjectEmail = subjectEmail;
	}

	public String getSubjectOU() {
		return subjectOU;
	}

	public void setSubjectOU(String subjectOU) {
		this.subjectOU = subjectOU;
	}

	public String getIssuerID() {
		return issuerID;
	}

	public void setIssuerID(String issuerID) {
		this.issuerID = issuerID;
	}

	public String getIssuerCN() {
		return issuerCN;
	}

	public void setIssuerCN(String issuerCN) {
		this.issuerCN = issuerCN;
	}

	public String getIssuerEmail() {
		return issuerEmail;
	}

	public void setIssuerEmail(String issuerEmail) {
		this.issuerEmail = issuerEmail;
	}

	public String getIssuerOU() {
		return issuerOU;
	}

	public void setIssuerOU(String issuerOU) {
		this.issuerOU = issuerOU;
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

	public String getRevokedReason() {
		return revokedReason;
	}

	public void setRevokedReason(String revokedReason) {
		this.revokedReason = revokedReason;
	}
	
	
}
