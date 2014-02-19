package programmateurs.views;

import programmateurs.beans.Account;
import programmateurs.beans.User;
import programmateurs.models.ArtificialDataSource;
import net.programmateurs.DepositActivity;
import net.programmateurs.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private static Account[] accountArray;
	private Account account;
	private User[] users;
	private ArtificialDataSource data;
	
	Button depositFunds;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account,
				container, false);
		
		data = new ArtificialDataSource();	//Importing the data source so I can access the account and user info
		users = data.getUsers();
		accountArray = data.getAccountsForUser(users[0].getUserID());
		
		depositFunds = (Button) rootView.findViewById(R.id.depositFunds);		/*Returns null????*/
		
		TextView view = (TextView) rootView.findViewById(R.id.textViewAccountName);
	    view.setText(accountArray[0].getAccountName());		//Works but have to fix to work with current user logged in
	    
	    //Adds a new listener to the Deposit Funds button that will take 
	  	//User to new Screen with Input box to enter the amount of funds to deposit
	  	depositFunds.setOnClickListener(new OnClickListener() {

	  		@Override
	  		public void onClick(View v) {
	  			Intent i = new Intent(v.getContext(), DepositActivity.class);
	  			v.getContext().startActivity(i); 		
	  						
	  		}
	  					
	  	});

		return rootView;
	}
}