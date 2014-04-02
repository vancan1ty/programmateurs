package programmateurs.models;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.util.DateUtility;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class manages transaction data in the database.
 * 
 * @author vancan1ty
 * 
 */
public class TransactionsDAO {

    // v1
    public static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE Transactions"
            + " (transactionID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " accountID INTEGER NOT NULL REFERENCES Accounts(accountID), "
            + " categoryID INTEGER REFERENCES Categories(categoryID), "
            + " transaction_name TEXT DEFAULT '',"
            + " transaction_type TEXT NOT NULL, "
            + " transaction_amount INTEGER NOT NULL,"
            + " transaction_date DATE, "
            + " transaction_comment TEXT DEFAULT '',"
            + " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + " rolledback BOOLEAN NOT NULL " + ");";

    private static long gcl(Cursor c, String columnName) {
        return c.getLong(c.getColumnIndexOrThrow(columnName));
    }

    private static String gcs(Cursor c, String columnName) {
        return c.getString(c.getColumnIndexOrThrow(columnName));
    }

    private static Transaction cursorToTransaction(Cursor c, SQLiteDatabase db)
            throws ParseException {
        long transactionID = gcl(c, "transactionID");
        long accountID = gcl(c, "accountID");
        long userID = gcl(c, "userID");
        long categoryID = gcl(c, "categoryID");
        String transactionName = gcs(c, "transaction_name");
        Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE
                .valueOf(gcs(c, "transaction_type"));
        long transactionAmount = gcl(c, "transaction_amount");
        Date transactionDate = DateUtility.getCalendarFromFormattedLong(
                gcl(c, "transaction_date")).getTime();
        String transactionComment = gcs(c, "transaction_comment");
        Date timestamp = DateUtility.getCalendarFromFormattedLong(
                gcl(c, "timestamp")).getTime();
        boolean rolledback = (gcl(c, "rolledback") == 1) ? true : false;

        Category category = null;
        String categoryName = gcs(c, "category_name");
        if (categoryName != null) {
            category = new Category(categoryID, userID, categoryName);
        }
        /*
         * int categoryNameIndex = c.getColumnIndex("category_name"); if
         * (categoryNameIndex != -1) { String categoryName =
         * c.getString(categoryNameIndex); category = new Category(categoryID,
         * userID, categoryName); }
         */

        return new Transaction(transactionID, accountID, transactionName,
                transactionType, transactionAmount, transactionDate,
                transactionComment, timestamp, rolledback, category);
    }

    /**
     * returns a list of all accounts owned by a given user
     * 
     * @param userID
     * @return list of accounts owned by user
     */
    public static Transaction[] getTransactionsForAccount(SQLiteDatabase db,
            long accountID) {

        Cursor c = db
                .rawQuery(
                        "SELECT * "
                                + "FROM transactions AS T "
                                + "JOIN Accounts AS A ON T.accountID = ? AND T.accountID = A.accountID "
                                + "LEFT JOIN Categories AS C ON T.categoryID = C.categoryID;",
                        new String[] { Long.toString(accountID) });
        List<Transaction> outL = new ArrayList<Transaction>();

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Transaction transaction;

            try {
                transaction = cursorToTransaction(c, db);
            } catch (ParseException e) {
                e.printStackTrace();
                transaction = null;
            }
            outL.add(transaction);
            c.moveToNext();
        }
        // make sure to close the cursor
        c.close();
        return outL.toArray(new Transaction[0]);
    }

    /**
     * adds a transaction with the associated information to the DB, returns an
     * object representation of it
     */
    public static Transaction addTransactionToDB(SQLiteDatabase db,
            long accountID, String transactionName,
            Transaction.TRANSACTION_TYPE transactionType,
            long transactionAmount, Date transactionDate,
            String transactionComment, boolean rolledback, Category category) {
        ContentValues toInsert = new ContentValues();
        toInsert.put("accountID", accountID);
        if (category != null) {
            toInsert.put("categoryID", category.getCategoryID());
        } else {
            toInsert.putNull("categoryID");
        }
        toInsert.put("transaction_name", transactionName);
        toInsert.put("transaction_type", transactionType.name());
        toInsert.put("transaction_amount", transactionAmount);
        Log.d("TransactionsDAO",
                "about to add w/ date: " + transactionDate.toString());
        toInsert.put("transaction_date",
                DateUtility.formatDateAsLong(transactionDate));
        toInsert.put("transaction_comment", transactionComment);
        toInsert.put("rolledback", rolledback ? 1 : 0);
        long transactionID = db.insert("transactions", null, toInsert);
        Transaction thetrans = getTransactionWithID(db, transactionID);
        // Transaction thetrans = new Transaction(transactionID, accountID,
        // transactionName, transactionType,
        // transactionAmount, transactionDate, transactionComment, transa,
        // rolledback, category);
        return thetrans;
    }

    /**
     * returns a list of all transactions belonging to a certain user
     * 
     * @param userID
     * @return list of accounts owned by user
     * @author Pavel
     */
    public static Transaction[] getTransactionsForUser(SQLiteDatabase db,
            long userID) {
        Log.d("TransactionsDAO", "starting getTransactionsForUser");

        Cursor c = db
                .rawQuery(

                        "SELECT * "
                                + "FROM transactions AS T "
                                + "JOIN Accounts AS A ON A.userID = ? AND T.accountID = A.accountID "
                                + "LEFT JOIN Categories AS C ON T.categoryID = C.categoryID;",

                        new String[] { Long.toString(userID) });
        List<Transaction> outL = new ArrayList<Transaction>();

        c.moveToFirst();
        Log.d("TransactionsDAO", "count: " + c.getCount());
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

        Log.d("TransactionsDAO", "outlen: " + outL.size());
        return outL.toArray(new Transaction[0]);
    }

    public static Transaction getTransactionWithID(SQLiteDatabase db,
            long transactionID) {

        Cursor c = db
                .rawQuery(
                        "SELECT * "
                                + "FROM transactions AS T "
                                + "JOIN Accounts AS A ON T.transactionID = ? AND T.accountID = A.accountID "
                                + "LEFT JOIN Categories as C ON T.categoryID = C.categoryID;",
                        new String[] { Long.toString(transactionID) });

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

    public static String getCategoryReport(SQLiteDatabase db,
            Calendar dateStart, Calendar dateEnd,
            Transaction.TRANSACTION_TYPE transactionType, long userID) {

        Cursor c = db
                .rawQuery(

                        " SELECT category_name, SUM(transaction_amount) As transaction_amount, 0 AS order_fac"
                                + " FROM Transactions AS T"
                                + " LEFT JOIN Categories as C ON T.categoryID = C.categoryID"
                                + " JOIN Accounts as A ON A.accountID = T.accountID"
                                + " WHERE transaction_date > ? AND transaction_date < ?"
                                + " AND transaction_type = ? AND A.userID = ?"
                                + " GROUP BY category_name"
                                + " UNION"
                                + " SELECT 'Total' AS category_name, SUM(transaction_amount) AS transaction_amount, 1 AS order_fac"
                                + " FROM transactions AS T"
                                + " JOIN Accounts as A ON A.accountID = T.accountID"
                                + " WHERE transaction_date > ? AND transaction_date < ? AND userID = ?"
                                + " AND transaction_type = ?"
                                + " ORDER BY order_fac" + ";",
                        new String[] {
                                Long.toString(DateUtility
                                        .formatCalendarAsLong(dateStart)),
                                Long.toString(DateUtility
                                        .formatCalendarAsLong(dateEnd)),
                                transactionType.name(),
                                Long.toString(userID),
                                Long.toString(DateUtility
                                        .formatCalendarAsLong(dateStart)),
                                Long.toString(DateUtility
                                        .formatCalendarAsLong(dateEnd)),
                                Long.toString(userID), transactionType.name() });

        c.moveToFirst();

        StringBuilder out = new StringBuilder();
        out.append(String.format("%-15s %s\n", "Category", "Amount"));
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        while (!c.isAfterLast()) {
            out.append(String.format("%-15s %s\n", gcs(c, "category_name"),
                    nf.format(gcl(c, "transaction_amount") / 100.0)));

            c.moveToNext();
        }
        // make sure to close the cursor
        c.close();
        return out.toString();
    }

}
