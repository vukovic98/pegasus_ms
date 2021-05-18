package com.ftn.uns.ac.rs.hospitalapp.util;

public class UnknownDeviceData {
	private int patientID;
	private double temperature;
	
	
	
	public UnknownDeviceData() {
		super();
	}
	public UnknownDeviceData(int patientID, double temperature) {
		super();
		this.patientID = patientID;
		this.temperature = temperature;
	}
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	@Override
	public String toString() {
		String s = "-TEMPERATURE DEVICE DATA-\n";

		s += "\t-Patient ID: \t" + this.patientID + "\n";
		s += "\t-Temperature: \t" + this.temperature + "C\n";

		return s;

	}
	
}
