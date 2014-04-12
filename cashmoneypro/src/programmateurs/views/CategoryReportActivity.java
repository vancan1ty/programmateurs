package programmateurs.views;

import java.util.Calendar;

import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;

import net.programmateurs.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/**
 * This Activity displays category reports about our transactions.  Currently
 * there are two kinds of reports -- spending category reports and income
 * source reports.  These are basically the same thing, except one takes its
 * data from withdrawals and the other takes its data from deposits.
 * 
 * Example spending category report from the 2340 project spec.
 * 
 *Spending Category Report for George Burdell
   January 1, 2014 - January 31, 2014
   Food           87.45
   Rent          400.54
   Entertainment  35.00
   Clothing       23.74
   Total         546.73 

 * 
 * Example income source report from the 2340 project spec. 
 * Income Source Report for George Burdell
   January 1, 2014 - January 31, 2014
   Salary         250.00
   Birthday       100.00
   Parents        300.00
   Scholarship     50.00
   Total          700.00
 * 
 * @author vancan1ty
 * 
 */
public class CategoryReportActivity extends Activity {

    // CHECKSTYLE:OFF
    private RealDataSource src;
    TextView textReport;
    Calendar startCalendar;
    Calendar endCalendar;
    Transaction.TRANSACTION_TYPE reportType;
    Anchor anchor;

    // CHECKSTYLE:ON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anchor = Anchor.getInstance();
        setContentView(R.layout.activity_category_report);
        Bundle extras = getIntent().getExtras();
        startCalendar = (Calendar) extras.getSerializable("startCalendar");
        endCalendar = (Calendar) extras.getSerializable("endCalendar");
        reportType = (TRANSACTION_TYPE) extras.getSerializable("reportType");
        src = new RealDataSource(this);

        textReport = (TextView) findViewById(R.id.text_cat_report);

    }

    @Override
    protected void onResume() {
        super.onResume();

        src.open();
        textReport.setText(src.getCategoryReport(startCalendar, endCalendar,
                reportType, anchor.getCurrentUser().getUserID()));
    }

    @Override
    protected void onPause() {
        super.onPause();

        src.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_report, menu);
        return true;
    }

}
