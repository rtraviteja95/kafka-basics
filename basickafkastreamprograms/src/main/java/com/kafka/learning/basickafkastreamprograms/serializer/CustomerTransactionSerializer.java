package com.kafka.learning.basickafkastreamprograms.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.learning.basickafkastreamprograms.model.CustomerTransaction;

public class CustomerTransactionSerializer implements Serializer<CustomerTransaction>{
	
	private ObjectMapper objectMapper = new ObjectMapper();

	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	public byte[] serialize(String topic, CustomerTransaction data) {
		try {
			return objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			new RuntimeException(e);
		}
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
