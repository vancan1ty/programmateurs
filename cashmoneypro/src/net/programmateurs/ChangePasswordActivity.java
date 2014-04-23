package net.programmateurs;

import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import programmateurs.views.ConfirmDialog;
import programmateurs.views.HomeActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Page that allows user to change password. Accessed through upper-right 
 * drop down menu.
 * 
 * @author Justin
 * @version 0.0
 */
public class ChangePasswordActivity extends Activity {
    private EditText passField;
    private EditText confirmField;
    private Button changePassButton;
    private User user;
    private Anchor anchor;
    private Activity me;
    private DataSourceInterface dbHandler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        
        passField = (EditText) findViewById(R.id.password_field);
        confirmField = (EditText) findViewById(R.id.confirmation_field);
        changePassButton = (Button) findViewById(R.id.change_password);
        anchor = Anchor.getInstance();
        user = anchor.getCurrentUser();
        dbHandler = new RealDataSource(this);
        me = this;
        
        changePassButton.setOnClickListener(new OnClickListener(){
            
            public void onClick(View v){
                if(checkPasswordValidity() && checkConfirmation()){
                    ConfirmDialog confirm = new ConfirmDialog(me,
                            "Confirm Password Change",
                            "Are you sure you want to change your password?") {
                        public boolean onOkClicked(String input) {
                            user.setPasshash(passField.getText().toString());
                            dbHandler.updateUser(user);
                            anchor.showDialog(me, "Success!", "Your password has been changed.");
                            return true;
                        }
                    };
                    confirm.show();
                    
                }
            }
            
        });
        
    }

    private boolean checkPasswordValidity(){
        boolean valid = passField.getText().toString().matches(".{5,}");
        if(!valid) anchor.showDialog(this, "Password Change Error",
                "Please choose a password that is at least five characters long.");
        return valid;
    }
    
    private boolean checkConfirmation(){
        boolean valid = passField.getText().toString().equals(confirmField.getText().toString());
        if(!valid) anchor.showDialog(this, "Password Change Error",
                "Password does not match password entered in the confirmation field.");
        return valid;
    }
    
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, HomeActivity.class));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.real_settings, menu);
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
