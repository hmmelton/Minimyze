package com.sonora.android.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sonora.android.SonoraApplication;
import com.sonora.android.models.User;

/**
 * Created by harrisonmelton on 3/10/17.
 * This is a utility class to help with SharedPreferences
 */

public class SharedPrefsUtil {

    // SharedPreferences and Gson setup
    private static final String PREFS_NAME = "sonora_preferences";
    private static final SharedPreferences mPrefs =
            SonoraApplication.getInstance().getSharedPreferences(PREFS_NAME, 0);
    private static final SharedPreferences.Editor mEditor = mPrefs.edit();
    private static final Gson mGson = new Gson();

    // Key strings
    private static final String USER_KEY = "user";

    /**
     * This method saves a user to local storage.
     * @param user User to be saved
     */
    public static void saveUser(User user) {
        String json = mGson.toJson(user);
        mEditor.putString(USER_KEY, json);
        mEditor.commit();
    }

    /**
     * This method returns the current user form local storage.
     * @return Currently-signed in user
     */
    public static User getUser() {
        String json = mPrefs.getString(USER_KEY, null);
        return json == null ? null : mGson.fromJson(json, User.class);
    }

    /**
     * This method signs the current user out of local storage.
     */
    public static void signOut() {
        mEditor.remove(USER_KEY);
        mEditor.commit();
    }
}
