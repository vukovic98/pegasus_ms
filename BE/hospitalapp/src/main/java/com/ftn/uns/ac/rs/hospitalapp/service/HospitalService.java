package com.ftn.uns.ac.rs.hospitalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Hospital;
import com.ftn.uns.ac.rs.hospitalapp.repository.HospitalRepository;

@Service
public class HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;
	
	public boolean isCertified(long id) {
		Hospital h = this.hospitalRepository.findById(id).orElse(null);
		
		if(h != null)
			return h.isCertified();
		else
			return false;
	}
}
