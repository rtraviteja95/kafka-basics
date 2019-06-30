package com.kafka.learning.basickafkaprograms.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KConsumer {

	public static void main(String[] args) {

		KConsumer kConsumer = new KConsumer();
		kConsumer.startConsumer("test.topic");

	}

	private Consumer<String, String> consumer = null;

	public KConsumer(){
		
		final Properties props = new Properties();

		props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put( ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-3");
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
