package programmateurs.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class manages creating and updating the database schema on your android
 * device.
 * 
 * Note: Sqlite "Integer" corresponds to long in java, apparently.
 * 
 * @author vancan1ty
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * this tells android the version of the database. when this number changes,
     * android rebuilds the database on the device using the methods in this
     * class.
     */
    private static final int DATABASE_VERSION = 22;

    /**
     * initialize the DBHelper using the android context.
     * 
     * @param context
     *            android context instance
     */
    public DBHelper(final Context context) {
        super(context, "programmateurs", null, DATABASE_VERSION);
    }
    
    @Override
    public final void onCreate(final SQLiteDatabase database) {
        database.execSQL(UsersDAO.CREATE_USERS_TABLE);
        database.execSQL(AccountsDAO.CREATE_ACCOUNTS_TABLE);
        database.execSQL(CategoriesDAO.CREATE_CATEGORIES_TABLE);
        database.execSQL(TransactionsDAO.CREATE_TRANSACTIONS_TABLE);
        
        //add initial users to the db.
        UsersDAO.addUserToDB(database, "admin", "pass123", "admin", "admin", 
                "admin@programmateurs.awesome");

        UsersDAO.addUserToDB(database, "test", "test", "tfirst", "tlast", "test@test.com");

    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Transactions_Categories");
        db.execSQL("DROP TABLE IF EXISTS Categories;");
        db.execSQL("DROP TABLE IF EXISTS Transactions;");
        db.execSQL("DROP TABLE IF EXISTS Accounts;");
        db.execSQL("DROP TABLE IF EXISTS Users;");
        onCreate(db);
    }

}
