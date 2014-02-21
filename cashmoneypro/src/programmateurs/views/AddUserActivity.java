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
				String name = nameField.getText().toString(); //takes in name field
				
				//name function currently doesn't work but we still want to take in name
				//check to see if name is valid
				/*if(!validName(name)){
					anchor.showDialog(me, "Invalid Name", "Please use your real name, both first and last.");
					//need to request your name be changed
				}*/
				
				String email = emailField.getText().toString(); //takes email from field
				String username = usernameField.getText().toString(); //takes username from field
				String password = passwordField.getText().toString(); //takes password from field
				Log.d("BERRY","username: " + username + " password: " + password);
				dbHandler.cheapAddUserToDB(username, password);
				
				//check validity of username, password, and email, and if the username is unique
				boolean validUsername = validUsername(username);
				boolean validPassword = validPassword(password); 
				boolean validEmail = validEmail(email);
				boolean userDoesntExists = dbHandler.isUserInDB(username, password);
				//changed userExists to userDoesntExist to the way this is set up
				
				if (userDoesntExists&&validUsername&&validPassword) { //user already exists, username and pass is valid
					Intent i = new Intent(v.getContext(), HomeActivity.class);
					anchor.setCurrentUser(dbHandler.getUser(username));
					v.getContext().startActivity(i); 
				}
				else{
					String errorMsg = "Please resolve the following errors:\n" +
							"";
					if(!validUsername)
						errorMsg += "\n- Usernames must be at least 5 characters long and may contain only letters, numbers, and underscores.";
					if(!userDoesntExists)
						errorMsg += "\n- The username you chose is already taken.";
					if(!validEmail){
						errorMsg += "\n- The email you provided is not a valid email address.";
					}
					if(!validPassword)
						errorMsg += "\n- Passwords must be at least 5 characters long and are case-sensitive.";
					anchor.showDialog(me, "Registration Error(s)", errorMsg);
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
	
	private Boolean validEmail(String email){
		if(email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}")){
		//name: letters, numbers, dash, underscore, plus, dot, percent (1 or more)
		//@, URL: letters, dash, numbers, dot
		//extension: letters from length 2 to 3 inclusive
			return true;
		}
		return false;
	}
	
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
