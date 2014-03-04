package programmateurs.views;

import net.programmateurs.R;
import net.programmateurs.R.id;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;
import programmateurs.models.RealDataSource;
import android.content.Intent;
import java.util.Random;
import programmateurs.beans.User;
import programmateurs.models.Anchor;
import programmateurs.interfaces.DataSourceInterface;

/**
 * AdminActivity is the page that the admin is taken to immediately upon
 * logging in. Provides a way for the admin to select an account to enable
 * password reset for and the button which he actually clicks to enable the reset.
 * Currently relies on the fact that the user will have a settings screen at some
 * point that will allow them to permanently  change the password.
 * 
 * @author Justin
 * @version 0.1
 */
public class AdminActivity extends Activity {
	Button resetButton;
	Button logoutButton;
	TextView promptText;
	EditText userField;
	private RealDataSource dbHandler;
	Activity me = this;
	Anchor anchor = Anchor.getInstance();
	
	
	/**
	 * Displays an editable text field (where admin should input username of
	 * user desiring password reset), button to enable reset (creates temporary password,
	 * displays that password), and button to logout.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		//instantiate dbHandler and UI stuff
		dbHandler = new RealDataSource(this);
		resetButton = (Button) findViewById(R.id.resetButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		promptText = (TextView) findViewById(R.id.promptText);
		userField = (EditText) findViewById(R.id.userField);
		
		//logout button sends user back to splash screen
		logoutButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(v.getContext(), WelcomeActivity.class);
//				anchor.setCurrentUser(null);
				v.getContext().startActivity(i);
			}
		});
		
		//reset button sets instance-only password for user
		resetButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				final String username = userField.getText().toString();
				
				//request confirmation of password reset
				ConfirmDialog confirm = new ConfirmDialog(me,"Confirm Password Reset","Are you sure you want to enable password reset for "
						+username+" ?"){
					public boolean onOkClicked(String input){
						User user = dbHandler.getUser(username);
						if(username!=null && user!=null){
							String temp = setTempPassword(user,dbHandler);
							anchor.showDialog(me,"Password Reset","Password for "+username+" temporarily set to "+temp);
						} else{
							anchor.showDialog(me,"Password Reset Error","Given user does not exist." );
						}
						return true;	
					}
				};
				confirm.show();
			}
		});
	}
	
	/**
	 * Creates a random 5-digit password for given user and resets that
	 * user's password in the given database. (Does no quality assurance)
	 * 
	 * @param user User whose password is being reset
	 * @param db the DataSource containing the login information to update
	 * @return password that User's password is temporarily set to
	 */
	private String setTempPassword(User user, DataSourceInterface db){
		Random rand = new Random();
		String temp = "";
		for(int i=0; i<5; i++)
			temp+= rand.nextInt(10); //build random temp password
		user.setPasshash(temp);
		db.updateUser(user);
		return temp;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
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
}