package net.programmateurs.test;


import programmateurs.models.DBHelper;
import programmateurs.models.RealDataSource;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

public class DatabaseTest extends AndroidTestCase {
	private DBHelper db;
	private RealDataSource src;
	
	@Override
	protected void setUp(){
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
		db = new DBHelper(context);
		System.out.println("runnin'");
		src=new RealDataSource(context);
		src.open();
		src.deleteAllFromDB();
	}
	
	public void testAddUser() {
		src.addUserToDB("vancan1ty", "blabla", "Currell", "Berry", "currellberry@gmail.com");
		src.addUserToDB("bigfoot", "bigfoot", "bigfoot", "bigfoot", "currellberry@gmail.com");
		Log.d("TESTER","number of users: " + src.getUsers().length);
		assertTrue(src.getUsers().length == 2);
	}

	public void tearDown() throws Exception{
		src.deleteAllFromDB();
		src.close();
		super.tearDown();
	}

}
