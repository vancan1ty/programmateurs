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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * Account Fragment class is part of HomeActivity. HomeActivity is an Activity
 * and account fragment makes up the Accounts tab of Home Activity.
 * 
 * @author brent
 * @version 0.0
 */
public class AccountListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */

    // CHECKSTYLE:OFF
    private static Account[] accountArray;
    Anchor anchor = Anchor.getInstance();
    private User user;
    private RealDataSource dbHandler;
    private Button newAccount;
    private ListView accountView;
    private AccountsAdapter adapter;

    // CHECKSTYLE:ON

    /**
     * The onCreateView method creates the view for the fragment. I have
     * onClickListeners to detect when the button is pressed and perform actions
     * based on those events.
     * 
     * @param inflater
     *            inflater
     * @param container
     *            container
     * @param savedInstanceState
     *            saved instance state
     * @return rootView is something
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account_listing,
                container, false);
        this.setHasOptionsMenu(true);

        newAccount = (Button) rootView.findViewById(R.id.newAccount);
        accountView = (ListView) rootView.findViewById(R.id.listViewAccounts);
        newAccount.setOnClickListener(new OnClickListener() {

            /**
             * If the user clicks new account button, the screen transitions to
             * NewAccountActivity
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewAccountActivity.class);
                v.getContext().startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu,
            android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.account_list_fragment, menu);
    };

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // if (item.getItemId() == R.id.action_add_acount) {
        //
        // }
        super.onOptionsItemSelected(item);
        return true;
    };

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
        // Log.d("AccountsListFragment", "user: " + user);
        // Log.d("AccountsListFragment", "dbHandler: " + dbHandler);
        dbHandler.getAccountsForUser(user.getUserID());
        accountArray = dbHandler.getAccountsForUser(user.getUserID());
        List<Account> accountList = new ArrayList<Account>();
        for (int i = 0; i < accountArray.length; i++) {
            accountList.add(accountArray[i]);
        }
        adapter = new AccountsAdapter(getActivity(), accountList);
        accountView.setAdapter(adapter);

        super.onResume();
    }

    /**
     * The method need each time RealDataSource is used in a class.
     */
    @Override
    public void onPause() {
        dbHandler.close();
        super.onPause();
    }
}
