package banking;

import java.util.ArrayList;

/**
 * The BankAccount class represents a bank account and is meant mainly to be
 * sub-classed
 */
public abstract class BankAccount {
	private int accountNumber;
	private String accountHolder;
	protected double balance = 0;
	protected ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	protected int transactionCount = 0;

	public BankAccount(int accountNumber, String accountHolder, double openingBalance) {
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
		this.balance = openingBalance;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public int getTransactionCount() {
		return transactionCount;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		transactionCount++;
		balance += amount;
	}

	public void transferTo(BankAccount b, double amount) {
		//TODO Add Implementation
		b.deposit(amount);
		balance -= amount;
		transactionCount++;
	}

	public abstract void deductFees();

	public abstract boolean withdraw(double amount);

	public void processTransaction(Transaction t) {
		this.transactions.add(t);
		switch (t.transactionType) {
		case Transaction.WITHDRAW:
			if (!withdraw(t.amount)) {
				t.cancelled = true;
			}
			break;
		case Transaction.DEPOSIT:
			deposit(t.amount);
			break;
		case Transaction.TRANSFER:
			transferTo(t.toAccount, t.amount);
			break;
		default:
			System.err.println("Wrong transaction type");
		}
	}

	public String toString() {
		return accountNumber + "has a balance of : " + balance;
	}

	public void printStatement() {
		//TODO Add Implementation
		if (this instanceof TraditionalAccount) {
			((TraditionalAccount)this).addInterest(1.5);
			deductFees();
		} else if (this instanceof ShariahAccount) {
			deductFees();
		}
		
		System.out.println("Statement for account number " + accountNumber);
		System.out.println("Account Holder: " + accountHolder);
		System.out.println("Closing Balance: " + String.format("%.2f", balance));
		System.out.println(transactionCount + " transactions this month:");
		
		for (Transaction t : transactions) {
			System.out.println(t);
		}
		
		System.out.println();
	}

	//INNER CLASS
	public class Transaction {
		public final static int WITHDRAW = 0;
		public final static int DEPOSIT = 1;
		public final static int TRANSFER = 2;

		// Date of Transaction
		int day;
		int month;
		int year;

		double amount;
		int transactionType;
		BankAccount toAccount;
		boolean cancelled = false;

		public Transaction(int day, int month, int year, int transactionType, BankAccount toAccount, double amount) {
			this.day = day;
			this.month = month;
			this.year = year;
			this.transactionType = transactionType;
			this.toAccount = toAccount;
			this.amount = amount;
		}

		public boolean isCancelled() {
			return cancelled;
		}

		public String toString() {
			String transactionString = "";
			transactionString += day + " / " + month + " / " + year + " : ";
			switch (transactionType) {
			case WITHDRAW:
				transactionString += "withdrwal of : R " + String.format("%.2f", amount);
				if (isCancelled()) {
					transactionString += " [CANCELLED]";
				}
				break;
			case DEPOSIT:
				transactionString += "deposit of : R " + String.format("%.2f", amount);
				break;
			case TRANSFER:
				transactionString += "transfer to : " + "Account No.: " + toAccount.getAccountNumber() + " amount of R " + String.format("%.2f", amount);
				break;
			default:
				transactionString += "R " + String.format("%.2f", amount);
			}
			return transactionString;
		}

	}

}
