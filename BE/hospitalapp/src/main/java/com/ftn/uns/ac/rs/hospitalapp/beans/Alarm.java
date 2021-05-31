package com.ftn.uns.ac.rs.hospitalapp.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="alarm")
public class Alarm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="alarm_id")
	private Long id;
	
	@Column
	private int patientID;
	
	@Column
	private String dataType;
	
	@Column
	private String value;
	
	private double numValue;
	
	@Column
	private Date date;
	
	@Column
	private String patientsName;
	


	public Alarm() {
		super();
	}

	









	public Alarm( int patientID, String dataType, String value, double numValue, Date date) {
		super();
		this.patientID = patientID;
		this.dataType = dataType;
		this.value = value;
		this.numValue = numValue;
		this.date = date;
	}











	public Alarm(int patientID, String dataType, String value) {	
		super();
		this.patientID = patientID;
		this.dataType = dataType;
		this.value = value;

	}

	public Alarm(Long id, int patientID, String dataType, String value) {
		super();
		this.id = id;
		this.patientID = patientID;
		this.dataType = dataType;
		this.value = value;
	}
	
	

	

	public double getNumValue() {
		return numValue;
	}





	public void setNumValue(double numValue) {
		this.numValue = numValue;
	}





	public Date getDate() {
		return date;
	}





	public void setDate(Date date) {
		this.date = date;
	}





	public String getPatientsName() {
		return patientsName;
	}

	public void setPatientsName(String patientsName) {
		this.patientsName = patientsName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
