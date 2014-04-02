package programmateurs.views;

import net.programmateurs.R;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * The main screen that is loaded when you click the app icon
 * 
 * @author currell?
 * @version 0.0
 */
public class WelcomeActivity extends Activity {
    Button buttonLogin;
    Button buttonRegister;
    private Anchor anchor = Anchor.getInstance();
    private RealDataSource src;

    @Override
    public void onBackPressed() {
        // This method intentionally does nothing. Overridden to prevent user
        // from
        // doing something stupid like hitting back after logging out and
        // breaking
        // our code.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // anchor.setCurrentUser(null);
        buttonLogin = (Button) findViewById(R.id.buttontologin);
        buttonRegister = (Button) findViewById(R.id.buttontoregister);

        buttonLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(i);
            }

        });

        buttonRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddUserActivity.class);
                v.getContext().startActivity(i);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        src = new RealDataSource(this);
        src.open();

        // This code allows us to bypass login while testing
        if (Anchor.TEST_MODE) {
            Intent i = new Intent(this, HomeActivity.class);
            anchor.setCurrentUser(src.getUser("test"));
            this.startActivity(i);
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        src.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.d("WelcomeActivity", "featureID" + featureId);
        if (item.getItemId() == R.id.about_us) {
            anchor.showDialog(
                    this,
                    "About the Creators",
                    "This superb specimen of software engineering.\n\n"
                            + "Which you are fortunate to have the opportunity to use.\n\n"
                            + "Was made by:\n" + "Currell Berry,\n"
                            + "Sara Cagle,\n" + "Pavel Komarov,\n"
                            + "Brent McCorvey,\n" + "Justin Nieto.\n\n"
                            + "for our GT CS 2340 team project.");
        }
        return super.onMenuItemSelected(featureId, item);
    }

}
