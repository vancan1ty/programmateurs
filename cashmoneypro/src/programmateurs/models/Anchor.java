package programmateurs.models;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import programmateurs.android_client.beans.SessionStatusObject;
import programmateurs.views.MainActivity;

import android.accounts.AuthenticatorException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

public class Anchor {
	public static String ROOTURL="bah";
	private static Anchor instance;	
	
	public SessionStatusObject sessionStatus = new SessionStatusObject();
	
	public static Anchor getInstance() {
		if (instance == null) {
			instance = new Anchor();
		}
		return instance;
	}

	private Anchor() {

	}


//	//demonstration of asynctask at least.  let's leave this for now
//	public class VerifyUserLogin extends
//			AsyncTask<MainActivity, Void, Boolean> {
//		MainActivity mActivity;
//		StringBuilder errResponseHeader = new StringBuilder();
//
//		@Override
//		// check to see if the credentials work
//		protected Boolean doInBackground(MainActivity... params) {
//			publishProgress(); //make sure that progress gets shown...
//			int count = params.length;
//			if (count != 1) { // no assert on android ;(
//				return false;
//			}
//
//			mActivity = params[0];
//
//			String username = mActivity.getUsernameEntry();
//			sessionStatus.setEmail(username);
//
//			String password = mActivity.getPasswordEntry();
//			sessionStatus.setPassword(password);
//			
//
//			boolean authSuccessful = true;
//			return authSuccessful;
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			mActivity.progress.dismiss();
//			if (result == true) { //then we logged in ok
//				Intent i = new Intent(mActivity, CheckInActivity.class);
//				mActivity.startActivity(i); 
//			} else { //unsuccessful!
//				showDialog(mActivity, "Authentication Failure", errResponseHeader.toString());
//			}
//			
//		}
//
//		@Override
//		protected void onPreExecute() {
//		}
//
//		@Override
//		protected void onProgressUpdate(Void... values) {
//			mActivity.progress.setTitle("Logging you in.");
//			mActivity.progress.setMessage("Please Wait...");
//			mActivity.progress.show();
//		}
//	}
	
	
	public static String readInputStream(java.io.InputStream in) {
	    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

	private abstract class ShowDialogPasser implements Runnable {
		protected String title;
		protected String message;
		protected Activity parent;

		public ShowDialogPasser(String title, String message, Activity parent) {
			this.title = title;
			this.message = message;
			this.parent = parent;
		}

		public abstract void run();
	}

	public void showDialog(Activity activity, String title, String message) {

		activity.runOnUiThread(new ShowDialogPasser(title, message,
				activity) {
			public void run() {
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(parent);

				// 2. Chain together various setter methods to set the dialog
				// characteristics
				builder.setMessage(message).setTitle(title);

				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();

				dialog.show();
			}
		});
	}
}