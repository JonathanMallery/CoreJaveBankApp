package com.dollarsbank.utility;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;

public class ConsolePrinterUtility {

	public static void runProgram(DollarsBankController dbc) {
		Scanner scan = new Scanner(System.in);
		boolean running = true;
		
		while (running) {
			System.out.println(ColorsUtility.BLUE +"+------------------------+");
			System.out.println(ColorsUtility.BLUE +"| Welcome to DOLLARSBANK |");
			System.out.println(ColorsUtility.BLUE +"+------------------------+");
			System.out.println(ColorsUtility.BLACK +"1. Create New Account");
			System.out.println(ColorsUtility.BLACK +"2. Login");
			System.out.println(ColorsUtility.BLACK +"3. Exit");
			System.out.println(ColorsUtility.GREEN +"\nEnter Choice (1, 2, or 3) : ");
			// Read Command, run through exception
			String choice = scan.nextLine();
			
			try {
				switch (choice) {
				case "1":
					createNewAccount(scan, dbc);
					break;
				case "2":
						loginAccount(scan, dbc);				
					break;
				case "3":
					System.out.println("Have a nice day!!");
					running = false;
//					Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0), 3, TimeUnit.SECONDS);
					break;
				default:
					System.out.println("Incorrect command.");
				}
			}catch (InputMismatchException e) {
					System.out.println("Incorrect input");
				}
			}		
		scan.close();	
		System.exit(0);
	}
	
	public static void createNewAccount(Scanner scan, DollarsBankController dbc) {
		System.out.println(ColorsUtility.BLUE +"+-------------------------------+");
		System.out.println(ColorsUtility.BLUE +"| Enter Details For New Account |");
		System.out.println(ColorsUtility.BLUE +"+-------------------------------+");
		String firstName = "";
		String lastName = "";
		String address = "";
		String contactNumber = "";
		String username = "";
		String password = "";
		double initialDeposit;
		
		System.out.print(ColorsUtility.BLACK +"Customer First Name: ");
		System.out.println();
		firstName = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"Customer Last Name: ");
		System.out.println();
		lastName = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"Address: \n");
		address = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"Customer Contact Number: \n");
		contactNumber = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"User Id: \n");
		username = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"Password : *8 Characters Long Including Lower, Upper & Special Characters* \n");
		password = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"Initial Deposit Amount: \n");
		while(true) {
			try {
			
				initialDeposit = scan.nextDouble();
				scan.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Incorrect Input. Please enter a number. : ");
				scan.nextLine();
			}
		}
		
		Customer customer = new Customer(Customer.generateCustomerId(), firstName, lastName, address, contactNumber, username, password);
		Account account = new Account(Account.generateAccountId(), initialDeposit, customer.getCustomerId());
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm");
		Transaction transaction = new Transaction(Transaction.generateTransactionId(), account.getAccountId(), "Initial Deposit amount " + 
																												initialDeposit + 
																												" in account[" + account.getAccountId() + "]. " +
																												"Balance - " + account.getBalance() + " as of " +
																												df.format(date));
		
		if(dbc.addAccount(account) && dbc.addCustomer(customer) && dbc.addTransaction(transaction)) {
			System.out.println();
			System.out.println(account.toString());
			System.out.println(customer.toString());
			System.out.println(transaction.toString());
			System.out.println("New Account has been added successfully");
		} else {
			System.out.println("Failed to add New Account.\n\n");
		}
		
	}
	
	public static void loginAccount(Scanner scan, DollarsBankController dbc) {
		System.out.println(ColorsUtility.BLUE +"+---------------------+");
		System.out.println(ColorsUtility.BLUE +"| Enter Login Details |");
		System.out.println(ColorsUtility.BLUE +"+---------------------+");
		String username = "";
		String password = "";
		
		System.out.print(ColorsUtility.BLACK +"User Id: \n");
		username = scan.nextLine();
		
		System.out.print(ColorsUtility.BLACK +"Password: \n");
		password = scan.nextLine();
		
		int accountId;
		while (true) {
			if (dbc.verifyAccountDetails(username, password)) {
				Customer customer = dbc.findCustomerByUsername(username);
				Account account = dbc.findAccountByCustomerId(customer.getCustomerId());
				accountId = account.getAccountId();
				break;
			}
			System.out.println("Sorry");
			loginAccount(scan, dbc);
		}
		
		accountPage(scan, dbc, accountId);
	}
	
	public static void accountPage(Scanner scan, DollarsBankController dbc, int accountId) {
		Account account = dbc.findAccountByAccountId(accountId);
		Customer customer = dbc.findCustomerByCustomerId(account.getCustomerId());
		
		
		boolean running = true;
		
		
		while(running) {
			System.out.println(ColorsUtility.BLUE + "+---------------------+");
			System.out.println(ColorsUtility.BLUE +"| Welcome Customer!!! |");
			System.out.println(ColorsUtility.BLUE +"+---------------------+");
			System.out.println(ColorsUtility.BLACK +"1. Deposit");
			System.out.println(ColorsUtility.BLACK +"2. Withdraw");
			System.out.println(ColorsUtility.BLACK +"3. Funds Transfer");
			System.out.println(ColorsUtility.BLACK +"4. View 5 Recent Transactions");
			System.out.println(ColorsUtility.BLACK +"5. Display Customer Information");
			System.out.println(ColorsUtility.BLACK +"6. Sign Out");
			System.out.println(ColorsUtility.GREEN +"\nEnter Choice (1, 2, 3, 4, 5, or 6) :");
			int command = scan.nextInt();
			
			try {
				switch (command) {
				case 1:
					System.out.println(ColorsUtility.BLACK +"Enter deposit amount : ");
					while(true) {
						try {
						
							double deposit = scan.nextDouble();
							scan.nextLine();
							double balance = account.getBalance();
							balance = balance + deposit;
							account.setBalance(balance);
							Date date = new Date();
							DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm");
							Transaction transaction = new Transaction(Transaction.generateTransactionId(), account.getAccountId(), "Deposit of " + deposit + 
																													" in account[" + account.getAccountId() + "]. " +
																													"Balance - " + account.getBalance() + " as of " +
																													df.format(date));
							if(dbc.addTransaction(transaction)) {
								System.out.println();
								System.out.println(transaction.toString());
								System.out.println("Transaction completed successfully");
							} else {
								System.out.println("Failed to add New Account.\n\n");
							}
							break;
						} catch (InputMismatchException e) {
							System.out.println(ColorsUtility.RED +"Incorrect Input. Please enter a number. : ");
							scan.nextLine();
						}
					}
					break;
				case 2:
					System.out.println(ColorsUtility.BLUE +"+----------------------+");
					System.out.println(ColorsUtility.BLUE +"| Deposit Transaction: |");
					System.out.println(ColorsUtility.BLUE +"+----------------------+");
					System.out.println(ColorsUtility.BLACK +"Enter withdrawal amount : ");
					while(true) {
						try {
						
							double withdraw = scan.nextDouble();
							scan.nextLine();
							double balance = account.getBalance();
							balance = balance - withdraw;
							account.setBalance(balance);
							Date date = new Date();
							DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm");
							Transaction transaction = new Transaction(Transaction.generateTransactionId(), account.getAccountId(), "Withdrawal of " + withdraw + 
																													" in account[" + account.getAccountId() + "]. " +
																													"Balance - " + account.getBalance() + " as of " +
																													df.format(date));
							if(dbc.addTransaction(transaction)) {
								System.out.println();
								System.out.println(transaction.toString());
								System.out.println("Transaction completed successfully");
							}
							break;
						} catch (InputMismatchException e) {
							System.out.println("Incorrect Input. Please enter a number. : ");
							scan.nextLine();
						}
					}
					break;
				case 3:
					System.out.println(ColorsUtility.BLUE +"+-------------------------+");
					System.out.println(ColorsUtility.BLUE +"| Withdrawal Transaction: |");
					System.out.println(ColorsUtility.BLUE +"+-------------------------+");
					System.out.println(ColorsUtility.BLACK +"Enter transfer amount : ");
					double transfer = scan.nextDouble();
					scan.nextLine();
					
					System.out.println(ColorsUtility.BLACK +"Enter Accound Id : ");
					int accountNumber = scan.nextInt();
					scan.nextLine();
					while(true) {
						try {
							double balance = account.getBalance();
							balance = balance - transfer;
							account.setBalance(balance);
							
							Account transferAccount = dbc.findAccountByAccountId(accountNumber);
							double transferAccountBalance = transferAccount.getBalance();
							transferAccountBalance = transferAccountBalance + transfer;
							transferAccount.setBalance(transferAccountBalance);
							
							
							Date date = new Date();
							DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm");
							Transaction transaction = new Transaction(Transaction.generateTransactionId(), account.getAccountId(), "Tranfer of " + transfer + 
																													" from account[" + account.getAccountId() + "] to account[" 
																													+ transferAccount.getAccountId() + "]. "  +
																													"Balance - " + account.getBalance() + " as of " +
																													df.format(date));
							
							Transaction transferTransaction = new Transaction(Transaction.generateTransactionId(), transferAccount.getAccountId(), "Tranfer of " + transfer + 
																														" to account[" + transferAccount.getAccountId() + "] from account[" 
																														+ account.getAccountId() + "]. "  +
																														"Balance - " + transferAccount.getBalance() + " as of " +
																														df.format(date));
							
							if(dbc.addTransaction(transaction) && dbc.addTransaction(transferTransaction)) {
								System.out.println(ColorsUtility.BLACK);
								System.out.println(transaction.toString());
								System.out.println(transferTransaction.toString());
								System.out.println("Transaction completed successfully");
							}
							break;
						} catch (InputMismatchException e) {
							System.out.println(ColorsUtility.RED +"Incorrect Input. Please enter a number. : ");
							scan.nextLine();
						}
					}
					break;
				case 4:
					System.out.println(ColorsUtility.BLUE +"+------------------------+");
					System.out.println(ColorsUtility.BLUE +"| 5 Recent Transactions: |");
					System.out.println(ColorsUtility.BLUE +"+------------------------+");
					System.out.println(ColorsUtility.BLACK );
					ArrayList<Transaction> lastFive = dbc.findLastFiveTransactionsByAccountId(account.getAccountId());
					lastFive.stream().forEach(System.out::println);
					break;
				case 5:
					System.out.println(ColorsUtility.BLUE +"+-----------------------+");
					System.out.println(ColorsUtility.BLUE +"| Customer Information: |");
					System.out.println(ColorsUtility.BLUE +"+-----------------------+");
					System.out.println(ColorsUtility.BLACK );
					dbc.listCustomer(customer.getCustomerId());
					break;
				case 6:
					running = false;
					break;
				default:
					System.out.println("Incorrect command.");
				}
			}catch (InputMismatchException e) {
					System.out.println("Incorrect input");
				}
			}		
//		scan.close();
		runProgram(dbc);
	}
	
}

