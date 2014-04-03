package programmateurs.views;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.AccountsDAO;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import net.programmateurs.R;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.media.MediaPlayer; //for sounds, delete this import if error

/**
 * The Deposit Activity class It will have a screen to deposit money to the
 * user's account Right now it is just a skeleton
 * 
 * @author brent
 * @version 0.2
 */
public class TransactionScreen extends Activity {

	private EditText amountText, textViewName, textViewComment;
	private DatePicker picker;
	private Button buttonTransaction;
	private Spinner categorySpinner;
	private Calendar cal = Calendar.getInstance();
	DateFormat sdf = new SimpleDateFormat();
	Anchor anchor = Anchor.getInstance();
	DataSourceInterface dbHandler = new RealDataSource(this);

	Activity me = this;

	// Account account1 = new Account(0, 4, Account.ACCOUNT_TYPE.CHECKING,
	// "berrychecking", 0);

	Date currentDate = new Date(System.currentTimeMillis());
	long accountID;
	TRANSACTION_TYPE transactionType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_screen);

		Bundle extras = getIntent().getExtras();
		this.accountID = extras.getLong("account_id");
		this.transactionType = (TRANSACTION_TYPE) extras
				.getSerializable("transaction_type");

		amountText = (EditText) findViewById(R.id.amountNumber);
		textViewName = (EditText) findViewById(R.id.textViewName);
		textViewComment = (EditText) findViewById(R.id.comment);
		picker = (DatePicker) findViewById(R.id.datePicker);
		picker.setMaxDate(Calendar.getInstance().getTimeInMillis());
		buttonTransaction = (Button) findViewById(R.id.buttonTransaction);
		categorySpinner = (Spinner) findViewById(R.id.transaction_category_spinner);

		// stuff for sound so it's not created in line with the button
		final MediaPlayer depositSound = MediaPlayer
				.create(this, R.raw.caching);

		buttonTransaction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), HomeActivity.class);
				String transactionName = textViewName.getText().toString();
				String transactionComment = textViewComment.getText()
						.toString();
				String money = amountText.getText().toString();
				int day = picker.getDayOfMonth();
				int month = picker.getMonth();
				int year = picker.getYear();
				cal.set(year, month, day);
				Double amount = Double.parseDouble(money);
				if (validTransactionAmount(money)
						&& validDate(cal)
						&& !AccountsDAO.overdrawn(dbHandler, accountID, amount,
								transactionType)) {
					double transactionAmountD = Double.parseDouble(money);
					long transactionAmountL = Math
							.round(transactionAmountD * 100);
					dbHandler.addTransactionToDB(accountID, transactionName,
							transactionType, (long) transactionAmountL, cal
									.getTime(), transactionComment, false,
							getCategoryByName((String) categorySpinner
									.getSelectedItem()));

					// $$.mp3 sound goes here:
					depositSound.start();
					// end sound
					me.onBackPressed();
				} else {
					String errorMessage = "Please resolve the following errors:\n";
					if (!validTransactionAmount(money)) {
						errorMessage += "\n- Enter an amount greater than 0.";
					}
					if (!validDate(cal)) {
						errorMessage += "\n- Enter a valid startDate.";
					}
					if (AccountsDAO.overdrawn(dbHandler, accountID, amount,
							transactionType)) {
						errorMessage += "\n- You have insufficient funds to complete this transaction.";
					}
					anchor.showDialog(me, "Transaction Error(s)", errorMessage);
				}
			}
		});
		// System.out.println(artificialSource.getTransactionsForAccount(0));
	}

	private boolean validTransactionAmount(String money) {
		if (money.matches("^0*[1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*$")) {
			return true;
		}
		return false;
	}

	/**
	 * Makes sure the startDate is valid. I hate Date
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private boolean validDate(Calendar cal) {
		return true;
		/*
		 * if(currentDate.getTime() > cal.getTime().getTime()) { return true; }
		 * System.out.println(currentDate.getTime());
		 * System.out.println(cal.getTime().getTime()); return false;
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deposit, menu);
		return true;
	}

	/**
	 * 
	 * @param name
	 *            Name of category being searched for
	 * @return category with matching name
	 */
	public Category getCategoryByName(String name) {
		Category[] categories = dbHandler.getCategoriesForUser(anchor
				.getCurrentUser().getUserID());
		for (Category c : categories) {
			if (c.getCategory_name().equals(name)) {
				return c;
			}
		}
		throw new RuntimeException("No existing category with name " + name); // only
																				// executed
																				// if
																				// somebody
																				// done
																				// goofed.
	}

	/**
	 * Method used when RealDataSource is used
	 */
	@Override
	protected void onResume() {
		dbHandler.open();
		super.onResume();
		Category[] categories = dbHandler.getCategoriesForUser(anchor
				.getCurrentUser().getUserID());
		String[] stringArray = new String[categories.length];
		if (categories != null) {
			for (int i = 0; i < categories.length; i++) {
				if (categories[i] != null)
					stringArray[i] = categories[i].getCategory_name();
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringArray);
		categorySpinner.setAdapter(adapter);
	}

	/**
	 * Method used when RealDataSource is used
	 */
	@Override
	protected void onPause() {
		dbHandler.close();
		super.onPause();
	}

}
