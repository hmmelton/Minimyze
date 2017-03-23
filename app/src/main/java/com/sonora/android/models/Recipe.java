package com.sonora.android.models;

import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model for an individual recipe.
 */

public class Recipe {

    String id;
    String uid;
    String firstName;
    String lastName;
    String name;
    String imageUrl;
    List<String> instructions;
    List<String> tags;
    List<Ingredient> ingredients;
    boolean featured = false;
    String dateCreated;
    String dateModified;

    // Empty constructor required by Firebase
    public Recipe() {}

    // Constructor
    public Recipe(JSONObject json) {
        try {
            this.id = json.getString("id");
            this.uid = json.getString("uid");
            this.firstName = json.getString("first_name");
            this.lastName = json.getString("last_name");
            this.name = json.getString("name");
            this.imageUrl = json.getString("image");
            this.instructions = JsonUtil.arrayToStringList(json.getJSONArray("instructions"));
            this.ingredients = JsonUtil.arrayToListItemList(json.getJSONArray("ingredients"));
            this.tags = JsonUtil.arrayToStringList(json.getJSONArray("tags"));
            this.featured = json.getBoolean("featured");
            this.dateCreated = json.getString("date_created");
            this.dateModified = json.getString("date_modified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getter methods

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getTags() {
        return tags;
    }
}
