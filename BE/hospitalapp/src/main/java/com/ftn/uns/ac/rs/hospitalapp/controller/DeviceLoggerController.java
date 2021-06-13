package com.ftn.uns.ac.rs.hospitalapp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.DeviceLog;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.service.DeviceLoggerService;
import com.ftn.uns.ac.rs.hospitalapp.util.CertificateRevokedException;
import com.ftn.uns.ac.rs.hospitalapp.util.CertificateUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/device-logs")
public class DeviceLoggerController {

	@Autowired
	private DeviceLoggerService deviceLoggerService;

	@Autowired
	private CertificateService certService;
	
	@Autowired
	private LoggerProxy logger;

	@Autowired
	private Environment env;

	@GetMapping(path = "/blood-logs/by-page/{pageNum}")
	public ResponseEntity<FinalMessage> getBloodLogs(@PathVariable("pageNum") int pageNum) {

		try {
		
		Gson gson = new Gson();
		
		PageImplementation<DeviceLog> logPage = this.deviceLoggerService.getDeviceData("BLOOD_DEVICE", pageNum);

		String data = gson.toJson(logPage);

		byte[] compressed_data = EncryptionUtil.compress(data);

		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), certService.getMyPrivateKey(),
				compressed_data, this.env.getProperty("cipherKey"));
		
		this.logger.info("Successfully read logs for [ BLOOD_DEVICE ] by another app!", DeviceLoggerController.class);

		return new ResponseEntity<>(finalMess, HttpStatus.OK);
		
		}catch(CertificateRevokedException e) {
			
			this.logger.error("[CERTIFICATE REVOKED] The certificate provided with this request has been revoked.", CertificateUtil.class);
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			
		}
	}

	@GetMapping(path = "/heart-logs/by-page/{pageNum}")
	public ResponseEntity<FinalMessage> getHeartLogs(@PathVariable("pageNum") int pageNum) {
		
		try {
		
		Gson gson = new Gson();

		PageImplementation<DeviceLog> logPage = this.deviceLoggerService.getDeviceData("HEART_MONITOR_DEVICE", pageNum);

		String data = gson.toJson(logPage);

		byte[] compressed_data = EncryptionUtil.compress(data);

		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), certService.getMyPrivateKey(),
				compressed_data, this.env.getProperty("cipherKey"));
		
		this.logger.info("Successfully read logs for [ HEART_MONITOR_DEVICE ] by another app!", DeviceLoggerController.class);

		return new ResponseEntity<>(finalMess, HttpStatus.OK);	
		
		}catch(CertificateRevokedException e) {
			
			this.logger.error("[CERTIFICATE REVOKED] The certificate provided with this request has been revoked.", CertificateUtil.class);
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			
		}
		
		}

	@GetMapping(path = "/neuro-logs/by-page/{pageNum}")
	public ResponseEntity<FinalMessage> getNeuroLogs(@PathVariable("pageNum") int pageNum) {
		
		try {
		
		Gson gson = new Gson();

		PageImplementation<DeviceLog> logPage = this.deviceLoggerService.getDeviceData("NEUROLOGICAL_DEVICE", pageNum);

		String data = gson.toJson(logPage);

		byte[] compressed_data = EncryptionUtil.compress(data);

		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), certService.getMyPrivateKey(),
				compressed_data, this.env.getProperty("cipherKey"));
		
		this.logger.info("Successfully read logs for [ NEUROLOGICAL_DEVICE ] by another app!", DeviceLoggerController.class);

		return new ResponseEntity<>(finalMess, HttpStatus.OK);	
		
		}catch(CertificateRevokedException e) {
			
			this.logger.error("[CERTIFICATE REVOKED] The certificate provided with this request has been revoked.", CertificateUtil.class);
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			
		}
		
		}

}
