package net.programmateurs.test;

import org.junit.Test;

import programmateurs.models.DBHelper;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class DatabaseTest extends AndroidTestCase {
	private DBHelper db;
	
	public void setup(){
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
		db = new DBHelper(context);
	}
	
	@Test
	public void testAddEntry(){
		
		
	}

	public void tearDown() throws Exception{
		db.close();
		super.tearDown();
	}
	

}
