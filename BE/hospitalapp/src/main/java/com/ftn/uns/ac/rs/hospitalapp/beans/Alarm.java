package com.ftn.uns.ac.rs.hospitalapp.beans;

public class Alarm {
	
	private int patientID;

	private String dataType;
	private double value;
	


	public Alarm() {
		super();
	}

	public Alarm(String dataType, double value) {		super();
		this.patientID = patientID;
		this.dataType = dataType;
		this.value = value;

	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}

}
