package programmateurs.models;

import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.Category;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoriesDAO {

    // v1
    public static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE Categories"
            + " (categoryID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " userID INTEGER,"
            + " category_name TEXT NOT NULL,"
            + " FOREIGN KEY(userID) REFERENCES Users(userID)" + ");";

    // NO LONGER NEEDED BECAUSE ONLY ONE CATEGORY PER TRANSACTION
    /*
     * public static final String CREATE_TRANSACTIONS_CATEGORIES_TABLE =
     * "CREATE TABLE Transactions_Categories" +
     * " (transactions_categoriesID INTEGER PRIMARY KEY AUTOINCREMENT, " +
     * " transactionID INTEGER NOT NULL, " + " categoryID INTEGER NOT NULL, " +
     * " FOREIGN KEY(transactionID) REFERENCES Transactions(transactionID), " +
     * " FOREIGN KEY(categoryID) REFERENCES Categories(categoryID)" + ");";
     */

    private static Category cursorToCategory(Cursor cursor) {
        long categoryID = cursor.getInt(0);
        long userID = cursor.getInt(1);
        String category_name = cursor.getString(2);

        return new Category(categoryID, userID, category_name);
    }

    /**
     * returns a list of all categories owned by a given user
     * 
     * @param userID
     * @return list of categories owned by user
     */
    public static Category[] getCategoriesForUser(SQLiteDatabase db, long userID) {

        Cursor c = db.rawQuery("SELECT * FROM categories WHERE userid = ?;",
                new String[] { Long.toString(userID) });
        List<Category> outL = new ArrayList<Category>();

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Category cat = cursorToCategory(c);
            outL.add(cat);
            c.moveToNext();
        }
        // make sure to close the cursor
        c.close();
        return outL.toArray(new Category[0]);
    }

    public static Category[] getCategoriesForTransaction(SQLiteDatabase db,
            long transactionID) {

        Cursor c = db.rawQuery(
                "SELECT C.categoryID, C.userID, C.category_name "
                        + "	FROM Transactions as T "
                        + "	JOIN Transactions_Categories as TC "
                        + "   JOIN Categories as C "
                        + "	WHERE T.transactionID = ?"
                        + "	AND TC.transactionID = T.transactionID"
                        + "	AND TC.categoryID = C.categoryID;",
                new String[] { Long.toString(transactionID) });
        List<Category> outL = new ArrayList<Category>();

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Category cat = cursorToCategory(c);
            outL.add(cat);
            c.moveToNext();
        }
        // make sure to close the cursor
        c.close();
        return outL.toArray(new Category[0]);
    }

    /**
     * adds a category with the associated information to the DB, returns an
     * object representation of it
     */
    public static Category addCategoryForDB(SQLiteDatabase db, long userID,
            String categoryName) {
        ContentValues toInsert = new ContentValues();
        toInsert.put("userID", userID);
        toInsert.put("category_name", categoryName);
        long categoryID = db.insert("categories", null, toInsert);
        Category cat = new Category(categoryID, userID, categoryName);
        return cat;
    }
}
