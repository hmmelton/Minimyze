package com.sonora.android.models;

import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model for a menu (collection of recipes).
 */

public class Menu {

    private long id;
    private String uid;
    private String imageUrl, userProfileImageUrl;
    private String name;
    private long[] appetizers, entrees, sides, desserts, drinks;
    private String[] tags;

    // Constructor
    public Menu(JSONObject json) {
        try {
            this.id = json.getLong("id");
            this.uid = json.getString("uid");
            this.imageUrl = json.getString("image");
            this.userProfileImageUrl = json.getString("user_prof_image");
            this.name = json.getString("name");
            JSONObject recipes = json.getJSONObject("recipes");
            this.appetizers = JsonUtil.arrayToLongArray(recipes.getJSONArray("appetizers"));
            this.entrees = JsonUtil.arrayToLongArray(recipes.getJSONArray("entrees"));
            this.sides = JsonUtil.arrayToLongArray(recipes.getJSONArray("sides"));
            this.desserts = JsonUtil.arrayToLongArray(recipes.getJSONArray("desserts"));
            this.drinks = JsonUtil.arrayToLongArray(recipes.getJSONArray("drinks"));
            this.tags = JsonUtil.arrayToStringArray(recipes.getJSONArray("tags"));
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public String getName() {
        return name;
    }

    public long[] getAppetizers() {
        return appetizers;
    }

    public long[] getEntrees() {
        return entrees;
    }

    public long[] getSides() {
        return sides;
    }

    public long[] getDesserts() {
        return desserts;
    }

    public long[] getDrinks() {
        return drinks;
    }

    public String[] getTags() {
        return tags;
    }
}
