package banking;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
    	System.out.println("Account statements: ");
    	File file;
    	Scanner account;
    	Scanner transact;
    	
    	try {
    		file = new File("accounts.txt");
    		account = new Scanner(file);
    		
    		String[] tokens;
    		String transactLine;
    		
    		ArrayList<BankAccount> accounts = new ArrayList<>();
    		
    		account.nextLine();
    		while(account.hasNext()) {
    			transactLine = account.nextLine();
    			tokens = transactLine.split("\t");
    			
    			int accountNumber = Integer.parseInt(tokens[0]);
    			String accountType = tokens[1];
    			String accountHolder = tokens[2];
    			Double openingBalance = Double.parseDouble(tokens[3]);
    			
    			BankAccount bankAcct;
    			if (accountType.equals("Traditional")) {
    				bankAcct = new TraditionalAccount(accountNumber, accountHolder, openingBalance);
    				accounts.add(bankAcct);
    			} else if (accountType.equals("Shariah")) {
    				bankAcct = new ShariahAccount(accountNumber, accountHolder, openingBalance);
    				accounts.add(bankAcct);
    			}
    		}
    		
    		file = new File("transact.txt");
			transact = new Scanner(file);
			transact.nextLine();
			
    		while(transact.hasNext()) {
    			transactLine = transact.nextLine();
    			tokens = transactLine.split("\t");
    			
    			for (BankAccount currentAccount : accounts){
        			
        			int day = Integer.parseInt(tokens[0]);
        			int month = Integer.parseInt(tokens[1]);
        			int year = Integer.parseInt(tokens[2]);
        			int transactionAccountNum = Integer.parseInt(tokens[3]);
        			
        			if (transactionAccountNum != currentAccount.getAccountNumber()) {
        				continue;
        			}
        			
        			int transactionType = 0;
        			switch (tokens[4]) {
        			case "withdraw":
        				transactionType = 0;
        				break;
        			case "deposit":
        				transactionType = 1;
        				break;
        			case "transfer":
        				transactionType = 2;
        				break;
        			}
        			
        			Double amount = Double.parseDouble(tokens[5]);
        			
        			if (transactionType == 0 || transactionType == 1) {
        				BankAccount.Transaction transaction = currentAccount.new Transaction(day, month, year, transactionType, currentAccount, amount);
						currentAccount.processTransaction(transaction);
        			} else {
        				int transferToNum = Integer.parseInt(tokens[6]);
        				for (BankAccount transferTo : accounts) {
        					if (transferTo.getAccountNumber() == transferToNum) {
        						BankAccount.Transaction transaction = currentAccount.new Transaction(day, month, year, transactionType, transferTo, amount);
        						transferTo.transactions.add(transaction);
        						currentAccount.processTransaction(transaction);
        						break;
        					}
        				}
        			}
        		}
    		}
    		
    		for (BankAccount output : accounts) {
    			output.printStatement();
    		}
    	} catch(IOException exception) {
    		System.out.println("File Error");
    	}
    }
}

