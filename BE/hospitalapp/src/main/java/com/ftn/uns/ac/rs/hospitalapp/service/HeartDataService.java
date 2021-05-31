package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.HeartDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.HeartMonitorData;

@Service
public class HeartDataService {

	@Autowired
	private HeartDataRepository heartDataRepository;
	
	public HeartMonitorData insert(HeartMonitorData d) {
		d.setDate(new Date());
		return this.heartDataRepository.save(d);
	}
	
	public Page<HeartMonitorData> findAll(Pageable pageable) {
		return this.heartDataRepository.findAllByDate(pageable);
	}
	
}
