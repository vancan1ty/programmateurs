package programmateurs.models;

import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccountsDAO {

	//v1
  public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE Accounts"
		  + " (accountID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " userID INTEGER NOT NULL, "
		  + " account_type TEXT NOT NULL, "
		  + " account_name TEXT NOT NULL, "
		  + " interest_rate INTEGER NOT NULL, "
		  + " FOREIGN KEY(userID) REFERENCES Users(userID) "
		  + ");";

  private static Account cursorToAccount(Cursor cursor) {
	long accountID = cursor.getInt(0);
	long userID = cursor.getInt(1);
	Account.ACCOUNT_TYPE accountType = Account.ACCOUNT_TYPE.valueOf(cursor.getString(2));
	String accountName = cursor.getString(3);
	int interestRate = cursor.getInt(4);
	return new Account(accountID, userID, accountType, accountName, interestRate);
  }

	/**
	 * returns a list of all accounts owned by a given user
	 * @param userID
	 * @return list of accounts owned by user
	 */
	public static Account[] getAccountsForUser(SQLiteDatabase db, long userID) {
		
		Cursor c = db.rawQuery("SELECT * FROM users WHERE userid = ?;", 
				new String[]{Long.toString(userID)});
		List<Account> outL = new ArrayList<Account>();

		c.moveToFirst();
		while (!c.isAfterLast()) {
			Account acct = cursorToAccount(c);
			outL.add(acct);
			c.moveToNext();
		}
		// make sure to close the cursor
		c.close();
		return outL.toArray(new Account[0]);
	}

	/**
	 * adds an account with the associated information to the DB, returns an object representation of it
	 * @param userID
	 * @param accountType
	 * @param accountName
	 * @param interestRate
	 * @return
	 */
	public static Account addAccountToDB(SQLiteDatabase db, long userID, Account.ACCOUNT_TYPE accountType,
			String accountName, int interestRate) {
		ContentValues toInsert = new ContentValues();
		toInsert.put("userID", userID);
		toInsert.put("account_type", accountType.name());
		toInsert.put("account_name", accountName);
		toInsert.put("interest_rate", interestRate);
		long accountID = db.insert("accounts", null, toInsert);
		Account acct = new Account(accountID,userID,accountType,accountName,interestRate);
		return acct;
	}
}