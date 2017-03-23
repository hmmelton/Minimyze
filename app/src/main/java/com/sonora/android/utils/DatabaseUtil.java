package com.sonora.android.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sonora.android.interfaces.OnCompleteListener;
import com.sonora.android.models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harrisonmelton on 2/20/17.
 * This is a utility file to help with database interactions.
 */

public class DatabaseUtil {

    private static final String TAG = "DatabaseUtil";

    // References to database objects
    private static final DatabaseReference DATABASE_REF =
            FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference USER_REF = DATABASE_REF.child("users");

    /**
     * This method checks whether or not the user is new.
     * @param id Id of user
     * @param listener completion listener
     */
    public static void checkIfNewUser(String id, OnCompleteListener listener) {
        Log.e(TAG, "checkIfNew");
        USER_REF.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "on data change");
                if (dataSnapshot.exists()) {
                    Log.e(TAG, "data exists");
                    // User is not new
                    listener.complete(false);
                } else {
                    Log.e(TAG, "data does not exist");
                    // User is new
                    listener.complete(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                // There was an error with the database.
                listener.error(databaseError.getMessage());
            }
        });
    }

    /**
     * This method signs the user up with Facebook
     * @param id user's ID in the Firebase database
     * @param data JSONObject containing user's Facebook information
     * @param fbId user's Facebook ID
     * @param listener completion listener
     */
    public static void signUpWithFacebook(String id, JSONObject data, String fbId,
                                          OnCompleteListener listener) {
        Log.e(TAG, "sign up with Facebook");
        try {
            // Pull values from JSONObject
            String email = data.getString("email");
            String firstName = data.getString("first_name");
            String lastName = data.getString("last_name");
            String profileUrl = data.getJSONObject("picture")
                    .getJSONObject("data")
                    .getString("url");
            // Create new user
            User user = new User(id, email, firstName, lastName, profileUrl);
            user.setFacebookId(fbId);
            // Save new user to local storage
            SharedPrefsUtil.saveUser(user);
            // Upload user to Firebase
            USER_REF.child(id).setValue(user);
            // Return
            listener.complete(true);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            // JSON parsing error
            e.printStackTrace();
            listener.error(e.getMessage());
        }
    }

    /**
     * This method gets the user whose ID is passed from the database.
     * @param id ID of user to be fetched
     * @param listener completion listener
     */
    public static void getUser(String id, ValueEventListener listener) {
        Log.e(TAG, "get user");
        USER_REF.child(id).addListenerForSingleValueEvent(listener);
    }

    /**
     * This method builds a user object after login.
     * @param id user's ID
     * @param email user's email address
     * @param firstName user's first name
     * @param lastName user's last name
     * @param profileImageUrl user's profile image URL
     * @return User built with basic information
     */
    private static User buildUser(String id, String email, String firstName, String lastName,
                                  String profileImageUrl) {
        Log.e(TAG, "build user");
        return new User(id, email, firstName, lastName, profileImageUrl);
    }
}
