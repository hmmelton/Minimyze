package com.sonora.android.utils;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sonora.android.interfaces.OnCompleteListener;
import com.sonora.android.models.User;

/**
 * Created by harrisonmelton on 3/22/17.
 * This is a utility class to help with authentication processes
 */

public class AuthUtil {

    /**
     * This method handles user login
     * @param token
     */
    public static void handleFacebookLogin(AccessToken token, OnCompleteListener listener) {
        // Signed into Firebase -- get more user info
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Check if user is new
        DatabaseUtil.checkIfNewUser(id, new OnCompleteListener() {
            @Override
            public void complete(boolean result) {
                if (result) {
                    // User is new -- goToLoginPage
                    makeFBGraphRequest(token, id, new OnCompleteListener() {
                        @Override
                        public void complete(boolean result) {
                            // Successfully uploaded new user
                            listener.complete(true);
                        }

                        @Override
                        public void error(String message) {
                            // Error occurred uploading user
                            listener.error(message);
                        }
                    });
                } else {
                    // User is not new -- fetch from database
                    DatabaseUtil.getUser(id, new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Grab user and save to local storage
                            User user = dataSnapshot.getValue(User.class);
                            SharedPrefsUtil.saveUser(user);
                            listener.complete(true);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // An error occurred -- alert user
                            listener.error(databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void error(String message) {
                // Error occurred checking if new user -- alert user
                listener.error(message);
            }
        });
    }

    /**
     * This method calls the Facebook Graph API
     * @param token Facebook token from authenticated user
     * @param id ID of user
     * @param listener callback listener
     */
    private static void makeFBGraphRequest(AccessToken token, String id,
                                           OnCompleteListener listener) {
        GraphRequest request = GraphRequest.newMeRequest(
                token, (object, response) ->
                        // Graph request complete -- register custom user object on Firebase
                        DatabaseUtil.signUpWithFacebook(id, object, token.getUserId(), listener));
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
