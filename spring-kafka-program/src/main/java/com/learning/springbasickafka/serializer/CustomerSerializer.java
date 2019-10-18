package com.learning.springbasickafka.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springbasickafka.model.Customer;

public class CustomerSerializer implements Serializer<Customer>{
	
	private ObjectMapper objectMapper = new ObjectMapper();

	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	public byte[] serialize(String topic, Customer data) {
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
