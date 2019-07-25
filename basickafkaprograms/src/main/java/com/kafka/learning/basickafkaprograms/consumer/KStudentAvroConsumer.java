package com.kafka.learning.basickafkaprograms.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.kafka.learning.basickafkaprograms.avro.Student;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class KStudentAvroConsumer {
	
	private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

	public static void main(String[] args) {

		KStudentAvroConsumer kConsumer = new KStudentAvroConsumer();
		kConsumer.startConsumer("TEST_STUDENT_AVRO_TOPIC_F_FILTER");

	}

	private Consumer<String, Student> consumer = null;

	public KStudentAvroConsumer(){
		
		final Properties props = new Properties();

		props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		props.put( ConsumerConfig.GROUP_ID_CONFIG, "test-avro-consumer-1");
		props.put( ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put( AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
		
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
			final ConsumerRecords<String, Student> consumerRecords =
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
