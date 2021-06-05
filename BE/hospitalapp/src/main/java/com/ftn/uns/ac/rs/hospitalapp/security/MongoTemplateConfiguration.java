package com.ftn.uns.ac.rs.hospitalapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoTemplateConfiguration {

	public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://kts:kts.nvt17@cluster-vukovic.ssf51.mongodb.net");
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "pegasus_ms");
    }
	
}
