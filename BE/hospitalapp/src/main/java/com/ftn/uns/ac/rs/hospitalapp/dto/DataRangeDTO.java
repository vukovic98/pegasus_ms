package com.ftn.uns.ac.rs.hospitalapp.dto;

public class DataRangeDTO {

	public double minValue;
	public double maxValue;

	public DataRangeDTO() {
		super();
	}

	public DataRangeDTO(double minValue, double maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

}
