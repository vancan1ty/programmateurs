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
 * 
 * @author programmateurs
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
