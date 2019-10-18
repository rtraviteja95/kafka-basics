package com.learning.springbasickafka.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface KProcessor {

	String INPUT = "input-topic";

	@Input(KProcessor.INPUT)
    SubscribableChannel myInput();
 
}
