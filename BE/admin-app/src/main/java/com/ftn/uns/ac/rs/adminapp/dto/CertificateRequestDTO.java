package com.ftn.uns.ac.rs.adminapp.dto;

public class CertificateRequestDTO {
	private long id;
	private String subjectCN;
	private String subjectOU;
	private String subjectEmail;
	private String subjectON;
	private String subjectL;
	private String subjectST;
	private String subjectC;

	public CertificateRequestDTO(String subjectCN, String subjectOU, String subjectEmail, String subjectON,
			String subjectL, String subjectST, String subjectC, long id) {
		super();
		this.subjectCN = subjectCN;
		this.subjectOU = subjectOU;
		this.subjectEmail = subjectEmail;
		this.subjectON = subjectON;
		this.subjectL = subjectL;
		this.subjectST = subjectST;
		this.subjectC = subjectC;
		this.id = id;
	}

	public CertificateRequestDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubjectCN() {
		return subjectCN;
	}

	public void setSubjectCN(String subjectCN) {
		this.subjectCN = subjectCN;
	}

	public String getSubjectOU() {
		return subjectOU;
	}

	public void setSubjectOU(String subjectOU) {
		this.subjectOU = subjectOU;
	}

	public String getSubjectEmail() {
		return subjectEmail;
	}

	public void setSubjectEmail(String subjectEmail) {
		this.subjectEmail = subjectEmail;
	}

	public String getSubjectON() {
		return subjectON;
	}

	public void setSubjectON(String subjectON) {
		this.subjectON = subjectON;
	}

	public String getSubjectL() {
		return subjectL;
	}

	public void setSubjectL(String subjectL) {
		this.subjectL = subjectL;
	}

	public String getSubjectST() {
		return subjectST;
	}

	public void setSubjectST(String subjectST) {
		this.subjectST = subjectST;
	}

	public String getSubjectC() {
		return subjectC;
	}

	public void setSubjectC(String subjectC) {
		this.subjectC = subjectC;
	}

}
