package programmateurs.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import net.programmateurs.R;
import programmateurs.beans.Transaction;
import programmateurs.util.DateEntryFieldUtils;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * On this screen, the user is presented with a series of options which allow 
 * him to choose the parameters he wants for a report.  After clicking the
 * generate report button, the results will be tabulated and he will be shown
 * another screen which actually contains the report.
 * 
 * @author vancan1ty
 * 
 */
public class ReportBuilderActivity extends Activity {

    EditText textStartDate;
    EditText textEndDate;
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();
    Button buttonGenerateReport;
    String reportType0 = "reportType";

    Transaction.TRANSACTION_TYPE reportType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_builder);
        Bundle extras = getIntent().getExtras();
        reportType = (Transaction.TRANSACTION_TYPE) extras
                .getSerializable(reportType0);
        textStartDate = (EditText) findViewById(R.id.text_start_date);
        textEndDate = (EditText) findViewById(R.id.text_end_date);
        buttonGenerateReport = (Button) findViewById(R.id.button_generate_report);

        DateEntryFieldUtils.makeEditTextADateChooser(this, startCalendar, 
                textStartDate);
        DateEntryFieldUtils.makeEditTextADateChooser(this, endCalendar, 
                textEndDate);
 
        buttonGenerateReport.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),
                        CategoryReportActivity.class);
                i.putExtra("startCalendar", startCalendar);
                i.putExtra("endCalendar", endCalendar);
                i.putExtra(reportType0, reportType);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_builder, menu);
        return true;
    }

}
