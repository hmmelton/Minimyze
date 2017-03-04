package com.sonora.android.models;

import com.google.gson.annotations.SerializedName;
import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model for a menu (collection of recipes).
 */

public class Menu {

    @SerializedName("id")
    String id;
    @SerializedName("user_id")
    String uid;
    @SerializedName("image_url")
    String imageUrl;
    @SerializedName("prof_image")
    String userProfileImageUrl;
    @SerializedName("name")
    String name;
    @SerializedName("appetizers")
    List<Recipe> appetizers;
    @SerializedName("entrees")
    List<Recipe> entrees;
    @SerializedName("sides")
    List<Recipe> sides;
    @SerializedName("desserts")
    List<Recipe> desserts;
    @SerializedName("beverages")
    List<Recipe> beverages;
    @SerializedName("tags")
    List<String> tags;
    @SerializedName("featured")
    boolean featured = false;

    // Constructor
    public Menu(JSONObject json) {
        try {
            this.id = json.getString("id");
            this.uid = json.getString("uid");
            this.imageUrl = json.getString("image");
            this.userProfileImageUrl = json.getString("user_prof_image");
            this.name = json.getString("name");
            JSONObject recipes = json.getJSONObject("recipes");
            this.appetizers = JsonUtil.arrayToRecipeList(recipes.getJSONArray("appetizers"));
            this.entrees = JsonUtil.arrayToRecipeList(recipes.getJSONArray("entrees"));
            this.sides = JsonUtil.arrayToRecipeList(recipes.getJSONArray("sides"));
            this.desserts = JsonUtil.arrayToRecipeList(recipes.getJSONArray("desserts"));
            this.beverages = JsonUtil.arrayToRecipeList(recipes.getJSONArray("drinks"));
            this.tags = JsonUtil.arrayToStringList(recipes.getJSONArray("tags"));
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public String getName() {
        return name;
    }

    public List<Recipe> getAppetizers() {
        return appetizers;
    }

    public List<Recipe> getEntrees() {
        return entrees;
    }

    public List<Recipe> getSides() {
        return sides;
    }

    public List<Recipe> getDesserts() {
        return desserts;
    }

    public List<Recipe> getDrinks() {
        return beverages;
    }

    public List<String> getTags() {
        return tags;
    }
}
