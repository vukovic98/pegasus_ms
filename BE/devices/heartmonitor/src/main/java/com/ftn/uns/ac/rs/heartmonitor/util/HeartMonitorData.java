package com.ftn.uns.ac.rs.heartmonitor.util;

public class HeartMonitorData {
	private int saturation;
	private String bloodPressure;
	private int heartRate;
	private int patientID;
	
	
	public HeartMonitorData() {
		super();
	}
	public HeartMonitorData(int patientID,int saturation, String bloodPressure, int heartRate) {
		super();
		this.saturation = saturation;
		this.bloodPressure = bloodPressure;
		this.heartRate = heartRate;
		this.patientID = patientID;
	}
	
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public int getSaturation() {
		return saturation;
	}
	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public int getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	

}
