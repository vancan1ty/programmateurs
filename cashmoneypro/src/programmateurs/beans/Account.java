package programmateurs.beans;

import java.util.Locale;

import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.RealDataSource;

import android.content.Context;


/**
 * The Account class represents an account possessed by a User.
 * A single User can have multiple accounts, but each account has
 * only one User
 * 
 * @author Currell
 * @version 0.2
 */
public class Account {

	public enum ACCOUNT_TYPE {
		SAVINGS, CHECKING;
		
		@Override
		public String toString(){
			//Look at this cute block of code. It takes an all-capital toString
			//and makes everything but the first letter lower case.
			String type = super.toString();
			type = type.charAt(0) + type.substring(1,type.length()).toLowerCase(Locale.US);
			return type + " Account";
		}
	}

	private long accountID;
	private long userID;
	private ACCOUNT_TYPE accountType;
	private String accountName;
	private double interestRate;

	/**
	 * Constructor for Account class; assigns all appropriate values.
	 * 
	 * @param accountID ID of the account
	 * @param userID ID of owner
	 * @param accountType Type of account (enum)
	 * @param accountName Name given to an account 
	 * @param interestRate Specified as a double
	 */
	public Account(long accountID, long userID, ACCOUNT_TYPE accountType,
			String accountName, double interestRate) {
		super();
		this.accountID = accountID;
		this.userID = userID;
		this.accountType = accountType;
		this.accountName = accountName;
		this.interestRate = interestRate;
	}

	/**
	 * Getter for account ID.
	 * 
	 * @return Account ID
	 */
	public long getAccountID() {
		return accountID;
	}
	
	/**
	 * Calculates the balance for this account.
	 * 
	 * @param context The view from which the method is called
	 * @return balance of the account
	 */
	public double getBalance(Context context){
		DataSourceInterface db = new RealDataSource(context);
		db.open();
		double balance = 0;
		for (Transaction transaction : db.getTransactionsForAccount(accountID)) {
			TRANSACTION_TYPE type = transaction.getTransactionType();
			if ((type == TRANSACTION_TYPE.DEPOSIT || type == TRANSACTION_TYPE.REBALANCE) 
					&& transaction.isRolledback() == false) {
				balance += transaction.getTransactionAmountAsDouble();
			} else if (type == TRANSACTION_TYPE.WITHDRAWAL && transaction.isRolledback() == false) {
				balance -= transaction.getTransactionAmountAsDouble();
			}
		}
		db.close();
		return balance;
	}
	
	/**
	 * Checks to see if a potential transaction would result in overdrawing from the account.
	 * 
	 * @param context The view from which the method is called
	 * @param amount The amount of the potential transaction
	 * @param type The type of the potential transaction
	 * @return true if transaction would result in overdrawing from account
	 */
	public boolean overdrawn(Context context, Double amount, Transaction.TRANSACTION_TYPE type){
		if(type == Transaction.TRANSACTION_TYPE.WITHDRAWAL){
			return (getBalance(context) - amount) < 0;
		} else{
			return false;
		}
	}
	
	/**
	 * Getter for the ID of the user who owns the account.
	 * 
	 * @return ID of owner
	 */
	public long getUserID() {
		return userID;
	}
	
	/**
	 * Getter for account type.
	 * 
	 * @return Account type
	 */
	public ACCOUNT_TYPE getAccountType() {
		return accountType;
	}
	
	/**
	 * Getter for account name.
	 * 
	 * @return Account name
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/**
	 * Getter for account interest rate.
	 * 
	 * @return Account interest rate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * 
	 * @return String representation of Account
	 */
	public String toString() {
		return "Account [accountID=" + accountID + ", userID=" + userID
				+ ", accountType=" + accountType + ", accountName="
				+ accountName + ", interestRate=" + interestRate + "]";
	}



}
