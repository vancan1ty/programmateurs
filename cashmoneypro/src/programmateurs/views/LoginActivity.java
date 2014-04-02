package programmateurs.views;

import net.programmateurs.R;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * LoginActivity is the login screen. It does different things based on the
 * username and password
 * 
 * @author currell
 * @verison 0.1
 */
public class LoginActivity extends Activity {

    EditText usernameField;
    EditText passwordField;
    Button buttonLogin;
    Button buttonLaunchCreateUser;
    public ProgressDialog progress;
    private RealDataSource dbHandler;

    Anchor anchor = Anchor.getInstance();
    Activity me = this;

    /**
     * The onCreate method sets all the objects on the screen
     * 
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHandler = new RealDataSource(this);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordfield);
        buttonLogin = (Button) findViewById(R.id.buttonlogin);
        progress = new ProgressDialog(this);

        buttonLogin.setOnClickListener(new OnClickListener() {

            /**
             * If the user's name is admin, go to the AdminActivity page Else go
             * to the HomeActivity page and set the current user
             */
            @Override
            public void onClick(final View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                Log.d("BERRY", "username: " + username + " password: "
                        + password);
                boolean userExists = dbHandler.isUserInDB(username, password);
                if (userExists) {
                    Intent i;
                    if (username.equals("admin")) {
                        i = new Intent(v.getContext(), AdminActivity.class);
                    } else {
                        i = new Intent(v.getContext(), HomeActivity.class);
                    }
                    anchor.setCurrentUser(dbHandler.getUser(username));
                    v.getContext().startActivity(i);
                } else {
                    anchor.showDialog(me, "Log in Failure",
                            "couldn't log you in!");
                }

            }

        });

    }

    /**
     * Forces the back button to take the user back to the welcome screen when
     * pressed during LoginActivity (to prevent weird user-hacks)
     */
    @Override
    public final void onBackPressed() {
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    /**
     * Method used every time RealDataSource is used
     */
    @Override
    protected final void onResume() {
        super.onResume();
        dbHandler.open();
    }

    /**
     * Method used every time RealDataSource is used
     */
    @Override
    protected final void onPause() {
        dbHandler.close();
        super.onPause();
    }

    /**
     * Return the user's username
     * 
     * @return string the username
     */
    public final String getUsernameEntry() {
        return usernameField.getText().toString();
    }

    /**
     * Returns the user's password
     * 
     * @return string the password
     */
    public final String getPasswordEntry() {
        return passwordField.getText().toString();
    }
}
