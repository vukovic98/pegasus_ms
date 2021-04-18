package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Doctor;
import com.ftn.uns.ac.rs.hospitalapp.dto.UserDetailsDTO;
import com.ftn.uns.ac.rs.hospitalapp.service.DoctorService;
import com.ftn.uns.ac.rs.hospitalapp.util.DoctorDetailsMapper;

@RestController
@RequestMapping(path = "/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private DoctorDetailsMapper userMapper;

	@GetMapping(path = "/by-page/{pageNum}")
	public ResponseEntity<Page<UserDetailsDTO>> findAll(@PathVariable int pageNum) {

		Pageable pageRequest = PageRequest.of(pageNum, 10);

		Page<Doctor> page = this.doctorService.findAll(pageRequest);

		List<UserDetailsDTO> doctorsDTOS = this.userMapper.listToDTO(page.toList());
		Page<UserDetailsDTO> pageOffersDTOS = new PageImpl<>(doctorsDTOS, page.getPageable(), page.getTotalElements());

		return new ResponseEntity<>(pageOffersDTOS, HttpStatus.OK);
	}
	
}
