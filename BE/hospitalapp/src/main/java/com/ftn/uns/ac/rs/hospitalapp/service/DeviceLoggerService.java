package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.DeviceLog;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;

@SuppressWarnings("deprecation")
@Service
public class DeviceLoggerService {

	@Autowired
	private MongoTemplate mongoRepository;

	public PageImplementation<DeviceLog> getDeviceData(String device, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, 12);

		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		query.addCriteria(Criteria.where("user").is(device));

		long count = this.mongoRepository.count(query, DeviceLog.class);

		query.with(pageable);
		ArrayList<DeviceLog> logs = (ArrayList<DeviceLog>) mongoRepository.find(query, DeviceLog.class);

		PageImplMapper<DeviceLog> mapper = new PageImplMapper<>();

		PageImplementation<DeviceLog> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));
		
		return logPage;
	}
	
}
