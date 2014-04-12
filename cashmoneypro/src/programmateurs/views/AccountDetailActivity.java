package programmateurs.views;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.RealDataSource;
import net.programmateurs.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

/**
 * 
 * This activity displays the "details" screen for a given account.
 * The most prominent feature of this screen is a list of transactions on
 * the account.
 * 
 * @author vancan1ty
 * 
 */
public class AccountDetailActivity extends Activity {

    private Account account;  //the account to display details for!
    private long accountID;

    private DataSourceInterface src; //provides db access
    private TextView textViewAccountTitle; //displays account title
    private TextView textViewAccountType; //checking or savings currently
    private TextView textViewBalance; //the balance on the account
    private ListView listViewTransactions; //the list of transactions to show.

    //this button will take you to the "deposit version" of the 
    //transactions screen
    private Button buttonDeposit; 
    //the withdrawal counterpart of buttonDeposit 
    private Button buttonWithdraw;
    //the list of transactions on this account.
    private List<Transaction> transactionList = new ArrayList<Transaction>();
    private static final String REBALANCE = "Rebalance Account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        // Show the Up button in the action bar.
        setupActionBar();

        textViewAccountTitle = (TextView) findViewById(R.id.textViewAccountTitle);
        textViewAccountType = (TextView) findViewById(R.id.textViewAccountType);
        textViewBalance = (TextView) findViewById(R.id.textViewBalance);
        listViewTransactions = (ListView) findViewById(R.id.listViewTransactions);
        buttonDeposit = (Button) findViewById(R.id.button_deposit);
        buttonWithdraw = (Button) findViewById(R.id.button_withdraw);

        buttonDeposit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransactionScreen(TRANSACTION_TYPE.DEPOSIT);
            }
        });

        buttonWithdraw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTransactionScreen(TRANSACTION_TYPE.WITHDRAWAL);
            }
        });

        Bundle extras = getIntent().getExtras();
        accountID = extras.getLong("accountID");
        src = new RealDataSource(this);
    }

    /**
     * Go to the new transaction screen.
     * 
     * @param type
     *            deposit/withdraw
     */
    public void gotoTransactionScreen(TRANSACTION_TYPE type) {
        Intent i = new Intent(this, NewTransactionActivity.class);
        i.putExtra("transaction_type", type);
        i.putExtra("account_id", this.accountID);
        this.startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        src.open();

        account = src.getAccountWithID(accountID);
        textViewAccountTitle.setText(account.getAccountName());
        textViewAccountType.setText(account.getAccountType().toString());
        transactionList = Arrays.asList(src
                .getTransactionsForAccount(accountID));
        listViewTransactions.setAdapter(new TransactionAdapter(this,
                transactionList));

        int balance = 0;
        for (Transaction transaction : transactionList) {
            TRANSACTION_TYPE type = transaction.getTransactionType();
            if ((type == TRANSACTION_TYPE.DEPOSIT || type == TRANSACTION_TYPE.REBALANCE)
                    && !transaction.isRolledback() /* == false */) {
                balance += transaction.getTransactionAmount();
            } else if (type == TRANSACTION_TYPE.WITHDRAWAL
                    && !transaction.isRolledback()/* == false */) {
                balance -= transaction.getTransactionAmount();
            } else {
                // should never get here?
            }
        }
        NumberFormat fmter = NumberFormat.getCurrencyInstance();
        String balanceString = fmter.format(balance / 100.0);
        textViewBalance.setText("Balance: " + balanceString);

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
        //MenuItem settings = menu.add("Settings");
        //settings.setIntent(new Intent(this, SettingsActivity2.class));

        MenuItem reequilibrate = menu.add(REBALANCE);
        reequilibrate.setIntent(new Intent(this, RebalanceActivity.class));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        this.startActivity(item.getIntent());
        return true;
    }
    

}
