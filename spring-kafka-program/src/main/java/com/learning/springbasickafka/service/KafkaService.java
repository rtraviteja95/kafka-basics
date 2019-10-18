package com.learning.springbasickafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.learning.springbasickafka.model.Customer;

@Service
public class KafkaService {

	@Autowired
	@Qualifier("hiveJdbcTemplate")
	private JdbcTemplate hiveJdbcTemplate;
	
	public void insertIntoHive(Customer customer) {
		hiveJdbcTemplate.execute("insert into customer(customerId, customerName, dateOfBirth) values('"+customer.getCustomerId()+"',"
				+ "'"+customer.getCustomerName()+"', '"+customer.getDateOfBirth().toString()+"')");
	}
	
	public KafkaService(JdbcTemplate hiveJdbcTemplate) {
		this.hiveJdbcTemplate = hiveJdbcTemplate;
	}
	
}
