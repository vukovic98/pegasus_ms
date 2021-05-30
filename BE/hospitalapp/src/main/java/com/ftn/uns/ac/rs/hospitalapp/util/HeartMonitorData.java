package com.ftn.uns.ac.rs.hospitalapp.util;

public class HeartMonitorData {
	private int saturation;
	private int systolic;
	private int diastolic;
	private int heartRate;
	private int patientID;
	
	
	public HeartMonitorData() {
		super();
	}
	
	
	public HeartMonitorData(int saturation, int systolic, int diastolic, int heartRate, int patientID) {
		super();
		this.saturation = saturation;
		this.systolic = systolic;
		this.diastolic = diastolic;
		this.heartRate = heartRate;
		this.patientID = patientID;
	}


	public int getSystolic() {
		return systolic;
	}


	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}


	public int getDiastolic() {
		return diastolic;
	}


	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
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

	public int getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	
	@Override
	public String toString() {
		String s = "-HEART MONITOR DATA-\n";
		
		s += "\t-Patient ID: " + this.patientID + "\n";
		s += "\t-Oxygen saturation: " + this.saturation + "%\n";
		s += "\t-Blood pressure: " + this.systolic +"/" + this.diastolic  + "mmHg\n";
		s += "\t-Heart rate: " + this.heartRate + " beats per minute\n";
		
		return s;

	}
}
