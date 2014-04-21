package net.programmateurs;

import programmateurs.beans.User;
import programmateurs.models.Anchor;
import programmateurs.models.CSVHandler;
import programmateurs.models.RealDataSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DataExportActivity extends Activity {

    RealDataSource src;
    Button buttonPrintData;
    Button buttonEmailData;
    TextView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_export);
        buttonPrintData = (Button) findViewById(R.id.button_print_data);
        buttonEmailData = (Button) findViewById(R.id.button_email_data);
        results = (TextView) findViewById(R.id.textViewCSV);
        src = new RealDataSource(this);
    }
    
    @Override
    protected void onResume() {
        final User currentUser = Anchor.getInstance().getCurrentUser();
        src.open();
        buttonPrintData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               results.setText(CSVHandler.TransactionsToCSV(src, 
                       currentUser.getUserID()));
            }
        });

        buttonEmailData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               results.setText(CSVHandler.TransactionsToCSV(src, 
                       currentUser.getUserID()));
               Intent i = new Intent(Intent.ACTION_SEND);
               i.setType("message/rfc822");
               i.putExtra(Intent.EXTRA_EMAIL  , new String[]{
                       currentUser.getEmail()
               });
               i.putExtra(Intent.EXTRA_SUBJECT, "CashMoneyPro CSV Data Dump");
               i.putExtra(Intent.EXTRA_TEXT   , 
                       CSVHandler.TransactionsToCSV(src, currentUser.getUserID()));
               try {
                   startActivity(Intent.createChooser(i, "Send mail..."));
               } catch (android.content.ActivityNotFoundException ex) {
                   Anchor.getInstance().showDialog(DataExportActivity.this, 
                           "Can't send Email", "There are no email clients installed");
               }
            }
        });

        super.onResume();
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        src.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data_export, menu);
        return true;
    }

}
