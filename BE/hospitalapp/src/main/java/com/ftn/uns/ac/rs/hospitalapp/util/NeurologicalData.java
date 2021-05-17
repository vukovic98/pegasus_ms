package com.ftn.uns.ac.rs.hospitalapp.util;

public class NeurologicalData {

	private int patientId;
	private double ICP;
	private double BIS;
	public NeurologicalData() {
		super();
	}
	public NeurologicalData(int patientId, double iCP, double bIS) {
		super();
		this.patientId = patientId;
		ICP = iCP;
		BIS = bIS;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public double getICP() {
		return ICP;
	}
	public void setICP(double iCP) {
		ICP = iCP;
	}
	public double getBIS() {
		return BIS;
	}
	public void setBIS(double bIS) {
		BIS = bIS;
	}
	
	@Override
	public String toString() {
		String s = "-NEUROLOGICAL DATA-\n";
		
		s += "\t-ICP: \t" + this.ICP + "  mmHg\n";
		s += "\t-BIS: \t" + this.BIS + "\n";
		
		return s;

	}
	
}
