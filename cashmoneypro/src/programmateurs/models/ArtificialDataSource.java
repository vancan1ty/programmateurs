package programmateurs.models;

import java.sql.Timestamp;

import programmateurs.beans.Account;
import programmateurs.beans.Account.ACCOUNT_TYPE;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.Transaction.TRANSACTION_TYPE;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;

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
	Account account5 = new Account(5, 1, Account.ACCOUNT_TYPE.SAVINGS, "bigfoot_saves", 100);
	
	Category category0 = new Category(0, 5, "gasoline");
	Category category1 = new Category(1, 5, "costume repairs");
	
	Timestamp t0 = new Timestamp(1391626265);
	Timestamp t1 = new Timestamp(1391625280);
	Timestamp t2 = new Timestamp(1391629265);
	Timestamp t3 = new Timestamp(1391636265);

	Transaction transaction0 = new Transaction(0,5,TRANSACTION_TYPE.REBALANCE,"3-11-13", t0,false,new Category[]{});
	Transaction transaction1 = new Transaction(1,5,TRANSACTION_TYPE.REBALANCE,"3-12-13", t1,false,new Category[]{});
	Transaction transaction2 = new Transaction(2,5,TRANSACTION_TYPE.REBALANCE,"3-13-13", t2,false,new Category[]{});
	Transaction transaction3 = new Transaction(3,5,TRANSACTION_TYPE.REBALANCE,"3-14-13", t3,false,new Category[]{});
	Transaction transaction4 = new Transaction(4,5,TRANSACTION_TYPE.REBALANCE,"3-15-13", t0,false,new Category[]{});


	@Override
	public User[] getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account[] getAccountsForUser(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category[] getCategoriesForUser(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction[] getTransactionsForAccount(int accountID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User addUserToDB(String username, String passhash, String first,
			String last, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account addAccountToDB(int userID, ACCOUNT_TYPE accountType,
			String accountName, int interestRate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction addTransactionToDB(int accountID,
			TRANSACTION_TYPE transactionType, String transactionDate,
			Timestamp timestamp, boolean rolledback, Category[] categories) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category addCategoryToDB(int userID, String category_name) {
		// TODO Auto-generated method stub
		return null;
	}

}
