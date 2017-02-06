package com.sonora.android.utils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a utility class composed of methods to help with JSON-related operations.
 */

public class JsonUtil {

    /**
     * This method converts a JSONArray to an array of Strings.
     * @param json JSONArray to be converted
     * @return newly created String array
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static String[] arrayToStringArray(JSONArray json) throws JSONException {
        String[] result = new String[json.length()];

        for (int i = 0; i < json.length(); i++) {
            result[i] = (String) json.get(i);
        }

        return result;
    }

    /**
     * This method converts a JSONArray to an array of longs.
     * @param json JSONArray to be converted
     * @return newly created long array
     * @throws JSONException thrown if there is an error fetching an item from the JSONArray.
     */
    public static long[] arrayToLongArray(JSONArray json) throws JSONException {
        long[] result = new long[json.length()];

        for (int i = 0; i < json.length(); i++) {
            result[i] = (long) json.get(i);
        }

        return result;
    }

}
