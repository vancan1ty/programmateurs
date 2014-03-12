package programmateurs.beans;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.text.NumberFormat;

/**
 * 
 * @author Somebody who wasn't paying attention during the "Please Javadoc" talk.
 * @version 0.1
 */
public class Transaction {

	public enum TRANSACTION_TYPE {
		DEPOSIT, WITHDRAWAL, REBALANCE;

		@Override
		public String toString(){
			String type = super.toString();
			type = type.charAt(0) + type.substring(1,type.length()).toLowerCase(Locale.US);
			return type;
		}
	}

	private long transactionID;
	private long accountID;
	private String transactionName;
	private TRANSACTION_TYPE transactionType;
	private long transactionAmount;
	private Date transactionDate;
	private String transactionComment;
	private Date timestamp;
	private boolean rolledback;
	private Category category;
	
	public Transaction(long transactionID, long accountID,
			String transactionName, TRANSACTION_TYPE transactionType, long transactionAmount, Date transactionDate,
			String transactionComment, Date timestamp, boolean rolledback, Category category) {
		super();
		this.transactionID = transactionID;
		this.accountID = accountID;
		this.transactionName = transactionName;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionComment = transactionComment;
		this.timestamp = timestamp;
		this.rolledback = rolledback;
		this.category = category;
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
	
	public double getTransactionAmountAsDouble(){
		return (transactionAmount/100) + 0.01*(transactionAmount % 100);
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
	public Category getCategory() {
		return this.category;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", accountID="
				+ accountID + ", transactionName=" + transactionName + ", transactionType=" + transactionType + ", transactionAmount: " + transactionAmount
				+ ", transactionDate=" + transactionDate + ", timestamp=" + ",transactionComment=" + transactionComment 
				+ timestamp + ", rolledback=" + rolledback + ", category="
				+ category + "]";
	}

	public String getTransactionName() {
		return transactionName;
	}

	public String getTransactionComment() {
		return transactionComment;
	}

}