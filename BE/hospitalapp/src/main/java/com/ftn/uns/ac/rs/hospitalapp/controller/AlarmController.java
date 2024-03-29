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

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.DoSDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.LogTypeDTO;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.AlarmService;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityDataService;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;


@RestController
@RequestMapping(path = "/alarm")
public class AlarmController {

	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private LoggerProxy logger;
	
	@Autowired
	private SecurityDataService securityDataService;
	
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_ALARMS')")
	@GetMapping(path = "/by-page/{pageNum}")
	public ResponseEntity<PageImplementation<Alarm>> findAll(@PathVariable int pageNum) {

		
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<Alarm> page = this.alarmService.findAll(pageRequest);



		PageImplMapper<Alarm> pageMapper = new PageImplMapper<>();
		PageImplementation<Alarm> pageImpl = pageMapper.toPageImpl(page);
		
		
		this.logger.info("Successful attempt for retrieving alarms from hospital-app", AlarmController.class);
		
		return new ResponseEntity<>(pageImpl, HttpStatus.OK);
	}
	
	//	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	@PostMapping(path = "/create-alarm-for-logs")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForLogs(@RequestBody LogTypeDTO dto) {

		boolean ok = this.securityDataService.createRuleForLogType(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/create-alarm-for-dos")
	public ResponseEntity<HttpStatus> alarmForDos(@RequestBody DoSDTO dto){
		
		boolean ok = this.securityDataService.createRuleForDoSAttack(dto);
		
		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
