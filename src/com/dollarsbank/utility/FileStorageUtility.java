package com.dollarsbank.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;

public class FileStorageUtility {

	DollarsBankController dbc = new DollarsBankController();
	public static final String CSV_ACCOUNTS_FILE = "resources/accounts.csv";
	public static final String CSV_CUSTOMERS_FILE = "resources/customers.csv";
	public static final String CSV_TRANSACTIONS_FILE = "resources/transactions.csv";
	
	
//	public FileStorageUtility() {
//		readCSV(dbc);
//	}
	
	public static void readAccountsCSV(DollarsBankController dbc){
		File file = new File(CSV_ACCOUNTS_FILE);
		if (!file.exists()) { // if text file doesn't not exist then create a new file.
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("File Created");
		}
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = br.readLine();
			line = br.readLine();

			while(line != null) { 
				try {
				 String[] arr = line.split(",");
				 int accountId = Integer.parseInt(arr[0].trim());
				 double balance = Double.parseDouble(arr[1].trim());
				 int customerId = Integer.parseInt(arr[2].trim());
				 
				 Account account = new Account(accountId, balance, customerId);
				 dbc.addAccount(account);
				} catch (NumberFormatException ex) {
					System.out.println("Seeing line as null so can't parseInt");
				}
				 
				 
				line = br.readLine();	
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.println("File could not be found");
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("File cound not be read");
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("File could not be closed.");
			}
		}
		
	}
	
	public static void readCustomersCSV(DollarsBankController dbc) {
		
		File file = new File(CSV_CUSTOMERS_FILE);
		if (!file.exists()) { // if text file doesn't not exist then create a new file.
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("File Created");
		}
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = br.readLine();
			line = br.readLine();

			while(line != null) { 
				try {
				 String[] arr = line.split(",");
				 int customerId = Integer.parseInt(arr[0].trim());
				 String firstName = arr[1].trim();
				 String lastName = arr[2].trim();
				 String address = arr[3].trim();
				 String contactNumber = arr[4].trim();
				 String username = arr[5].trim();
				 String password = arr[6].trim();
				 
				 Customer customer = new Customer(customerId, firstName, lastName, address, contactNumber, username, password);
				 dbc.addCustomer(customer);
				} catch (NumberFormatException ex) {
					System.out.println("Seeing line as null so can't parseInt");
				}
				 
				line = br.readLine();	
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.println("File could not be found");
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("File cound not be read");
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("File could not be closed.");
			}
		}
		
	}		

	public static void readTransactionsCSV(DollarsBankController dbc) {
		File file = new File(CSV_TRANSACTIONS_FILE);
		if (!file.exists()) { // if text file doesn't not exist then create a new file.
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("File Created");
		}
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = br.readLine();
			line = br.readLine();

			while(line != null) { 
				try {
				 String[] arr = line.split(",");
				 int transactionId = Integer.parseInt(arr[0].trim());
				 int accountId = Integer.parseInt(arr[0].trim());
				 String description = arr[2].trim();
				 
				 Transaction transaction = new Transaction(transactionId, accountId, description);
				 dbc.addTransaction(transaction);
				} catch (NumberFormatException ex) {
					System.out.println("Seeing line as null so can't parseInt");
				}
				 
				line = br.readLine();	
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.println("File could not be found");
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("File cound not be read");
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("File cound not be closed.");
			}
		}
	}
}
	

