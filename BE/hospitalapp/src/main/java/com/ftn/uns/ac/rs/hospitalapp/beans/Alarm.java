package com.ftn.uns.ac.rs.hospitalapp.beans;

public class Alarm {
	
	private int patientID;
	private String dataType;
	private String value;


	public Alarm() {
		super();
	}

	public Alarm(int patientID,String dataType, String value) {
		super();
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
