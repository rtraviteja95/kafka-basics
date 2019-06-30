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

import com.kafka.learning.basickafkaprograms.model.Student;

public class KProducer {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		KProducer kProducer = new KProducer();

		while(true) {
			kProducer.sendRecord("test.topic", null, new Student(new Random().nextInt(), "Ravi Teja Pasarakonda").toString());
			Thread.sleep(5000);
		}
	}

	private static Producer<String, String> producer = null;

	public KProducer(){
		final Properties props = new Properties();

		props.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		producer = new KafkaProducer<>(props);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				producer.close();
			}
		});
	}

	public void sendRecord(String topic, String key, String value) throws InterruptedException, ExecutionException{

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);

		KProducer.producer.send(record, new Callback() {

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
