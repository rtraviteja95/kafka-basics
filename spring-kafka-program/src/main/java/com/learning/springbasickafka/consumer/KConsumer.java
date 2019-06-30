package com.learning.springbasickafka.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(KProcessor.class)
public class KConsumer {
	
	@StreamListener(KProcessor.INPUT)
    @SendTo(KProcessor.OUTPUT)
    public String processMessage(String message) {
		System.err.println(message);
        return message;
    }

}
