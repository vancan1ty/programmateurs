package programmateurs.views;

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
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Random;

import programmateurs.beans.Account.ACCOUNT_TYPE;
import programmateurs.beans.User;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import programmateurs.beans.Account;

/**
 * <<<<<<< HEAD NewAccountActivity allows the user to create a
 * NewAccountActivity with a given ======= NewAccount allows the user to create
 * a NewAccount with a given >>>>>>> branch 'master' of
 * https://github.com/vancan1ty/programmateurs.git account name and interest
 * rate. The user can also select if the account is Checking or Savings.
 * 
 * @author brent
 * @version 0.0
 */
public class NewAccountActivity extends Activity {

    private User user;
    private RealDataSource dbHandler;
    Activity me = this;
    Anchor anchor = Anchor.getInstance();
    ACCOUNT_TYPE accountType;

    EditText accountField;
    EditText interestRate;
    Button createAccount;
    Random rand;
    Spinner spinner;

    /**
     * Creates the objects on the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        dbHandler = new RealDataSource(this);
        user = anchor.getCurrentUser();

        accountField = (EditText) findViewById(R.id.accountName);
        interestRate = (EditText) findViewById(R.id.interestRate);
        createAccount = (Button) findViewById(R.id.createAccount);

        spinner = (Spinner) findViewById(R.id.spinnerNewAccount);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // FIXME //Just leave this tag here. I might fix this
        adapter.add("Checking");
        adapter.add("Savings");
        spinner.setAdapter(adapter);

        createAccount.setOnClickListener(new OnClickListener() {

            /**
             * Boolean logic to detect if the interest rate and account name are
             * valid entries
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                String account = accountField.getText().toString();
                boolean validAccount = false;
                if (account.equals("")) {
                    anchor.showDialog(me, "Invalid Account Name",
                            "Please enter an account name");
                }
                String interestString = interestRate.getText().toString();
                double interestNum = -1.0;
                if (!interestString.equals("")) {
                    interestNum = Double.parseDouble(interestString);
                }

                if (interestNum < 0 || interestNum > 100) {
                    anchor.showDialog(me, "Invalid Interst Rate",
                            "Please enter an interest rate between 0 and 100");
                }

                validAccount = (interestNum <= 100 && interestNum >= 0 && !account
                        .equals(""));

                if (spinner.getSelectedItem().toString().equals("Checking")
                        && validAccount) {
                    accountType = ACCOUNT_TYPE.CHECKING;
                    dbHandler.addAccountToDB(user.getUserID(), accountType,
                            account, interestNum);
                    v.getContext().startActivity(i);
                } else if (spinner.getSelectedItem().toString()
                        .equals("Savings")
                        && validAccount) {
                    accountType = ACCOUNT_TYPE.SAVINGS;
                    dbHandler.addAccountToDB(user.getUserID(), accountType,
                            account, interestNum);
                    v.getContext().startActivity(i);
                }
            }

        });

    }

    /**
     * Creates the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_account, menu);
        return true;
    }

    /**
     * Method used when RealDataSource is used
     */
    @Override
    protected void onResume() {
        dbHandler.open();
        super.onResume();
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
