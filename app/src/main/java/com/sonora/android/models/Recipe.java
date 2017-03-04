package com.sonora.android.models;

import com.google.gson.annotations.SerializedName;
import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model for an individual recipe.
 */

public class Recipe {

    @SerializedName("id")
    long id;
    @SerializedName("user_id")
    String uid;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("name")
    String name;
    @SerializedName("image_url")
    String imageUrl;
    @SerializedName("instructions")
    List<String> instructions;
    @SerializedName("tags")
    List<String> tags;
    @SerializedName("ingredients")
    List<Ingredient> ingredients;
    @SerializedName("featured")
    boolean featured = false;

    // Constructor
    public Recipe(JSONObject json) {
        try {
            this.id = json.getLong("id");
            this.uid = json.getString("uid");
            this.firstName = json.getString("first_name");
            this.lastName = json.getString("last_name");
            this.name = json.getString("name");
            this.imageUrl = json.getString("image");
            this.instructions = JsonUtil.arrayToStringList(json.getJSONArray("instructions"));
            this.ingredients = JsonUtil.arrayToListItemList(json.getJSONArray("ingredients"));
            this.tags = JsonUtil.arrayToStringList(json.getJSONArray("tags"));
            this.featured = json.getBoolean("featured");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getter methods

    public long getId() {
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
