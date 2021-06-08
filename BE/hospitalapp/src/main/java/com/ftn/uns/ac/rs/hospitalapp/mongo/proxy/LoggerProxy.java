package com.ftn.uns.ac.rs.hospitalapp.mongo.proxy;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.hospitalapp.beans.HospitalLog;
import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityKnowledgeService;

@Component
public class LoggerProxy {
	
	@Autowired
	private MongoTemplate mongoRepository;
	
	@Autowired
	private SecurityKnowledgeService securityService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private Logger logger = LogManager.getLogger("com.pegasus");

	public void debug(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.logger.debug("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		this.mongoRepository.insert(new HospitalLog(new Date(), username, message, "DEBUG"));
	}

	public void info(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			try {
				username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			} catch (Exception e1) {
				username = "ADMIN-APP";
			}
		}
		this.mongoRepository.insert(new HospitalLog(new Date(), username, message, "INFO"));
		this.logger.info("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
	}

	public void warn(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			if(SecurityContextHolder.getContext().getAuthentication() != null)
				username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			else
				username = "";
		}

		this.mongoRepository.insert(new HospitalLog(new Date(), username, message, "WARN"));
		this.logger.warn("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
	}

	public void error(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.mongoRepository.insert(new HospitalLog(new Date(), username, message, "ERROR"));
		this.logger.error("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		
		SecurityAlarm a = new SecurityAlarm("", "ERROR LOG OCCURED", new Date());
		
		this.securityService.save(a);
		this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
		
	}

	public void fatal(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.mongoRepository.insert(new HospitalLog(new Date(), username, message, "FATAL"));
		this.logger.fatal("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
	}

}
