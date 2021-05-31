package com.ftn.uns.ac.rs.hospitalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.BloodDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;

@Service
public class BloodDataService {

	@Autowired
	private BloodDataRepository bloodDataRepository;
	
	public BloodData insert(BloodData d) {
		return this.bloodDataRepository.save(d);
	}
	
}
