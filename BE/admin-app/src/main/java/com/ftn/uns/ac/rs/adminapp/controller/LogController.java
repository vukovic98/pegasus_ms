package com.ftn.uns.ac.rs.adminapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.adminapp.beans.DeviceLog;
import com.ftn.uns.ac.rs.adminapp.beans.HospitalLog;
import com.ftn.uns.ac.rs.adminapp.beans.Log;
import com.ftn.uns.ac.rs.adminapp.dto.LogFilterDTO;
import com.ftn.uns.ac.rs.adminapp.service.CertificateService;
import com.ftn.uns.ac.rs.adminapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.adminapp.util.FinalMessage;
import com.ftn.uns.ac.rs.adminapp.util.LoggerProxy;
import com.ftn.uns.ac.rs.adminapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.adminapp.util.PageImplementation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(path = "/logs")
public class LogController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CertificateService certService;

	@Autowired
	private LoggerProxy logger;

	@Autowired
	private MongoTemplate mongoRepository;

	@GetMapping(path = "by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_LOGS')")
	public ResponseEntity<PageImplementation<Log>> findAll(@PathVariable("pageNum") int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, 12);

		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		long count = this.mongoRepository.count(query, Log.class);

		query.with(pageable);
		ArrayList<Log> logs = (ArrayList<Log>) mongoRepository.find(query, Log.class);

		PageImplMapper<Log> mapper = new PageImplMapper<>();

		PageImplementation<Log> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));

		this.logger.info("Successfull attempt for retrieving logs from admin-app", LogController.class);

		return new ResponseEntity<>(logPage, HttpStatus.OK);
	}

	@PostMapping(path = "filter-by-page/{pageNum}")
	public ResponseEntity<PageImplementation<Log>> filterByPage(@PathVariable("pageNum") int pageNum,
			@RequestBody LogFilterDTO dto) {
		Pageable pageable = PageRequest.of(pageNum, 12);

		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "date"));

		if (dto.getStatus() != null)
			query.addCriteria(Criteria.where("status").is(dto.getStatus()));

		if (dto.getRegex() != null) {
			query.addCriteria(new Criteria().orOperator(Criteria.where("date").regex(dto.getRegex()),
					Criteria.where("status").regex(dto.getRegex()), Criteria.where("user").regex(dto.getRegex()),
					Criteria.where("message").regex(dto.getRegex()), Criteria.where("id").regex(dto.getRegex())));
		}

		long count = this.mongoRepository.count(query, Log.class);

		query.with(pageable);
		ArrayList<Log> logs = (ArrayList<Log>) mongoRepository.find(query, Log.class);

		PageImplMapper<Log> mapper = new PageImplMapper<>();

		PageImplementation<Log> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));

		this.logger.info("Successfull attempt for filtering logs from admin-app", LogController.class);

		return new ResponseEntity<>(logPage, HttpStatus.OK);
	}

	@GetMapping(path = "hospital-logs/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_LOGS')")
	public ResponseEntity<PageImplementation<HospitalLog>> findAllHospitalLogs(@PathVariable int pageNum) {
		Gson gson = new Gson();

		ResponseEntity<FinalMessage> responseEntity = restTemplate.exchange(
				"https://localhost:8081/logs/by-page/" + pageNum, HttpMethod.GET, null,
				new ParameterizedTypeReference<FinalMessage>() {
				});

		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		PageImplementation<HospitalLog> dtos = gson.fromJson(data, new TypeToken<PageImplementation<HospitalLog>>() {
		}.getType());

		this.logger.info("Successfull attempt for retrieving logs from hospital-app", LogController.class);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@GetMapping(path = "hospital-logs/device-logs/blood-logs/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_LOGS')")
	public ResponseEntity<PageImplementation<DeviceLog>> findAllHospitalBloodLogs(@PathVariable int pageNum) {
		Gson gson = new Gson();

		ResponseEntity<FinalMessage> responseEntity = restTemplate.exchange(
				"https://localhost:8081/device-logs/blood-logs/by-page/" + pageNum, HttpMethod.GET, null,
				new ParameterizedTypeReference<FinalMessage>() {
				});

		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		PageImplementation<DeviceLog> dtos = gson.fromJson(data, new TypeToken<PageImplementation<DeviceLog>>() {
		}.getType());

		this.logger.info("Successfull attempt for retrieving blood logs from hospital-app", LogController.class);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@GetMapping(path = "hospital-logs/device-logs/heart-logs/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_LOGS')")
	public ResponseEntity<PageImplementation<DeviceLog>> findAllHospitalHeartLogs(@PathVariable int pageNum) {
		Gson gson = new Gson();

		ResponseEntity<FinalMessage> responseEntity = restTemplate.exchange(
				"https://localhost:8081/device-logs/heart-logs/by-page/" + pageNum, HttpMethod.GET, null,
				new ParameterizedTypeReference<FinalMessage>() {
				});

		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		PageImplementation<DeviceLog> dtos = gson.fromJson(data, new TypeToken<PageImplementation<DeviceLog>>() {
		}.getType());

		this.logger.info("Successfull attempt for retrieving heart logs from hospital-app", LogController.class);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@GetMapping(path = "hospital-logs/device-logs/neuro-logs/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_LOGS')")
	public ResponseEntity<PageImplementation<DeviceLog>> findAllHospitalNeuroLogs(@PathVariable int pageNum) {
		Gson gson = new Gson();

		ResponseEntity<FinalMessage> responseEntity = restTemplate.exchange(
				"https://localhost:8081/device-logs/neuro-logs/by-page/" + pageNum, HttpMethod.GET, null,
				new ParameterizedTypeReference<FinalMessage>() {
				});

		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		PageImplementation<DeviceLog> dtos = gson.fromJson(data, new TypeToken<PageImplementation<DeviceLog>>() {
		}.getType());

		this.logger.info("Successfull attempt for retrieving neuro logs from hospital-app", LogController.class);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping(path = "hospital-logs/filter-by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_LOGS')")
	public ResponseEntity<PageImplementation<HospitalLog>> filterAllHospitalLogs(@PathVariable int pageNum,
			@RequestBody LogFilterDTO dto) {
		Gson gson = new Gson();

		ResponseEntity<FinalMessage> responseEntity = restTemplate.postForEntity("https://localhost:8081/logs/filter-by-page/" + pageNum, dto, FinalMessage.class);
				
		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		PageImplementation<HospitalLog> dtos = gson.fromJson(data, new TypeToken<PageImplementation<HospitalLog>>() {
		}.getType());

		this.logger.info("Successfull attempt for filtering logs from hospital-app", LogController.class);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
}
