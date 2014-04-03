package programmateurs.views;

import java.util.Locale;

import net.programmateurs.R;
import programmateurs.models.Anchor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The class sets up the main screen when the user logs in It has the
 * SectionsPagerAdapter, which allows the user to scroll to new tabs.
 * 
 * @author Currell
 * @version 0.0
 * 
 */
public class HomeActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	
	//CHECKSTYLE:OFF
    SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
    ViewPager mViewPager;
    Anchor anchor;
    String logout0 = "Log Out";
    //CHECKSTYLE:ON
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anchor = Anchor.getInstance();
        this.setTitle(anchor.getCurrentUser().getFirst());

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem settings = menu.add("Settings");
        settings.setIntent(new Intent(this, SettingsActivity2.class));
        
        MenuItem logout = menu.add(logout0);
        logout.setIntent(new Intent(this, WelcomeActivity.class));
        MenuItem categories = menu.add("Edit Categories");
        categories.setIntent(new Intent(this, CategoryActivity.class));
		// getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// Log.d("HomeActivity","Title: " + item.getTitle());
        if (item.getTitle().equals(logout0)) {
			// Anchor.getInstance().setCurrentUser(null);//NOTE THAT SETTING THE
			// USER TO NULL CAUSES THE PROGRAM TO CRASH IF THE USER HITS THE
			// BACK BUTTON AFTER LOGGING OUT
        }
        this.startActivity(item.getIntent());
        return true;
    }

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

    	/**
    	 * SectionsPagerAdapter.
    	 * 
    	 * @param fm fragment manager
    	 */
        public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
        }

        @Override
        public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
            Fragment fragment;
            if (position == 0) {
                fragment = new AccountListFragment();
            }
            else if (position == 1) {
                fragment = new TransactionHistoryFragment();
            }
            else {
                fragment = new ReportFragment();
				// Bundle args = new Bundle();
				// args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position
				// + 1);
				// fragment.setArguments(args);
            }
            return fragment;
        }

        @Override
        public int getCount() {
			// Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_accounts).toUpperCase(l);
                case 1:
                    return getString(R.string.title_history).toUpperCase(l);
                case 2:
                    return getString(R.string.title_reports).toUpperCase(l);
            }
            return null;
        }
    }

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
    public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
        public static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * DummySectionFragment. Maybe we don't need it? Who knows.
         */
        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
