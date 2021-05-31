package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.BloodDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;

@Service
public class BloodDataService {

	@Autowired
	private BloodDataRepository bloodDataRepository;
	
	public BloodData insert(BloodData d) {
		d.setDate(new Date());
		return this.bloodDataRepository.save(d);
	}
	
	public Page<BloodData> findAll(Pageable pageable) {
		return this.bloodDataRepository.findAllByDate(pageable);
	}
	
}
