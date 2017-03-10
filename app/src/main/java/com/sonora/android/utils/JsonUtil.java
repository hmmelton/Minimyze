package com.sonora.android.utils;

import com.sonora.android.models.Ingredient;
import com.sonora.android.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;

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
     * This method converts a JSONArray to a List of Recipes.
     * @param json JSONArray to be converted
     * @return newly created Recipe list
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static List<Recipe> arrayToRecipeList(JSONArray json) throws JSONException {
        List<Recipe> result = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            // Add a new Recipe to result
            result.add(new Recipe(json.getJSONObject(i)));
        }

        return result;
    }

    /**
     * This method converts a JSONArray to a List of ListItems.
     * @param json JSONArray to be converted
     * @return newly created Ingredient list
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static List<Ingredient> arrayToListItemList(JSONArray json) throws JSONException {
        List<Ingredient> result = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            result.add(new Ingredient(json.getJSONObject(i)));
        }

        return result;
    }
}
