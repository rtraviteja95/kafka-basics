package com.learning.springbasickafka.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaRestProxyService {
	
	private static final String REST_PROXY = "http://localhost:8081/";

	@Autowired
	private RestTemplate restTemplate;

	public String getAllSubjects() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange( REST_PROXY+"/subjects", HttpMethod.GET, entity, String.class).getBody();
	}

	public String getSchemaForSubject(String topic, String version) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange( REST_PROXY+"/subjects/"+topic+"/versions/"+version, HttpMethod.GET, entity, String.class).getBody();
	}

	public KafkaRestProxyService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
