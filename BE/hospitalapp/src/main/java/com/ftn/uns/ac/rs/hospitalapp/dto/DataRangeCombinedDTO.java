package com.ftn.uns.ac.rs.hospitalapp.dto;

public class DataRangeCombinedDTO {
	
	private long patientID;
	private double minValue1;
	private double maxValue1;
	private double minValue2;
	private double maxValue2;
	private String attrName1;
	private String attrName2;
	public DataRangeCombinedDTO() {
		super();
	}
	
	public DataRangeCombinedDTO(long patientID, double minValue1, double maxValue1, double minValue2, double maxValue2,
			String attrName1, String attrName2) {
		super();
		this.patientID = patientID;
		this.minValue1 = minValue1;
		this.maxValue1 = maxValue1;
		this.minValue2 = minValue2;
		this.maxValue2 = maxValue2;
		this.attrName1 = attrName1;
		this.attrName2 = attrName2;
	}

	public long getPatientID() {
		return patientID;
	}
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	public double getMinValue1() {
		return minValue1;
	}
	public void setMinValue1(double minValue1) {
		this.minValue1 = minValue1;
	}
	public double getMaxValue1() {
		return maxValue1;
	}
	public void setMaxValue1(double maxValue1) {
		this.maxValue1 = maxValue1;
	}
	public double getMinValue2() {
		return minValue2;
	}
	public void setMinValue2(double minValue2) {
		this.minValue2 = minValue2;
	}
	public double getMaxValue2() {
		return maxValue2;
	}
	public void setMaxValue2(double maxValue2) {
		this.maxValue2 = maxValue2;
	}

	public String getAttrName1() {
		return attrName1;
	}

	public void setAttrName1(String attrName1) {
		this.attrName1 = attrName1;
	}

	public String getAttrName2() {
		return attrName2;
	}

	public void setAttrName2(String attrName2) {
		this.attrName2 = attrName2;
	}
	
	
	
}
