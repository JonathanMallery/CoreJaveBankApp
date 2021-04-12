package com.dollarsbank.model;

public class Transaction {

	private int transactionId;
	private int accountId;
	private String description;
	private static int historyId;
	
	
	public Transaction(int transactionId, int accountId, String description) {
		super();
		if (transactionId > historyId) {
			historyId = transactionId;
		}
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.description = description;
	}
	
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public void setAccountId(int account) {
		this.accountId = account;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static int generateTransactionId() {
		return ++historyId;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountId=" + accountId + ", description="
				+ description + "]";
	}
	
	
	
}
