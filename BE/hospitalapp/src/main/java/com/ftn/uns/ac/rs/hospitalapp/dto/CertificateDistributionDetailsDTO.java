package com.ftn.uns.ac.rs.hospitalapp.dto;

import java.math.BigInteger;

public class CertificateDistributionDetailsDTO {

	private String path;
	private BigInteger serialNum;
	private byte[] cert;

	public CertificateDistributionDetailsDTO() {
		super();
	}

	public CertificateDistributionDetailsDTO(String path, BigInteger serialNum, byte[] cert) {
		super();
		this.path = path;
		this.serialNum = serialNum;
		this.cert = cert;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BigInteger getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(BigInteger serialNum) {
		this.serialNum = serialNum;
	}

	public byte[] getCert() {
		return cert;
	}

	public void setCert(byte[] cert) {
		this.cert = cert;
	}

}
