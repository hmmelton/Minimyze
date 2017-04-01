package com.sonora.android.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sonora.android.interfaces.OnImageRetrievedListener;
import com.sonora.android.models.User;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by harrison on 3/27/17.
 * This is a utility file for uploading and downloading images from a database.
 */

public class ImageUtil {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();
    private static StorageReference mStorageRef;

    /**
     * This method uploads an image to the database.
     * @param uri Uri of image in device
     */
    @SuppressWarnings("VisibleForTests")
    public static void uploadImageToDatabase(Uri uri, ContentResolver resolver,
                                             OnImageRetrievedListener listener) throws IOException {
        // Create file from image
        String path;
        path = getPath(uri, resolver);
        if (path == null) {
            return;
        }
        // TODO: revisit this with Firebase
        User currentUser = SharedPrefsUtil.getUser();
        if (currentUser != null) {
            // Executed only if user is currently logged in (should be only state at this point)
            String id = currentUser.getId(); // Get current user's ID
            mStorageRef = FirebaseStorage.getInstance().getReference(); // Base reference to DB
            long timeInMillis = Calendar.getInstance().getTimeInMillis(); // Current time in ms
            // Create DB reference to specific image
            StorageReference imageRef = mStorageRef.child(
                    "images/" // Root reference to images folder
                            + id + "/" // Sub-folder by user ID
                            + String.valueOf(timeInMillis) // Image named for current time in ms
                            + ".png"); // End with PNG extension
            // Attempt to upload image to Firebase
            imageRef.putFile(Uri.fromFile(new File(path)))
                    .addOnSuccessListener(taskSnapshot -> {
                        // Success!
                        // Send url back to caller
                        listener.onSuccess(taskSnapshot.getDownloadUrl());
                    })
                    .addOnFailureListener(listener::onFailure); // Return error message to caller
        }
    }

    /**
     * This method gets the absolute path of a uri
     * @param uri Uri whose path is being fetched
     * @return String representation of image's location
     */
    private static String getPath(Uri uri, ContentResolver resolver) {
        String[] projection = { MediaStore.Images.Media.DATA };
        // Send query to MediaStore SQLite DB
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            return result;
        }

        // If method makes it here, cursor was null;
        return null;
    }
}
