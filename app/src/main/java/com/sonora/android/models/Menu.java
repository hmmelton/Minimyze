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

    private long id;
    private String uid;
    private String imageUrl, userProfileImageUrl;
    private String name;
    private List<Long> appetizers, entrees, sides, desserts, drinks;
    private List<String> tags;
    private boolean featured = false;

    // Empty constructor required for reading from Firebase
    public Menu() {}

    // Constructor
    public Menu(JSONObject json) {
        try {
            this.id = json.getLong("id");
            this.uid = json.getString("uid");
            this.imageUrl = json.getString("image");
            this.userProfileImageUrl = json.getString("user_prof_image");
            this.name = json.getString("name");
            JSONObject recipes = json.getJSONObject("recipes");
            this.appetizers = JsonUtil.arrayToLongList(recipes.getJSONArray("appetizers"));
            this.entrees = JsonUtil.arrayToLongList(recipes.getJSONArray("entrees"));
            this.sides = JsonUtil.arrayToLongList(recipes.getJSONArray("sides"));
            this.desserts = JsonUtil.arrayToLongList(recipes.getJSONArray("desserts"));
            this.drinks = JsonUtil.arrayToLongList(recipes.getJSONArray("drinks"));
            this.tags = JsonUtil.arrayToStringList(recipes.getJSONArray("tags"));
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

    public List<Long> getAppetizers() {
        return appetizers;
    }

    public List<Long> getEntrees() {
        return entrees;
    }

    public List<Long> getSides() {
        return sides;
    }

    public List<Long> getDesserts() {
        return desserts;
    }

    public List<Long> getDrinks() {
        return drinks;
    }

    public List<String> getTags() {
        return tags;
    }
}
