package programmateurs.interfaces;

import java.sql.Timestamp;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.User;

public interface DataSourceInterface {
	
	/**
	 * returns all users in the system.  useful for admin screen.
	 * @return users in system
	 */
	public User[] getUsers();

	/**
	 * returns a list of all accounts owned by a given user
	 * @param userID
	 * @return list of accounts owned by user
	 */
	public Account[] getAccountsForUser(int userID);

	/**
	 * returns a list of categories for a given user!  useful for reports.
	 * @param userID
	 * @return a list of categories for a given user.
	 */
	public Category[] getCategoriesForUser(int userID);

	/**
	 * a list of transactions for a given account.  need to decide what to do about rolling
	 * back transactions. 
	 * @param accountID
	 * @return a list of transactions for a given account
	 */
	public Transaction[] getTransactionsForAccount(int accountID);

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
	public Account addAccountToDB(int userID, Account.ACCOUNT_TYPE accountType,
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
	public Transaction addTransactionToDB(int accountID,
			Transaction.TRANSACTION_TYPE transactionType, int transactionAmount, 
			String transactionDate,
			Timestamp timestamp, boolean rolledback, Category[] categories); 

	/**
	 * adds category with associated information to the DB, returns an object representation of it.
	 * @param userID
	 * @param category_name
	 * @return
	 */
	public Category addCategoryToDB(int userID, String category_name);

}