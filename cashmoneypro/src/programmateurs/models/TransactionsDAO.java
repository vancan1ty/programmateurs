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
		  + " transaction_amount INTEGER NOT NULL,"
		  + " transaction_date DATE, "
		  + " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
		  + " rolledback BOOLEAN NOT NULL, "
		  + " FOREIGN KEY(accountID) REFERENCES Accounts(accountID) "
		  + ");";
 

  private static Transaction cursorToTransaction(Cursor cursor, SQLiteDatabase db) throws ParseException {
	long transactionID = cursor.getInt(0);
	long accountID = cursor.getInt(1);
	long userID = cursor.getInt(2);
	Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(3));
	long transactionAmount = cursor.getInt(4);
	Date transactionDate = DateUtility.getCalendarFromFormattedLong(cursor.getInt(5)).getTime();
	Date timestamp = DateUtility.getCalendarFromFormattedLong(cursor.getInt(6)).getTime();
	boolean rolledback = (cursor.getInt(7) == 1) ? true : false;
	Category[] categories = CategoriesDAO.getCategoriesForUser(db, userID);
	return new Transaction(transactionID,accountID,transactionType,transactionAmount,transactionDate,timestamp,rolledback,categories);
  }

	/**
	 * returns a list of all accounts owned by a given user
	 * @param userID
	 * @return list of accounts owned by user
	 */
	public static Transaction[] getTransactionsForAccount(SQLiteDatabase db, long accountID) {
		
		Cursor c = db.rawQuery(
					"SELECT transactionID, T.accountID, userID, transaction_type, transaction_amount, transaction_date, timestamp, rolledback " +
					"	FROM transactions AS T JOIN Accounts AS A WHERE T.accountID = ? AND T.accountID = A.accountID;", 
				new String[]{Long.toString(accountID)});
		List<Transaction> outL = new ArrayList<Transaction>();

		c.moveToFirst();
		while (!c.isAfterLast()) {
			Transaction transaction;
			
			try {
				transaction = cursorToTransaction(c, db);
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
	 * adds a transaction with the associated information to the DB, returns an object representation of it
	 */
	public static Transaction addTransactionToDB(SQLiteDatabase db, long accountID, Transaction.TRANSACTION_TYPE transactionType,
			long transactionAmount, Date transactionDate, boolean rolledback) {
		ContentValues toInsert = new ContentValues();
		toInsert.put("accountID", accountID);
		toInsert.put("transaction_type", transactionType.name());
		toInsert.put("transaction_amount", transactionAmount);
		toInsert.put("transaction_date", DateUtility.formatDateAsLong(transactionDate));
		toInsert.put("rolledback", rolledback ? 1 : 0);
		long transactionID = db.insert("accounts", null, toInsert);
		Category[] categories = CategoriesDAO.getCategoriesForTransaction(db, transactionID);
		Transaction thetrans = getTransactionWithID(db, transactionID);
		return thetrans;
	}

	/**
	 * returns a list of all transactions belonging to a certain user
	 * @param userID
	 * @return list of accounts owned by user
	 * @author Pavel
	 */
	public static Transaction[] getTransactionsForUser(SQLiteDatabase db, long userID) {
		
		Cursor c = db.rawQuery(
					"SELECT transactionID, T.accountID, userID, transaction_type, transaction_amount, transaction_date, timestamp, rolledback " +
					"	FROM transactions AS T JOIN Accounts AS A WHERE A.userID = ? AND T.accountID = A.accountID;", 
					
					
					new String[]{Long.toString(userID)});
		List<Transaction> outL = new ArrayList<Transaction>();

		c.moveToFirst();
		while (!c.isAfterLast()) {
			Transaction transaction;
			
			try {
				transaction = cursorToTransaction(c, db);
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

	public static Transaction getTransactionWithID(SQLiteDatabase db, long transactionID) {
		
		Cursor c = db.rawQuery(
					"SELECT transactionID, accountID, userID, transaction_type, transaction_amount, transaction_date, timestamp, rolledback " +
					"	FROM transactions WHERE transactionID = ?;", 
					new String[]{Long.toString(transactionID)});

		c.moveToFirst();
		Transaction out;
			try {
				out = cursorToTransaction(c, db);
			} catch (ParseException e) {
				out = null;
				e.printStackTrace();
			}
		// make sure to close the cursor
		c.close();
		return out;
	}
	
	
}
