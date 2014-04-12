package programmateurs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import programmateurs.views.ReportBuilderActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * this class provides the STANDARD date entry functionality for our app.
 * Anywhere you need to enter a date, use the makeEditTextADateChooser method
 * below! Created in response to UI evaluation which mentioned that date entry
 * was confusing.
 * 
 * @author vancan1ty
 *
 */
public class DateEntryFieldUtils {
    /**
     * Updates the label on an edittext to match the given calendar.
     * 
     * @param toSet the EditText to update
     * @param cal the object to extract the date from.
     * @return the string which was set on the EditText.
     */
    private static void updateLabel(TextView toSet, Calendar cal) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        toSet.setText(sdf.format(cal.getTime()));
    }
    
    /**
     * send in the current activity, a calendar object, and an EditText,
     * and this method will take care of adding date picker functionality to
     * the edittext so that when you touch it, a date picker will pop up,
     * and when you choose a date cal will be updated. <br><br>
     * 
     * Look in activity_transaction_screen.xml or activity_report_builder.xml
     * for examples of how to properly specify the datepicker edittext in
     * xml. <br> <br>
     * 
     * max date will be set to now.
     * @param ctx an activity for context purposes.
     * @param cal the calendar to store the backing date in.
     * @param text the EditText to turn into a date chooser.
     */
    public static void makeEditTextADateChooser(final Activity ctx, 
            final Calendar cal, final TextView text) {
        
    /**
     * On date listener.
     */
        final DatePickerDialog.OnDateSetListener dateSetListener = 
                new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateEntryFieldUtils.updateLabel(text, cal);
            }
        };

        Log.d("BERRY","we are hea");
        text.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(ctx, dateSetListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 
                        cal.get(Calendar.DAY_OF_MONTH));
                dateDialog.getDatePicker().setMaxDate(Calendar.getInstance()
                        .getTimeInMillis());
                dateDialog.show();
            }
        });
        
        //finally set the initial date to today!
        updateLabel(text,cal);


    }



}
