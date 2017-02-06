package com.sonora.android.models;

/**
 * Created by harrisonmelton on 2/5/17.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class holds information for each item in a shopping list.
 */
public class ListItem {

    private int count; // Number of item
    private String itemName, countType; // Name of item and count type (gallon, pound, etc.)

    /**
     * Constructor
     * @param json JSON Object holding information for the individual item
     */
    ListItem(JSONObject json) {
        try {
            this.count = json.getInt("count");
            this.itemName = json.getString("name");
            this.countType = json.getString("count_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getter methods
    protected int getCount() {
        return count;
    }

    protected String getItemName() {
        return itemName;
    }

    protected String getCountType() {
        return countType;
    }
}
