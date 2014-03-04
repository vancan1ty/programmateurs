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
		  + " transaction_name TEXT DEFAULT '',"
		  + " transaction_type TEXT NOT NULL, "
		  + " transaction_amount INTEGER NOT NULL,"
		  + " transaction_date DATE, "
		  + " transaction_comment TEXT DEFAULT '',"
		  + " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
		  + " rolledback BOOLEAN NOT NULL, "
		  + " FOREIGN KEY(accountID) REFERENCES Accounts(accountID) "
		  + ");";

  private static long gcl (Cursor c, String columnName) {
	  return c.getLong(c.getColumnIndexOrThrow(columnName));
  }
  
  private static String gcs (Cursor c, String columnName) {
	  return c.getString(c.getColumnIndexOrThrow(columnName));
  }

  private static Transaction cursorToTransaction(Cursor c, SQLiteDatabase db) throws ParseException {
	long transactionID = gcl(c, "transactionID");
	long accountID = gcl(c, "accountID");
	long userID = gcl(c,"userID");
	String transactionName = gcs(c,"transaction_name");
	Transaction.TRANSACTION_TYPE transactionType = 
			Transaction.TRANSACTION_TYPE.valueOf(gcs(c, "transaction_type"));
	long transactionAmount = gcl(c,"transaction_amount");
	Date transactionDate = DateUtility.getCalendarFromFormattedLong(
			gcl(c,"transaction_date")).getTime();
	String transactionComment = gcs(c,"transaction_comment");
	Date timestamp = DateUtility.getCalendarFromFormattedLong(
			gcl(c,"timestamp")).getTime();
	boolean rolledback = (gcl(c, "rolledback") == 1) ? true : false;
	Category[] categories = CategoriesDAO.getCategoriesForUser(db, userID);
	return new Transaction(transactionID,accountID,transactionName,transactionType,transactionAmount,
				transactionDate,transactionComment,timestamp,rolledback,categories);
  }

	/**
	 * returns a list of all accounts owned by a given user
	 * @param userID
	 * @return list of accounts owned by user
	 */
	public static Transaction[] getTransactionsForAccount(SQLiteDatabase db, long accountID) {
		
		Cursor c = db.rawQuery(
					"SELECT transactionID, T.accountID, userID, transaction_name, transaction_type, transaction_amount, " +
					"transaction_date, transaction_comment, timestamp, rolledback " +
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
	public static Transaction addTransactionToDB(SQLiteDatabase db, long accountID, String transactionName, Transaction.TRANSACTION_TYPE transactionType,
			long transactionAmount, Date transactionDate, String transactionComment, boolean rolledback) {
		ContentValues toInsert = new ContentValues();
		toInsert.put("accountID", accountID);
		toInsert.put("transaction_name", transactionName);
		toInsert.put("transaction_type", transactionType.name());
		toInsert.put("transaction_amount", transactionAmount);
		toInsert.put("transaction_date", DateUtility.formatDateAsLong(transactionDate));
		toInsert.put("transaction_comment", transactionComment);
		toInsert.put("rolledback", rolledback ? 1 : 0);
		long transactionID = db.insert("transactions", null, toInsert);
		Category[] categories = CategoriesDAO.getCategoriesForTransaction(db, transactionID);
		//Transaction thetrans = getTransactionWithID(db, transactionID);
		Transaction thetrans = new Transaction(transactionID, accountID, transactionName, transactionType, 
				transactionAmount, transactionDate, transactionComment, new Date(), rolledback, categories);
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
					"SELECT transactionID, T.accountID, userID, transaction_name, transaction_type, transaction_amount, " +
					"transaction_date, transaction_comment, timestamp, rolledback " +
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
					"SELECT transactionID, T.accountID, userID, transaction_name, transaction_type," +
					" transaction_amount, transaction_date, transaction_comment, timestamp, rolledback " +
					"	FROM transactions AS T JOIN Accounts AS A WHERE T.transactionID = ? AND T.accountID = A.accountID;", 
					new String[]{Long.toString(transactionID)});

		c.moveToFirst();
		Transaction out;
			try {
				out = cursorToTransaction(c, db);
			} catch (Exception e) {
				out = null;
				e.printStackTrace();
			}
		// make sure to close the cursor
		c.close();
		return out;
	}
	
	
}
