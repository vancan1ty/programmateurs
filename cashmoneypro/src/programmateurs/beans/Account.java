package programmateurs.beans;

import java.util.Locale;

/**
 * The Account class represents an account possessed by a User. A single User
 * can have multiple accounts, but each account has only one User. Each account
 * has an ID for database retrieval, a reference to the ID of the user who owns
 * it. An enumerated type (withdrawal or deposit), a name, and an interest rate.
 * 
 * @author Currell
 * @version 0.3
 */
public class Account {

    /**
     * ACCOUNT_TYPE can be CHECKING or WITHDRAWAL, representing exactly what
     * you'd think. This javadoc is here because checkstyle is excessive.
     * 
     */
    // CHECKSTYLE:OFF Enumerated types defined in enum javadoc.
    public enum ACCOUNT_TYPE {
        SAVINGS, CHECKING;
        // CHECKSTYLE:ON

        @Override
        public String toString() {
            // Look at this cute block of code. It takes an all-capital toString
            // and makes everything but the first letter lower case.
            String type = super.toString();
            type = type.charAt(0)
                    + type.substring(1, type.length()).toLowerCase(Locale.US);
            return type + " Account";
        }
    }

    // CHECKSTYLE:OFF Variables defined in class javadoc.
    private long accountID;
    private long userID;
    private ACCOUNT_TYPE accountType;
    private String accountName;
    private double interestRate;

    // CHECKSTYLE:ON

    /**
     * Constructor for Account class; assigns all appropriate values.
     * 
     * @param accountID
     *            ID of the account
     * @param userID
     *            ID of owner
     * @param accountType
     *            Type of account (enum)
     * @param accountName
     *            Name given to an account
     * @param interestRate
     *            Specified as a double
     */
    // CHECKSTYLE:OFF No ambiguity about variables when "this" is used.
    public Account(long accountID, long userID, ACCOUNT_TYPE accountType,
            String accountName, double interestRate) {
        // CHECKSTYLE:ON

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
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "Account [accountID=" + accountID + ", userID=" + userID
                + ", accountType=" + accountType + ", accountName="
                + accountName + ", interestRate=" + interestRate + "]";
    }

}
