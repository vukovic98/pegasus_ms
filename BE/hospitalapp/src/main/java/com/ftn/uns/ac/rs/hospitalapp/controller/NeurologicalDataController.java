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
import com.ftn.uns.ac.rs.hospitalapp.service.NeurologicalDataService;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;

@RestController
@RequestMapping(path = "/neuro-data")
public class NeurologicalDataController {

	@Autowired
	private NeurologicalDataService neuroDataService;

	@Autowired
	private LoggerProxy logger;

	@GetMapping("/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_DEVICES')")
	public ResponseEntity<PageImplementation<NeurologicalData>> findAllPageable(@PathVariable("pageNum") int pageNum) {
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<NeurologicalData> page = this.neuroDataService.findAll(pageRequest);

		PageImplMapper<NeurologicalData> pageMapper = new PageImplMapper<>();
		PageImplementation<NeurologicalData> pageImpl = pageMapper.toPageImpl(page);

		this.logger.info("Successful attempt for retrieving neuro-data from hospital-app",
				NeurologicalDataController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_DEVICES')")
	@GetMapping("/by-page/{pageNum}/by-patient/{id}")
	public ResponseEntity<PageImplementation<NeurologicalData>> findAllByPatientId(@PathVariable("pageNum") int pageNum, @PathVariable("id") long id){
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<NeurologicalData> page = this.neuroDataService.findAllByPatientId(pageRequest, id);

		PageImplMapper<NeurologicalData> pageMapper = new PageImplMapper<>();
		PageImplementation<NeurologicalData> pageImpl = pageMapper.toPageImpl(page);

		this.logger.info("Successful attempt for retrieving neuro-data for patient [ " + id + " ] from hospital-app", NeurologicalDataController.class);

		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-bis")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForBIS(@RequestBody DataRangeDTO dto) {

		boolean ok = this.neuroDataService.createRuleForHeartmonitorBIS(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-icp")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForICP(@RequestBody DataRangeDTO dto) {

		boolean ok = this.neuroDataService.createRuleForHeartmonitorICP(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-combined-alarm")
	public ResponseEntity<ArrayList<DataRangeDTO>> combinedAlarm(@RequestBody DataRangeCombinedDTO dto) {
		boolean ok = this.neuroDataService.createRuleForBloodDeviceCombined(dto);
		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
