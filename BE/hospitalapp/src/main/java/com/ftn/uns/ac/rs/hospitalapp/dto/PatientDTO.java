package com.ftn.uns.ac.rs.hospitalapp.dto;

public class PatientDTO {
	
	private Long id;
	private String gender;
	private String name;
	private String birthday;
	private int currentAge;
	private double height;
	private double weight;
	private String averageBloodPressure;
	private String pastMedicalRecord;
	
	
	public PatientDTO() {
		super();
	}
	public PatientDTO(Long id, String gender, String name, String birthday, int currentAge, double height,
			double weight, String averageBloodPressure, String pastMedicalRecord) {
		super();
		this.id = id;
		this.gender = gender;
		this.name = name;
		this.birthday = birthday;
		this.currentAge = currentAge;
		this.height = height;
		this.weight = weight;
		this.averageBloodPressure = averageBloodPressure;
		this.pastMedicalRecord = pastMedicalRecord;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
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
	public String getPastMedicalRecord() {
		return pastMedicalRecord;
	}
	public void setPastMedicalRecord(String pastMedicalRecord) {
		this.pastMedicalRecord = pastMedicalRecord;
	}
	
	

}
