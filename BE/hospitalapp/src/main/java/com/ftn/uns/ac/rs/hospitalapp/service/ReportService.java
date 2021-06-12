package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.beans.DeviceLog;
import com.ftn.uns.ac.rs.hospitalapp.beans.HospitalLog;
import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.dto.ReportDTO;
import com.ftn.uns.ac.rs.hospitalapp.repository.AlarmRepository;
import com.ftn.uns.ac.rs.hospitalapp.repository.SecurityAlarmRepository;

@Service
public class ReportService {
	
	@Autowired
	private MongoTemplate mongoRepository;
	
	@Autowired
	private AlarmRepository deviceAlarmRepository;
	
	@Autowired
	private SecurityAlarmRepository securityAlarmRepository;

	public ArrayList<HospitalLog> getAllLogs(ReportDTO dto) {
		ArrayList<HospitalLog> logs = new ArrayList<>();
		
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));

		if (dto.getStartDate() != null && dto.getEndDate() != null)
			query.addCriteria(Criteria.where("date").gt(dto.getStartDate()).lt(dto.getEndDate()));
		else if(dto.getStartDate() != null)
			query.addCriteria(Criteria.where("date").gt(dto.getStartDate()));	
		else if(dto.getEndDate() != null)
			query.addCriteria(Criteria.where("date").lt(dto.getEndDate()));

		logs = (ArrayList<HospitalLog>) mongoRepository.find(query, HospitalLog.class);
		
		return logs;
	}

	public ArrayList<DeviceLog> getAllDeviceData(ReportDTO dto) {
		ArrayList<DeviceLog> logs = new ArrayList<>();
		
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));

		if (dto.getStartDate() != null && dto.getEndDate() != null)
			query.addCriteria(Criteria.where("date").gt(dto.getStartDate()).lt(dto.getEndDate()));
		else if(dto.getStartDate() != null)
			query.addCriteria(Criteria.where("date").gt(dto.getStartDate()));	
		else if(dto.getEndDate() != null)
			query.addCriteria(Criteria.where("date").lt(dto.getEndDate()));

		logs = (ArrayList<DeviceLog>) mongoRepository.find(query, DeviceLog.class);
		
		return logs;
	}

	public ArrayList<Alarm> getAllDeviceAlarmData(ReportDTO dto) {
		ArrayList<Alarm> logs = new ArrayList<>();

		if (dto.getStartDate() != null && dto.getEndDate() != null)
			logs = this.deviceAlarmRepository.findByDate(dto.getStartDate(), dto.getEndDate());
		else if(dto.getStartDate() != null)
			logs = this.deviceAlarmRepository.findByStartDate(dto.getStartDate());
		else if(dto.getEndDate() != null)
			logs = this.deviceAlarmRepository.findByEndDate(dto.getEndDate());
		else
			logs = (ArrayList<Alarm>) this.deviceAlarmRepository.findAll();
		
		return logs;
	}

	public ArrayList<SecurityAlarm> getAllLogAlarmData(ReportDTO dto) {
		ArrayList<SecurityAlarm> logs = new ArrayList<>();

		if (dto.getStartDate() != null && dto.getEndDate() != null)
			logs = this.securityAlarmRepository.findByDate(dto.getStartDate(), dto.getEndDate());
		else if(dto.getStartDate() != null)
			logs = this.securityAlarmRepository.findByStartDate(dto.getStartDate());
		else if(dto.getEndDate() != null)
			logs = this.securityAlarmRepository.findByEndDate(dto.getEndDate());
		else
			logs = (ArrayList<SecurityAlarm>) this.securityAlarmRepository.findAll();
		
		return logs;
	}
	
}
