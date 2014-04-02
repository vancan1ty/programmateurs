package programmateurs.interfaces;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;

public interface DataSourceInterface {

    /**
     * opens data source. make sure to open before calling data access methods.
     */
    void open();

    /**
     * close data source. make sure to close when you're done with the data
     * source!
     */
    void close();

    /**
     * returns all users in the system. useful for admin screen.
     * @return users in system
     */
    User[] getUsers();

    /**
     * returns user in system corresponding to given username. returns null if
     * there is no user in the system corresponding to given username.
     * @param username
     * @return user in system, null if no user in system
     */
    User getUser(String username);

    /**
     * Verifies that given password is the password in the database for the
     * given username.
     * @param username
     * @param password
     * @return true if given username/password combination corresponds to
     *         database
     */
    boolean isUserInDB(String username, String password);

    /**
     * retrieves an account with the given id, or null if none exists.
     * @param accountID
     * @return The account corresponding to the ID given
     */
    Account getAccountWithID(long accountID);

    /**
     * returns a list of all accounts owned by a given user.
     * @param userID
     * @return list of accounts owned by user
     */
    Account[] getAccountsForUser(long userID);

    /**
     * returns a list of categories for a given user! useful for reports.
     * @param userID
     * @return a list of categories for a given user.
     */
    Category[] getCategoriesForUser(long userID);

    /**
     * a list of transactions for a given account. need to decide what to do
     * about rolling back transactions.
     * @param accountID
     * @return a list of transactions for a given account
     */
    Transaction[] getTransactionsForAccount(long accountID);

    /**
     * adds a user with the given information to the db, returns the object
     * created.
     * @param username the user's identifying string
     * @param passhash a has
     * @param first the user's first name
     * @param last the user's surname
     * @param email the user's email
     * @return a new User
     * @throws UnsupportedEncodingException when invalid encodings given
     * @throws NoSuchAlgorithmException nonexistent algorithms given
     */
    User addUserToDB(String username, String passhash, String first,
            String last, String email) throws NoSuchAlgorithmException,
            UnsupportedEncodingException;

    /**
     * Adds an account with the associated information to the DB, returns an
     * object representation of it.
     * @param userID the ID of the user to whom the account belongeth
     * @param accountType the type of the account
     * @param accountName the name of the account
     * @param interestRate the interest rate of the account
     * @return the acount just added to the database
     */
    Account addAccountToDB(long userID,
            Account.ACCOUNT_TYPE accountType, String accountName,
            double interestRate);

    /**
     * Adds transaction with associated information to the DB, returns an object
     * representation of it.
     * @param accountID
     * @param transactionName
     * @param transactionType
     * @param transactionAmount
     * @param transactionDate
     * @param transactionComment
     * @param rolledback
     * @param category
     * @return
     */
    Transaction addTransactionToDB(long accountID,
            String transactionName, TRANSACTION_TYPE transactionType,
            long transactionAmount, Date transactionDate,
            String transactionComment, boolean rolledback, Category category);

    /**
     * adds category with associated information to the DB, returns an object
     * representation of it.
     * @param userID
     * @param category_name
     * @return
     */
    Category addCategoryToDB(long userID, String categoryName);

    User updateUser(User user);

    /**
     * retrieves transactions associated with a given user.
     * @author Pavel
     */
    Transaction[] getTransactionsForUser(long userID);

}
