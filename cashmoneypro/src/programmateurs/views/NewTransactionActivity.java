package programmateurs.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import programmateurs.beans.Category;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.AccountsDAO;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import programmateurs.util.DateEntryFieldUtils;
import net.programmateurs.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
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
 * user's account Right now it is just a skeleton.
 * 
 * @author Brent
 * @version 0.2
 */
public class NewTransactionActivity extends Activity {

    /**
     * the id of the account to add the transaction to!
     */
    long accountID;

    /**
     * The type of the transaction to add. Currently either deposit or
     * withdrawal.
     */
    TRANSACTION_TYPE transactionType;

    /**
     * data source.
     */
    DataSourceInterface src = new RealDataSource(this);

    /**
     * lets you enter the amount of the transaction.
     */
    protected EditText amountText; // amount of the transaction

    /**
     * lets you enter the name of the transaction
     */
    private EditText textViewName;

    /**
     * lets you enter an optional comment on the transaction.
     */
    private EditText textViewComment;

    /**
     * lets you pick the date on which the transaction occurred. defaults to the
     * user's current day.
     */
    private EditText textViewDate;
    protected Calendar transactionCal = Calendar.getInstance();

    /**
     * lets you pick a category for your transaction.
     */
    private Spinner categorySpinner;

    /**
     * this button finalizes the transaction.
     */
    protected Button buttonTransaction;

    /**
     * the anchor point provides some utility functionality.
     */
    protected Anchor anchor = Anchor.getInstance();


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
        textViewDate = (EditText) findViewById(R.id.textViewDate);
        DateEntryFieldUtils.makeEditTextADateChooser(this, transactionCal, 
                textViewDate);
        
        buttonTransaction = (Button) findViewById(R.id.buttonTransaction);
        categorySpinner = (Spinner) findViewById(R.id.transaction_category_spinner);

        // stuff for sound so it's not created in line with the button
        final MediaPlayer depositSound = MediaPlayer
                .create(this, R.raw.caching);

        buttonTransaction.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //@SuppressWarnings("unused")
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                String transactionName = textViewName.getText().toString();
                String transactionComment = textViewComment.getText()
                        .toString();
                String amountString = amountText.getText().toString();
                Category category = getCategoryByName((String) 
                        categorySpinner.getSelectedItem());


                if (checkIfInputsAreValid(transactionName,amountString,
                        transactionCal,transactionComment,category)) { 
                    //if all fields validate then go ahead and add the 
                    //transaction to the db!

                    src.addTransactionToDB(accountID, transactionName,
                            transactionType, parseStringAsCents(amountString), 
                            transactionCal.getTime(), transactionComment, false,
                            category);

                    // $$.mp3 sound goes here:
                    depositSound.start();

                    //now go back to previous screen.
                    NewTransactionActivity.this.onBackPressed();
                    
                } else {
                   String errorMessage = computeErrorMessage(transactionName, 
                           amountString, transactionCal, transactionComment, category);
                   anchor.showDialog(NewTransactionActivity.this, "Transaction Error(s)", errorMessage);
                }
            }
        });
    }

    /**
     * Method used when RealDataSource is used.
     */
    @Override
    protected void onResume() {
        src.open();
        super.onResume();
        Category[] categories = src.getCategoriesForUser(anchor
                .getCurrentUser().getUserID());
        String[] stringArray = new String[categories.length];
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                if (categories[i] != null) {
                    stringArray[i] = categories[i].getCategory_name();
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stringArray);
        categorySpinner.setAdapter(adapter);
    }

    /**
     * Method used when RealDataSource is used.
     */
    @Override
    protected void onPause() {
        src.close();
        super.onPause();
    }
    
    //--------------------------------------------------------------helpers below
    
    /**
     * this function is used to check the inputs provided by the user on this
     * activity.
     * 
     * @param transactionName the provided name of the transaction.
     * @param amountString the provided amount of the transaction.
     * @param transactionDate the user's provided date.
     * @param transactionComment the user's optional comment.
     * @param category the provided category of the transaction.
     * @return true if all the values check out, false if there is an error.
     */
    protected boolean checkIfInputsAreValid(String transactionName,
            String amountString, Calendar transactionDate,
            String transactionComment, Category category) {

        if (validTransactionAmount(amountString)
                && validDate(transactionCal)) {
            try {
                long cents = parseStringAsCents(amountString);
                if (!AccountsDAO.overdrawn(src, accountID,  cents, 
                        transactionType)) {
                    return true;
                }
            } catch (NumberFormatException e) {
                Log.d("NewTransactionActivity","caught routine NumberFormatException");
                e.printStackTrace();
                //NOTHING NEEDS TO BE DONE HERE
            }
        }

        return false;
    }


    /**
     * attempts to parse a string in the format [dollars].[cents] aka
     * 22 or
     * 252.31
     * and return the number of cents which the string represents.
     * 
     * if it can't parse the string, it throws a NumberFormatException. 
     * 
     * @param s a string which represents a dollar value.
     * @return the number of cents in s, or null if the string can't be
     *         parsed to a long.
     */
    protected long parseStringAsCents(String moneyString) throws
        NumberFormatException {
                double transactionAmountD = Double
                        .parseDouble(moneyString);
                /*
                 * we always represent money values as integers in cents! 
                 * this eliminates the rounding errors inherent to floating 
                 * point values.
                 */
                long transactionAmountL = Math
                        .round(transactionAmountD * 100);
                return transactionAmountL;
    }
        
    
    
    /**
     * computes an error message given the values collected from fields on this
     * activity.  it is intended that this will be called AFTER we have checked
     * that there is indeed a problem with the user's inputs.
     * 
     * @param transactionName the name of the transaction the user provided.
     * @param amountString the amount of the transaction the user provided.
     * @param transactiondate the date of the transaction from the date picker.
     * @param transactionComment an optional comment on the transaction.
     * @param category the provided category of the transaction.
     * @return
     */
    protected String computeErrorMessage(String transactionName,
            String amountString, Calendar transactiondate,
            String transactionComment, Category category) {
            String errorMessage = "Please resolve the following errors:\n";
                    if (amountString == null) {
                        errorMessage += 
                                "\n- you must enter a transaction amount!";
                    } else if (!validTransactionAmount(amountString)) {
                        errorMessage += "\n- Transaction amount invalid.";
                    }
                    if (!validDate(transactionCal)) {
                        errorMessage += "\n- Enter a valid startDate.";
                    }
                    try {
                        long cents = parseStringAsCents(amountString); 
                        if (AccountsDAO.overdrawn(src, accountID, cents,
                                transactionType)) {
                            errorMessage += "\n- You have insufficient funds to complete this transaction.";
                        }
                    } catch (NumberFormatException e) {
                        Log.d("NewTransactionActivity","caught routine NumberFormatException");
                        e.printStackTrace();
                        //no need to do anything just show other error
                        //messages.
                    }
                    return errorMessage;
    }


    /**
     * checks if a string representing money is actually valid.
     * 
     * @param money the string to check for ex: 22.34
     * @return boolean true if can be parsed to cents, else false
     */
    private boolean validTransactionAmount(String money) {
        if (money != null
                //first do a regex check.
                && money.matches("^0*[1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*$")) {
            try {
                //now check to see if we can *actually* parse the string.
                long cents = parseStringAsCents(money);
                return true;
            } catch (NumberFormatException e) {
                Log.d("NewTransactionActivity","caught routine NumberFormatException");
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * checks all the entries on the calendar object to make sure it has
     * the necessary fields before we attempt to persist to the db.
     * 
     * @param transactionCal calendar to check
     * @return true if the date is valid, else false. 
     */
    protected boolean validDate(Calendar cal) {
        if (cal.isSet(cal.DAY_OF_MONTH) 
                && cal.isSet(cal.MONTH)
                && cal.isSet(cal.YEAR))
        {
            return true;
        } else {
            return false;
        }
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
        Category[] categories = src.getCategoriesForUser(anchor
                .getCurrentUser().getUserID());
        for (Category c : categories) {
            if (c.getCategory_name().equals(name)) {
                return c;
            }
        }
        throw new RuntimeException("No existing category with name " + name);
        // only executed if somebody dun goofed.
    }

}
