package com.dollarsbank.model;

import java.util.ArrayList;

public class Account {

	private int accountId;
	private double balance;
	private int customerId;
	private static int maxId;
	
	
	public Account(int accountId, double balance, int customerId) {
		super();
		if (accountId > maxId ) {
			maxId = accountId;
		}
		this.accountId = accountId;
		this.balance = balance;
		this.customerId = customerId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setCustomerId(Customer customer) {
		this.customerId = customer.getCustomerId();
	}
	
	public int getCustomerId(){
		return customerId;
	}
	
	public static int generateAccountId() {
		return ++maxId;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", customerId=" + customerId + "]";
	}
	
	
	
}
