package programmateurs.views;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import programmateurs.models.UsersDAO;

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
	EditText nameField;
	EditText emailField;
	Button buttonLogin;
	public ProgressDialog progress;
	private RealDataSource dbHandler;
	
	Anchor anchor = Anchor.getInstance();
	Activity me = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		dbHandler = new RealDataSource(this);

		nameField = (EditText) findViewById(R.id.inputName);
		emailField = (EditText) findViewById(R.id.inputEmail);
		usernameField = (EditText) findViewById(R.id.inputUsername);
		passwordField = (EditText) findViewById(R.id.inputPassword);
		buttonLogin = (Button) findViewById(R.id.buttonLaunchCreateUser);
		progress = new ProgressDialog(this);

		
		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = nameField.getText().toString();
				
				//name function currently doesn't work but we still want to take in name
				//check to see if name is valid
				//if we could make this happen when the user hits 'create account' that'd be best
				/*if(!validName(name)){
					anchor.showDialog(me, "Invalid Name", "Please use your real name, both first and last.");
					//need to request your name be changed
				}*/
				
				String email = emailField.getText().toString();
				
				
				//email function currently doesn't work, but we still want to take in the email
				//check to see if email is valid
				/*if(!validEmail(email)){
					anchor.showDialog(me, "Invalid Email", "Please check your email address and ensure it is a valid email address.");
					//need to request a different email address
				}*/
				
				
				String username = usernameField.getText().toString();
				//check to see if username is valid
				if(!validUsername(username)){//username is not valid
					anchor.showDialog(me, "Invalid Username", "Usernames must be"+
							" at least 5 characters long consisting of letters, numbers, and underscores." +
							" Usernames are case-sensitive.");
					//need to request a new username
				}
				
				
				String password = passwordField.getText().toString();
				//check to see if password is valid
				if(!validPassword(password)){//password is not valid
					anchor.showDialog(me, "Invalid Password", "Password must be"+
							" at least 5 characters long and are case-sensitive.");
					//need to request a new password
				}
				
				
				Log.d("BERRY","username: " + username + " password: " + password);
					dbHandler.cheapAddUserToDB(username, password);
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
	
	
	private Boolean validUsername(String username){
		if(username.matches("\\w{5,}")){
			//letters, numbers, underscore length 5 or more
			return true;
		}
		return false;
	}
	
	private Boolean validPassword(String pass){
		if(pass.matches(".{5,}")){
			//anything length 5 or more
			return true;
		}
		return false;
	}
	//currently does not work, will get back to later
	/*private Boolean validEmail(String email){
		if(email.matches("(\\w\\-\\+\\.)+@(\\w\\-\\.)+\\.[A-Za-z]{2,3}")){
		//DEBUG fix this regex
		//name: letters, numbers, dash, underscore, plus, dot
		//@, URL: letters, dash, numbers, underscore, dot
		//extension: letters from length 2 to 3 inclusive
			return true;
		}
		return false;
	}*/
	
	//currently does not work. will get back to later
	/*private Boolean validName(String name){
		if(name.matches("[A-Za-z]+\\-*[A-Za-z]*[\\s+(A-Za-z)+\\-*(A-Za-z)*]+")){
			return true;
			//DEBUG fix this regex
			//first name and last name are expected, but any number of
			//names (more than 2) will be allowed
			//Must start with a letter
			//allows dashes
			//names are separated by any number of spaces more than 0
		}
		return false;
	}*/

	
	public String getUsernameEntry() {
		return usernameField.getText().toString();
	}
	
	public String getPasswordEntry() {
		return passwordField.getText().toString();
	}
}
