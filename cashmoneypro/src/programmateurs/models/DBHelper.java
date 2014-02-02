package programmateurs.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts.Intents.Insert;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

//  public static final String TABLE_COMMENTS = "comments";
//  public static final String COLUMN_ID = "_id";
//  public static final String COLUMN_COMMENT = "comment";

  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
//  private static final String DATABASE_CREATE = "create table "
//      + TABLE_COMMENTS + "(" + COLUMN_ID
//      + " integer primary key autoincrement, " + COLUMN_COMMENT
//      + " text not null);";
  
  public static final String TABLE_USERS = "Users";
  public static final String TABLE_ACCOUNTTYPES = "AccountTypes";
  public static final String TABLE_TRANSACTIONTYPES = "TransactionTypes";
  public static final String TABLE_ACCOUNTS = "Accounts";
  public static final String TABLE_TRANSACTIONS = "Transactions";
  public static final String TABLE_CATEGORIES = "Categories";
  public static final String TABLE_TRANSACTIONS_CATEGORIES = "Transactions_Categories";


  
  private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
		  + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " username TEXT NOT NULL, "
		  + " passhash TEXT NOT NULL);";
  
  private static final String CREATE_ACCOUNTTYPES_TABLE = "CREATE TABLE " + TABLE_ACCOUNTTYPES
		  + " (accounttypeID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " accounttype_name TEXT NOT NULL);";
  
  private static final String CREATE_TRANSACTIONTYPES_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONTYPES
		  + " (transactiontypeID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " transactiontype_name TEXT NOT NULL);";

  private static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS
		  + " (accountID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " userID INTEGER NOT NULL, "
		  + " accounttypeID INTEGER NOT NULL, "
		  + " account_name TEXT NOT NULL, "
		  + " interest_rate INTEGER NOT NULL, "
		  + " FOREIGN KEY(userID) REFERENCES Users(userID), "
		  + " FOREIGN KEY(accounttypeID) REFERENCES AccountTypes(accounttypeID)"
		  + ");";
  
  private static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS
		  + " (transactionID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " accountID INTEGER NOT NULL, "
		  + " transactiontypeID INTEGER NOT NULL, "
		  + " transaction_date DATE, "
		  + " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
		  + " rolledback BOOLEAN NOT NULL, "
		  + " FOREIGN KEY(accountID) REFERENCES Accounts(accountID), "
		  + " FOREIGN KEY(transactiontypeID) REFERENCES TransactionsTypes(transactiontypeID) "
		  + ");";
  
  private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES
		  + " (categoryID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " category_name TEXT NOT NULL"
		  + ");";
  
  private static final String CREATE_TRANSACTIONS_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS_CATEGORIES
		  + " (transactions_categoriesID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " transactionID INTEGER NOT NULL, "
		  + " categoryID INTEGER NOT NULL, "
		  + " FOREIGN KEY(transactionID) REFERENCES Transactions(transactionID), "
		  + " FOREIGN KEY(categoryID) REFERENCES Categories(categoryID)"
		  + ");";
  



  public DBHelper(Context context) {
    super(context, "users", null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(CREATE_USERS_TABLE);
    ContentValues toInsert = new ContentValues();
    toInsert.put("username", "admin");
    toInsert.put("passhash", "admin123");
    database.insert("users", null, toInsert);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(DBHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    onCreate(db);
  }

} 