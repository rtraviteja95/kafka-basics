package com.learning.springbasickafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.learning.springbasickafka.model.Customer;
import com.learning.springbasickafka.service.KafkaService;

@EnableBinding(KProcessor.class)
public class KConsumer {
	
	@Autowired
	private KafkaService kafkaService;
	
	@StreamListener(KProcessor.INPUT)
    public void processMessage(Customer customer) {
		System.err.println(customer);
		kafkaService.insertIntoHive(customer);
    }
	
	public KConsumer(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

}
