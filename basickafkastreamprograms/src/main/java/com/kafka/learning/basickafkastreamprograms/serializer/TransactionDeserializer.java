package com.kafka.learning.basickafkastreamprograms.serializer;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.learning.basickafkastreamprograms.model.Transaction;

public class TransactionDeserializer implements Deserializer<Transaction>{

	private ObjectMapper objectMapper = new ObjectMapper();

	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	public Transaction deserialize(String topic, byte[] data) {
		
		if(data ==null)
			return null;
		
		try {
			return objectMapper.readValue(data, Transaction.class);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
