package com.ftn.uns.ac.rs.hospitalapp.util;

public class BloodData {

	private double CRP;
	private double erythrocytes;
	private double leukocytes;
	private double hemoglobin;

	public BloodData() {
		super();
	}

	public BloodData(double cRP, double erythrocytes, double leukocytes, double hemoglobin) {
		super();
		CRP = cRP;
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
	
	@Override
	public String toString() {
		String s = "-BLOOD DATA-\n";
		
		s += "\t-CRP: \t" + this.CRP + "\n";
		s += "\t-Erythrocytes: \t" + this.erythrocytes + "\n";
		s += "\t-Hemoglobin: \t" + this.hemoglobin + "\n";
		s += "\t-Leukocytes: \t" + this.leukocytes + "\n";
		
		return s;

	}

}
