package programmateurs.beans;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;



public class Transaction {

	public enum TRANSACTION_TYPE {
		DEPOSIT, WITHDRAWAL, REBALANCE 
	}

	private long transactionID;
	private long accountID;
	private TRANSACTION_TYPE transactionType;
	private long transactionAmount;
	private Date transactionDate;
	private Date timestamp;
	private boolean rolledback;
	private Category[] categories;
	
	public Transaction(long transactionID, long accountID,
			TRANSACTION_TYPE transactionType, long transactionAmount, Date transactionDate,
			Date timestamp, boolean rolledback, Category[] categories) {
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

	public long getTransactionID() {
		return transactionID;
	}
	public long getAccountID() {
		return accountID;
	}
	public TRANSACTION_TYPE getTransactionType() {
		return transactionType;
	}
	public long getTransactionAmount() {
		return transactionAmount;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getTimestamp() {
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