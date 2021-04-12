package com.dollarsbank.applicaton;

import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.utility.ConsolePrinterUtility;
import com.dollarsbank.utility.FileStorageUtility;

public class DollarsBankApplicaction {

	public static void main(String[] args) {
		
		DollarsBankController dbc = new DollarsBankController();
		FileStorageUtility.readAccountsCSV(dbc);
		FileStorageUtility.readCustomersCSV(dbc);
		FileStorageUtility.readTransactionsCSV(dbc);
		ConsolePrinterUtility.runProgram(dbc);
		
	}

}
