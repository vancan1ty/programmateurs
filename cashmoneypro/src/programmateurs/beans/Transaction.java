package programmateurs.beans;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Somebody who wasn't paying attention during the "Please Javadoc"
 *         talk.
 * @version 0.1
 */
public class Transaction {

    public enum TRANSACTION_TYPE {
        DEPOSIT, WITHDRAWAL, REBALANCE;

        @Override
        public String toString() {
            String type = super.toString();
            type = type.charAt(0)
                    + type.substring(1, type.length()).toLowerCase(Locale.US);
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

    /**
     * Transaction constructor.
     * @param transactionID
     * @param accountID
     * @param transactionName
     * @param transactionType
     * @param transactionAmount
     * @param transactionDate
     * @param transactionComment
     * @param timestamp
     * @param rolledback
     * @param category
     */
    public Transaction(final long transactionID, final long accountID,
            final String transactionName, final TRANSACTION_TYPE transactionType,
            final long transactionAmount, final Date transactionDate,
            final String transactionComment, final Date timestamp, final boolean rolledback,
            final Category category) {
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

    public final long getTransactionID() {
        return transactionID;
    }

    public final long getAccountID() {
        return accountID;
    }

    public final TRANSACTION_TYPE getTransactionType() {
        return transactionType;
    }

    public final double getTransactionAmountAsDouble() {
        return (transactionAmount / 100) + 0.01 * (transactionAmount % 100);
    }

    public final long getTransactionAmount() {
        return transactionAmount;
    }

    public final Date getTransactionDate() {
        return transactionDate;
    }

    public final Date getTimestamp() {
        return timestamp;
    }

    public final boolean isRolledback() {
        return rolledback;
    }

    public final Category getCategory() {
        return this.category;
    }

    @Override
    public final String toString() {
        return "Transaction [transactionID=" + transactionID + ", accountID="
                + accountID + ", transactionName=" + transactionName
                + ", transactionType=" + transactionType
                + ", transactionAmount: " + transactionAmount
                + ", transactionDate=" + transactionDate + ", timestamp="
                + ",transactionComment=" + transactionComment + timestamp
                + ", rolledback=" + rolledback + ", category=" + category + "]";
    }

    public final String getTransactionName() {
        return transactionName;
    }

    /**
     * Not just a standard getter. If the user provided no transaction comment,
     * then this returns "No Comment Provided"
     * 
     * @return
     */
    public final String getTransactionComment() {
        if (!transactionComment.equals("")) {
            return "\"" + transactionComment + "\"";
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
    public final String formatDetails() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder returnStr = new StringBuilder(getTransactionComment()
                + "\n\n");
        returnStr.append("-" + transactionType + " Amount: "
                + nf.format(getTransactionAmountAsDouble()) + "\n");
        returnStr.append("-Transaction ID: " + transactionID + "\n");
        returnStr.append("-Account ID: " + accountID + "\n"); // TODO replace
                                                              // this with
                                                              // Account name.
        returnStr.append("-Date: " + transactionDate + "\n");
        returnStr.append("-Category: " + category.getCategory_name() + "\n");

        return returnStr.toString();
    }

}
