package com.ftn.uns.ac.rs.adminapp.util;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.adminapp.beans.Log;

@Component
public class LoggerProxy {
	
	@Autowired
	private MongoTemplate mongoRepository;

	private Logger logger = LogManager.getLogger("com.pegasus");

	public void debug(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.logger.debug("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		this.mongoRepository.insert(new Log(new Date(), username, message, "DEBUG"));
	}
	
	public void info(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			if (SecurityContextHolder.getContext().getAuthentication() != null)
				username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			else
				username = "";
		}

		this.logger.info("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		this.mongoRepository.insert(new Log(new Date(), username, message, "INFO"));
	}
	
	public void warn(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.logger.warn("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		this.mongoRepository.insert(new Log(new Date(), username, message, "WARN"));
	}
	
	public void error(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		this.logger.error("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		this.mongoRepository.insert(new Log(new Date(), username, message, "ERROR"));
	}
	
	public void fatal(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.logger.fatal("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		this.mongoRepository.insert(new Log(new Date(), username, message, "FATAL"));
	}
	
	
}
