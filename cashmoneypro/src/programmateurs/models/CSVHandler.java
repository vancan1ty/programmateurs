package programmateurs.models;

import java.util.List;

import programmateurs.beans.Account;
import programmateurs.beans.Transaction;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.util.DateUtility;

import android.database.sqlite.SQLiteDatabase;

public class CSVHandler {

    /**
     * outputs the contents of the transactions table in csv format.
     * @param db an open db connection
     * @return
     */
    public static String TransactionsToCSV(RealDataSource src, long userID) {
        Transaction[] transactions = src.getTransactionsForUser(userID);
        StringBuilder out = new StringBuilder();
        for (Transaction trans : transactions) {
            out.append(transactionToLineItem(src, trans) + "\n");
        }
        return out.toString();
    }
    
    /**
     * escapes string for csv portrayal!
     * enclose with double quotes, and then precede double quotes within the 
     * string with a double quote itself.
     * 
     * following this spec:
     * http://tools.ietf.org/html/rfc4180
     * @param a raw string
     * @return a string with commas properly escaped.
     */
    public static String esc(String s) {
       StringBuilder out = new StringBuilder(); 
       out.append('"');
       for (int i = 0; i < s.length(); i++) {
           char c = s.charAt(i);
           if(c=='"') {
              out.append("\"\"");
           } else {
               out.append(c);
           }
       }
       out.append('"');
       return out.toString();
    }
    
    //puts a transaction in line item (csv) format.
    public static String transactionToLineItem(RealDataSource src, 
            Transaction transaction) {
        String out =
                esc(transaction.getTransactionName()) + "," +
                esc(transaction.getTransactionType().name()) + "," +
                esc(String.format("%.2f", 
                        transaction.getTransactionAmountAsDouble())) + "," +
                esc(transaction.getTransactionComment()) + "," +
                esc(transaction.getCategory().getCategory_name()) + "," +
                esc(src.getAccountWithID(transaction.getAccountID())
                    .getAccountName()) + "," +
                esc(DateUtility.getDateStringFromDate(
                        transaction.getTransactionDate())) + "," +
                esc(DateUtility.getDateTimeStringFromDate(
                        transaction.getTimestamp())) + "," +
                esc(Boolean.toString(transaction.isRolledback())); 
        return out;
    }
}
