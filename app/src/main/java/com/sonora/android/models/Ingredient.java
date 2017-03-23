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
public class Ingredient {

    String id;
    double count; // Number of item
    String itemName;
    String countType;
    String category; // Name of item and count type (gallon, pound, etc.)

    // Empty constructor required by Firebase
    public Ingredient() {}

    /**
     * Constructor
     * @param json JSON Object holding information for the individual item
     */
    public Ingredient(JSONObject json) {
        try {
            this.id = json.getString("id");
            this.count = json.getDouble("count");
            this.itemName = json.getString("name");
            this.countType = json.getString("count_type");
            this.category = json.getString("category");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getter methods
    public double getCount() {
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
