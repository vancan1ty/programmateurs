package programmateurs.models;

import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.util.DButil;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * this class manages access to Accounts data.
 * 
 * @author vancan1ty
 * 
 */
public final class AccountsDAO {

    /**
     * dummy constructor.
     */
    private AccountsDAO() {

    }

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
     *            a db cursor generated by one of the relavant queries below.
     * @return an Account object
     */
    //CHECKSTYLE:OFF Duplication necessary for JQuery
    private static Account cursorToAccount(final Cursor cursor) {
        long accountID = cursor.getInt(0);
        long userID = cursor.getInt(1);
        Account.ACCOUNT_TYPE accountType = Account.ACCOUNT_TYPE.valueOf(DButil
                .stringFromCursor(cursor, "account_type"));
        String accountName = DButil.stringFromCursor(cursor, "account_name");
        double interestRate = DButil.doubleFromCursor(cursor, "interest_rate");
        Log.d("uponpullfromdb", Double.toString(interestRate));
        return new Account(accountID, userID, accountType, accountName,
                interestRate);
    }
    //CHECKSTYLE:ON
    
    /**
     * returns a list of all accounts owned by a given user.
     * 
     * @param db
     *            a reference to the database
     * @param userID
     *            the user id for the user.
     * @return list of accounts owned by user
     */
    public static Account[] getAccountsForUser(final SQLiteDatabase db,
            final long userID) {

        Cursor c = db.rawQuery("SELECT * FROM Accounts WHERE userid = ?;",
                new String[] {Long.toString(userID)});
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
     * gets an account with a given id.
     * 
     * @param db
     *            a database reference
     * @param accountID
     *            the id of the account
     * @return an account pulled from the db.
     */
    public static Account getAccountWithID(final SQLiteDatabase db,
            final long accountID) {

        Log.d("AccountsDAO", "db + " + db);
        Cursor c = db.rawQuery("SELECT * FROM Accounts WHERE accountid = ?;",
                new String[] {Long.toString(accountID)});

        c.moveToFirst();
        Account acct = cursorToAccount(c);
        // make sure to close the cursor
        c.close();
        return acct;
    }

    /**
     * adds an account with the associated information to the DB, returns an
     * object representation of it.
     * 
     * @param db
     *            an open db connection.
     * @param userID
     *            the id of the user in the db.
     * @param accountType
     *            the type of the account (from ACCOUNT_TYPE enum)
     * @param accountName
     *            the string name of the account.
     * @param interestRate
     *            the interest rate on the account.
     * @return an account object with the data that was just put in the db.
     */
    public static Account addAccountToDB(final SQLiteDatabase db,
            final long userID, final Account.ACCOUNT_TYPE accountType,
            final String accountName, final double interestRate) {
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
     * @param src an open db connection.
     * @param accountID the account to find the balance for.
     * @return balance of the account in cents.
     */
    public static long getBalance(final DataSourceInterface src,
            final long accountID) {
        long balance = 0;
        for (Transaction transaction : src.getTransactionsForAccount(accountID)) {
            TRANSACTION_TYPE type = transaction.getTransactionType();
            if ((type == TRANSACTION_TYPE.DEPOSIT || type == TRANSACTION_TYPE.REBALANCE)
                    && !transaction.isRolledback()) {
                balance += transaction.getTransactionAmount();
            } else if (type == TRANSACTION_TYPE.WITHDRAWAL
                    && !transaction.isRolledback()) {
                balance -= transaction.getTransactionAmount();
            }
        }
        return balance;
    }

    /**
     * Checks to see if a potential transaction would result in overdrawing from
     * an account.
     * 
     * @param src open db connection
     * @param accountID the id of the account to check.
     * @param amount The amount of the potential transaction in cents.
     * @param type The type of the potential transaction
     * @return true if transaction would result in overdrawing from account
     */
    public static boolean overdrawn(final DataSourceInterface src,
            final long accountID, final long amount,
            final Transaction.TRANSACTION_TYPE type) {
        if (type == Transaction.TRANSACTION_TYPE.WITHDRAWAL) {
            return (getBalance(src, accountID) - amount) < 0;
        } else {
            return false;
        }
    }
}
