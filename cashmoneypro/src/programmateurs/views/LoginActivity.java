package programmateurs.views;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import programmateurs.models.UsersDAO;

import net.programmateurs.R;
import net.programmateurs.R.id;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * LoginActivity is the login screen. It does different things based on the 
 * username and password
 * 
 * @author currell
 *
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		dbHandler = new RealDataSource(this);
		usernameField = (EditText) findViewById(R.id.usernameField);
		passwordField = (EditText) findViewById(R.id.passwordfield);
		buttonLogin = (Button) findViewById(R.id.buttonlogin);
		progress = new ProgressDialog(this);
		
		buttonLogin.setOnClickListener(new OnClickListener() {

			/**
			 * If the user's name is admin, go to the AdminActivity page
			 * Else go to the HomeActivity page and set the current user
			 */
			@Override
			public void onClick(View v) {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				Log.d("BERRY","username: " + username + " password: " + password);
				boolean userExists =  dbHandler.isUserInDB(username, password);
				if (userExists) {
					Intent i;
					if(username.equals("admin"))
						i = new Intent(v.getContext(), AdminActivity.class);
					else
						i = new Intent(v.getContext(), HomeActivity.class);
					anchor.setCurrentUser(dbHandler.getUser(username));
					v.getContext().startActivity(i); 
				} else {
					anchor.showDialog(me, "Log in Failure", "couldn't log you in!");
				}
				
			}
			
		});
		
	}

	/**
	 * Method used every time RealDataSource is used
	 */
	@Override
	protected void onResume() {
		super.onResume();
		dbHandler.open();
	}

	/**
	 * Method used every time RealDataSource is used
	 */
	@Override
	protected void onPause() {
		dbHandler.close();
		super.onPause();
	}

	/**
	 * Return the user's username
	 * @return string the username
	 */
	public String getUsernameEntry() {
		return usernameField.getText().toString();
	}
	
	/**
	 * Returns the user's password
	 * @return string the password
	 */
	public String getPasswordEntry() {
		return passwordField.getText().toString();
	}
}
