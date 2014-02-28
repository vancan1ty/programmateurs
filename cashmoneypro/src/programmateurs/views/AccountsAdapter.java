package programmateurs.views;

import java.util.List;

import net.programmateurs.R;

import programmateurs.beans.Account;
import programmateurs.models.Anchor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//Useful example of how to do list adapters in android... 


/**
 * I'd ask Currell for more information on how this class works
 * Basically it make the view that fits inside a ListView object
 * 
 * @author currell
 * @version 0.0
 *
 */
public class AccountsAdapter extends BaseAdapter {

  private final Activity activity;
  List<Account> accounts ;

  public AccountsAdapter(Activity activity, List<Account> accounts) {
    this.activity = activity;
    this.accounts = accounts;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

	LayoutInflater inflater = (LayoutInflater)  activity
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.corqitemlayout, parent, false);
    TextView header = (TextView) rowView.findViewById(R.id.corqHeader);
    TextView body = (TextView) rowView.findViewById(R.id.corqText);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.corqImage);
    final Account q = accounts.get(position);
    String headerText = q.getAccountName();
    
    header.setText(headerText);
    body.setText(q.getAccountType().name() + " \n\n" + q.getInterestRate());

    imageView.setImageResource(R.drawable.cashmoney_icon);
    
    //onClickListener for each view
    //We can change this to go to an Activity when pressed later. 
    rowView.setOnClickListener(new OnClickListener() {
    	public void onClick(View v) {
    		Anchor.getInstance().showDialog(activity, "Details", "Account details");
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