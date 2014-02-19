package programmateurs.models;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import programmateurs.beans.User;


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
 
  public static User cursorToUser(Cursor c) {
	long userID = c.getInt(0);
	String username = c.getString(1);
	String passhash = c.getString(2);
	String first = c.getString(3);
	String last = c.getString(4);
	String email = c.getString(5);
	return new User(userID, username, passhash, first, last, email);
  }
  
  public static User[] getUsers(SQLiteDatabase db) {
		Cursor c = db.rawQuery("SELECT * FROM users;",
				new String[]{});
		List<User> outL = new ArrayList<User>();

		c.moveToFirst();
		while (!c.isAfterLast()) {
			User user = cursorToUser(c);
			outL.add(user);
			c.moveToNext();
		}
		// make sure to close the cursor
		c.close();
		return outL.toArray(new User[0]);
  }
  
  public static User getUser(SQLiteDatabase db, long userID) {
	  Cursor c = db.rawQuery("SELECT * FROM users WHERE userID = ?;",
			  new String[]{Long.toString(userID)});
	  List<User> outL = new ArrayList<User>();

	  c.moveToFirst();
	  User user = cursorToUser(c);

	  return user;
  }

 
  public static boolean isUserInDB(SQLiteDatabase db, String username, String password) {
	  Cursor c = db.rawQuery("SELECT * FROM users WHERE username = ? AND passhash = ?", new String[]{username,password});
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

  public static void addUserToDB(SQLiteDatabase db, String username, String password) {
//	  String passHash = hashPassword(password);
	  ContentValues toInsert = new ContentValues();
	  toInsert.put("username", username);
	  toInsert.put("passhash", password);
	  db.insert("users", null, toInsert);
  }
 
  /**
   * updates the user matching the parameter user in the db with whatever
   * information the user parameter contains, returns the user read back from the database.
   * @param db
   * @param user
   * @return reading the results of the operation back from the db
   */
  public static User updateUser(SQLiteDatabase db, User user) {
	  ContentValues toSet = new ContentValues();
	  toSet.put("username", user.getUsername());
	  toSet.put("passhash", user.getPasshash());
	  toSet.put("first", user.getFirst());
	  toSet.put("last", user.getLast());
	  toSet.put("email", user.getEmail());
	  db.update("Users", toSet, "userid=?", new String[]{Long.toString(user.getUserID())});
	  return getUser(db, user.getUserID());
  }

  public static User addUserToDB(SQLiteDatabase db, String username, String password, String first, String last, String email) {
//	  String passHash = hashPassword(password);
	  ContentValues toInsert = new ContentValues();
	  toInsert.put("username", username);
	  toInsert.put("passhash", password);
	  toInsert.put("first", first);
	  toInsert.put("last", last);
	  toInsert.put("email", email);
	  long userid = db.insert("users", null, toInsert);
	  User user = new User(userid, username, password, first, last, email);
	  return user;
  }

} 