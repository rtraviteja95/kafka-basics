package com.kafka.learning.basickafkastreamprograms.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.kafka.learning.basickafkastreamprograms.model.CustomerTransaction;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerTransactionDeserializer;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerTransactionSerializer;

public class CustomerTransactionSerde implements Serde<CustomerTransaction>{

	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public Serializer<CustomerTransaction> serializer() {
		return new CustomerTransactionSerializer();
	}

	public Deserializer<CustomerTransaction> deserializer() {
		return new CustomerTransactionDeserializer();
	}

}
