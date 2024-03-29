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

import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeCombinedDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeDTO;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.HeartDataService;
import com.ftn.uns.ac.rs.hospitalapp.util.HeartMonitorData;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;


@RestController
@RequestMapping(path = "/heart-data")
public class HeartDataController {

	@Autowired
	private HeartDataService heartDataService;

	@Autowired
	private LoggerProxy logger;

	@GetMapping("/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_DEVICES')")
	public ResponseEntity<PageImplementation<HeartMonitorData>> findAllPageable(@PathVariable("pageNum") int pageNum) {
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<HeartMonitorData> page = this.heartDataService.findAll(pageRequest);

		PageImplMapper<HeartMonitorData> pageMapper = new PageImplMapper<>();
		PageImplementation<HeartMonitorData> pageImpl = pageMapper.toPageImpl(page);

		this.logger.info("Successful attempt for retrieving heart-data from hospital-app", HeartDataController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_DEVICES')")
	@GetMapping("/by-page/{pageNum}/by-patient/{id}")
	public ResponseEntity<PageImplementation<HeartMonitorData>> findAllByPatientId(@PathVariable("pageNum") int pageNum, @PathVariable("id") long id){
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<HeartMonitorData> page = this.heartDataService.findAllByPatientId(pageRequest, id);

		PageImplMapper<HeartMonitorData> pageMapper = new PageImplMapper<>();
		PageImplementation<HeartMonitorData> pageImpl = pageMapper.toPageImpl(page);

		this.logger.info("Successful attempt for retrieving heart-data for patient [ " + id + " ] from hospital-app", HeartDataController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-saturation")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForSaturation(@RequestBody DataRangeDTO dto) {

		boolean ok = this.heartDataService.createRuleForHeartmonitorSaturation(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-heart-rate")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForHeartRate(@RequestBody DataRangeDTO dto) {

		boolean ok = this.heartDataService.createRuleForHeartmonitorHeartRate(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-diastolic")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForDiastolic(@RequestBody DataRangeDTO dto) {

		boolean ok = this.heartDataService.createRuleForHeartmonitorDiastolic(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-systolic")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForSystolic(@RequestBody DataRangeDTO dto) {

		boolean ok = this.heartDataService.createRuleForHeartmonitorSystolic(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-combined-alarm")
	public ResponseEntity<ArrayList<DataRangeDTO>> combinedAlarm(@RequestBody DataRangeCombinedDTO dto) {
		boolean ok = this.heartDataService.createRuleForHeartMonitorCombined(dto);
		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
