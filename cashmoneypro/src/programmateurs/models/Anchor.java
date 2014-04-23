package programmateurs.models;

import programmateurs.beans.User;
import android.R;
import android.app.Activity;
import android.app.AlertDialog;

/**
 * This class is called Anchor, because it's intended to provide a central place
 * to store data accessible from everywhere in the app. Notice that it uses the
 * singleton pattern, so only one instance of this class can be instantiated
 * during a given runtime.
 * 
 * @author vancan1ty
 */
public final class Anchor {
    /**
     * the private instance of the class.
     */
    private static Anchor instance;
    /**
     * once the user is logged in, this represents the current user.
     */
    private User currentUser;
    /**
     * if the app is in test mode, the app will automatically log you in as the
     * test user.
     */
    public static final boolean TEST_MODE = false;

    /**
     * Acts as a selective constructor for Anchor class. If no instance of
     * Anchor exists, instantiates Anchor and returns that instance. Otherwise
     * it just returns the already-existing instance.
     * 
     * @return Current Anchor instance
     */
    public static Anchor getInstance() {
        if (instance == null) {
            instance = new Anchor();
        }
        return instance;
    }

    /**
     * hidden constructor.
     */
    private Anchor() {

    }

    // //demonstration of asynctask at least. let's leave this for now
    // public class VerifyUserLogin extends
    // AsyncTask<LoginActivity, Void, Boolean> {
    // LoginActivity mActivity;
    // StringBuilder errResponseHeader = new StringBuilder();
    //
    // @Override
    // // check to see if the credentials work
    // protected Boolean doInBackground(LoginActivity... params) {
    // publishProgress(); //make sure that progress gets shown...
    // int count = params.length;
    // if (count != 1) { // no assert on android ;(
    // return false;
    // }
    //
    // mActivity = params[0];
    //
    // String username = mActivity.getUsernameEntry();
    // sessionStatus.setEmail(username);
    //
    // String password = mActivity.getPasswordEntry();
    // sessionStatus.setPassword(password);
    //
    //
    // boolean authSuccessful = true;
    // return authSuccessful;
    // }
    //
    // @Override
    // protected void onPostExecute(Boolean result) {
    // mActivity.progress.dismiss();
    // if (result == true) { //then we logged in ok
    // Intent i = new Intent(mActivity, CheckInActivity.class);
    // mActivity.startActivity(i);
    // } else { //unsuccessful!
    // showDialog(mActivity, "Authentication Failure",
    // errResponseHeader.toString());
    // }
    //
    // }
    //
    // @Override
    // protected void onPreExecute() {
    // }
    //
    // @Override
    // protected void onProgressUpdate(Void... values) {
    // mActivity.progress.setTitle("Logging you in.");
    // mActivity.progress.setMessage("Please Wait...");
    // mActivity.progress.show();
    // }
    // }

    /**
     * Getter for the User that is currently logged in.
     * 
     * @return User currently logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * sets the current user for the app.
     * 
     * @param user
     *            nah.
     */
    public void setCurrentUser(final User user) {
        currentUser = user;
    }

    /**
     * Opens a dialog in the given activity with a given title and message. Used
     * to present information to the user.
     * 
     * @param activity
     *            Activity the dialog is to be displayed in
     * @param title
     *            Title of dialog
     * @param message
     *            Dialog message
     */
    public void showDialog(final Activity activity, final String title,
            final String message) {

        activity.runOnUiThread(new Runnable() {
            public void run() {
                // 1. Instantiate an AlertDialog.Builder with
                // its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                // 2. Chain together various setter methods to
                // set the dialog
                // characteristics
                builder.setMessage(message).setTitle(title);

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });
    }

}
