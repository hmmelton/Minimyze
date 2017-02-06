package com.sonora.android.interfaces;

import android.net.Uri;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a callback listener for retrieving an image URI from Firebase Storage.
 */

public interface OnImageRetrievedListener {
    // Success retrieving image URI
    void onSuccess(Uri uri);

    // Failure retrieving image URI
    void onFailure(Exception exception);
}
