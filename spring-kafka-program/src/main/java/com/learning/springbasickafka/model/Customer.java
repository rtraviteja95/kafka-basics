package com.learning.springbasickafka.model;

import java.util.Date;

public class Customer {
	
	private int customerId;
	
	private String customerName;
	
	private Date dateOfBirth;
	
	public Customer() {}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Customer(int customerId, String customerName, Date dateOfBirth) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", dateOfBirth=" + dateOfBirth
				+ "]";
	}
	
}
