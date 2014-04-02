package programmateurs.models;

import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * this class manages access to Accounts data.
 * 
 * @author vancan1ty
 * 
 */
public class AccountsDAO {

    /**
     * this is the query that builds the accounts table.
     */
    public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE Accounts"
            + " (accountID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " userID INTEGER NOT NULL, " + " account_type TEXT NOT NULL, "
            + " account_name TEXT NOT NULL, "
            + " interest_rate DOUBLE NOT NULL, "
            + " FOREIGN KEY(userID) REFERENCES Users(userID) " + ");";

    /**
     * converts a db cursor to an Account object.
     * 
     * @param cursor
     * @return
     */
    private static Account cursorToAccount(Cursor cursor) {
        long accountID = cursor.getInt(0);
        long userID = cursor.getInt(1);
        Account.ACCOUNT_TYPE accountType = Account.ACCOUNT_TYPE.valueOf(cursor
                .getString(2));
        String accountName = cursor.getString(3);
        double interestRate = cursor.getDouble(4);
        Log.d("uponpullfromdb", Double.toString(interestRate));
        return new Account(accountID, userID, accountType, accountName,
                interestRate);
    }

    /**
     * returns a list of all accounts owned by a given user
     * 
     * @param db
     *            a reference to the database
     * @param userID
     * @return list of accounts owned by user
     */
    public static Account[] getAccountsForUser(final SQLiteDatabase db,
            long userID) {

        Log.d("AccountsDAO", "db + " + db);
        Cursor c = db.rawQuery("SELECT * FROM Accounts WHERE userid = ?;",
                new String[] { Long.toString(userID) });
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

    public static Account getAccountWithID(SQLiteDatabase db, long accountID) {

        Log.d("AccountsDAO", "db + " + db);
        Cursor c = db.rawQuery("SELECT * FROM Accounts WHERE accountid = ?;",
                new String[] { Long.toString(accountID) });

        c.moveToFirst();
        Account acct = cursorToAccount(c);
        // make sure to close the cursor
        c.close();
        return acct;
    }

    /**
     * adds an account with the associated information to the DB, returns an
     * object representation of it
     * 
     * @param userID
     * @param accountType
     * @param accountName
     * @param interestRate
     * @return
     */
    public static Account addAccountToDB(SQLiteDatabase db, long userID,
            Account.ACCOUNT_TYPE accountType, String accountName,
            double interestRate) {
        ContentValues toInsert = new ContentValues();
        toInsert.put("userID", userID);
        toInsert.put("account_type", accountType.name());
        toInsert.put("account_name", accountName);
        toInsert.put("interest_rate", interestRate);
        Log.d("uponaddaccount", Double.toString(interestRate));
        long accountID = db.insert("accounts", null, toInsert);
        Account acct = new Account(accountID, userID, accountType, accountName,
                interestRate);
        return acct;
    }

    /**
     * Calculates the balance for an account.
     * 
     * @param context
     *            The view from which the method is called
     * @return balance of the account
     */
    public static double getBalance(DataSourceInterface src, long accountID) {
        double balance = 0;
        for (Transaction transaction : src.getTransactionsForAccount(accountID)) {
            TRANSACTION_TYPE type = transaction.getTransactionType();
            if ((type == TRANSACTION_TYPE.DEPOSIT || type == TRANSACTION_TYPE.REBALANCE)
                    && transaction.isRolledback() == false) {
                balance += transaction.getTransactionAmountAsDouble();
            } else if (type == TRANSACTION_TYPE.WITHDRAWAL
                    && transaction.isRolledback() == false) {
                balance -= transaction.getTransactionAmountAsDouble();
            }
        }
        return balance;
    }

    /**
     * Checks to see if a potential transaction would result in overdrawing from
     * an account.
     * 
     * @param context
     *            The view from which the method is called
     * @param amount
     *            The amount of the potential transaction
     * @param type
     *            The type of the potential transaction
     * @return true if transaction would result in overdrawing from account
     */
    public static boolean overdrawn(DataSourceInterface src, long accountID,
            Double amount, Transaction.TRANSACTION_TYPE type) {
        if (type == Transaction.TRANSACTION_TYPE.WITHDRAWAL) {
            return (getBalance(src, accountID) - amount) < 0;
        } else {
            return false;
        }
    }
}
