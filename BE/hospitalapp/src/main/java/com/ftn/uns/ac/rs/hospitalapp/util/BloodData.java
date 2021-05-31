package com.ftn.uns.ac.rs.hospitalapp.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blood_data")
public class BloodData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "patient_id", nullable = false)
	private int patientID;
	
	@Column(name = "crp", nullable = false)
	private double CRP;
	
	@Column(name = "erythrocytes", nullable = false)
	private double erythrocytes;
	
	@Column(name = "leukocytes", nullable = false)
	private double leukocytes;
	
	@Column(name = "hemoglobin", nullable = false)
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

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
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

		s += "\t-Patient ID: \t" + this.patientID + "\n";
		s += "\t-CRP: \t" + this.CRP + " mg/L\n";
		s += "\t-Erythrocytes: \t" + this.erythrocytes + " 10^12/L\n";
		s += "\t-Hemoglobin: \t" + this.hemoglobin + " g/L\n";
		s += "\t-Leukocytes: \t" + this.leukocytes + " 10^9/L\n";

		return s;

	}

}
