package com.ftn.uns.ac.rs.hospitalapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class PatientDTO {
	
	private Long id;
	
	@NotBlank(message = "Gender can not be empty.")
	@Pattern(regexp = "(FE)?MALE")
	private String gender;
	
	@NotBlank(message = "Name can not be empty.")
	@Pattern(regexp = "[A-Za-z]+",message="Name must start with capital letter and can contain only letters.")
	private String name;

	@NotNull(message = "Date of birth cannot be empty.")
	@DateTimeFormat(pattern = "YYYY-MM-DD")
	private String birthday;
	
	@NotNull(message = "Current age can not be empty.")
	@Pattern(regexp = "[0-9]+", message = "Current age can contain only numbers.")
	private int currentAge;
	
	@NotNull(message="Height can not be empty.")
	@Pattern(regexp = "[0-9]+",message = "Height can contain only numbers.")
	private double height;
	
	@NotNull(message="Weight can not be empty.")
	@Pattern(regexp = "[0-9]+",message = "Weight can contain only numbers.")
	private double weight;
	
	@NotNull(message="Blood pressure can not be empty.")
	@Pattern(regexp = "[0-9]+",message = "Blood pressure can contain only numbers.")
	private String averageBloodPressure;
	
	
	@Pattern(regexp = "[A-Za-z0-9]+")
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
