package programmateurs.util;

import android.database.Cursor;

/**
 * This class contains utility methods for interacting with the database.
 * 
 * @author vancan1ty
 */
public final class DButil {

    /**
     * don't allow instantiation.
     */
    private DButil() {

    }

    /**
     * returns a "long" value associated with the given column name. convenience
     * method.
     * 
     * @param c
     *            the db cursor.
     * @param columnName
     *            the name of the column which contains a long.
     * @return the long parsed from the db field.
     */
    public static long longFromCursor(final Cursor c, final String columnName) {
        return c.getLong(c.getColumnIndexOrThrow(columnName));
    }

    /**
     * returns a "double" value associated with the given column name in the
     * cursor. convenience method.
     * 
     * @param c
     *            the db cursor.
     * @param columnName
     *            the name of the column which contains a double.
     * @return the double parsed from the db field.
     */
    public static double doubleFromCursor(final Cursor c,
            final String columnName) {
        return c.getDouble(c.getColumnIndexOrThrow(columnName));
    }

    /**
     * returns a "String" value associated with the given column name.
     * convenience method.
     * 
     * @param c
     *            the db cursor.
     * @param columnName
     *            the name of the column which contains a String.
     * @return the String parsed from the db field.
     */
    public static String stringFromCursor(final Cursor c,
            final String columnName) {
        return c.getString(c.getColumnIndexOrThrow(columnName));
    }

}
