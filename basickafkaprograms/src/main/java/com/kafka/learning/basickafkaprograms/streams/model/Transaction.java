package com.kafka.learning.basickafkaprograms.streams.model;

import java.util.Date;

public class Transaction {
	
	private int transactionId;
	
	private Date transactionTime;
	
	private long transactionAmount;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Transaction(int transactionId, Date transactionTime, long transactionAmount) {
		super();
		this.transactionId = transactionId;
		this.transactionTime = transactionTime;
		this.transactionAmount = transactionAmount;
	}
	
}
