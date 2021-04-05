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
	
	public boolean setRequested(long id) {
		Hospital h = this.hospitalRepository.findById(id).orElse(null);
		
		if(h != null) {
			h.setRequestedCertificate(true);
			this.hospitalRepository.save(h);
			return true;
		}
		else
			return false;
	}
	
	public boolean isRequestedCertificate(long id) {
		Hospital h = this.hospitalRepository.findById(id).orElse(null);
		
		if(h != null)
			return h.isRequestedCertificate();
		else
			return false;
	}
}
