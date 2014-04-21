package programmateurs.models;

import java.util.Calendar;
import java.util.Date;

import programmateurs.beans.Account;
import programmateurs.beans.Account.ACCOUNT_TYPE;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Usage of RealDataSource
 * 
 * **IMPORTANT, READ THESE DIRECTIONS** in any activity that uses RealDataSource
 * put a call to open() in onResume and a call to close() in onPause.
 * RealDataSource will manage the DB connection otherwise. thanks!
 * 
 * @author vancan1ty
 * 
 */
public class RealDataSource implements DataSourceInterface {

    /* ********INSTANCE VARS, AND SCAFFOLDING************** */

    // Database fields
    /**
     * the database reference.
     */
    private SQLiteDatabase db;
    /**
     * the helper class that helps manage and upgrade the db.
     */
    private DBHelper dbHelper;

    /**
     * build the datasource from the android context.
     * 
     * @param context
     *            the android context for the application.
     */
    public RealDataSource(final Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public final void open() {
        db = dbHelper.getWritableDatabase();
        Log.d("RealDataSource", "opened db: " + db);
    }

    @Override
    public final void close() {
        db.close();
    }
    
    public SQLiteDatabase getDB() {
        return this.db;
    }

    /* ******** Operations on USERS ************** */

    @Override
    public final User[] getUsers() {
        return UsersDAO.getUsers(db);
    }

    @Override
    public final User getUser(final String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public final boolean isUserInDB(final String username, final String password) {
        return UsersDAO.isUserInDB(db, username, password);
    }

    @Override
    public final User updateUser(final User user) {
        return UsersDAO.updateUser(db, user);
    }

    @Override
    public final Account getAccountWithID(final long accountID) {
        return AccountsDAO.getAccountWithID(db, accountID);

    }

    @Override
    public final Account[] getAccountsForUser(final long userID) {
        return AccountsDAO.getAccountsForUser(db, userID);
    }

    @Override
    public final Category[] getCategoriesForUser(final long userID) {
        return CategoriesDAO.getCategoriesForUser(db, userID);
    }

    @Override
    public final Transaction[] getTransactionsForAccount(final long accountID) {
        return TransactionsDAO.getTransactionsForAccount(db, accountID);
    }

    @Override
    public final Transaction[] getTransactionsForUser(final long userID) {
        return TransactionsDAO.getTransactionsForUser(db, userID);
    }

    @Override
    public final User addUserToDB(final String username, final String passhash,
            final String first, final String last, final String email) {
        return UsersDAO.addUserToDB(db, username, passhash, first, last, email);
    }


    @Override
    public final Account addAccountToDB(final long userID,
            final ACCOUNT_TYPE accountType, final String accountName,
            final double interestRate) {
        return AccountsDAO.addAccountToDB(db, userID, accountType, accountName,
                interestRate);
    }

    // CHECKSTYLE:OFF I need more than 7 parameters.
    @Override
    public final Transaction addTransactionToDB(final long accountID,
            final String transactionName,
            final TRANSACTION_TYPE transactionType,
            final long transactionAmount, final Date transactionDate,
            final String transactionComment, final boolean rolledback,
            final Category category) {
        // CHECKSTYLE:ON
        return TransactionsDAO.addTransactionToDB(db, accountID,
                transactionName, transactionType, transactionAmount,
                transactionDate, transactionComment, rolledback, category);
    }

    /**
     * returns a "category report" for the app, as specified in the 2340 app
     * directions.
     * 
     * @param dateStart
     *            the start date for the report.
     * @param dateEnd
     *            the end date.
     * @param type
     *            the type of the report (either spending category or income
     *            source)
     * @param userID
     *            the id of the user to run the report on
     * @return a string representing the category report.
     */
    public final String getCategoryReport(final Calendar dateStart,
            final Calendar dateEnd, final Transaction.TRANSACTION_TYPE type,
            final long userID) {
        return TransactionsDAO.getCategoryReport(db, dateStart, dateEnd, type,
                userID);
    }

    @Override
    public final Category addCategoryToDB(final long userID,
            final String categoryName) {
        return CategoriesDAO.addCategoryToDB(db, userID, categoryName);
    }

    /**
     * clears out the database.
     */
    public final void deleteAllFromDB() {
        db.execSQL("delete from transactions");
        db.execSQL("delete from categories");
        db.execSQL("delete from accounts");
        db.execSQL("delete from users");
    }
    
    public Transaction getTransactionWithID(long id) {
       return TransactionsDAO.getTransactionWithID(db, id) ;
    }
    
    public Category getCategoryWithID(long id) {
        return CategoriesDAO.getCategoryWithID(db, id);
    }

}
