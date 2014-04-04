package net.programmateurs.test;

/*
import static org.junit.Assert.*;

import org.junit.Test;

import programmateurs.beans.Account;
import programmateurs.beans.Category;
import programmateurs.beans.Transaction;
import programmateurs.beans.User; */

public class ArtificialDataSourceTest {

///*	public User getBigFoot() {
//		ArtificialDataSource mSrc = new ArtificialDataSource();
//		return mSrc.getUsers()[5];
//	}
//
//	@Test
//	public void testGetUsers() {
//		ArtificialDataSource mSrc = new ArtificialDataSource();
//		User[] users = mSrc.getUsers();
//		User justin = users[0];
//		assertTrue(justin.getUsername().equals("JNieto"));
//	}
//
//	@Test
//	public void testGetAccountsForUser() {
//		ArtificialDataSource mSrc = new ArtificialDataSource();
//		User bigfoot = mSrc.getUsers()[5];
//		Account[] accounts = mSrc.getAccountsForUser(bigfoot.getUserID());
//	}
//
//	@Test
//	public void testGetCategoriesForUser() {
//		ArtificialDataSource mSrc = new ArtificialDataSource();
//		User bigfoot = mSrc.getUsers()[5];
//		Category[] categories = mSrc.getCategoriesForUser(bigfoot.getUserID());
//	}
//
//	@Test
//	public void testGetTransactionsForAccount() {
//		ArtificialDataSource mSrc = new ArtificialDataSource();
//		User bigfoot = mSrc.getUsers()[5];
//		Account[] accounts = mSrc.getAccountsForUser(bigfoot.getUserID());
//		Account bigfootAccount = accounts[0];
//		Transaction[] transactions = mSrc.getTransactionsForAccount(bigfootAccount.getAccountID());
//	}
//
//	@Test
//	public void testAddUserToDB() {
//		ArtificialDataSource mSrc = new ArtificialDataSource();
//		User[] users = mSrc.getUsers();
//		int origlen = users.length;
//		mSrc.addUserToDB("newuser", "bah", "one", "two", "three@three.com");
//
//		User[] nusers = mSrc.getUsers();
//		int newlen = nusers.length;
//		assertTrue(newlen==origlen+1);
//
//	}
//
////	@Test
////	public void testAddAccountToDB() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testAddTransactionToDB() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testAddCategoryToDB() {
////		fail("Not yet implemented");
////	}

}
