package com.sonora.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sonora.android.adapters.IngredientsListAdapter;
import com.sonora.android.helpers.RecyclerViewHelper;
import com.sonora.android.models.Ingredient;
import com.sonora.android.utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddIngredientsActivity extends AppCompatActivity {

    // RecyclerView's adapter
    private IngredientsListAdapter mAdapter;
    // Views
    @BindView(R.id.et_ingredient_name)
    EditText mIngredientNameView;
    @BindView(R.id.et_ingredient_count)
    EditText mIngredientCountView;
    @BindView(R.id.sp_ingredient_ct_type)
    Spinner mIngredientCountTypeSpinner;
    @BindView(R.id.rv_add_ingredients)
    RecyclerView mRecyclerView;

    // OnClicks
    @OnClick(R.id.button_new_ingredient)
    void onAddClicked() {
        if (mIngredientNameView.getText().length() == 0
                || mIngredientCountView.getText().length() == 0) {
            Toast.makeText(AddIngredientsActivity.this, R.string.error_ingredient_info,
                    Toast.LENGTH_LONG).show();
            return;
        }
        // Fetch ingredient components
        String ingredientName = mIngredientNameView.getText().toString();
        try {
            // Parse count to double
            double ingredientCount = Double.parseDouble(mIngredientCountView.getText().toString());
            String ingredientCountType = (String) mIngredientCountTypeSpinner.getSelectedItem();
            // Construct ingredient from components
            Ingredient ingredient = new Ingredient(ingredientName, ingredientCount,
                    ingredientCountType);
            // Add ingredient to adapter
            mAdapter.addIngredient(ingredient);
            // Clear text from input views
            mIngredientNameView.setText("");
            mIngredientCountView.setText("");
        } catch (Exception e) {
            // An error occurred parsing the count type to a double
            e.printStackTrace();
            Toast.makeText(AddIngredientsActivity.this, R.string.error_ingredient_count_parse,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        ButterKnife.bind(this);
        // Hide keyboard unless EditText is tapped
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Set up screen content
        setUpActionBar();
        setUpRecyclerView();
        setUpSpinner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Back button was pressed -- return to sending activity

            // Get type of Ingredients list for Gson to use
            Type listOfIngredients = new TypeToken<List<Ingredient>>(){}.getType();
            // Convert Ingredients list to String
            String data = new Gson().toJson(mAdapter.getIngredients(), listOfIngredients);
            // Add data to intent
            Intent intent = new Intent();
            intent.putExtra(Constants.INGREDIENTS_KEY, data);
            // Send data back to calling activity
            setResult(RESULT_OK, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method sets up the Activity's RecyclerView.
     */
    private void setUpRecyclerView() {
        mAdapter = new IngredientsListAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Helper for RecyclerView
        RecyclerViewHelper helper = new RecyclerViewHelper(mRecyclerView);
        helper.addSwipeToRemove(this);
        helper.addHorizontalSeparatorLines();
    }

    /**
     * This method sets up the Activity's ActionBar.
     */
    private void setUpActionBar() {
        // Add back button and title to action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_add_ingredients);
        }
    }

    /**
     * This sets up the ingredient Spinner.
     */
     private void setUpSpinner() {
        // Generate array of options from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.count_types,
                android.R.layout.simple_spinner_item /* default spinner layout */);
        // Specify layout to use when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set adapter to spinner
        mIngredientCountTypeSpinner.setAdapter(adapter);
    }
}
