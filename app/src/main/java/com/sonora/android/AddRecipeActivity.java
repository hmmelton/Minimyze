package com.sonora.android;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sonora.android.adapters.IngredientsListAdapter;
import com.sonora.android.models.Ingredient;
import com.sonora.android.models.Recipe;
import com.sonora.android.utils.DatabaseImageUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddRecipeActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    private final int PICK_IMAGE_REQUEST = 1;

    /* Resources */
    @BindString(R.string.error_ingredient_info)
    String mIngredientError;
    @BindString(R.string.error_instruction_info)
    String mInstructionError;
    @BindString(R.string.error_tag_info)
    String mTagError;
    @BindString(R.string.error_recipe_info)
    String mRecipeError;

    /* Views */
    @BindView(R.id.add_image_iv)
    ImageView mRecipeImage;
    @BindView(R.id.et_recipe_name)
    EditText mNewRecipeName;
    // Ingredient views
    @BindView(R.id.et_ingredient_name)
    EditText mNewIngredientName;
    @BindView(R.id.et_ingredient_count)
    EditText mNewIngredientCount;
    @BindView(R.id.spinner_ingredient)
    Spinner mNewIngredientSpinner;
    @BindView(R.id.rv_new_recipe_ingredients)
    RecyclerView mNewIngredientsRecycler;
    // Instruction views
    @BindView(R.id.et_instruction_name)
    EditText mNewInstructionName;
    @BindView(R.id.rv_new_recipe_instructions)
    RecyclerView mNewInstructionsRecycler;
    // Tag views
    @BindView(R.id.et_tag_name)
    EditText mNewTagName;
    @BindView(R.id.rv_new_recipe_tags)
    RecyclerView mNewTagsRecycler;

    /* OnClick Listeners */
    // Add image section
    @OnClick(R.id.add_image_layout)
    void onAddImageClick() {
        Intent intent = new Intent();
        // Show images
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Show picker in case multiple options are available
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    // Add ingredient
    @OnClick(R.id.button_add_ingredient)
    void onAddIngredientClick() {
        if (mNewIngredientName.getText().length() == 0 ||
                mNewIngredientCount.getText().length() == 0) {
            // One or more empty fields
            Toast.makeText(AddRecipeActivity.this, mIngredientError, Toast.LENGTH_LONG).show();
        } else {
            // Gather ingredient info
            String ingredientName = mNewIngredientName.getText().toString();
            String ingredientCountType = mNewIngredientSpinner.getSelectedItem().toString();
            double ingredientCount;
            try {
                ingredientCount = Double.valueOf(mNewIngredientCount.getText().toString());
            } catch (Exception e) {
                // Error parsing to double
                e.printStackTrace();
                return;
            }
            // Create new ingredient
            Ingredient ingredient =
                    new Ingredient(ingredientName, ingredientCount, ingredientCountType);
            // Add ingredient to adapter
            ((IngredientsListAdapter) mNewIngredientsRecycler.getAdapter())
                    .addIngredient(ingredient);
        }
    }

    /* Methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        ButterKnife.bind(this);

        // Hide keyboard unless EditText is tapped
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Add back button and title to action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_add_recipe_activity);
        }

        // Set up views with adapters
        initSpinner();
        initRecyclerViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            // Get location Uri
            Uri uri = data.getData();
            Glide.with(this).load(uri).into(mRecipeImage);
            // Upload image to database
            // TODO: This should be in OnClick for submit button
            try {
                DatabaseImageUtil.uploadImageToDatabase(uri, getContentResolver());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Back button was pressed -- return to sending activity
            setResult(RESULT_OK);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method sets up the Activity's various RecyclerViews.
     */
    private void initRecyclerViews() {
        // Set adapter for
        LinearLayoutManager ingredientLLM =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        IngredientsListAdapter ingredientAdapter = new IngredientsListAdapter(new ArrayList<>());
        mNewIngredientsRecycler.setLayoutManager(ingredientLLM);
        mNewIngredientsRecycler.setAdapter(ingredientAdapter);
    }

    /**
     * This sets up the ingredient Spinner.
     */
    private void initSpinner() {
        // Generate array of options from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.count_types,
                android.R.layout.simple_spinner_item /* default spinner layout */);
        // Specify layout to use when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set adapter to spinner
        mNewIngredientSpinner.setAdapter(adapter);
    }
}
