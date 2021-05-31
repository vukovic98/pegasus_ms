package com.ftn.uns.ac.rs.hospitalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ftn.uns.ac.rs.hospitalapp.beans.Patient;
import com.ftn.uns.ac.rs.hospitalapp.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	

	public Page<Patient> findAll(Pageable pageable){
		return (Page<Patient>) this.patientRepository.findAll(pageable);
	}
	
	public Patient findById(Long id) {
		return this.patientRepository.findById(id).orElse(null);
	}
}
