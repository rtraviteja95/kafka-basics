package com.learning.springbasickafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springbasickafka.service.KafkaRestProxyService;

@RestController
public class KafkaRestProxyController {
	
	@Autowired
	private KafkaRestProxyService kafkaRestProxyService;

	@GetMapping(value="/getSubjects", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllSubjects() {
		return kafkaRestProxyService.getAllSubjects();
	}
	
	@GetMapping(value="/getSchema/{topic}/{version}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getTopicSchema(@PathVariable("topic") String topic, @PathVariable("version") String version) {
		return kafkaRestProxyService.getSchemaForSubject(topic, version);
	}
	
	public KafkaRestProxyController(KafkaRestProxyService kafkaRestProxyService) {
		this.kafkaRestProxyService = kafkaRestProxyService;
	}
	
}
