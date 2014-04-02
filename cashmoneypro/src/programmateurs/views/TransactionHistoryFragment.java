package programmateurs.views;

import java.util.ArrayList;
import java.util.List;

import programmateurs.beans.Transaction;
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
 * TransactionHistory Fragment class is part of HomeActivity. HomeActivity is an
 * Activity and account fragment makes up the Accounts tab of Home Activity.
 * 
 * @author Pavel
 * @version 0.0
 */
public class TransactionHistoryFragment extends Fragment {

	/**
	 * The fragment argument representing the section number for this fragment.
	 */

	private static Transaction[] transactionArray;
	Anchor anchor = Anchor.getInstance();
	private User user;
	private RealDataSource dbHandler;
	private ListView accountView;

	private TransactionAdapter adapter;

	/**
	 * The onCreateView method creates the view for the fragment. I have
	 * onClickListeners to detect when the button is pressed and perform actions
	 * based on those events.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_global_feed,
				container, false);

		accountView = (ListView) rootView.findViewById(R.id.listViewAccounts);

		return rootView;
	}

	/**
	 * The reason why all the database stuff is in the onResume() method is
	 * because of the nature of a Fragment's lifecycle. Whenever you use a
	 * fragment you must put the database code inside onResume(). Look up a
	 * Fragment's lifecycle on Google
	 */
	@Override
	public void onResume() {
		dbHandler = new RealDataSource(getActivity());
		dbHandler.open();
		user = anchor.getCurrentUser();
		dbHandler.getAccountsForUser(user.getUserID());
		transactionArray = dbHandler.getTransactionsForUser(user.getUserID());
		List<Transaction> transactionList = new ArrayList<Transaction>();
		for (int i = 0; i < transactionArray.length; i++) {
			transactionList.add(transactionArray[i]);
		}
		Log.d("TransactionsHistoryScreen", "transactions: " + transactionArray);
		adapter = new TransactionAdapter(getActivity(), transactionList);
		accountView.setAdapter(adapter);

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
