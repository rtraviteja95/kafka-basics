package com.kafka.learning.basickafkastreamprograms.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.kafka.learning.basickafkastreamprograms.model.Customer;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerDeserializer;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerSerializer;

public class CustomerSerde implements Serde<Customer>{

	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public Serializer<Customer> serializer() {
		return new CustomerSerializer();
	}

	public Deserializer<Customer> deserializer() {
		return new CustomerDeserializer();
	}

}
