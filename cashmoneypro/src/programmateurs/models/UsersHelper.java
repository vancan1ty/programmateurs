package programmateurs.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts.Intents.Insert;
import android.util.Log;

public class UsersHelper extends SQLiteOpenHelper {

//  public static final String TABLE_COMMENTS = "comments";
//  public static final String COLUMN_ID = "_id";
//  public static final String COLUMN_COMMENT = "comment";

  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
//  private static final String DATABASE_CREATE = "create table "
//      + TABLE_COMMENTS + "(" + COLUMN_ID
//      + " integer primary key autoincrement, " + COLUMN_COMMENT
//      + " text not null);";
  
  public static final String TABLE_USERS = "users";
  
  private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
		  + "(userID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " username TEXT NOT NULL, "
		  + " passhash TEXT NOT NULL);";

  public UsersHelper(Context context) {
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
    Log.w(UsersHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    onCreate(db);
  }

} 