package com.ftn.uns.ac.rs.hospitalapp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoggerProxy {

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
	}
	
	public void info(String message, Class<?> classInitializator) {

		String username = "";

		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		} catch (Exception e) {
			username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		this.logger.info("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
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
	}
	
	public void device(String message, Class<?> classInitializator) {
		
		String username = "hospital-device";

		this.logger.info("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
		
	}
	
}
