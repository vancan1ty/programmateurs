package programmateurs.interfaces;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import android.database.SQLException;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;

/**
 * This interface defines the operations a datasource must support to be the
 * backend for cashmoneypro.
 * @author vancan1ty
 *
 */
public interface DataSourceInterface {

    /**
     * opens data source. make sure to open before calling data access methods.
     * 
     * @throws SQLException if the db connection can't be opened.
     */
    void open() throws SQLException;

    /**
     * close data source. make sure to close when you're done with the data
     * source!
     */
    void close();

    /**
     * returns all users in the system. useful for admin screen.
     * 
     * @return users in system
     */
    User[] getUsers();

    /**
     * returns user in system corresponding to given username. returns null if
     * there is no user in the system corresponding to given username.
     * 
     * @param username the username of the user
     * @return user in system, null if no user in system
     */
    User getUser(String username);

    /**
     * Verifies that given password is the password in the database for the
     * given username.
     * 
     * @param username username for the user
     * @param password password for the user.
     * @return true if given username/password combination corresponds to
     *         database
     */
    boolean isUserInDB(String username, String password);

    /**
     * retrieves an account with the given id, or null if none exists.
     * 
     * @param accountID the account id of the user
     * @return the Account object representing the account matching the input id
     */
    Account getAccountWithID(long accountID);

    /**
     * returns a list of all accounts owned by a given user.
     * 
     * @param userID the id of the user to find the account for.
     * @return list of accounts owned by user
     */
    Account[] getAccountsForUser(long userID);

    /**
     * returns a list of categories for a given user! useful for reports.
     * 
     * @param userID the id of the user to find the list of categories for
     * @return a list of categories for a given user.
     */
    Category[] getCategoriesForUser(long userID);

    /**
     * a list of transactions for a given account. need to decide what to do
     * about rolling back transactions.
     * 
     * @param accountID the id of the account to get transactions for.
     * @return a list of transactions for a given account
     */
    Transaction[] getTransactionsForAccount(long accountID);

    /**
     * adds a user with the given information to the db, returns the object
     * created.
     * 
     * @param username username
     * @param passhash passhash
     * @param first first
     * @param last last
     * @param email email
     * @return return
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    User addUserToDB(String username, String passhash, String first,
            String last, String email) throws NoSuchAlgorithmException,
            UnsupportedEncodingException;

    /**
     * adds an account with the associated information to the DB, returns an
     * object representation of it.
     * 
     * @param userID userID
     * @param accountType accountType.
     * @param accountName accountName
     * @param interestRate interestRate
     * @return return
     */
    Account addAccountToDB(long userID,
            Account.ACCOUNT_TYPE accountType, String accountName,
            double interestRate);

    /**
     * adds transaction with associated information to the DB, returns an object
     * representation of it.
     * 
     * @param accountID the account id
     * @param transactionName the transaction's name
     * @param transactionType the type of the transaction
     * @param transactionAmount the amount of the transaction
     * @param transactionDate the date of the transaction
     * @param transactionComment a comment on the transaction
     * @param rolledback whether or no the transaction has been rolled back
     * @param category the category of the transaction
     * @return return a transaction object matching the one added to the db
     */
    Transaction addTransactionToDB(long accountID,
            String transactionName, TRANSACTION_TYPE transactionType,
            long transactionAmount, Date transactionDate,
            String transactionComment, boolean rolledback, Category category);

    /**
     * adds category with associated information to the DB, returns an object
     * representation of it.
     * 
     * @param userID the user's id
     * @param categoryName the category to add.
     * @return an object representation of the added category.
     */
    Category addCategoryToDB(long userID, String categoryName);

    /**
     * updates a user in the db with new information.
     * @param user the user to update
     * @return a copy of the modified user
     */
    User updateUser(User user);

    /**
     * retrieves transactions associated with a given user.
     * 
     * @author Pavel
     * @param userID the id of the user
     * @return a list of transaction associated with him.
     */
    Transaction[] getTransactionsForUser(long userID);

}
