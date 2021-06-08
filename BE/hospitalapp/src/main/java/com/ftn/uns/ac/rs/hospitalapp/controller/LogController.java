package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.uns.ac.rs.hospitalapp.beans.HospitalLog;
import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.LogFilterDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.LogTypeDTO;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityDataService;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;
import com.google.gson.Gson;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(path = "/logs")
public class LogController {

	@Autowired
	private MongoTemplate mongoRepository;

	@Autowired
	private CertificateService certService;

	@Autowired
	private LoggerProxy logger;

	@Autowired
	private Environment env;
	
	@Autowired
	private SecurityDataService securityDataService;

	@GetMapping(path = "by-page/{pageNum}")
	public ResponseEntity<FinalMessage> findAll(@PathVariable("pageNum") int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, 12);

		Gson gson = new Gson();

		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		long count = this.mongoRepository.count(query, HospitalLog.class);

		query.with(pageable);
		ArrayList<HospitalLog> logs = (ArrayList<HospitalLog>) mongoRepository.find(query, HospitalLog.class);

		PageImplMapper<HospitalLog> mapper = new PageImplMapper<>();

		PageImplementation<HospitalLog> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));

		String data = gson.toJson(logPage);

		byte[] compressed_data = EncryptionUtil.compress(data);

		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), certService.getMyPrivateKey(),
				compressed_data, this.env.getProperty("cipherKey"));

		this.logger.info("Successfully read logs from hospital app by another app!", LogController.class);

		return new ResponseEntity<>(finalMess, HttpStatus.OK);
	}

	@PostMapping(path = "filter-by-page/{pageNum}")
	public ResponseEntity<FinalMessage> filterByPage(@PathVariable("pageNum") int pageNum,
			@RequestBody LogFilterDTO dto) {
		Pageable pageable = PageRequest.of(pageNum, 12);
		Gson gson = new Gson();
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));

		if (dto.getStatus() != null)
			query.addCriteria(Criteria.where("status").is(dto.getStatus()));

		if (dto.getRegex() != null) {
			query.addCriteria(new Criteria().orOperator(Criteria.where("date").regex(dto.getRegex()),
					Criteria.where("status").regex(dto.getRegex()), Criteria.where("user").regex(dto.getRegex()),
					Criteria.where("message").regex(dto.getRegex()), Criteria.where("id").regex(dto.getRegex())));
		}

		long count = this.mongoRepository.count(query, HospitalLog.class);

		query.with(pageable);
		ArrayList<HospitalLog> logs = (ArrayList<HospitalLog>) mongoRepository.find(query, HospitalLog.class);

		PageImplMapper<HospitalLog> mapper = new PageImplMapper<>();

		PageImplementation<HospitalLog> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));

		String data = gson.toJson(logPage);

		byte[] compressed_data = EncryptionUtil.compress(data);

		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), certService.getMyPrivateKey(),
				compressed_data, this.env.getProperty("cipherKey"));

		this.logger.info("Successfully filtered logs from hospital app by another app!", LogController.class);

		return new ResponseEntity<>(finalMess, HttpStatus.OK);
	}
	
	@PostMapping(path = "/create-alarm-for-logs")
	@PreAuthorize("hasAuthority('PRIVILEGE_MAKE_ALARM')")
	public ResponseEntity<ArrayList<DataRangeDTO>> alarmForLogs(@RequestBody LogTypeDTO dto) {

		boolean ok = this.securityDataService.createRuleForLogType(dto);

		if (ok) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
