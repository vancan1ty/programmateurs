package programmateurs.views;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.programmateurs.R;

import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.AccountsDAO;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class RebalanceActivity extends NewTransactionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebalance);
        
        Bundle extras = getIntent().getExtras();
        this.accountID = extras.getLong("account_id");
        //this.transactionType = (TRANSACTION_TYPE) extras
        //        .getSerializable("transaction_type");
        
        amountText = (EditText) findViewById(R.id.amountNumber);
        buttonTransaction = (Button) findViewById(R.id.buttonRebalance);
        buttonTransaction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(v.getContext(), AccountDetailActivity.class);
                String transactionName = "Rebalance";
                String transactionComment = "Automatically-generated rebalance transaction";
                transactionType = Transaction.TRANSACTION_TYPE.REBALANCE;
                Category category = getCategoryByName("Misc.");

                double targetBalance = Double.parseDouble(amountText.getText().toString());
                double currentBalance = getBalance();
                NumberFormat fmter = NumberFormat.getCurrencyInstance();
                //String amountString = fmter.format(targetBalance-currentBalance);
                String amountString = Double.toString(targetBalance-currentBalance);
                Log.d("amountString", amountString);
                
                if (checkIfInputsAreValid(transactionName, amountString,
                        transactionCal, transactionComment, category)) {
                
                    src.addTransactionToDB(accountID, transactionName,
                            transactionType, parseStringAsCents(amountString),
                            transactionCal.getTime(), transactionComment, false,
                            category);
                    
                    RebalanceActivity.this.onBackPressed();
                } else {
                    String errorMessage = computeErrorMessage(transactionName, 
                            amountString, transactionCal, transactionComment, category);
                    anchor.showDialog(RebalanceActivity.this, "Transaction Error(s)", errorMessage);
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
    }

    /**
     * Method used when RealDataSource is used.
     */
    @Override
    protected void onPause() {
        src.close();
        super.onPause();
    }
    
    //------------------------------------------------------helpers below
    //really this class and NewTransactionActivity are so similar they should be sibling-classes
    /**
     * return the total balance of the account we're rebalancing
     * @return the total balance of an account
     */
    private double getBalance() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList = Arrays.asList(src
                .getTransactionsForAccount(accountID));
        double balance = 0;
        for (Transaction transaction : transactionList) {
            TRANSACTION_TYPE type = transaction.getTransactionType();
            if ((type == TRANSACTION_TYPE.DEPOSIT || type == TRANSACTION_TYPE.REBALANCE)
                    && !transaction.isRolledback() /* == false */) {
                balance += transaction.getTransactionAmountAsDouble();
            } else if (type == TRANSACTION_TYPE.WITHDRAWAL
                    && !transaction.isRolledback()/* == false */) {
                balance -= transaction.getTransactionAmountAsDouble();
            }
        }
        return balance;
    }    
    
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
    @Override
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
     * checks if a string representing money is actually valid.
     * 
     * @param money the string to check for ex: 22.34
     * @return boolean true if can be parsed to cents, else false
     */
    private boolean validTransactionAmount(String money) {
        if (money != null
                //first do a regex check.
                && money.matches("^-?0*[1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*$")) {
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
    
}
