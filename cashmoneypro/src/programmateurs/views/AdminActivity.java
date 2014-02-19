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


public class AdminActivity extends Activity {
	Button resetButton;
	Button logoutButton;
	TextView promptText;
	EditText userField;
	private RealDataSource dbHandler;
	Activity me = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		dbHandler = new RealDataSource(this);
		resetButton = (Button) findViewById(R.id.resetButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		promptText = (TextView) findViewById(R.id.promptText);
		userField = (EditText) findViewById(R.id.userField);
		
		logoutButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(v.getContext(), WelcomeActivity.class);
				v.getContext().startActivity(i);
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				final String username = userField.getText().toString();
				PromptDialog confirm = new PromptDialog(me,"Confirm Password Reset","Are you sure you want to enable password reset for "
						+username+" ?"){
					public boolean onOkClicked(String input){
						//if(username!=null && dbHandler.getUser(username)!=null){
							
						//} else{
							
						//}
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