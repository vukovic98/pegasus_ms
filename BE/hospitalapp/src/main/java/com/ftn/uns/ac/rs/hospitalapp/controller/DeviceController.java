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
import com.ftn.uns.ac.rs.hospitalapp.service.BloodDataService;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.beans.Patient;
import com.ftn.uns.ac.rs.hospitalapp.service.DeviceKnowledgeService;
import com.ftn.uns.ac.rs.hospitalapp.service.HeartDataService;
import com.ftn.uns.ac.rs.hospitalapp.service.NeurologicalDataService;
import com.ftn.uns.ac.rs.hospitalapp.service.PatientService;
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
	private DeviceKnowledgeService deviceService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private BloodDataService bloodDataService;

	@Autowired
	private HeartDataService heartDataService;

	@Autowired
	private NeurologicalDataService neurologicalDataService;

	@PostMapping(path = "/blood-device")
	public ResponseEntity<HttpStatus> bloodDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBloodDevicePublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		BloodData bloodData = gson.fromJson(data, BloodData.class);

		this.logger.device("Successfully received blood device data", DeviceController.class);

		try {
			this.bloodDataService.insert(bloodData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Alarm> alarms = this.deviceService.bloodData(bloodData);
		for (Alarm a : alarms) {
			Patient p = this.patientService.findById(Long.valueOf(a.getPatientID()));
			a.setPatientsName(p.getFirstName() + " " + p.getLastName());
			this.deviceService.save(a);
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

		try {
			this.neurologicalDataService.insert(neuroData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Alarm> alarms = this.deviceService.neurologicalData(neuroData);
		for (Alarm a : alarms) {
			Patient p = this.patientService.findById(Long.valueOf(a.getPatientID()));
			a.setPatientsName(p.getFirstName() + " " + p.getLastName());
			this.deviceService.save(a);
			this.simpMessagingTemplate.convertAndSend("/topic", a);
		}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PostMapping(path = "/heart-monitor")
	public ResponseEntity<HttpStatus> heartMonitorData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getHeartMonitorPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		HeartMonitorData heartMonitorData = gson.fromJson(data, HeartMonitorData.class);

		this.logger.device("Successfully received heart monitor data", DeviceController.class);

		try {
			this.heartDataService.insert(heartMonitorData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Alarm> alarms = this.deviceService.heartMonitorData(heartMonitorData);
		for (Alarm a : alarms) {
			Patient p = this.patientService.findById(Long.valueOf(a.getPatientID()));
			a.setPatientsName(p.getFirstName() + " " + p.getLastName());
			this.deviceService.save(a);
			this.simpMessagingTemplate.convertAndSend("/topic", a);
		}

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
