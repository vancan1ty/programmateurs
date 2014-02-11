package programmateurs.beans;

import java.sql.Timestamp;
import java.util.Arrays;



public class Transaction {

	public enum TRANSACTION_TYPE {
		DEPOSIT, WITHDRAWAL, REBALANCE 
	}

	private int transactionID;
	private int accountID;
	private TRANSACTION_TYPE transactionType;
	private int transactionAmount;
	private String transactionDate;
	private Timestamp timestamp;
	private boolean rolledback;
	private Category[] categories;
	
	public Transaction(int transactionID, int accountID,
			TRANSACTION_TYPE transactionType, int transactionAmount, String transactionDate,
			Timestamp timestamp, boolean rolledback, Category[] categories) {
		super();
		this.transactionID = transactionID;
		this.accountID = accountID;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.timestamp = timestamp;
		this.rolledback = rolledback;
		this.categories = categories;
	}

	public int getTransactionID() {
		return transactionID;
	}
	public int getAccountID() {
		return accountID;
	}
	public TRANSACTION_TYPE getTransactionType() {
		return transactionType;
	}
	public int getTransactionAmount() {
		return transactionAmount;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public boolean isRolledback() {
		return rolledback;
	}
	public Category[] getCategories() {
		return this.categories;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", accountID="
				+ accountID + ", transactionType=" + transactionType + ", transactionAmount: " + transactionAmount
				+ ", transactionDate=" + transactionDate + ", timestamp="
				+ timestamp + ", rolledback=" + rolledback + ", categories="
				+ Arrays.toString(categories) + "]";
	}

}