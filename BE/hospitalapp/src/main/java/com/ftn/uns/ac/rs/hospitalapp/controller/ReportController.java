package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.beans.DeviceLog;
import com.ftn.uns.ac.rs.hospitalapp.beans.HospitalLog;
import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.dto.ReportDTO;
import com.ftn.uns.ac.rs.hospitalapp.service.ReportService;

@RestController
@RequestMapping(path = "/report")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@PostMapping(path = "/log-data")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_REPORTS')")
	public ResponseEntity<ArrayList<HospitalLog>> getLogReport(@RequestBody ReportDTO dto) {
		ArrayList<HospitalLog> logs = this.reportService.getAllLogs(dto);
		
		if(!logs.isEmpty())
			return new ResponseEntity<>(logs, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/device-data")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_REPORTS')")
	public ResponseEntity<ArrayList<DeviceLog>> getDeviceReport(@RequestBody ReportDTO dto) {
		ArrayList<DeviceLog> logs = this.reportService.getAllDeviceData(dto);
		
		if(!logs.isEmpty())
			return new ResponseEntity<>(logs, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/device-alarm")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_REPORTS')")
	public ResponseEntity<ArrayList<Alarm>> getDeviceAlarmReport(@RequestBody ReportDTO dto) {
		ArrayList<Alarm> logs = this.reportService.getAllDeviceAlarmData(dto);
		
		if(!logs.isEmpty())
			return new ResponseEntity<>(logs, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(path = "/log-alarm")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_REPORTS')")
	public ResponseEntity<ArrayList<SecurityAlarm>> getLogAlarmReport(@RequestBody ReportDTO dto) {
		ArrayList<SecurityAlarm> logs = this.reportService.getAllLogAlarmData(dto);
		
		if(!logs.isEmpty())
			return new ResponseEntity<>(logs, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
