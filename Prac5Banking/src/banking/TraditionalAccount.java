package banking;

public class TraditionalAccount extends BankAccount implements InterestBearingAccount{
	
	public TraditionalAccount(int accountNumber, String accountHolder, double openingBalance) {
		super(accountNumber, accountHolder, openingBalance);
	}
	
	@Override
	public void addInterest(double interestRate) {
		if (balance > 500) {
			double interest = balance * (interestRate / 100);
			Transaction transaction = new Transaction(31, 3, 2024, 1, this, interest); 
			processTransaction(transaction);
		}
	}
	
	@Override
	public void deductFees() {
		Transaction transaction = new Transaction(31, 3, 2024, 0, this, 50);
		processTransaction(transaction);
	}
	
	@Override
	public boolean withdraw(double amount) {
		transactionCount++;
		if (balance - amount >= 500) {
			balance -= amount;
			return true;
		} else {
			return false;
		}
	}
}
