package programmateurs.views;

import java.util.List;

import net.programmateurs.R;

import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
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
import java.text.NumberFormat;
import java.util.Locale;

//Useful example of how to do list adapters in android... 

/**
 * I'd ask Currell for more information on how this class works Basically it
 * make the view that fits inside a ListView object.
 * 
 * @author currell
 * @version 0.2
 * 
 */
public class TransactionAdapter extends BaseAdapter {
    // CHECKSTYLE:OFF
    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    private final Activity activity;
    List<Transaction> transactions;

    // CHECKSTYLE:ON

    /**
     * Transaction adapter.
     * 
     * @param activity
     *            activity
     * @param transactions
     *            transactions
     */
    // CHECKSTYLE:OFF
    public TransactionAdapter(Activity activity, List<Transaction> transactions) {
        this.activity = activity;
        this.transactions = transactions;
    }

    // CHECKSTYLE:ON

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.unused_corqitemlayout, parent,
                false);
        TextView header = (TextView) rowView.findViewById(R.id.corqHeader);
        TextView body = (TextView) rowView.findViewById(R.id.corqText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.corqImage);
        final Transaction q = transactions.get(position);
        String headerText = q.getTransactionName();

        String bodyText = q.getTransactionType() + " of "
                + nf.format(q.getTransactionAmountAsDouble());
        bodyText += "\n" + q.getTransactionComment();

        header.setText(headerText);
        body.setText(bodyText);

        if (q.getTransactionType() == TRANSACTION_TYPE.DEPOSIT) {
            imageView.setImageResource(R.drawable.deposit_icon);
        } else if (q.getTransactionType() == TRANSACTION_TYPE.WITHDRAWAL) {
            imageView.setImageResource(R.drawable.withdraw_icon);
        } else {
            imageView.setImageResource(R.drawable.roll_back_icon);
        }

        // onClickListener for each view
        // We can change this to go to an Activity when pressed later.
        rowView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Anchor.getInstance()
                        .showDialog(activity,
                                q.getTransactionName() + ": Details",
                                q.formatDetails());
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
