package programmateurs.views;

import java.util.Random;

import net.programmateurs.R;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * AdminActivity is the page that the admin is taken to immediately upon logging
 * in. Provides a way for the admin to select an account to enable password
 * reset for and the button which he actually clicks to enable the reset.
 * Currently relies on the fact that the user will have a settings screen at
 * some point that will allow them to permanently change the password.
 *
 * @author Justin
 * @version 0.2
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
     * user desiring password reset), button to enable reset (creates temporary
     * password, displays that password), and button to logout.
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // instantiate dbHandler and UI stuff
        dbHandler = new RealDataSource(this);
        resetButton = (Button) findViewById(R.id.resetButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        promptText = (TextView) findViewById(R.id.promptText);
        userField = (EditText) findViewById(R.id.userField);

        // logout button sends user back to splash screen
        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(v.getContext(), WelcomeActivity.class);
                // anchor.setCurrentUser(null);
                v.getContext().startActivity(i);
            }
        });

        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String username = userField.getText().toString();

                // request confirmation of password reset
                ConfirmDialog confirm = new ConfirmDialog(me,
                        "Confirm Password Reset",
                        "Are you sure you want to enable password reset for "
                                + username + " ?") {
                    @Override
                    public boolean onOkClicked(final String input) {
                        String[] message = setTempPassword(username, dbHandler);
                        anchor.showDialog(me, message[0], message[1]);
                        return true;
                    }
                };

                if (username != null && !username.equals("")) {
                    confirm.show();
                } else {
                    anchor.showDialog(me, "Invalid Input",
                            "Please supply a username.");
                }
            }
        });
    }

    /**
     * Creates a random 5-digit password for given user and resets that user's
     * password in the given database, if a user with that username exists.
     * Otherwise, does nothing. To be called after database is opened.
     * 
     * @param username
     *            Username of user whose password is being reset
     * @param db
     *            the DataSource containing the login information to update
     * @return String array containing the header and message for the UI display
     */
    private String[] setTempPassword(final String username, final DataSourceInterface db) {
        User user = db.getUser(username);
        if (user != null) {
            String temp = "";
            Random rand = new Random();
            for (int i = 0; i < 5; i++)
             {
                temp += rand.nextInt(10); // build random temp password
            }
            user.setPasshash(temp);
            db.updateUser(user);
            return new String[] { "Password Reset",
                    "Password for " + username + " temporarily set to " + temp };
        } else {
            return new String[] { "Password Reset Error",
                    "Given user does not exist." };
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    protected final void onResume() {
        dbHandler.open();
        super.onResume();
    }

    @Override
    protected final void onPause() {
        dbHandler.close();
        super.onPause();
    }
}