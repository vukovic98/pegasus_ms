package com.ftn.uns.ac.rs.adminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.beans.SerialKeyCounter;
import com.ftn.uns.ac.rs.adminapp.repository.SerialKeyCounterRepository;

@Service
public class SerialKeyCounterService {

	@Autowired
	private SerialKeyCounterRepository counterRepository;
	
	public long getNextValue() {
		SerialKeyCounter c = this.counterRepository.findById(1L).orElse(null);
		
		if(c != null) {
			long val = c.getCounter();
			
			c.setCounter(val + 1);
			this.counterRepository.save(c);
			
			return val;
		} else
			return -1;
	}
}
