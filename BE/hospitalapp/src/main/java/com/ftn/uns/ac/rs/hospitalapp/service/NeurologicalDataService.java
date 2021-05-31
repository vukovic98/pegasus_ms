package com.ftn.uns.ac.rs.hospitalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.NeurologicalDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;

@Service
public class NeurologicalDataService {

	@Autowired
	private NeurologicalDataRepository neurologicalDataRepository;
	
	public NeurologicalData insert(NeurologicalData d) {
		return this.neurologicalDataRepository.save(d);
	}
	
}
