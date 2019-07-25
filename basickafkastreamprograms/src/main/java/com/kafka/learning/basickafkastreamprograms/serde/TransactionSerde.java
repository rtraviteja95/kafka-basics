package com.kafka.learning.basickafkastreamprograms.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.kafka.learning.basickafkastreamprograms.model.Transaction;
import com.kafka.learning.basickafkastreamprograms.serializer.TransactionDeserializer;
import com.kafka.learning.basickafkastreamprograms.serializer.TransactionSerializer;

public class TransactionSerde implements Serde<Transaction>{

	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public Serializer<Transaction> serializer() {
		return new TransactionSerializer();
	}

	public Deserializer<Transaction> deserializer() {
		return new TransactionDeserializer();
	}

}
