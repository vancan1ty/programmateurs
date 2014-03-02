package programmateurs.views;

import java.util.List;

import net.programmateurs.R;

import programmateurs.beans.Transaction;
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
public class TransactionAdapter extends BaseAdapter {

  private final Activity activity;
  List<Transaction> transactions ;

  public TransactionAdapter(Activity activity, List<Transaction> transactions) {
    this.activity = activity;
    this.transactions = transactions;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

	LayoutInflater inflater = (LayoutInflater)  activity
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.unused_corqitemlayout, parent, false);
    TextView header = (TextView) rowView.findViewById(R.id.corqHeader);
    TextView body = (TextView) rowView.findViewById(R.id.corqText);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.corqImage);
    final Transaction q = transactions.get(position);
    String headerText = q.getTransactionType() + " " + q.getTransactionAmount();
    
    header.setText(headerText);
    body.setText(q.toString());

    imageView.setImageResource(R.drawable.person_dark);
    
    //onClickListener for each view
    //We can change this to go to an Activity when pressed later. 
    rowView.setOnClickListener(new OnClickListener() {
    	public void onClick(View v) {
    		Anchor.getInstance().showDialog(activity, "Details", "Transaction details");
    	}
    });
    
    return rowView;
  }

  @Override
  public int getCount() {
	  return transactions.size();
  }

  @Override
  public Object getItem(int position) {
	  return transactions.get(position);
  }

  @Override
  public long getItemId(int position) {
	  return position;
  }

} 