package com.ftn.uns.ac.rs.hospitalapp.mongo.proxy;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.hospitalapp.beans.DeviceLog;

@Component
public class LoggerProxyDevice {
	
	@Autowired
	private MongoTemplate mongoRepository;

	private Logger logger = LogManager.getLogger("com.pegasus.device");

	public void log(String message, Class<?> classInitializator, String username) {
		this.mongoRepository.insert(new DeviceLog(new Date(), username, message));
		this.logger.info("[ {} ] : {} : {}", username, classInitializator.getSimpleName(), message);
	}
	
}
