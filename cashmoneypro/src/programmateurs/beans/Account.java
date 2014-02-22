package programmateurs.beans;


/**
 * The Account class represents an account possessed by a User.
 * A single User can have multiple accounts, but each account has
 * only one User
 * 
 * @author Currell
 * @version 0.0
 */
public class Account {

	public enum ACCOUNT_TYPE {
		SAVINGS, CHECKING
	}

	private long accountID;
	private long userID;
	private ACCOUNT_TYPE accountType;
	private String accountName;
	private int interestRate;

	/**
	 * Constructor for Account class; assigns all appropriate values.
	 * 
	 * @param accountID ID of the account
	 * @param userID ID of owner
	 * @param accountType Type of account (enum)
	 * @param accountName Name given to an account 
	 * @param interestRate Specified as an integer over 1000
	 */
	public Account(long accountID, long userID, ACCOUNT_TYPE accountType,
			String accountName, int interestRate) {
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
	public int getInterestRate() {
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
