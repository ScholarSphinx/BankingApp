package banking;

public class ShariahAccount extends BankAccount{
	
	public ShariahAccount(int accountNumber, String accountHolder, double openingBalance) {
		super(accountNumber, accountHolder, openingBalance);
	}
	
	@Override
	public void deductFees() {
		double fees = 0;
		if(transactions.size() > 5) {
			fees = 1.8 * (transactions.size() - 5);
		}
		Transaction transaction = new Transaction(31, 3, 2024, 0, this, fees);
		processTransaction(transaction);
	}
	
	@Override
	public boolean withdraw(double amount) {
		transactionCount++;
		if (balance - amount >= 0) {
			balance -= amount;
			return true;
		} else {
			return false;
		}
	}
}
