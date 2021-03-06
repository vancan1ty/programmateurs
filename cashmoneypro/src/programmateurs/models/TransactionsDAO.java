package programmateurs.models;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static programmateurs.util.DButil.longFromCursor;
import static programmateurs.util.DButil.stringFromCursor;
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
public final class TransactionsDAO {

    /**
     * checkstyle, can you see me? (O^o)
     */
    private TransactionsDAO() {

    }

    /**
     * the query used to build the transactions table in the db.
     */
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

    /**
     * converts a db cursor to a transaction.
     * 
     * @param c
     *            a cursor generated by an associated method
     * @param db
     *            an open db connection.
     * @return a Transaction object from the db
     * @throws ParseException
     *             if something goes awry
     */
    //CHECKSTYLE:OFF    Duplicates necessary for JQuery
    private static Transaction cursorToTransaction(final Cursor c,
            final SQLiteDatabase db) throws ParseException {
        long transactionID = longFromCursor(c, "transactionID");
        long accountID = longFromCursor(c, "accountID");
        long userID = longFromCursor(c, "userID");
        long categoryID = longFromCursor(c, "categoryID");
        String transactionName = stringFromCursor(c, "transaction_name");
        Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE
                .valueOf(stringFromCursor(c, "transaction_type"));
        long transactionAmount = longFromCursor(c, "transaction_amount");
        Date transactionDate = DateUtility.getCalendarFromFormattedLong(
                longFromCursor(c, "transaction_date")).getTime();
        String transactionComment = stringFromCursor(c, "transaction_comment");
        Date timestamp = DateUtility.getCalendarFromFormattedLong(
                longFromCursor(c, "timestamp")).getTime();
        long rolledbackL = longFromCursor(c, "rolledback");
        boolean rolledback = false;
        if (rolledbackL == 1) {
            rolledback = true;
        }

        Category category = null;
        String categoryName = stringFromCursor(c, "category_name");
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
     * returns a list of all transactions for a given account.
     * 
     * @param db
     *            an open db connection.
     * @param accountID
     *            the account id to use.
     * @return list of accounts owned by user
     */
    public static Transaction[] getTransactionsForAccount(
            final SQLiteDatabase db, final long accountID) {

        Cursor c = db
                .rawQuery(
                        "SELECT * "
                                + "FROM transactions AS T "
                                + "JOIN Accounts AS A ON T.accountID = ? AND T.accountID = A.accountID "
                                + "LEFT JOIN Categories AS C ON T.categoryID = C.categoryID;",
                        new String[] {Long.toString(accountID)});
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
    //CHECKSTYLE:ON

    // CHECKSTYLE:OFF this method needs a bunch of parameters sorry.
    // JQuery requires duplicate literal.
    /**
     * adds a transaction with the associated information to the DB, returns an
     * object representation of it.
     * 
     * @param db
     *            an open db connection
     * @param accountID
     *            the id of the account to add the transaction to
     * @param transactionName
     *            the name of the transaction
     * @param transactionType
     *            the type (deposit or withdraw currently).
     * @param transactionAmount
     *            the amount of the transaction.
     * @param transactionDate
     *            the date of the transaction.
     * @param transactionComment
     *            an optional comment on the transaction.
     * @param rolledback
     *            whether or not the transaction has been rolled back.
     * @param category
     *            the category of the transaction.
     * @return nah
     */
    public static Transaction addTransactionToDB(final SQLiteDatabase db,
            final long accountID, final String transactionName,
            final Transaction.TRANSACTION_TYPE transactionType,
            final long transactionAmount, final Date transactionDate,
            final String transactionComment, final boolean rolledback,
            final Category category) {
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
        Log.d("BERRY","before insert transaction amount: " + transactionAmount);
        Log.d("TransactionsDAO",
                "about to add w/ date: " + transactionDate.toString());
        toInsert.put("transaction_date",
                DateUtility.formatDateAsLong(transactionDate));
        toInsert.put("transaction_comment", transactionComment);
        Date now = new Date();
        toInsert.put("timestamp", DateUtility.formatDateAsLong(now));
        if (rolledback) {
            toInsert.put("rolledback", true);
        } else {
            toInsert.put("rolledback", false);
        }
        long transactionID = db.insert("transactions", null, toInsert);
        Transaction thetrans = getTransactionWithID(db, transactionID);
        // Transaction thetrans = new Transaction(transactionID,
        // accountID,
        // transactionName, transactionType,
        // transactionAmount, transactionDate, transactionComment,
        // transa,
        // rolledback, category);
        return thetrans;
    }
    // CHECKSTYLE:ON

    /**
     * returns a list of all transactions belonging to a certain user.
     * 
     * @param db
     *            db connection
     * @param userID
     *            nah
     * @return list of accounts owned by user
     * @author Pavel
     */
    public static Transaction[] getTransactionsForUser(final SQLiteDatabase db,
            final long userID) {
        Log.d("TransactionsDAO", "starting getTransactionsForUser");

        Cursor c = db
                .rawQuery(

                        "SELECT * "
                                + "FROM transactions AS T "
                                + "JOIN Accounts AS A ON A.userID = ? AND T.accountID = A.accountID "
                                + "LEFT JOIN Categories AS C ON T.categoryID = C.categoryID;",

                        new String[] {Long.toString(userID)});
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

    /**
     * gets the transaction associated with a given id.
     * 
     * @param db
     *            nah
     * @param transactionID
     *            nah
     * @return nah
     */
    public static Transaction getTransactionWithID(final SQLiteDatabase db,
            final long transactionID) {

        Cursor c = db
                .rawQuery(
                        "SELECT * "
                                + "FROM transactions AS T "
                                + "JOIN Accounts AS A ON T.transactionID = ? AND T.accountID = A.accountID "
                                + "LEFT JOIN Categories as C ON T.categoryID = C.categoryID;",
                        new String[] {Long.toString(transactionID)});

        c.moveToFirst();
        Transaction out;
        try {
            out = cursorToTransaction(c, db);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            out = null;
            e.printStackTrace();
        }
        
        // make sure to close the cursor
        c.close();
        return out;
    }

    /**
     * contains the number of cents in a dollar.
     */
    public static final double CENTS_IN_DOLLAR_DOUBLE = 100.0;

    /**
     * returns a category report.
     * 
     * @param db
     *            nah
     * @param dateStart
     *            nah
     * @param dateEnd
     *            nah
     * @param transactionType
     *            nah
     * @param userID
     *            nah
     * @return nah
     */
    //CHECKSTYLE:OFF
    public static String getCategoryReport(final SQLiteDatabase db,
            final Calendar dateStart, final Calendar dateEnd,
            final Transaction.TRANSACTION_TYPE transactionType,
            final long userID) {

        Cursor c = db
                .rawQuery(

                        " SELECT category_name, SUM(transaction_amount) As transaction_amount,"
                                + " 0 AS order_fac"
                                + " FROM Transactions AS T"
                                + " LEFT JOIN Categories as C ON T.categoryID = C.categoryID"
                                + " JOIN Accounts as A ON A.accountID = T.accountID"
                                + " WHERE transaction_date > ? AND transaction_date < ?"
                                + " AND transaction_type = ? AND A.userID = ?"
                                + " GROUP BY category_name"
                                + " UNION"
                                + " SELECT 'Total' AS category_name, SUM(transaction_amount) AS"
                                + " transaction_amount, 1 AS order_fac"
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
            out.append(String.format(
                    "%-15s %s\n",
                    stringFromCursor(c, "category_name"),
                    nf.format(longFromCursor(c, "transaction_amount")
                            / CENTS_IN_DOLLAR_DOUBLE)));

            c.moveToNext();
        }
        // make sure to close the cursor
        c.close();
        return out.toString();
    }


}
