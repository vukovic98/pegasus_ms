package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeDTO;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.BloodDataService;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;

@RestController
@RequestMapping(path = "/blood-data")
public class BloodDataController {

	@Autowired
	private BloodDataService bloodDataService;

	@Autowired
	private LoggerProxy logger;

	@GetMapping("/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_DEVICES')")
	public ResponseEntity<PageImplementation<BloodData>> findAllPageable(@PathVariable("pageNum") int pageNum) {
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<BloodData> page = this.bloodDataService.findAll(pageRequest);

		PageImplMapper<BloodData> pageMapper = new PageImplMapper<>();
		PageImplementation<BloodData> pageImpl = pageMapper.toPageImpl(page);

		this.logger.info("Successful attempt for retrieving blood-data from hospital-app", BloodDataController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}

	@GetMapping("/by-page/{pageNum}/by-patient/{patientID}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_DEVICES')")
	public ResponseEntity<PageImplementation<BloodData>> findAllByPatient(@PathVariable("pageNum") int pageNum,
			@PathVariable("patientID") long patientID) {
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<BloodData> page = this.bloodDataService.findAllByPatient(pageRequest, patientID);

		PageImplMapper<BloodData> pageMapper = new PageImplMapper<>();
		PageImplementation<BloodData> pageImpl = pageMapper.toPageImpl(page);

		this.logger.info("Successful attempt for retrieving blood-data for patient [ " + patientID + " ] from hospital-app", BloodDataController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
	

	@PostMapping(path = "/create-alarm-for-crp")
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForCrp(@RequestBody DataRangeDTO dto) {

		boolean ok = this.bloodDataService.createRuleForCRP(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-leukocytes")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForLeukocytes(@RequestBody DataRangeDTO dto) {

		boolean ok = this.bloodDataService.createRuleForLeukocytes(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-erythrocytes")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForErythrocites(@RequestBody DataRangeDTO dto) {

		boolean ok = this.bloodDataService.createRuleForErythrocites(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-hemoglobin")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForHemoglobin(@RequestBody DataRangeDTO dto) {

		boolean ok = this.bloodDataService.createRuleForHemoglobin(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
