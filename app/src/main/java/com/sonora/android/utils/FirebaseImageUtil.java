package com.sonora.android.utils;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sonora.android.R;
import com.sonora.android.SonoraApplication;
import com.sonora.android.interfaces.OnImageRetrievedListener;

/**
 * Created by harrisonmelton on 2/5/17.
 * This is a utility class to help with storage database references.
 */

public class FirebaseImageUtil {

    @SuppressWarnings("unused")
    private static final String TAG = "FirebaseImageUtil";

    private static final String STORAGE_ROOT =
            SonoraApplication.getInstance().getResources().getString(R.string.firebase_storage_root);

    public static void getImageReference(OnImageRetrievedListener callback) {
        // Get reference to root
        StorageReference reference =
                FirebaseStorage.getInstance().getReferenceFromUrl(STORAGE_ROOT);
        // Get reference to specific image
        // TODO: remove hard code
        StorageReference sponsoredRef = reference.child("sponsored");
        StorageReference imageRef = sponsoredRef.child("vegan_burger.jpg");

        // Get reference to image's URI, and return it, or exception if there was one
        imageRef.getDownloadUrl().addOnSuccessListener(callback::onSuccess)
        .addOnFailureListener(callback::onFailure);
    }
}
