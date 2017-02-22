package com.sonora.android.models;

/**
 * Created by harrisonmelton on 2/5/17.
 * This is a data model for a recipe/shopping list ingredient.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class holds information for each item in a shopping list.
 */
public class ListItem {

    private int count; // Number of item
    private String itemName, countType, category; // Name of item and count type (gallon, pound, etc.)

    /**
     * Constructor
     * @param json JSON Object holding information for the individual item
     */
    public ListItem(JSONObject json) {
        try {
            this.count = json.getInt("count");
            this.itemName = json.getString("name");
            this.countType = json.getString("count_type");
            this.category = json.getString("category");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getter methods
    public int getCount() {
        return count;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCountType() {
        return countType;
    }

    public String getCategory() {
        return category;
    }
}
