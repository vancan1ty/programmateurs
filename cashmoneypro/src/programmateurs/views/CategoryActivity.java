package programmateurs.views;

import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import net.programmateurs.R;
import net.programmateurs.R.layout;
import net.programmateurs.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * CategoryActivity is the screen in which users can add or delete categories.
 * it is accessed through the Options Menu in HomeActivity.
 * 
 * @author Justin
 * @version 0.0
 */
public class CategoryActivity extends Activity {
	private EditText categoryNameField;
	private Button addButton;
	private Button deleteButton;
	private Spinner categorySpinner;
	private DataSourceInterface dbHandler;
	private Anchor anchor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		dbHandler = new RealDataSource(this);
		anchor = Anchor.getInstance();
		categoryNameField = (EditText) findViewById(R.id.name_field);
		addButton = (Button) findViewById(R.id.add_button);
		deleteButton = (Button) findViewById(R.id.delete_button);
		categorySpinner = (Spinner) findViewById(R.id.category_spinner);
		
		addButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				addCategory(categoryNameField.getText().toString());
			}
		});
		
	}
	
	private void populateSpinner(){
		//TODO figure out how to populate spinner
	}
	
	private void addCategory(String name){
		dbHandler.addCategoryToDB(anchor.getCurrentUser().getUserID(), name);
		populateSpinner();
	}
	
	private void deleteCategory(){
		//TODO set up a way to remove categories from database AND transactions
	}
	
	@Override
	public void onResume(){
		dbHandler.open();
		super.onResume();
	}
	
	@Override
	public void onDestroy(){
		dbHandler.close();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}
	
	
	

}
