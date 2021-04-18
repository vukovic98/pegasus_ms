package com.ftn.uns.ac.rs.hospitalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Doctor;
import com.ftn.uns.ac.rs.hospitalapp.repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	public Page<Doctor> findAll(Pageable pageable) {
		return (Page<Doctor>) this.doctorRepository.findAll(pageable);
	}
	
	public Doctor save(Doctor d) {
		return this.doctorRepository.save(d);
	}
	
}
