package com.dollarsbank.model;

public class Customer {

	private int customerId;
	private String firstname;
	private String lastname;
	private String address;
	private String contactNumber;
	private String username;
	//Password has form to follow
	private String password;
	
	private static int counter = 0;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(int customerId, String firstname, String lastname, String address, String contactNumber,
			String username, String password) {
		super();
		if (customerId > counter ) {
			counter = customerId;
		}
		this.customerId = customerId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.contactNumber = contactNumber;
		this.username = username;
		this.password = password;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static int generateCustomerId() {
		return ++counter;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", address=" + address + ", contactNumber=" + contactNumber + ", username=" + username + ", password="
				+ password + "]";
	}
	
	
	
}
