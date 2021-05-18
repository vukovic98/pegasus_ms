package com.ftn.uns.ac.rs.adminapp.security;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class SecureRestTemplateProperties {

	@Value("${server.ssl.key-store}")
	private String keyStore;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
		String allPassword = "vukovic";

		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(ResourceUtils.getFile(keyStore), allPassword.toCharArray(), allPassword.toCharArray())
				.build();

		
		HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(client);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;

	}
	
	public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://kts:kts.nvt17@cluster-vukovic.ssf51.mongodb.net");
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "pegasus_ms");
    }

}