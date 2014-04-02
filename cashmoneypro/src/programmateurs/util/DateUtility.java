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
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final SimpleDateFormat mdateFormat = new SimpleDateFormat(
			DATE_FORMAT);
	private static final SimpleDateFormat mdatetimeFormat = new SimpleDateFormat(
			DATETIME_FORMAT);

	public static long formatCalendarAsLong(Calendar cal) {
		return cal.getTime().getTime();
	}

	public static long formatDateAsLong(Date date) {
		return date.getTime();
	}

	public static Calendar getCalendarFromFormattedLong(long l)
			throws ParseException {
		Calendar c = Calendar.getInstance();
		Date d = new Date(l);
		c.setTime(d);
		return c;
	}

	public static Date getDateFromString(String dstring) throws ParseException {
		return mdateFormat.parse(dstring);
	}

	public static String getDateStringFromDate(Date date) {
		return mdateFormat.format(date);
	}

	public static String getDateTimeStringFromDate(Date date) {
		return mdatetimeFormat.format(date);
	}

}
