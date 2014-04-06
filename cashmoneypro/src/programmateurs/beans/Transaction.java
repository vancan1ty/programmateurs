package programmateurs.beans;

import java.util.Date;
import java.util.Locale;
import java.text.NumberFormat;

/**
 * The Transaction class represents a user transaction, which is classified
 * into one of three enumerated types: deposit, withdrawal, or rebalance.
 * Transactions also have an amount, date, ID, name, comment, and a "rolled
 * back" status that is used to determine whether or not to include the 
 * transaction in account balance calculations. Additionally, transactions
 * hold a reference to their category and the account they're associated with. 
 * Finally, a transaction has a timestamp: the time it was entered into the app.
 * 
 * Transactions are immutable.
 * 
 * @author Currell
 * @version 0.1
 */
public class Transaction {

    /**
     * TRANSACTION_TYPE represents the type of transaction supported by this
     * application: DEPOSIT (add money to an account), WITHDRAWAL (removing
     * money from an account), or REBALANCE (a special type of transaction
     * that is used to rebalance an account after a user error).
     * 
     * @author Currel
     *
     */
    public enum TRANSACTION_TYPE {
        //CHECKSTYLE:OFF        Types defined in enum javadoc.
        DEPOSIT, WITHDRAWAL, REBALANCE;
        //CHECKSTYLE:ON
        
        @Override
        public String toString() {
            String type = super.toString();
            type = type.charAt(0)
                    + type.substring(1, type.length()).toLowerCase(Locale.US);
            return type;
        }
    }

    //CHECKSTYLE:OFF    Variables defined in class javadoc      
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
    //CHECKSTYLE:ON

    //CHECKSTYLE:OFF    No ambiguity about variable when "this" is used.
    public Transaction(long transactionID, long accountID,
            String transactionName, TRANSACTION_TYPE transactionType,
            long transactionAmount, Date transactionDate,
            String transactionComment, Date timestamp, boolean rolledback,
            Category category) {
    //CHECKSTYLE:ON
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

    /**
     * Getter for transactionID.
     * 
     * @return ID for this transaction
     */
    public long getTransactionID() {
        return transactionID;
    }

    /**
     * Getter for the ID of the account this transaction is associated with.
     * 
     * @return account ID
     */
    public long getAccountID() {
        return accountID;
    }

    /**
     * Getter for the enumerated type TRANSACTION_TYPE. This can either be
     * of type DEPOSIT, WITHDRAWAL, or REBALANCE.
     * 
     * @return enumerated transaction type
     */
    public TRANSACTION_TYPE getTransactionType() {
        return transactionType;
    }

    /**
     * Getter for transaction amount, formatted as a double for presentation.
     *
     * @return transaction amount
     */
    public double getTransactionAmountAsDouble() {
        return (transactionAmount / 100) + 0.01 * (transactionAmount % 100);
    }

    /**
     * Getter for the transaction amount, formatted as a long for storage
     * purposes.
     * 
     * @return transaction amount
     */
    public long getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Getter for the date the transaction was made.
     * 
     * @return transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Getter for the actual timestamp that the transaction was entered on.
     * 
     * @return transaction timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Getter "for rolled back" status of transaction.
     * 
     * @return true if transaction is rolled back
     */
    public boolean isRolledback() {
        return rolledback;
    }

    /**
     * Getter for the category that the transaction belongs to.
     * 
     * @return transaction's category
     */
    public Category getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return "Transaction [transactionID=" + transactionID + ", accountID="
                + accountID + ", transactionName=" + transactionName
                + ", transactionType=" + transactionType
                + ", transactionAmount: " + transactionAmount
                + ", transactionDate=" + transactionDate + ", timestamp="
                + ",transactionComment=" + transactionComment + timestamp
                + ", rolledback=" + rolledback + ", category=" + category + "]";
    }

    /**
     * Getter for transaciton name.
     * 
     * @return name of transaction
     */
    public String getTransactionName() {
        return transactionName;
    }

    /**
     * Not just a standard getter. If the user provided no transaction comment,
     * then this returns "No Comment Provided"
     * 
     * @return formatted transaction comment
     */
    public String getTransactionComment() {
        String quote = "\"";
        if (!transactionComment.equals("")) {
            return quote + transactionComment + quote;
        } else {
            return "No Comment Provided";
        }
    }

    /**
     * This is currently just a method that formats the transaction info to be
     * displayed to the user.
     * 
     * @return user-friendly String with transaction data
     */
    public String formatDetails() {
        String nl = "\n";
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder returnStr = new StringBuilder(getTransactionComment()
                + nl + nl);
        returnStr.append("-" + transactionType + " Amount: "
                + nf.format(getTransactionAmountAsDouble()) + nl);
        returnStr.append("-Transaction ID: " + transactionID + nl);
        returnStr.append("-Account ID: " + accountID + nl); // TODO replace
                                                                // this with
                                                                // Account name.
        returnStr.append("-Date: " + transactionDate + nl);
        returnStr.append("-Category: " + category.getCategory_name() + nl);

        return returnStr.toString();
    }

}