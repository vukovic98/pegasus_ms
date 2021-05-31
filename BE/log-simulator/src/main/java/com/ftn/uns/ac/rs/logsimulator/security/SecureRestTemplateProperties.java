package com.ftn.uns.ac.rs.logsimulator.security;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecureRestTemplateProperties {

	@Value("${server.ssl.key-store}")
	private String keyStore;
	
	@Value("${server.ssl.key-store-password}")
	private String allPassword;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(ResourceUtils.getFile(keyStore), allPassword.toCharArray(), allPassword.toCharArray())
				.build();

		HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(client);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;

	}

}