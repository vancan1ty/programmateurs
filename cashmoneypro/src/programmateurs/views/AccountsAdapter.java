package programmateurs.views;

import java.util.List;

import net.programmateurs.R;

import programmateurs.beans.Account;
import programmateurs.beans.Account.ACCOUNT_TYPE;
//import programmateurs.models.Anchor;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//Useful example of how to do list adapters in android... 

/**
 * I'd ask Currell for more information on how this class works Basically it
 * make the view that fits inside a ListView object.
 * 
 * @author currell
 * @version 0.0
 * 
 */
public class AccountsAdapter extends BaseAdapter {

	//CHECKSTYLE:OFF
	private final Activity activity;
	List<Account> accounts;
	

	/**
	 * Accounts adaptor.
	 * 
	 * @param activity the activity
	 * @param accounts the accounts
	 */
    public AccountsAdapter(Activity activity, List<Account> accounts) {
        this.activity = activity;
        this.accounts = accounts;
    }
    //CHECKSTYLE:ON

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.unused_corqitemlayout, parent,
				false);
        TextView header = (TextView) rowView.findViewById(R.id.corqHeader);
        TextView body = (TextView) rowView.findViewById(R.id.corqText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.corqImage);
        final Account q = accounts.get(position);
        String headerText = q.getAccountName();

        header.setText(headerText);
        body.setText("\nType: " + q.getAccountType() + "\nInterest rate: " + q.getInterestRate() + "%");

        ACCOUNT_TYPE type = q.getAccountType();
        if (type == ACCOUNT_TYPE.SAVINGS) {
            imageView.setImageResource(R.drawable.social_person);
        }
        else {
            imageView.setImageResource(R.drawable.person_dark);
        }

		// onClickListener for each view
		// We can change this to go to an Activity when pressed later.
        rowView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AccountDetailActivity.class);
                i.putExtra("accountID", q.getAccountID());
                v.getContext().startActivity(i);
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
