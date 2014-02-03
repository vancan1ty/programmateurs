package programmateurs.views;

import net.programmateurs.R;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends Activity {
	Button buttonLogin;
	Button buttonRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
}
