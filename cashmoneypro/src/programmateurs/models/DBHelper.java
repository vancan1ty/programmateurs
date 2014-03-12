package programmateurs.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts.Intents.Insert;
import android.util.Log;

/**
 * This class manages creating and updating the database schema on your android device.
 * 
 * Note: Sqlite "Integer" corresponds to long in java, apparently.
 * 
 * @author vancan1ty
 *
 */
public class DBHelper extends SQLiteOpenHelper {

//  public static final String TABLE_COMMENTS = "comments";
//  public static final String COLUMN_ID = "_id";
//  public static final String COLUMN_COMMENT = "comment";

  private static final int DATABASE_VERSION = 18;

  public DBHelper(Context context) {
    super(context, "programmateurs", null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(UsersDAO.CREATE_USERS_TABLE);
    database.execSQL(AccountsDAO.CREATE_ACCOUNTS_TABLE);
    database.execSQL(CategoriesDAO.CREATE_CATEGORIES_TABLE);
    database.execSQL(TransactionsDAO.CREATE_TRANSACTIONS_TABLE);
    ContentValues toInsert = new ContentValues();
    toInsert.put("username", "admin");
    toInsert.put("passhash", "pass123");
    database.insert("users", null, toInsert);
    
    ContentValues toInsert2 = new ContentValues();
    toInsert2.put("username", "test");
    toInsert2.put("passhash", "test");
    toInsert2.put("first", "tfirst");
    toInsert2.put("last", "tlast");
    toInsert2.put("email", "test@test.com");
    database.insert("users", null, toInsert2);
    
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(DBHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS Transactions_Categories");
    db.execSQL("DROP TABLE IF EXISTS Categories;" );
    db.execSQL("DROP TABLE IF EXISTS Transactions;" );
    db.execSQL("DROP TABLE IF EXISTS Accounts;" );
    db.execSQL("DROP TABLE IF EXISTS Users;" );
    onCreate(db);
  }

} 