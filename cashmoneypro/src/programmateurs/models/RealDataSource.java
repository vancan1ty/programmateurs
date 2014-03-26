package programmateurs.models;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
 * and a call to close() in onPause.
 * RealDataSource will manage the DB connection otherwise.
 * thanks!
 * @author vancan1ty
 *
 */
public class RealDataSource implements DataSourceInterface {
	
	/* ********INSTANCE VARS, AND SCAFFOLDING************** */

  // Database fields
  private SQLiteDatabase db;
  private DBHelper dbHelper;

  public RealDataSource(Context context) {
	 dbHelper = new DBHelper(context);
  }
	  
  @Override
  public void open() throws SQLException {
    db = dbHelper.getWritableDatabase();
    Log.d("RealDataSource", "opened db: " + db);
  }

  @Override
  public void close() {
    db.close();
  }
  
	/* ******** Operations on USERS ************** */

	@Override
	public User[] getUsers() {
		return UsersDAO.getUsers(db);
	}
	
	@Override
	public User getUser(String username){
		for(User user: getUsers()){
			if(user.getUsername().equalsIgnoreCase(username))
				return user;
		}
		return null;
	}
	
	@Override
	public boolean isUserInDB(String username, String password) {
		return UsersDAO.isUserInDB(db, username, password);
	}
	
	@Override
	public User updateUser(User user) {
		return UsersDAO.updateUser(db, user);
	}
	
	@Override
	public Account getAccountWithID(long accountID) {
		return AccountsDAO.getAccountWithID(db, accountID);
		
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
	public Transaction[] getTransactionsForUser(long userID) {//Pavel
		return TransactionsDAO.getTransactionsForUser(db, userID);
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
			String accountName, double interestRate) {
		return AccountsDAO.addAccountToDB(db, userID, accountType, accountName, interestRate);
	}

	@Override
	public Transaction addTransactionToDB(long accountID, String transactionName,
			TRANSACTION_TYPE transactionType, long transactionAmount,
			Date transactionDate, String transactionComment, boolean rolledback,
			Category category) {
		return TransactionsDAO.addTransactionToDB(db, accountID, transactionName, transactionType, 
				transactionAmount, transactionDate, transactionComment, rolledback, category);
	}


public String getCategoryReport(Calendar dateStart, Calendar dateEnd, Transaction.TRANSACTION_TYPE type, long userID) {
	return TransactionsDAO.getCategoryReport(db, dateStart, dateEnd, type, userID);
}
	
	@Override
	public Category addCategoryToDB(long userID, String categoryName) {
		return CategoriesDAO.addCategoryForDB(db, userID, categoryName);
	}
	
	public void deleteAllFromDB() {
		db.execSQL("delete from transactions");
		db.execSQL("delete from categories");
		db.execSQL("delete from accounts");
		db.execSQL("delete from users");
	}

}
