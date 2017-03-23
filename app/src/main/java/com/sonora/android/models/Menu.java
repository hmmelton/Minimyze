package com.sonora.android.models;

import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model for a menu (collection of recipes).
 */

public class Menu {

    String id;
    String uid;
    String imageUrl;
    String userProfileImageUrl;
    String name;
    List<Recipe> appetizers;
    List<Recipe> entrees;
    List<Recipe> sides;
    List<Recipe> desserts;
    List<Recipe> beverages;
    List<String> tags;
    boolean featured = false;
    String dateCreated;
    String dateModified;

    // Empty constructor required by Firebase
    public Menu() {}

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
