package programmateurs.views;

import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.User;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import net.programmateurs.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * ReportFragment serves as a jump-off point for the user to build reports.
 * 
 * @author Currell
 * @version 0.0
 */
public class ReportFragment extends Fragment {

	private User user;
	Anchor anchor = Anchor.getInstance();
	private RealDataSource dbHandler;	 
	private Button buttonSpendingCategory;
	private Button buttonIncomeSource;
	
	/**
	 * The onCreateView method creates the view for the fragment. I have 
	 * onClickListeners to detect when the button is pressed and perform actions
	 * based on those events.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.report_fragment,
				container, false);
		
		buttonSpendingCategory = (Button) rootView.findViewById(R.id.button_spendingcategory);
		buttonIncomeSource = (Button) rootView.findViewById(R.id.button_incomesource);
		
		this.setHasOptionsMenu(true);
	  	buttonSpendingCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO implement
				Intent i = new Intent(v.getContext(), ReportBuilder.class);
				v.getContext().startActivity(i);
			}
		});


	  	buttonIncomeSource.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO implement
				Intent i = new Intent(v.getContext(), ReportBuilder.class);
				v.getContext().startActivity(i);
			}
		});


		return rootView;
	}
	@Override
	public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.report_fragment, menu);
			
	};
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		/*if (item.getItemId() == R.id.action_add_acount) {
			
		} */
		super.onOptionsItemSelected(item);
		return true;
	};
	
	/**
	 * The reason why all the database stuff is in the onResume() method
	 * is because of the nature of a Fragment's lifecycle. Whenever you use a 
	 * fragment you must put the database code inside onResume(). 
	 * Look up a Fragment's lifecycle on Google
	 */
	@Override
	public void onResume() {
		dbHandler = new RealDataSource(getActivity());
		dbHandler.open();
		user = anchor.getCurrentUser();
		Log.d("AccountsListFragment","user: " + user);
		Log.d("AccountsListFragment","dbHandler: " + dbHandler);
		dbHandler.getAccountsForUser(user.getUserID()); 
		super.onResume();
	}

	/**
	 * The method need each time RealDataSource is used in a class
	 */
	@Override
	public void onPause() {
		dbHandler.close();
		super.onPause();
	}
}
