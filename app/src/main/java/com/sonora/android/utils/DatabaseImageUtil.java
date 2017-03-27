package com.sonora.android.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by harrison on 3/27/17.
 * This is a utility file for uploading and downloading images from a database.
 */

public class DatabaseImageUtil {

    private final String TAG = getClass().getSimpleName();

    /**
     * This method uploads an image to the database.
     * @param uri Uri of image in device
     */
    public static void uploadImageToDatabase(Uri uri, ContentResolver resolver) throws IOException {
        // Create file from image
        String path;
        path = getPath(uri, resolver);
        if (path == null) {
            return;
        }
        File file = new File(path);
        // TODO: revisit this with Firebase
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
