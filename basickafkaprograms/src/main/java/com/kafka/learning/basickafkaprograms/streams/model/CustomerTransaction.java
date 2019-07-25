package com.kafka.learning.basickafkaprograms.streams.model;

public class CustomerTransaction {
	
	private Customer customer;
	
	private Transaction transaction;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public CustomerTransaction(Customer customer, Transaction transaction) {
		super();
		this.customer = customer;
		this.transaction = transaction;
	}
	
}
