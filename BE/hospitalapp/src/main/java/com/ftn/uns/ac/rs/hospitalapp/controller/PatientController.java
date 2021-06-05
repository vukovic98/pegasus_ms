package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ftn.uns.ac.rs.hospitalapp.beans.Patient;
import com.ftn.uns.ac.rs.hospitalapp.dto.PatientDTO;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.PatientService;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;
import com.ftn.uns.ac.rs.hospitalapp.util.PatientDetailsMapper;

@RestController
@RequestMapping(path = "/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientDetailsMapper patientDetailsMapper;
	
	@Autowired
	private LoggerProxy logger;
	
	
	@GetMapping(path = "/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_PATIENTS')")
	public ResponseEntity<PageImplementation<PatientDTO>> findAll(@PathVariable int pageNum) {
		
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<Patient> page = this.patientService.findAll(pageRequest);

		List<PatientDTO> patientsDTOS = this.patientDetailsMapper.entityListToDtoList(page.toList());
		Page<PatientDTO> pagePatientsDTOS = new PageImpl<>(patientsDTOS, page.getPageable(), page.getTotalElements());

		PageImplMapper<PatientDTO> pageMapper = new PageImplMapper<>();
		PageImplementation<PatientDTO> pageImpl = pageMapper.toPageImpl(pagePatientsDTOS);
		
		this.logger.info("Successful attempt for retrieving patients from hospital-app", PatientController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
}
