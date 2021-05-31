package com.ftn.uns.ac.rs.hospitalapp.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "heart_data")
public class HeartMonitorData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "saturation", nullable = false)
	private int saturation;

	@Column(name = "systolic", nullable = false)
	private int systolic;

	@Column(name = "diastolic", nullable = false)
	private int diastolic;

	@Column(name = "heart_rate", nullable = false)
	private int heartRate;

	@Column(name = "patient_id", nullable = false)
	private int patientID;

	@Column(name = "date", nullable = false)
	private Date date;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String s = "-HEART MONITOR DATA-\n";

		s += "\t-Patient ID: " + this.patientID + "\n";
		s += "\t-Oxygen saturation: " + this.saturation + "%\n";
		s += "\t-Blood pressure: " + this.systolic + "/" + this.diastolic + "mmHg\n";
		s += "\t-Heart rate: " + this.heartRate + " beats per minute\n";

		return s;

	}
}
