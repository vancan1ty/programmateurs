package programmateurs.interfaces;

import java.sql.Timestamp;
import java.util.Date;

import android.database.SQLException;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;

public interface DataSourceInterface {
	

	/**
	 * opens data source.  make sure to open before calling data access methods.
	 * @throws SQLException
	 */
	public void open() throws SQLException; 

	/**
	 * close data source.  make sure to close when you're done with the data source!
	 */
	public void close() ;

	/**
	 * returns all users in the system.  useful for admin screen.
	 * @return users in system
	 */
	public User[] getUsers();

	/**
	 * returns user in system corresponding to given username.
	 * returns null if there is no user in the system corresponding to
	 * given username.
	 * 
	 * @param username
	 * @return user in system, null if no user in system
	 */
	public User getUser(String username);
	
	/**
	 * Verifies that given password is the password in the database for
	 * the given username.
	 * 
	 * @param username
	 * @param password
	 * @return true if given username/password combination corresponds to database
	 */
	public boolean isUserInDB(String username, String password);
	
	/**
	 * returns a list of all accounts owned by a given user
	 * @param userID
	 * @return list of accounts owned by user
	 */
	public Account[] getAccountsForUser(long userID);

	/**
	 * returns a list of categories for a given user!  useful for reports.
	 * @param userID
	 * @return a list of categories for a given user.
	 */
	public Category[] getCategoriesForUser(long userID);

	/**
	 * a list of transactions for a given account.  need to decide what to do about rolling
	 * back transactions. 
	 * @param accountID
	 * @return a list of transactions for a given account
	 */
	public Transaction[] getTransactionsForAccount(long accountID);

	/**
	 * adds a user with the given information to the db, returns the object created.
	 * @param username
	 * @param passhash
	 * @param first
	 * @param last
	 * @param email
	 * @return
	 */
	public User addUserToDB(String username, String passhash, String first,
			String last, String email);
	
	/**
	 * adds an account with the associated information to the DB, returns an object representation of it
	 * @param userID
	 * @param accountType
	 * @param accountName
	 * @param interestRate
	 * @return
	 */
	public Account addAccountToDB(long userID, Account.ACCOUNT_TYPE accountType,
			String accountName, int interestRate);
	
	/**
	 * adds transaction with associated information to the DB, returns an object representation of it.
	 * @param accountID
	 * @param transactionType
	 * @param transactionDate
	 * @param timestamp
	 * @param rolledback
	 * @param categories
	 * @return
	 */
	public Transaction addTransactionToDB(long accountID,
			TRANSACTION_TYPE transactionType, long transactionAmount,
			Date transactionDate, Date timestamp, boolean rolledback,
			Category[] categories);



	/**
	 * adds category with associated information to the DB, returns an object representation of it.
	 * @param userID
	 * @param category_name
	 * @return
	 */
	public Category addCategoryToDB(long userID, String categoryName);

	User updateUser(User user);
	
	/**
	 * @author Pavel
	 */
	public Transaction[] getTransactionsForUser(long userID);
}