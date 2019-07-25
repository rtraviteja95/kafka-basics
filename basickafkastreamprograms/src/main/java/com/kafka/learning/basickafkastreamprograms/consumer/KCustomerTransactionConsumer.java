package com.kafka.learning.basickafkastreamprograms.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import com.kafka.learning.basickafkastreamprograms.model.CustomerTransaction;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerTransactionDeserializer;

public class KCustomerTransactionConsumer {
	
	public static void main(String[] args) {

		KCustomerTransactionConsumer kConsumer = new KCustomerTransactionConsumer();
		kConsumer.startConsumer("customertransaction.topic.test");

	}

	private Consumer<String, CustomerTransaction> consumer = null;

	public KCustomerTransactionConsumer(){
		
		final Properties props = new Properties();

		props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerTransactionDeserializer.class);
		props.put( ConsumerConfig.GROUP_ID_CONFIG, "test-avro-consumer-1");
		props.put( ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		consumer = new KafkaConsumer<>(props);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				consumer.close();
			}
		});
	}

	public void startConsumer(String topic){
		consumer.subscribe(Arrays.asList(topic));

		while (true) {
			final ConsumerRecords<String, CustomerTransaction> consumerRecords =
					consumer.poll(1000);

			consumerRecords.forEach(record -> {
				System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
						record.key(), record.value(),
						record.partition(), record.offset());
			});

			consumer.commitAsync();
		}

	}

}
