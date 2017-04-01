package com.sonora.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sonora.android.adapters.RecipeCompsAdapter;
import com.sonora.android.interfaces.ListItemClickListener;
import com.sonora.android.interfaces.OnImageRetrievedListener;
import com.sonora.android.models.Ingredient;
import com.sonora.android.utils.ImageUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRecipeActivity extends AppCompatActivity implements ListItemClickListener{

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    // Request flags
    private final int PICK_IMAGE_REQUEST = 1;
    private final int ADD_INGREDIENTS_REQUEST = 2;
    private final int ADD_INSTRUCTIONS_REQUEST = 3;
    private final int ADD_TAGS_REQUEST = 4;

    // New Recipe data
    private List<Ingredient> mIngredients;
    private List<String> mInstructions;
    private List<String> mTags;
    private Uri mImageUri;

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
    @BindView(R.id.rv_recipe_components)
    RecyclerView mRecyclerView;

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
        initRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            // Prepare image for upload
            prepareImageForUpload(data);
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
    private void initRecyclerView() {
        // Create adapter and layout manager
        RecipeCompsAdapter adapter = new RecipeCompsAdapter(this, this);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // Set adapter and layout manager to RecyclerView
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        // Create and add item decoration (divider lines)
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent; // Create intent to navigate to the appropriate Activity
        switch (clickedItemIndex) {
            case 0:
                intent = new Intent(AddRecipeActivity.this, AddIngredientsActivity.class);
                startActivity(intent);
            default: break;
        }
    }

    /**
     * This method prepares an image to be uploaded to the database.
     * @param data intent containing image Uri
     */
    private void prepareImageForUpload(Intent data) {
        // Get image's Uri
        Uri uri = data.getData();
        // Load image into ImageView
        Glide.with(this).load(uri).into(mRecipeImage);
        mImageUri = uri;
    }

    /**
     * This method uploads an image to the database
     * @param uri Uri of image to be uploaded
     */
    private void uploadImage(Uri uri) {
        try {
            // Try to upload image to database
            ImageUtil.uploadImageToDatabase(uri, getContentResolver(),
                    new OnImageRetrievedListener() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Call successful! Save Uri
                            mImageUri = uri;
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            // Error!
                            exception.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            // IOException occurred -- invalid URI?
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method uploads the new recipe.
     */
    private void uploadRecipe() {
        // TODO: upload recipe
        // TODO: upload recipe
    }

    /**
     * This sets up the ingredient Spinner.
     */
    /*private void initSpinner() {
        // Generate array of options from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.count_types,
                android.R.layout.simple_spinner_item /* default spinner layout );
        // Specify layout to use when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set adapter to spinner
        mNewIngredientSpinner.setAdapter(adapter);
    }*/
}
