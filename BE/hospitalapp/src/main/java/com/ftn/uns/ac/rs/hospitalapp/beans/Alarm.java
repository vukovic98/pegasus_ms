package com.ftn.uns.ac.rs.hospitalapp.beans;

public class Alarm {

	private String dataType;
	private String value;

	public Alarm() {
		super();
	}

	public Alarm(String dataType, String value) {
		super();
		this.dataType = dataType;
		this.value = value;
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
