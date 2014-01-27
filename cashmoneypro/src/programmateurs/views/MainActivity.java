package programmateurs.views;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import programmateurs.models.Anchor;
import programmateurs.models.DBHandler;

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

public class MainActivity extends Activity {
	
	EditText usernameField;
	EditText passwordField;
	Button buttonLogin;
	public ProgressDialog progress;
//	private DBHandler dbHandler = new DBHandler(this);
	
	Anchor anchor = Anchor.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		usernameField = (EditText) findViewById(R.id.emailfield);
		passwordField = (EditText) findViewById(R.id.passwordfield);
		buttonLogin = (Button) findViewById(R.id.buttonlogin);
		progress = new ProgressDialog(this);
		
		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				Log.d("BERRY","username: " + username + " password: " + password);
				boolean userExists = true; //dbHandler.isUserInDB(username, password);
				if (userExists) {
					Intent i = new Intent(v.getContext(), HomeActivity.class);
					v.getContext().startActivity(i); 
				} else {
					Toast.makeText(v.getContext(), "couldn't log you in!", Toast.LENGTH_LONG);
				}
				
			}
			
		});
	
	//	checkForGooglePlayServices();
		

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public String getUsernameEntry() {
		return usernameField.getText().toString();
	}
	
	public String getPasswordEntry() {
		return passwordField.getText().toString();
	}
}
