package programmateurs.views;

import net.programmateurs.R;
import programmateurs.beans.User;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
    protected final void onCreate(final Bundle savedInstanceState) {
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
            public void onClick(final View v) {
                String name = nameField.getText().toString(); // takes in name
                                                              // field
                String email = emailField.getText().toString(); // takes email
                                                                // from field
                String username = usernameField.getText().toString(); // takes
                                                                      // username
                                                                      // from
                                                                      // field
                String password = passwordField.getText().toString(); // takes
                                                                      // password
                                                                      // from
                                                                      // field
                // Log.d("BERRY","username: " + username + " password: " +
                // password);

                String[] nameComponents = name.split(" ");
                String first = nameComponents[0];
                String last = nameComponents[nameComponents.length - 1];
                // TODO handle contingencies
                // dbHandler.cheapAddUserToDB(username, password);

                // check validity of username, password, and email, and if the
                // username is unique
                boolean validUsername = validUsername(username);
                boolean validPassword = validPassword(password);
                boolean validEmail = validEmail(email);
                // boolean validName = validName(name);
                boolean userExists = dbHandler.isUserInDB(username, password);

                if (!userExists && validUsername && validPassword && validEmail) { // user
                                                                                   // doesnt
                                                                                   // exist
                                                                                   // yet,
                                                                                   // username
                                                                                   // and
                                                                                   // pass
                                                                                   // is
                                                                                   // valid
                    dbHandler.addUserToDB(username, password, first, last,
                            email);
                    Intent i = new Intent(v.getContext(), HomeActivity.class);
                    User user = dbHandler.getUser(username);
                    anchor.setCurrentUser(user);
                    dbHandler.addCategoryToDB(user.getUserID(), "Misc."); // Adds
                                                                          // a
                                                                          // default
                                                                          // category
                                                                          // at
                                                                          // user
                                                                          // creation
                                                                          // to
                                                                          // avoid
                                                                          // brreaking
                    v.getContext().startActivity(i);
                } else {
                    String errorMsg = "Please resolve the following errors:\n"
                            + "";
                    if (userExists) {
                        errorMsg += "\n- The username you chose is already taken.";
                    }
                    /*
                     * if(!validName){ errorMsg +=
                     * "\n- Please correct your first and last name."; }
                     */
                    if (!validUsername) {
                        errorMsg += "\n- Usernames must be at least 5 characters long and may contain only letters, numbers, and underscores.";
                    }
                    if (!validEmail) {
                        errorMsg += "\n- The email you provided is not a valid email address.";
                    }
                    if (!validPassword) {
                        errorMsg += "\n- Passwords must be at least 5 characters long and are case-sensitive.";
                    }
                    anchor.showDialog(me, "Registration Error(s)", errorMsg);
                }

            }

        });
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

    /*
     * Regex to see if the username is valid
     *
     * Checks the username against a regular expression. The expression will
     * allow all letters (capital and lower case), all digits (0-9), and
     * underscores (_). The username is also required to be 5 or more characters
     * long.
     *
     * @param username the username string that the user input into the system.
     *
     * @return bool the username is valid, (True), or the username fails the
     * regex (False)
     *
     * @version 0.0
     */
    private Boolean validUsername(final String username) {
        if (username.matches("\\w{5,}")) {
            // letters, numbers, underscore length 5 or more
            return true;
        }
        return false;
    }

    /*
     * Regex to see if the password is valid
     *
     * Checks the password against a regular expression. The expression will
     * allow any characters (including whitespace). The password must be 5 or
     * more characters long.
     *
     * @param pass the password string that the user input into the system.
     *
     * @return bool the password is valid, (True), or the password fails the
     * regex (False)
     *
     * @version 0.0
     */
    private Boolean validPassword(final String pass) {
        if (pass.matches(".{5,}")) {
            // anything length 5 or more
            return true;
        }
        return false;
    }

    /*
     * Regex to see if the email is valid
     *
     * Checks the email against a regular expression. The expression will allow
     * the name portion: all letters (capital and lowercase), all digits (0-9),
     * period (.), underscore (_), percent (%), plus (+), and dash (-), one or
     * more times, followed by an @ symbol, followed by the URL portion: all
     * letters (capital and lowercase), all digits (0-9), period (.), and dash
     * (-), one or more times. The TLD: all letters (capital and lowercase), 2
     * to 3 characters in length.
     *
     * @param email the email string that the user input into the system.
     *
     * @return bool the email is valid, (True), or the email fails the regex
     * (False)
     *
     * @version 0.1
     */
    private Boolean validEmail(final String email) {
        if (email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
            // name: letters, numbers, dash, underscore, plus, dot, percent (1
            // or more)
            // @, URL: letters, dash, numbers, dot
            // extension: letters from length 2 to 3 inclusive
            return true;
        }
        return false;
    }

    /*
     * Regex to see if the name is valid
     *
     * Checks the name against a regular expression. The expression will allow a
     * first name: all letters (capital and lowercase), followed by an optional
     * dash and optional letters (capital and lowercase) (in case a user has a
     * hyphened name.) A spacing between the letters will be accepted as any
     * number of spaces, 1 or more. The last name: all letters (capital and
     * lowercase), followed by an optional dash and optional letters (capital
     * and lowercase). The regex will not allow more than two "names"; no more
     * than 2 words with whitespace in between them.
     *
     * @param name the real name string that the user input into the system.
     *
     * @return bool the name is valid, (True), or the namefails the regex
     * (False)
     *
     * @version 0.1
     */
    // currently does not work. will get back to later
    /*
     * private Boolean validName(String name){
     * if(name.matches("[A-Za-z]+[-]*[A-Za-z]*\\s+[A-Za-z]+[-]*[A-Za-z]*")){
     * return true; //DEBUG fix this regex //first name and last name are
     * expected, but any number of //names (more than 2) will be allowed //Must
     * start with a letter //allows dashes (could end in a dash, should we fix
     * this?) //names are separated by any number of spaces more than 0 } return
     * false; }
     */

    public final String getUsernameEntry() {
        return usernameField.getText().toString();
    }

    public final String getPasswordEntry() {
        return passwordField.getText().toString();
    }
}
