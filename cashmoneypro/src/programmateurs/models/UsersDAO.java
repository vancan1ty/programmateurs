package programmateurs.models;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static programmateurs.util.DButil.longFromCursor;
import static programmateurs.util.DButil.stringFromCursor;
import programmateurs.beans.User;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * manages access to user data in the db.
 * 
 * @author vancan1ty
 * 
 */
public final class UsersDAO {


    /**
     * sql query which creates the users table in the database.
     */
    public static final String CREATE_USERS_TABLE = "CREATE TABLE Users"
            + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " username TEXT NOT NULL COLLATE NOCASE, "
            + " passhash TEXT NOT NULL," + " first TEXT DEFAULT '',"
            + " last TEXT DEFAULT ''," + " email TEXT DEFAULT ''" + ");";

    /**
     * converts a db cursor to a user.
     * 
     * @param c the properly generated db cursor.
     * @return a User object extracted from the cursor.
     */
    public static User cursorToUser(final Cursor c) {
        long userID = longFromCursor(c, "userID");
        String username = stringFromCursor(c, "username");
        String passhash = stringFromCursor(c, "passhash");
        String first = stringFromCursor(c, "first");
        String last = stringFromCursor(c, "last");
        String email = stringFromCursor(c, "email");
        return new User(userID, username, passhash, first, last, email);
    }

    /**
     * gets all the users in the db.
     * 
     * @param db an open db connection.
     * @return an array of all users present in the database.
     */
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

    /**
     * gets a user with a given userID.
     * 
     * @param db an open db connection.
     * @param userID the userID to match.
     * @return the User object associated with the given userID
     */
    public static User getUser(final SQLiteDatabase db, final long userID) {
        Cursor c = db.rawQuery("SELECT * FROM users WHERE userID = ?;",
                new String[] {Long.toString(userID)});
        // List<User> outL = new ArrayList<User>();

        c.moveToFirst();
        User user = cursorToUser(c);

        return user;
    }

    /**
     * Finds whether a User exists already in the database
     * 
     * @param db the database
     * @param username the name of the user
     * @param password the user's password
     * @return a boolean which specifies whether the user is in the database
     */
    public static boolean isUserInDB(final SQLiteDatabase db,
            final String username, final String password) {
        Cursor c;
        c = db.rawQuery(
                "SELECT * FROM users WHERE username = ? AND passhash = ?",
                new String[] {username, hashPassword(password)});
        return c.getCount() == 1;
    }

    // public String hashPassword(String password) throws
    // InvalidKeySpecException, NoSuchAlgorithmException {
    // byte[] salt = new byte[16]; // no salt for now
    // KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536,
    // 128);
    // SecretKeyFactory f =
    // SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    // byte[] hash = f.generateSecret(spec).getEncoded();
    // return new BigInteger(1, hash).toString(16);
    // }
    //

    /**
     * not the best way to do hashing but it's better than nothing.
     */
    public static final String HASH = "ludicrousHash";

    /**
     * hashes a password.
     * 
     * @param password password in plaintext form
     * @return the hashed password
     */
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
            md.update((password + HASH).getBytes("UTF-8")); // Change
            // this
            // to
            // "UTF-16"
            // if
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


    /**
     * updates the user matching the parameter user in the db with whatever
     * information the user parameter contains, returns the user read back from
     * the database.
     * 
     * @param db an open db connection.
     * @param user the user to update
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
                new String[] {Long.toString(user.getUserID())});
        return getUser(db, user.getUserID());
    }

    /**
     * adds a user to the db. also takes care of any other housework that
     * must be done when creating a new user (creating initial categories,
     * etc...).
     * 
     * @param db an open db connection.
     * @param username the username for the user.
     * @param password the password for the user.
     * @param first the user's first name.
     * @param last the user's last name.
     * @param email the user's email address.
     * @return a user object like the one created in the database.
     */
    public static User addUserToDB(final SQLiteDatabase db,
            final String username, final String password, final String first,
            final String last, final String email) {
        // String passHash = hashPassword(password);
        ContentValues toInsert = new ContentValues();
        toInsert.put("username", username);
        toInsert.put("passhash", hashPassword(password));
        toInsert.put("first", first);
        toInsert.put("last", last);
        toInsert.put("email", email);
        long userID = db.insert("users", null, toInsert);

        CategoriesDAO.initializeCategoriesForNewUser(db, userID);

        /*
         * defensive programming.
         * we need to actually be able to retrieve the user from the db for this
         * to have worked properly!
         */
        User user = UsersDAO.getUser(db, userID); //

        return user;
    }

}
