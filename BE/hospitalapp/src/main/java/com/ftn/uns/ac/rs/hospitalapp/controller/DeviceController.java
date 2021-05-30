package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.service.DeviceService;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.ftn.uns.ac.rs.hospitalapp.util.HeartMonitorData;
import com.ftn.uns.ac.rs.hospitalapp.util.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;
import com.ftn.uns.ac.rs.hospitalapp.util.UnknownDeviceData;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/device")
public class DeviceController {

	@Autowired
	private CertificateService certService;
	
	@Autowired
	private LoggerProxy logger;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private DeviceService deviceService;

	@PostMapping(path = "/blood-device")
	public ResponseEntity<HttpStatus> bloodDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBloodDevicePublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		BloodData bloodData = gson.fromJson(data, BloodData.class);

		this.logger.device("Successfully received blood device data", DeviceController.class);
		
		ArrayList<Alarm> alarms = this.deviceService.bloodData(bloodData);
		
		if(!alarms.isEmpty()) {
			for(Alarm a : alarms)
				this.simpMessagingTemplate.convertAndSend("/topic", a);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/neurological-device")
	public ResponseEntity<HttpStatus> neurologicalDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getNeurologicalDevicePublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		NeurologicalData neuroData = gson.fromJson(data, NeurologicalData.class);

		this.logger.device("Successfully received neurological device data", DeviceController.class);

		System.out.println(neuroData);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/heart-monitor")
	public ResponseEntity<HttpStatus> heartMonitorData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getHeartMonitorPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		HeartMonitorData heartMonitorData = gson.fromJson(data, HeartMonitorData.class);

		System.out.println(heartMonitorData);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// unknown device
	@PostMapping(path = "/temperature-device")
	public ResponseEntity<HttpStatus> temperatureDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getTemperatureDevicePublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		UnknownDeviceData deviceData = gson.fromJson(data, UnknownDeviceData.class);

		System.out.println(deviceData);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
