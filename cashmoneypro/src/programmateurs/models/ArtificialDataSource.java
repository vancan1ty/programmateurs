package programmateurs.models;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.database.SQLException;
import android.util.Log;

import programmateurs.beans.Account;
import programmateurs.beans.Account.ACCOUNT_TYPE;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.util.DateUtility;

/**
 * This class provides sample data to support UI development before we get
 * the DB working completely
 * @author vancan1ty
 *
 */
public class ArtificialDataSource implements DataSourceInterface {
	
	User user0 = new User(0,"JNieto","pass","Justin","Nieto","justin@cool.cool");
	User user1 = new User(1,"SaraCagle","pass","Sara","Cagle","sara@cool.cool");
	User user2 = new User(2,"pavelkomarov","pass","Pavel","Komarov","pavel@cool.cool");
	User user3 = new User(3,"bmcorvey","pass","Brent","McCorvey","brent@cool.cool");
	User user4 = new User(4,"vancan1ty","pass","Currell","Berry","currellberry@gmail.com");
	User user5 = new User(5,"bigfoot_forever","pass","BIG","FOOT","BIGFOOTNEEDSNOEMAIL");
	
	Account account0 = new Account(0, 4, Account.ACCOUNT_TYPE.CHECKING, "berrychecking", 0);
	Account account1 = new Account(1, 4, Account.ACCOUNT_TYPE.SAVINGS, "berrysavings", 10);
	Account account2 = new Account(2, 1, Account.ACCOUNT_TYPE.CHECKING, "sarachecking", 0);
	Account account3 = new Account(3, 0, Account.ACCOUNT_TYPE.CHECKING, "justinchecking", 0);
	Account account4 = new Account(2, 1, Account.ACCOUNT_TYPE.SAVINGS, "mccorveysavings", 0);
	Account account5 = new Account(5, 5, Account.ACCOUNT_TYPE.SAVINGS, "bigfoot_saves", 100);
	
	Category category0 = new Category(0, 5, "gasoline");
	Category category1 = new Category(1, 5, "costume repairs");
	
	Timestamp t0 = new Timestamp(1391626265);
	Timestamp t1 = new Timestamp(1391625280);
	Timestamp t2 = new Timestamp(1391629265);
	Timestamp t3 = new Timestamp(1391636265);

	Transaction transaction0;
	Transaction transaction1;
	Transaction transaction2;
	Transaction transaction3;
	Transaction transaction4;
	
	List<User> users;
	List<Account> accounts;
	List<Transaction> transactions;
	List<Category> categories;
	
	public ArtificialDataSource() {
		users = new ArrayList<User>(Arrays.asList(new User[]{user0, user1, user2, user3, user4, user5}));
		accounts = new ArrayList<Account>(Arrays.asList(new Account[]{account0, account1, account2, account3, account4, account5}));
		categories = new ArrayList<Category>(Arrays.asList(new Category[]{category0, category1}));

		try{
			Log.d("Artificial","HERE!!!");
			transaction0 = new Transaction(0,5,"first",TRANSACTION_TYPE.REBALANCE, 233,DateUtility.getDateFromString("3-11-13"), 
					"bla", t0,false,null);
/*			transaction1 = new Transaction(1,5,TRANSACTION_TYPE.REBALANCE, 542, DateUtility.getDateFromString("3-12-13"), 
					t1,false,new Category[]{});
			transaction2 = new Transaction(2,5,TRANSACTION_TYPE.DEPOSIT, 345, DateUtility.getDateFromString("3-13-13"), 
					t2,false,new Category[]{});
			transaction3 = new Transaction(3,5,TRANSACTION_TYPE.DEPOSIT, 756, DateUtility.getDateFromString("3-14-13"), 
					t3,false,new Category[]{});
			transaction4 = new Transaction(4,5,TRANSACTION_TYPE.REBALANCE, 126, DateUtility.getDateFromString("3-15-13"), 
					t0,false,new Category[]{}); */
		} catch (ParseException e) {
			Log.e("ArtificialDataSource", "failed to initialize transactions...");
		}

		transactions = new ArrayList<Transaction>(Arrays.asList(new Transaction[]{transaction0, transaction1, transaction2, transaction3, transaction4}));
	}
	
	@Override
	public boolean isUserInDB(String username, String password){
		return true;
	}


	@Override
	public User[] getUsers() {
		return users.toArray(new User[]{});
	}

	public User getUser(String username){
		for(User user: getUsers()){
			if(user.getUsername().equals(username))
				return user;
		}
		return null;
	}

	@Override
	public Account getAccountWithID(long accountID) {
		for (Account acct : accounts) {
			if (acct.getAccountID() == accountID) {
				return acct;
			}
		}
		return null;
	}

	@Override
	public Account[] getAccountsForUser(long userID) {
		//Case in point, why I can't wait for java 8 
		List<Account> outL = new ArrayList<Account>();

		for (Account account : accounts) {
			if (account.getUserID() == userID) {
				outL.add(account);
			}
		}

		return outL.toArray(new Account[]{});
	}

	@Override
	public Category[] getCategoriesForUser(long userID) {

		//Case in point, why I can't wait for java 8 
		List<Category> outL = new ArrayList<Category>();

		for (Category category : categories) {
			if (category.getUserID() == userID) {
				outL.add(category);
			}
		}

		return outL.toArray(new Category[]{});

	}

	@Override
	public Transaction[] getTransactionsForAccount(long accountID) {
	//Case in point, why I can't wait for java 8 
		List<Transaction> outL = new ArrayList<Transaction>();

		for (Transaction transaction : transactions) {
				outL.add(transaction);
		}

		return outL.toArray(new Transaction[]{});
	}

	@Override
	public User addUserToDB(String username, String passhash, String first,
			String last, String email) {
		long lastuserID = users.get(users.size()-1).getUserID();
		User user = new User(lastuserID+1,username,passhash,first,last,email);
		users.add(user);
		return user;
	}

	@Override
	public Account addAccountToDB(long userID, ACCOUNT_TYPE accountType,
			String accountName, double interestRate) {
		long lastAccountID = accounts.get(accounts.size()-1).getAccountID();
		Account acct = new Account(lastAccountID+1,userID,accountType,accountName,interestRate);
		accounts.add(acct);
		return acct;
	}



	@Override
	public Category addCategoryToDB(long userID, String category_name) {
		long lastCatID = categories.get(categories.size()-1).getCategoryID();
		Category cat = new Category(lastCatID+1,userID,category_name);
		categories.add(cat);
		return cat;
	}



	@Override
	public void open() throws SQLException {
		//do nothing
	}



	@Override
	public void close() {
		// do nothing
	}



	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Transaction[] getTransactionsForUser(long userID) {//Pavel
		return null;
	}

	@Override
	public Transaction addTransactionToDB(long accountID,
			String transactionName, TRANSACTION_TYPE transactionType,
			long transactionAmount, Date transactionDate,
			String transactionComment, boolean rolledback, Category category) {
		// TODO Auto-generated method stub
		return null;
	}


}
