package com.kafka.learning.basickafkaprograms.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KBasicConsumer {

	public static void main(String[] args) {

		KBasicConsumer kBasicConsumer = new KBasicConsumer();
		kBasicConsumer.startConsumer("test.topic");

	}

	private Consumer<String, String> consumer = null;

	public KBasicConsumer(){
		
		final Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "test-consumer-2");
		props.put("auto.offset.reset", "latest");
		
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
			final ConsumerRecords<String, String> consumerRecords =
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
