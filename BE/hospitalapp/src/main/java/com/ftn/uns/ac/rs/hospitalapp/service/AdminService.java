package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Admin;
import com.ftn.uns.ac.rs.hospitalapp.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	public Page<Admin> findAll(Pageable pageable) {
		return (Page<Admin>) this.adminRepository.findAll(pageable);
	}
	
	public ArrayList<Admin> findAdminsForHospital(long id) {
		return this.adminRepository.findByHospitalId(id);
	}
	
	public Admin save(Admin a) {
		return this.adminRepository.save(a);
	}
	
}
