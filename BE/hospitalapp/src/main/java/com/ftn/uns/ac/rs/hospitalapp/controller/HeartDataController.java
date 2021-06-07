package com.ftn.uns.ac.rs.hospitalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
