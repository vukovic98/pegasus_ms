package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.NeurologicalDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;

@Service
public class NeurologicalDataService {

	@Autowired
	private NeurologicalDataRepository neurologicalDataRepository;
	
	public NeurologicalData insert(NeurologicalData d) {
		d.setDate(new Date());
		return this.neurologicalDataRepository.save(d);
	}
	
	public Page<NeurologicalData> findAll(Pageable pageable) {
		return this.neurologicalDataRepository.findAllByDate(pageable);
	}
	
	public Page<NeurologicalData> findAllByPatientId(Pageable pageable, long id){
		return this.neurologicalDataRepository.findAllByPatient(pageable, id);
	}
}
