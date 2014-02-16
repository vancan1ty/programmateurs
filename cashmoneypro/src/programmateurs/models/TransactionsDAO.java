package programmateurs.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.util.DateUtility;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateUtils;

/**
 * This class manages transaction data in the database.
 * 
 * @author vancan1ty
 *
 */
public class TransactionsDAO {

	//v1
  public static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE Transactions"
		  + " (transactionID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " accountID INTEGER NOT NULL, "
		  + " transaction_type TEXT NOT NULL, "
		  + " transaction_date DATE, "
		  + " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
		  + " rolledback BOOLEAN NOT NULL, "
		  + " FOREIGN KEY(accountID) REFERENCES Accounts(accountID) "
		  + ");";
 

  // Database fields
  private SQLiteDatabase database;
  private DBHelper dbHelper;
  private CategoriesDAO catDAO;
  private Context context;

  public TransactionsDAO(Context context) {
    dbHelper = new DBHelper(context);
    this.context = context;
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
    catDAO = new CategoriesDAO(context);
    catDAO.open();
  }

  public void close() {
    catDAO.close();
    dbHelper.close();
  }

  private Transaction cursorToTransaction(Cursor cursor) throws ParseException {
	long transactionID = cursor.getInt(0);
	long accountID = cursor.getInt(1);
	long userID = cursor.getInt(2);
	Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(3));
	long transactionAmount = cursor.getInt(4);
	Date transactionDate = DateUtility.getCalendarFromFormattedLong(cursor.getInt(5)).getTime();
	Date timestamp = DateUtility.getCalendarFromFormattedLong(cursor.getInt(6)).getTime();
	boolean rolledback = (cursor.getInt(7) == 1) ? true : false;
	Category[] categories = catDAO.getCategoriesForUser(userID);
	return new Transaction(transactionID,accountID,transactionType,transactionAmount,transactionDate,timestamp,rolledback,categories);
  }

	/**
	 * returns a list of all accounts owned by a given user
	 * @param userID
	 * @return list of accounts owned by user
	 */
	public Transaction[] getTransactionsForAccount(int accountID) {
		
		Cursor c = database.rawQuery(
					"SELECT transactionID, accountID, userID, transaction_type, transaction_amount, transaction_date, timestamp, rolledback " +
					"	FROM transactions AS T JOIN Accounts AS A WHERE T.accountID = ? AND T.accountID = A.accountID;", 
				new String[]{Integer.toString(accountID)});
		List<Transaction> outL = new ArrayList<Transaction>();

		c.moveToFirst();
		while (!c.isAfterLast()) {
			Transaction transaction;
			try {
				transaction = cursorToTransaction(c);
			} catch (ParseException e) {
				transaction = null;
				e.printStackTrace();
			}
			outL.add(transaction);
			c.moveToNext();
		}
		// make sure to close the cursor
		c.close();
		return outL.toArray(new Transaction[0]);
	}

	/**
	 * adds an account with the associated information to the DB, returns an object representation of it
	 * @param userID
	 * @param accountType
	 * @param accountName
	 * @param interestRate
	 * @return
	 */
	public Account addAccountToDB(long userID, Account.ACCOUNT_TYPE accountType,
			String accountName, int interestRate) {
		ContentValues toInsert = new ContentValues();
		toInsert.put("userID", userID);
		toInsert.put("account_type", accountType.name());
		toInsert.put("account_name", accountName);
		toInsert.put("interest_rate", interestRate);
		long accountID = database.insert("accounts", null, toInsert);
		Account acct = new Account(accountID,userID,accountType,accountName,interestRate);
		return acct;
	}
}
