package programmateurs.models;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsersDAO {

  public static final String CREATE_USERS_TABLE = "CREATE TABLE Users"
		  + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, "
		  + " username TEXT NOT NULL, "
		  + " passhash TEXT NOT NULL,"
		  + " first TEXT DEFAULT '',"
		  + " last TEXT DEFAULT '',"
		  + " email TEXT DEFAULT ''"
		  + ");";
 

  // Database fields
  private SQLiteDatabase database;
  private DBHelper dbHelper;

  public UsersDAO(Context context) {
    dbHelper = new DBHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }
  
  public boolean isUserInDB(String username, String password) {
	  Cursor c = database.rawQuery("SELECT * FROM users WHERE username = ? AND passhash = ?", new String[]{username,password});
	  if (c.getCount()==1) {
		  return true;
	  } else {
		  return false;
	  }
  }
  
//  public String hashPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
//	  byte[] salt = new byte[16]; // no salt for now
//	  KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
//	  SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//	  byte[] hash = f.generateSecret(spec).getEncoded();
//	  return new BigInteger(1, hash).toString(16);
//  }
//  

  public void addUserToDB(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
//	  String passHash = hashPassword(password);
	  ContentValues toInsert = new ContentValues();
	  toInsert.put("username", username);
	  toInsert.put("passhash", password);
	  database.insert("users", null, toInsert);
  }

  public void addUserToDB(String username, String password, String first, String last, String email) throws InvalidKeySpecException, NoSuchAlgorithmException {
//	  String passHash = hashPassword(password);
	  ContentValues toInsert = new ContentValues();
	  toInsert.put("username", username);
	  toInsert.put("passhash", password);
	  toInsert.put("first", first);
	  toInsert.put("last", last);
	  toInsert.put("email", email);
	  database.insert("users", null, toInsert);
  }




} 