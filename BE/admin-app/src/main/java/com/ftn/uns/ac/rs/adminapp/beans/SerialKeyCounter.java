package com.ftn.uns.ac.rs.adminapp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "serial_key_counter")
public class SerialKeyCounter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "key_id")
	Long id;

	@Column(name = "counter", nullable = false)
	long counter;

	public SerialKeyCounter() {
		super();
	}

	public SerialKeyCounter(Long id, long counter) {
		super();
		this.id = id;
		this.counter = counter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}

}
