package programmateurs.beans;



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
	 * 
	 * @param accountID
	 * @param userID
	 * @param accountType
	 * @param accountName
	 * @param interestRate specified as an integer over 1000
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

	public long getAccountID() {
		return accountID;
	}
	public long getUserID() {
		return userID;
	}
	public ACCOUNT_TYPE getAccountType() {
		return accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public int getInterestRate() {
		return interestRate;
	}

	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", userID=" + userID
				+ ", accountType=" + accountType + ", accountName="
				+ accountName + ", interestRate=" + interestRate + "]";
	}



}
