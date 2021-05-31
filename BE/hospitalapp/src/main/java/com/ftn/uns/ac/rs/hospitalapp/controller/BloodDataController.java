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

import com.ftn.uns.ac.rs.hospitalapp.service.BloodDataService;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;
import com.ftn.uns.ac.rs.hospitalapp.util.LoggerProxy;
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

}
