package com.sonora.android.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sonora.android.models.ListItem;
import com.sonora.android.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a utility class composed of methods to help with JSON-related operations.
 */

public class JsonUtil {

    /**
     * This method converts a JSONArray to a List of Strings.
     * @param json JSONArray to be converted
     * @return newly created String list
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static List<String> arrayToStringList(JSONArray json) throws JSONException {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            result.add((String) json.get(i));
        }

        return result;
    }

    /**
     * This method converts a JSONArray to a List of longs.
     * @param json JSONArray to be converted
     * @return newly created long list
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static List<Long> arrayToLongList(JSONArray json) throws JSONException {
        List<Long> result = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            result.add((long) json.get(i));
        }

        return result;
    }

    /**
     * This method converts a JSONArray to a List of ListItems.
     * @param json JSONArray to be converted
     * @return newly created ListItem list
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static List<ListItem> arrayToListItemList(JSONArray json) throws JSONException {
        List<ListItem> result = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            result.add(new ListItem(json.getJSONObject(i)));
        }

        return result;
    }

    /**
     * This method creates and returns a new User.  It is used when a user first creates his/her
     * account.
     * @param json JSONObject containing information of user to be created
     * @return newly created user
     */
    public static User createNewUser(JSONObject json) {
        // Get ID and email of current user from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        String email = user.getEmail();

        try {
            // Pull name from JSON
            String firstName = json.getString("first_name");
            String lastName = json.getString("last_name");

            // Return newly created user
            return new User(id, firstName, lastName, email);
        } catch (JSONException e) {
            // There was an error parsing the JSON
            e.printStackTrace();
            return null;
        }
    }

}
