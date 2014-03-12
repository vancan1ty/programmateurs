package programmateurs.views;

import java.util.Calendar;

import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.models.RealDataSource;

import net.programmateurs.R;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class CategoryReportActivity extends Activity {
	
	private RealDataSource src;
	TextView textReport;
	Calendar startCalendar;
	Calendar endCalendar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_report);
		Bundle extras = getIntent().getExtras();
		startCalendar = (Calendar) extras.getSerializable("startCalendar");
		endCalendar = (Calendar) extras.getSerializable("endCalendar");
		src = new RealDataSource(this);
		
		textReport = (TextView) findViewById(R.id.text_cat_report);
		
		

	}
	
	@Override
	protected void onResume() {
		super.onResume();

		src.open();
		textReport.setText(src.getCategoryReport(startCalendar, endCalendar, TRANSACTION_TYPE.WITHDRAWAL));

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
