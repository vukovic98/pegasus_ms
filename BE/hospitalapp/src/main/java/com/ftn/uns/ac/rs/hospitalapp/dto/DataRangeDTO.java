package com.ftn.uns.ac.rs.hospitalapp.dto;

public class DataRangeDTO {

	public long patientID;
	public double minValue;
	public double maxValue;

	public DataRangeDTO() {
		super();
	}

	public DataRangeDTO(long patientID, double minValue, double maxValue) {
		super();
		this.patientID = patientID;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

}
