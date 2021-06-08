package com.ftn.uns.ac.rs.hospitalapp.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.beans.HospitalLog;
import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.controller.AuthenticationController;
import com.ftn.uns.ac.rs.hospitalapp.events.FailedLoginEvent;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.repository.SecurityAlarmRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.LoginAlarm;

@Service
public class SecurityKnowledgeService {

	@Autowired
	private LoggerProxy logger;
	
	@Autowired
	private KieStatefulSessionService kieService;
	
	@Autowired
	private SecurityAlarmRepository alarmRepository;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	public ArrayList<SecurityAlarm> maliciousIP(String ipAddress) throws IOException{
		
		
		System.out.println("Evo me ovde");
		
		KieSession eventSession = kieService.getEventsSession();
		eventSession.getAgenda().getAgendaGroup("login-fail").setFocus();
		LoginAlarm loginAlarm = new LoginAlarm();
		eventSession.setGlobal("loginAlarm", loginAlarm);
		
		ArrayList<SecurityAlarm> alarms = new ArrayList<>();
		
		eventSession.setGlobal("alarms", alarms);
		
		System.out.println(ipAddress);
		
		FailedLoginEvent event = new FailedLoginEvent(Date.from(Instant.now()), ipAddress);
		
		System.out.println("event ip "+event.getIpAddress());
		
		eventSession.insert(event);
		eventSession.fireAllRules();
		
		if(loginAlarm.getIpAddress() != null) {
			System.out.println("OVDE");
			if(loginAlarm.getIpAddress().equals(ipAddress)) {
				System.out.println("OVDE 2");
				List<String> lines = Arrays.asList(ipAddress);
				Path file = Paths.get("D:\\faks\\Bezb\\Repo\\BE\\hospitalapp\\src\\main\\resources\\static\\malicious_ip.txt");
				Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
				
				
				this.logger.warn("[MALICIOUS IP DETECTED] After 30 failed log-in attempts in the last 24h, IP address "
						+ ""+ipAddress+" has been marked as malicious. ", AuthenticationController.class);
				
			}
		}
		
		return alarms;
		
	}
	
	public ArrayList<SecurityAlarm> hospitalLog(HospitalLog log) throws IOException{
		
		KieSession eventSession = kieService.getEventsSession();
		eventSession.getAgenda().getAgendaGroup("logs").setFocus();
		
		ArrayList<SecurityAlarm> alarms = new ArrayList<>();
		
		eventSession.setGlobal("alarms", alarms);
		
		eventSession.insert(log);
		eventSession.fireAllRules();
	
		for (SecurityAlarm securityAlarm : alarms) {
			
			this.save(securityAlarm);
			this.simpMessagingTemplate.convertAndSend("/log-alarm", securityAlarm);
			
		}
		
		return alarms;
		
	}
	
	public SecurityAlarm save(SecurityAlarm a) {
		return this.alarmRepository.save(a);
	}
	
}
