package programmateurs.views;

import net.programmateurs.R;
import programmateurs.beans.Category;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * CategoryActivity is the screen in which users can add or delete categories.
 * it is accessed through the Options Menu in HomeActivity.
 * 
 * @author Justin
 * @version 0.1
 */
public class CategoryActivity extends Activity implements
        OnItemSelectedListener {
    private EditText categoryNameField;
    private Button addButton;
    private Button deleteButton;
    private Spinner categorySpinner;
    private DataSourceInterface dbHandler;
    private Anchor anchor;
    private ArrayAdapter<String> adapter;
    private User user;
    private Category[] categories;
    private Category toDelete;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbHandler = new RealDataSource(this);
        anchor = Anchor.getInstance();
        categoryNameField = (EditText) findViewById(R.id.name_field);
        addButton = (Button) findViewById(R.id.add_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        user = anchor.getCurrentUser();

        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinner.setOnItemSelectedListener(this);

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                addCategory(categoryNameField.getText().toString());
            }
        });

    }

    /**
     * Fills a spinner with options from user information in the database.
     */
    private void populateSpinner() {
        categories = dbHandler.getCategoriesForUser(user.getUserID());
        String[] categoryArray = new String[categories.length];
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                if (categories[i] != null) {
                    categoryArray[i] = categories[i].getCategory_name();
                }
            }
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int pos,
            final long id) {
        // toDelete = (Category) parent.getItemAtPosition(pos); //THIS METHOD
        // DOES NOT WORK YET. Will come back later.
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {
        // intentionally empty
    }

    /**
     * Adds a user-defined spending category to the database.
     * 
     * @param name
     */
    private void addCategory(final String name) {
        dbHandler.addCategoryToDB(anchor.getCurrentUser().getUserID(), name);
        anchor.showDialog(this, "Add Category", addButton.getText()
                + " was added to categories!");
        categoryNameField.setText("");
        populateSpinner();
    }

    /**
     * Removes a user-defined category from the database.
     */
    private void deleteCategory() {
        // TODO set up a way to remove categories from database AND transactions
    }

    @Override
    public final void onResume() {
        super.onResume();
        dbHandler.open();
        populateSpinner();
    }

    @Override
    public final void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }

}
