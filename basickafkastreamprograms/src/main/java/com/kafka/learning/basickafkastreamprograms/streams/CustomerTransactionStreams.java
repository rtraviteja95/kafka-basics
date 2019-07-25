package com.kafka.learning.basickafkastreamprograms.streams;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import com.kafka.learning.basickafkastreamprograms.model.Customer;
import com.kafka.learning.basickafkastreamprograms.model.CustomerTransaction;
import com.kafka.learning.basickafkastreamprograms.model.Transaction;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerDeserializer;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerSerializer;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerTransactionDeserializer;
import com.kafka.learning.basickafkastreamprograms.serializer.CustomerTransactionSerializer;
import com.kafka.learning.basickafkastreamprograms.serializer.TransactionDeserializer;
import com.kafka.learning.basickafkastreamprograms.serializer.TransactionSerializer;

public class CustomerTransactionStreams {

	public static void main(String[] args) {
		final Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-customerTransaction-1");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 10*1024);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(CommonClientConfigs.METADATA_MAX_AGE_CONFIG, 500);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		final StreamsBuilder builder = new StreamsBuilder();

		final Serializer<Customer> customerSerializer = new CustomerSerializer();
		final Deserializer<Customer> customerDeserializer = new CustomerDeserializer();
		final Serde<Customer> customerSerde = Serdes.serdeFrom(customerSerializer, customerDeserializer);
		
		KTable<Integer, Customer> customerTable = builder.table("customer.topic.test", 
				Consumed.with(Serdes.Integer(), customerSerde));
		
		final Serializer<Transaction> transactionSerializer = new TransactionSerializer();
		final Deserializer<Transaction> transactionDeserializer = new TransactionDeserializer();
		final Serde<Transaction> transactionSerde = Serdes.serdeFrom(transactionSerializer, transactionDeserializer);
		
		KTable<Integer, Transaction> transactionTable = builder.table("transaction.topic.test", 
				Consumed.with(Serdes.Integer(), transactionSerde));
		
		KTable<Integer, CustomerTransaction> customerTransactionTable = customerTable.join(transactionTable,
				(customer, transaction) -> new CustomerTransaction(customer, transaction));
		
		final Serializer<CustomerTransaction> customerTransactionSerializer = new CustomerTransactionSerializer();
		final Deserializer<CustomerTransaction> customerTransactionDeserializer = new CustomerTransactionDeserializer();
		final Serde<CustomerTransaction> customerTransactionSerde = Serdes.serdeFrom(customerTransactionSerializer, customerTransactionDeserializer);
		
		customerTransactionTable.toStream().to("customertransaction.topic.test", Produced.with(Serdes.Integer(), customerTransactionSerde));
		
		final KafkaStreams streams = new KafkaStreams(builder.build(), props);
		final CountDownLatch latch = new CountDownLatch(1);

		// attach shutdown handler to catch control-c
		Runtime.getRuntime().addShutdownHook(new Thread("streams-customertransaction-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				latch.countDown();
			}
		});

		try {
			streams.start();
			latch.await();
		} catch (final Throwable e) {
			System.exit(1);
		}
		System.exit(0);
	}

}
