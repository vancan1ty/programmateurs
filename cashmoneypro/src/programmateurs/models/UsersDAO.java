package programmateurs.models;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.User;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersDAO {

    public static final String CREATE_USERS_TABLE = "CREATE TABLE Users"
            + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " username TEXT NOT NULL COLLATE NOCASE, "
            + " passhash TEXT NOT NULL," + " first TEXT DEFAULT '',"
            + " last TEXT DEFAULT ''," + " email TEXT DEFAULT ''" + ");";

    public static User cursorToUser(final Cursor c) {
        long userID = c.getInt(0);
        String username = c.getString(1);
        String passhash = c.getString(2);
        String first = c.getString(3);
        String last = c.getString(4);
        String email = c.getString(5);
        return new User(userID, username, passhash, first, last, email);
    }

    public static User[] getUsers(final SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM users;", new String[] {});
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

    public static User getUser(final SQLiteDatabase db, final long userID) {
        Cursor c = db.rawQuery("SELECT * FROM users WHERE userID = ?;",
                new String[] {Long.toString(userID) });
        List<User> outL = new ArrayList<User>();

        c.moveToFirst();
        User user = cursorToUser(c);

        return user;
    }

    public static boolean isUserInDB(final SQLiteDatabase db, final String username,
            final String password) {
        Cursor c;
        c = db.rawQuery(
                "SELECT * FROM users WHERE username = ? AND passhash = ?",
                new String[] {username, hashPassword(password) });
        return c.getCount() == 1;
    }

    // public String hashPassword(String password) throws
    // InvalidKeySpecException, NoSuchAlgorithmException {
    // byte[] salt = new byte[16]; // no salt for now
    // KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536,
    // 128);
    // SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    // byte[] hash = f.generateSecret(spec).getEncoded();
    // return new BigInteger(1, hash).toString(16);
    // }
    //

    public static void addUserToDB(final SQLiteDatabase db, final String username,
            final String password) {
        // String passHash = hashPassword(password);
        ContentValues toInsert = new ContentValues();
        toInsert.put("username", username);
        toInsert.put("passhash", hashPassword(password));
        db.insert("users", null, toInsert);
    }

    public static final String HASH = "ludicrousHash";

    public static String hashPassword(final String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "nosuchalgorithm";
        }
        try {
            md.update((password + HASH).getBytes("UTF-8")); // Change this to
                                                            // "UTF-16" if
                                                            // needed
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "unsupportedencoding";
        }
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String output = bigInt.toString(16);
        return output;
    }

    public static boolean passwordEquals(final String password, final String passhash)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String npasshash = hashPassword(password);
        return passhash.equals(npasshash);
    }

    /**
     * updates the user matching the parameter user in the db with whatever
     * information the user parameter contains, returns the user read back from
     * the database.
     * @param db
     * @param user
     * @return reading the results of the operation back from the db
     */
    public static User updateUser(final SQLiteDatabase db, final User user) {
        ContentValues toSet = new ContentValues();
        toSet.put("username", user.getUsername());
        toSet.put("passhash", user.getPasshash());
        toSet.put("first", user.getFirst());
        toSet.put("last", user.getLast());
        toSet.put("email", user.getEmail());
        db.update("Users", toSet, "userid=?",
                new String[] {Long.toString(user.getUserID()) });
        return getUser(db, user.getUserID());
    }

    public static User addUserToDB(final SQLiteDatabase db, final String username,
            final String password, final String first, final String last, final String email) {
        // String passHash = hashPassword(password);
        ContentValues toInsert = new ContentValues();
        toInsert.put("username", username);
        toInsert.put("passhash", hashPassword(password));
        toInsert.put("first", first);
        toInsert.put("last", last);
        toInsert.put("email", email);
        long userid = db.insert("users", null, toInsert);
        User user = new User(userid, username, password, first, last, email);
        return user;
    }

}
