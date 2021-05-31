package com.ftn.uns.ac.rs.hospitalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.HeartDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.HeartMonitorData;

@Service
public class HeartDataService {

	@Autowired
	private HeartDataRepository heartDataRepository;
	
	public HeartMonitorData insert(HeartMonitorData d) {
		return this.heartDataRepository.save(d);
	}
	
}
