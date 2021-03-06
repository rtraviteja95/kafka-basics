package com.kafka.learning.basickafkaprograms.producer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.kafka.learning.basickafkaprograms.avro.Student;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class KStudentAvroProducer {

	private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		KStudentAvroProducer kAvroProducer = new KStudentAvroProducer();

		while(true) {
			long rand = new Random().nextLong();
			if(rand % 2 == 0) {
				kAvroProducer.sendRecord("test.student.avro.topic", null, new Student( rand, "Ross", "M"));
			}else {
				kAvroProducer.sendRecord("test.student.avro.topic", null, new Student( rand, "Rachel", "F"));
			}
			Thread.sleep(5000);
		}
	}

	private static Producer<String, Student> producer = null;

	public KStudentAvroProducer(){
		final Properties props = new Properties();

		props.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
		props.put( AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);

		producer = new KafkaProducer<>(props);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				producer.close();
			}
		});
	}

	public void sendRecord(String topic, String key, Student value) throws InterruptedException, ExecutionException{

		ProducerRecord<String, Student> record = new ProducerRecord<String, Student>(topic, key, value);

		KStudentAvroProducer.producer.send(record, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {

				if(metadata != null) {
					System.out.println("Topic -> "+metadata.topic()+"; Partition -> "+metadata.partition()+
							"; Offset -> "+metadata.offset()+"; Record -> "+value);
				}else {
					System.err.println(exception.getLocalizedMessage());
				}

			}
		});

	}

}
