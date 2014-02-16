package programmateurs.models;

import java.util.Date;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import programmateurs.beans.Account;
import programmateurs.beans.Account.ACCOUNT_TYPE;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;

/**
 * Usage of RealDataSource
 * 
 * **IMPORTANT, READ THESE DIRECTIONS**
 * in any activity that uses RealDataSource
 * put a call to open() in onResume
 * and a call to close() in onDestroy.
 * RealDataSource will manage the DB connection otherwise.
 * thanks!
 * @author vancan1ty
 *
 */
public class RealDataSource implements DataSourceInterface {
	
	/* ********INSTANCE VARS, AND SCAFFOLDING************** */
	UsersDAO usersDAO;
	AccountsDAO accountsDAO;
	CategoriesDAO categoriesDAO;
	TransactionsDAO transactionsDAO;

  // Database fields
  private SQLiteDatabase db;
  private DBHelper dbHelper;

  public RealDataSource(Context context) {
	 dbHelper = new DBHelper(context);
  }
	  
  public void open() throws SQLException {
    db = dbHelper.getWritableDatabase();
  }

  public void close() {
    db.close();
  }
  
	/* ******** Operations on USERS ************** */

	@Override
	public User[] getUsers() {
		return UsersDAO.getUsers(db);
	}
	
	public boolean isUserInDB(String username, String password) {
		return UsersDAO.isUserInDB(db, username, password);
	}

	@Override
	public Account[] getAccountsForUser(long userID) {
		return AccountsDAO.getAccountsForUser(db, userID);
	}

	@Override
	public Category[] getCategoriesForUser(long userID) {
		return CategoriesDAO.getCategoriesForUser(db, userID);
	}

	@Override
	public Transaction[] getTransactionsForAccount(long accountID) {
		return TransactionsDAO.getTransactionsForAccount(db, accountID);
	}

	@Override
	public User addUserToDB(String username, String passhash, String first,
			String last, String email) {
		return UsersDAO.addUserToDB(db, username, passhash, first, last, email);
	}

	public void cheapAddUserToDB(String username, String password) {
		UsersDAO.addUserToDB(db, username, password);
	}


	@Override
	public Account addAccountToDB(long userID, ACCOUNT_TYPE accountType,
			String accountName, int interestRate) {
		return AccountsDAO.addAccountToDB(db, userID, accountType, accountName, interestRate);
	}

	@Override
	public Transaction addTransactionToDB(long accountID,
			TRANSACTION_TYPE transactionType, long transactionAmount,
			Date transactionDate, Date timestamp, boolean rolledback,
			Category[] categories) {
		return TransactionsDAO.addTransactionToDB(db, accountID, transactionType, transactionAmount, transactionDate, timestamp, rolledback);
	}

	@Override
	public Category addCategoryToDB(long userID, String categoryName) {
		return CategoriesDAO.addCategoryForDB(db, userID, categoryName);
	}

}