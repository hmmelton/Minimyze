package com.sonora.android;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRecipeActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    private final int PICK_IMAGE_REQUEST = 1;

    @BindView(R.id.add_image_iv)
    ImageView mRecipeImage;
    // OnClickListener for add image section
    @OnClick(R.id.add_image_layout)
    void onAddImageClick() {
        Intent intent = new Intent();
        // Show images
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Show picker in case multiple options are available
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        ButterKnife.bind(this);

        // Add back button and title to action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_add_recipe_activity);
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
                uploadImageToDatabase(uri);
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
     * This method uploads an image to the database.
     * @param uri Uri of image in device
     */
    private void uploadImageToDatabase(Uri uri) throws IOException {
        // Create file from image
        File file = new File(String.valueOf(getPath(uri)));
        // Build body
        Log.d(TAG, getPath(uri));

        // TODO: make API request
    }

    /**
     * This method gets the absolute path of a uri
     * @param uri Uri whose path is being fetched
     * @return String representation of image's location
     */
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
