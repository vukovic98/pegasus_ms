package com.ftn.uns.ac.rs.hospitalapp.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "patient_id")
	private Long id;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;
	
	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column(name = "birthday", nullable = false)
	private Date birthday;
	
	@Column(name = "currentAge", nullable = false)
	private int currentAge;
	
	@Column(name = "height", nullable = false)
	private double height;
	
	@Column(name = "weight", nullable = false)
	private double weight;
	
	@Column(name = "averageBloodPressure", nullable = false)
	private String averageBloodPressure;
	
	@Column(name = "pastMedicalHistory", nullable = false)
	private String pastMedicalHistory;

	
	
	public Patient() {
		super();
	}

	public Patient(Long id, String gender, String firstName, String lastName, Date birthday, int currentAge,
			double height, double weight, String averageBloodPressure, String pastMedicalHistory) {
		super();
		this.id = id;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.currentAge = currentAge;
		this.height = height;
		this.weight = weight;
		this.averageBloodPressure = averageBloodPressure;
		this.pastMedicalHistory = pastMedicalHistory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getCurrentAge() {
		return currentAge;
	}

	public void setCurrentAge(int currentAge) {
		this.currentAge = currentAge;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getAverageBloodPressure() {
		return averageBloodPressure;
	}

	public void setAverageBloodPressure(String averageBloodPressure) {
		this.averageBloodPressure = averageBloodPressure;
	}

	public String getPastMedicalHistory() {
		return pastMedicalHistory;
	}

	public void setPastMedicalHistory(String pastMedicalHistory) {
		this.pastMedicalHistory = pastMedicalHistory;
	}
	
	
	
}
