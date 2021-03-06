package com.kafka.learning.basickafkaprograms.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KBasicConsumer {

	public static void main(String[] args) {

		KBasicConsumer kBasicConsumer = new KBasicConsumer();
		kBasicConsumer.startConsumer("streams-wordcount-output");

	}

	private Consumer<String, Long> consumer = null;

	public KBasicConsumer(){
		
		final Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
		props.put("group.id", "test-consumer-2");
		props.put("auto.offset.reset", "earliest");
		
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
			final ConsumerRecords<String, Long> consumerRecords =
					consumer.poll(1000);

			consumerRecords.forEach(record -> {
				System.out.println(record.key()+", "+ String.valueOf(record.value()));
			});

			consumer.commitAsync();
		}

	}

}
