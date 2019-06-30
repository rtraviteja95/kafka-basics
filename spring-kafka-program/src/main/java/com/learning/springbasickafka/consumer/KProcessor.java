package com.learning.springbasickafka.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface KProcessor {

	String INPUT = "input-topic";
	String OUTPUT = "output-topic";

	@Input(KProcessor.INPUT)
    SubscribableChannel myInput();
 
    @Output(KProcessor.OUTPUT)
    MessageChannel anOutput();
 
}
