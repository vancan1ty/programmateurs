package programmateurs.views;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

import programmateurs.models.Anchor;
import programmateurs.models.DBHandler;

import net.programmateurs.R;
import net.programmateurs.R.id;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import net.programmateurs.R.string;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;

public class AddUserActivity extends Activity {
	
	EditText usernameField;
	EditText passwordField;
	Button buttonLogin;
	public ProgressDialog progress;
	private DBHandler dbHandler; 
	
	Anchor anchor = Anchor.getInstance();
	Activity me = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		dbHandler = new DBHandler(this);
		dbHandler.open();

		usernameField = (EditText) findViewById(R.id.inputUsername);
		passwordField = (EditText) findViewById(R.id.inputPassword);
		buttonLogin = (Button) findViewById(R.id.buttonLaunchCreateUser);
		progress = new ProgressDialog(this);

		
		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				Log.d("BERRY","username: " + username + " password: " + password);
				try {
					dbHandler.addUserToDB(username, password);
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean userExists = dbHandler.isUserInDB(username, password);
				if (userExists) {
					Intent i = new Intent(v.getContext(), HomeActivity.class);
					v.getContext().startActivity(i); 
				} else {
					anchor.showDialog(me, "Log in Failure", "couldn't log you in!");
				}
				
			}
			
		});

	}

	@Override
	protected void onResume() {
		dbHandler.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		dbHandler.close();
		super.onPause();
	}

	
	public String getUsernameEntry() {
		return usernameField.getText().toString();
	}
	
	public String getPasswordEntry() {
		return passwordField.getText().toString();
	}
}