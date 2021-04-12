package com.dollarsbank.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;
import com.dollarsbank.utility.ColorsUtility;
import com.dollarsbank.utility.FileStorageUtility;

public class DollarsBankController {
	
	public ArrayList<Account> accounts;
	public ArrayList<Customer> customers;
	public ArrayList<Transaction> transactions;
	public static final String CSV_ACCOUNTS_FILE = "resources/accounts.csv";
	public static final String CSV_CUSTOMERS_FILE = "resources/customers.csv";
	public static final String CSV_TRANSACTIONS_FILE = "resources/transactions.csv";
	public static String accountHeaders = "AccountId,Balance,CustomerId\n";
	public static String customerHeaders = "CustomerId,FirstName,LastName,Address,Contact Number,Username,Password\n";
	public static String transactionHeaders = "TransactionId,AccountID,Descripton\n";

	
	public DollarsBankController() {
		this.accounts = new ArrayList<>();
		this.customers = new ArrayList<>();
		this.transactions = new ArrayList<>();
		
	}
	
	public boolean addAccount(Account account) {
		this.accounts.add(account);
		updateAccountsFile();
		return true;
	}
	
	public boolean addCustomer(Customer customer) {
		this.customers.add(customer);
		updateCustomersFile();
		return true;
	}
	
	public boolean addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
		updateTransactionsFile();
		return true;
	}
	
	public Customer findCustomerByUsername(String username) {
		Optional<Customer> customerOpt = this.customers.stream()
														.filter(c -> c.getUsername().equals(username))
														.findAny();
		if(customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			return customer;
		} else {
			return null;
		}	
	}
	
	public boolean verifyAccountDetails(String username, String password) {
		Customer customer = findCustomerByUsername(username);
		if(customer == null) {
			System.out.println("That username can not be found!!");
			return false;
		} else if(!customer.getPassword().equals(password)) {
			System.out.println(ColorsUtility.RED +"Invalid Credentials. Try Again!");
			return false;
		} else {
			return true;
		}
	}
	
	public Account findAccountByAccountId(int id) {
		Optional<Account> accountOpt = this.accounts.stream()
				.filter(a -> a.getAccountId() == id)
				.findFirst();
		
		if (accountOpt.isPresent()) {
			Account account = accountOpt.get();
			return account;
		} else {
			return null;
		}
	}
	
	public Account findAccountByCustomerId(int id) {
		Optional<Account> accountOpt = this.accounts.stream()
				.filter(a -> a.getCustomerId() == id)
				.findFirst();
		
		if (accountOpt.isPresent()) {
			Account account = accountOpt.get();
			return account;
		} else {
			return null;
		}
	}
	
	public boolean listAccount(int accountId) {
		Account account = findAccountByAccountId(accountId);
		
		if (account == null) {
			return false;
		}
		
		System.out.println("----------------");
		System.out.println("Account Id: " + account.getAccountId() + 
							"\nBalance: " + account.getBalance() + 
							"\nCustomer Id: " + account.getCustomerId());
	
		return true;
	}
	
	public Customer findCustomerByCustomerId(int id) {
		Optional<Customer> customerOpt = this.customers.stream()
				.filter(a -> a.getCustomerId() == id)
				.findFirst();
		
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			return customer;
		} else {
			return null;
		}
	}
	
	public boolean listCustomer(int customerId) {
		Customer customer = findCustomerByCustomerId(customerId);
		
		if (customer == null) {
			return false;
		}
		
		System.out.println("----------------");
		System.out.println("Customer Id: " + customer.getCustomerId() + 
							"\nCustomer Name: " + customer.getFirstname() + " " + customer.getLastname() + 
							"\nAddress: " + customer.getAddress() + 
							"\nContant Number: " + customer.getContactNumber() + 
							"\nUsername: " + customer.getUsername() + 
							"\nPassword: " + customer.getPassword());
	
		return true;
	}
	
	public ArrayList<Transaction> findLastFiveTransactionsByAccountId(int accountId){
		Account account = findAccountByAccountId(accountId);
		ArrayList<Transaction> transactionOpt = (ArrayList<Transaction>) this.transactions.stream()
																						.filter(t -> t.getAccountId() == account.getAccountId())
																						.collect(lastN(5));
		if(!transactionOpt.isEmpty()) {
			return transactionOpt;
		} else {
			return null;
		}
		
	}
	
	public static <T> Collector<T, ?, List<T>> lastN(int n) {
	    return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
	        if(acc.size() == n)
	            acc.pollFirst();
	        acc.add(t);
	    }, (acc1, acc2) -> {
	        while(acc2.size() < n && !acc1.isEmpty()) {
	            acc2.addFirst(acc1.pollLast());
	        }
	        return acc2;
	    }, ArrayList::new);
	}
	
	
	public void updateAccountsFile() {
		
		File file = new File(CSV_ACCOUNTS_FILE);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
				bw.write(accountHeaders);
				for (int i = 0; i < accounts.size(); i++) {
					
					Account currentAccount = accounts.get(i);				
					
					String entry = "" + currentAccount.getAccountId() + "," 
							+ currentAccount.getBalance() + "," 
							+ currentAccount.getCustomerId() + "\n";  
					
					bw.write(entry);
				}
			
		} catch (IOException e) {
			System.out.println("File could not be written to.");
		} catch (Exception e) {
			System.out.println("Something went wrong. Couldn't right to file");
		} finally {
			try {
				if (bw != null) bw.close();
				if (fw != null) fw.close();
			} catch (IOException e) {
				System.out.println("File could not be closed.");
			}
		}
		
	}
	
public void updateCustomersFile() {
		
		File file = new File(CSV_CUSTOMERS_FILE);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
				bw.write(customerHeaders);
				for (int i = 0; i < customers.size(); i++) {
					
					Customer currentCustomer = customers.get(i);				
					
					String entry = "" + currentCustomer.getCustomerId() + "," 
							+ currentCustomer.getFirstname() + "," 
							+ currentCustomer.getLastname() + "," 
							+ currentCustomer.getAddress() + "," 
							+ currentCustomer.getContactNumber() + "," 
							+ currentCustomer.getUsername() + "," 
							+ currentCustomer.getPassword() + "\n";  
					
					bw.write(entry);
				}
			
		} catch (IOException e) {
			System.out.println("File could not be written to.");
		} catch (Exception e) {
			System.out.println("Something went wrong. Couldn't right to file");
		} finally {
			try {
				if (bw != null) bw.close();
				if (fw != null) fw.close();
			} catch (IOException e) {
				System.out.println("File could not be closed.");
			}
		}
		
	}


	public void updateTransactionsFile() {
	
		File file = new File(CSV_TRANSACTIONS_FILE);
	
		FileWriter fw = null;
		BufferedWriter bw = null;
	
		try {
		
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			bw.write(transactionHeaders);
			for (int i = 0; i < transactions.size(); i++) {
			
				Transaction currentTransaction = transactions.get(i);				
			
				String entry = "" + currentTransaction.getTransactionId() + "," 
						+ currentTransaction.getAccountId() + "," 
						+ currentTransaction.getDescription() + "\n";  
			
				bw.write(entry);
			}
		
		
		} catch (IOException e) {
		System.out.println("File could not be written to.");
		} catch (Exception e) {
		System.out.println("Something went wrong. Couldn't right to file");
		} finally {
			try {
				if (bw != null) bw.close();
				if (fw != null) fw.close();
			} catch (IOException e) {
				System.out.println("File could not be closed.");
			}
		}
		
	}
}
