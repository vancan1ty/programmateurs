package programmateurs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * class to handle startDate operations between app and database. based on this
 * site:
 * 
 * http://joeshacks.blogspot.com/2011/02/managing-startDate-and-time-data-in-
 * android.html
 * 
 * @author vancan1ty
 * 
 */
public class DateUtility {
    /**
     * simple date format string without hours/minutes/seconds.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * a date format string with hours, minutes, and seconds.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * use this to format dates as just a date string, without time.
     */
    private static final SimpleDateFormat M_DATE_FORMAT = new SimpleDateFormat(
            DATE_FORMAT);

    /**
     * use this to format dates with both a date and time.
     */
    private static final SimpleDateFormat M_DATETIME_FORMAT =
            new SimpleDateFormat(DATETIME_FORMAT);

    /**
     * returns a long matching a calendar object.
     * 
     * @param cal
     *            a Calendar object
     * @return the unix timestamp long associated with the input Calendar
     */
    public static long formatCalendarAsLong(Calendar cal) {
        return cal.getTime().getTime();
    }

    /**
     * returns the timestamp associated with a Date.
     * 
     * @param date
     *            a Date object
     * @return the timestamp assoicated with the input
     */
    public static long formatDateAsLong(Date date) {
        return date.getTime();
    }

    /**
     * creates a calendar with time matching the timestamp input.
     * 
     * @param l
     *            a timestamp input as generated by formatCalendarAsLong() above
     * @return the Calendar with corresponding date/time to the timestamp
     * @throws ParseException
     *             if we can't parse the input to a Calendar.
     */
    public static Calendar getCalendarFromFormattedLong(long l)
        throws ParseException {
        Calendar c = Calendar.getInstance();
        Date d = new Date(l);
        c.setTime(d);
        return c;
    }

    /**
     * returns the date associated with a string in yyyy-MM-dd format.
     * 
     * @param dstring
     *            a string in yyy-MM-dd format
     * @return the date associated with it.
     * @throws ParseException
     *             if we can't parse the string.
     */
    public static Date getDateFromString(String dstring) throws ParseException {
        return M_DATE_FORMAT.parse(dstring);
    }

    /**
     * formats a date as a string in yyyy-MM-dd format.
     * 
     * @param date
     *            a date object to format
     * @return a string in yyyy-MM-dd format
     */
    public static String getDateStringFromDate(Date date) {
        return M_DATE_FORMAT.format(date);
    }

    /**
     * formats a date as a string in yyyy-MM-dd HH:mm:ss format.
     * 
     * @param date
     *            a date object to format.
     * @return a string representing the input w/ format yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStringFromDate(Date date) {
        return M_DATETIME_FORMAT.format(date);
    }

}
