package programmateurs.views;

import programmateurs.beans.Category;
import programmateurs.beans.User;
import programmateurs.interfaces.DataSourceInterface;
import programmateurs.models.Anchor;
import programmateurs.models.RealDataSource;
import net.programmateurs.R;
import android.os.Bundle;
import android.app.Activity;
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

    // CHECKSTYLE:OFF
    private EditText categoryNameField;
    private Button addButton;
    @SuppressWarnings("unused")
    private Button deleteButton;
    private Spinner categorySpinner;
    private DataSourceInterface dbHandler;
    private Anchor anchor;
    private ArrayAdapter<String> adapter;
    private User user;
    private Category[] categories;
    @SuppressWarnings("unused")
    private Category toDelete;

    // CHECKSTYLE:ON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            public void onClick(View v) {
                addCategory(categoryNameField.getText().toString());
            }
        });

    }

    /**
     * Populates the spinner.
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
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
            long id) {
        // toDelete = (Category) parent.getItemAtPosition(pos); //THIS METHOD
        // DOES NOT WORK YET. Will come back later.
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // intentionally empty
    }

    /**
     * Adds a category to the database.
     * 
     * @param name
     *            name of category?
     */
    private void addCategory(String name) {
        dbHandler.addCategoryToDB(anchor.getCurrentUser().getUserID(), name);
        anchor.showDialog(this, "Add Category", addButton.getText()
                + " was added to categories!");
        categoryNameField.setText("");
        populateSpinner();
    }

    /**
     * Needs to be done. A way to remove categories from database and
     * transactions.
     */
    @SuppressWarnings("unused")
    private void deleteCategory() {
        // TODO set up a way to remove categories from database AND transactions
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHandler.open();
        populateSpinner();
    }

    @Override
    public void onDestroy() {
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
