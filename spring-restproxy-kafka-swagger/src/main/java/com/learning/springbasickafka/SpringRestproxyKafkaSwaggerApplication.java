package com.learning.springbasickafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringRestproxyKafkaSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestproxyKafkaSwaggerApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
