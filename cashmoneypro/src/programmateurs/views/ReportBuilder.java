package programmateurs.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import net.programmateurs.R;
import programmateurs.beans.Transaction;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ReportBuilder extends Activity {

    EditText textStartDate;
    EditText textEndDate;
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();
    Button buttonGenerateReport;

    Transaction.TRANSACTION_TYPE reportType;

    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(final DatePicker view, final int year, final int monthOfYear,
                final int dayOfMonth) {
            // TODO Auto-generated method stub
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(textStartDate, startCalendar);
        }

    };

    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(final DatePicker view, final int year, final int monthOfYear,
                final int dayOfMonth) {
            // TODO Auto-generated method stub
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(textEndDate, endCalendar);
        }

    };

    private void updateLabel(final EditText toSet, final Calendar cal) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        toSet.setText(sdf.format(cal.getTime()));
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_builder);
        Bundle extras = getIntent().getExtras();
        reportType = (Transaction.TRANSACTION_TYPE) extras
                .getSerializable("reportType");
        textStartDate = (EditText) findViewById(R.id.text_start_date);
        textEndDate = (EditText) findViewById(R.id.text_end_date);
        buttonGenerateReport = (Button) findViewById(R.id.button_generate_report);

        textStartDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReportBuilder.this, startDate,
                        startCalendar.get(Calendar.YEAR), startCalendar
                                .get(Calendar.MONTH), startCalendar
                                .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        textEndDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReportBuilder.this, endDate, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonGenerateReport.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(),
                        CategoryReportActivity.class);
                i.putExtra("startCalendar", startCalendar);
                i.putExtra("endCalendar", endCalendar);
                i.putExtra("reportType", reportType);
                v.getContext().startActivity(i);

            }
        });

    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_builder, menu);
        return true;
    }

}
