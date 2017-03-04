package com.sonora.android.models;

/**
 * Created by harrisonmelton on 2/5/17.
 * This is a data model for a recipe/shopping list ingredient.
 */

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class holds information for each item in a shopping list.
 */
public class Ingredient {

    @SerializedName("id")
    String id;
    @SerializedName("count")
    int count; // Number of item
    @SerializedName("name")
    String itemName;
    @SerializedName("count_type")
    String countType;
    @SerializedName("category")
    String category; // Name of item and count type (gallon, pound, etc.)

    /**
     * Constructor
     * @param json JSON Object holding information for the individual item
     */
    public Ingredient(JSONObject json) {
        try {
            this.id = json.getString("id");
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
