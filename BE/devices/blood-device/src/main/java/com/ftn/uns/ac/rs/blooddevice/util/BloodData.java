package com.ftn.uns.ac.rs.blooddevice.util;

public class BloodData {

	private int patientID;
	private double CRP;
	private double erythrocytes;
	private double leukocytes;
	private double hemoglobin;

	public BloodData() {
		super();
	}

	public BloodData(int id, double cRP, double erythrocytes, double leukocytes, double hemoglobin) {
		super();
		this.patientID = id;
		this.CRP = cRP;
		this.erythrocytes = erythrocytes;
		this.leukocytes = leukocytes;
		this.hemoglobin = hemoglobin;
	}

	public double getCRP() {
		return CRP;
	}

	public void setCRP(double cRP) {
		CRP = cRP;
	}

	public double getErythrocytes() {
		return erythrocytes;
	}

	public void setErythrocytes(double erythrocytes) {
		this.erythrocytes = erythrocytes;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public double getLeukocytes() {
		return leukocytes;
	}

	public void setLeukocytes(double leukocytes) {
		this.leukocytes = leukocytes;
	}

	public double getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(double hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

}
