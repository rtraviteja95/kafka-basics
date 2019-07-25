package com.kafka.learning.basickafkastreamprograms.producer;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;

import com.kafka.learning.basickafkastreamprograms.model.Customer;
import com.kafka.learning.basickafkastreamprograms.model.Transaction;
import com.kafka.learning.basickafkastreamprograms.serializer.TransactionSerializer;

public class KTransactionProducer {
	
	@SuppressWarnings("deprecation")
	private static List<Customer> customers = Arrays.asList(
			new Customer(11111, "Ross", new Date(1995, 7, 01)),
			new Customer(22222, "Rachel", new Date(1992, 8, 01)),
			new Customer(33333, "Chandler", new Date(1994, 5, 01)),
			new Customer(44444, "Joey", new Date(1996, 7, 03)),
			new Customer(55555, "Monica", new Date(1993, 4, 01)),
			new Customer(66666, "Phobe", new Date(1991, 6, 01))
			);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		KTransactionProducer kTransactionProducer = new KTransactionProducer();

		for(Customer customer: customers) {
			kTransactionProducer.sendRecord("transaction.topic.test", customer.getCustomerId(),
					new Transaction(new Random().nextInt(), Calendar.getInstance().getTime(), new Random().nextLong()));
		}
	}

	private static Producer<Integer, Transaction> producer = null;

	public KTransactionProducer(){
		final Properties props = new Properties();

		props.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionSerializer.class);

		producer = new KafkaProducer<>(props);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				producer.close();
			}
		});
	}

	public void sendRecord(String topic, int key, Transaction value) throws InterruptedException, ExecutionException{

		ProducerRecord<Integer, Transaction> record = new ProducerRecord<Integer, Transaction>(topic, key, value);

		KTransactionProducer.producer.send(record, new Callback() {

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
