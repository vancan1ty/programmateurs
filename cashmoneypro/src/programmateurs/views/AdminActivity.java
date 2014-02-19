package programmateurs.views;

import net.programmateurs.R;
import net.programmateurs.R.id;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
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

public class AdminActivity extends Activity {
	Button resetButton;
	Button logoutButton;
	TextView promptText;
	EditText userField;
	private RealDataSource dbHandler;
	Activity me = this;
	Anchor anchor = Anchor.getInstance();
	
	
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
							Random rand = new Random();
							String temp = "";
							for(int i=0; i<5; i++)
								temp+= rand.nextInt(10); //build random temp password
							user.setPasshash(temp);
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