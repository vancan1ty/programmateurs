package programmateurs.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.Transaction;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.ArtificialDataSource;
import programmateurs.models.RealDataSource;
import net.programmateurs.R;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class AccountDetailActivity extends Activity {
	
	DataSourceInterface src;
	TextView textViewAccountTitle;
	TextView textViewAccountType;
	ListView listViewTransactions;
	long accountID;
	Account account;
	List<Transaction> transactionList = new ArrayList<Transaction>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		// Show the Up button in the action bar.
		setupActionBar();
		
		textViewAccountTitle = (TextView) findViewById(R.id.textViewAccountTitle);
		textViewAccountType = (TextView) findViewById(R.id.textViewAccountType);
		listViewTransactions = (ListView) findViewById(R.id.listViewTransactions);
		
		Bundle extras= getIntent().getExtras();
		accountID = extras.getLong("accountID");
		src = new RealDataSource(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		src.open();
		
		account = src.getAccountWithID(accountID);
		textViewAccountTitle.setText(account.getAccountName());
		textViewAccountType.setText(account.getAccountType().toString() + " " + account.getInterestRate() + "%");
		transactionList = Arrays.asList(src.getTransactionsForAccount(accountID));
		listViewTransactions.setAdapter(new TransactionAdapter(this, transactionList));
		
	};
	
	@Override
	protected void onPause() {
		super.onPause();
		src.close();
	};


	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}