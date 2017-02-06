package com.sonora.android.models;

import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model class for users.
 */

public class User {

    private long id;
    private String firstName, lastName, bio;
    private String profileImageUrl;
    private long[] publicRecipes, privateRecipes, menus;
    private boolean isPrivate;
    private long numFollowers, numFollowing;

    // Constructor
    public User(JSONObject json) {
        try {
            this.id = json.getLong("id");
            this.firstName = json.getString("first_name");
            this.lastName = json.getString("last_name");
            this.bio = json.getString("bio");
            this.profileImageUrl = json.getString("prof_image");
            this.publicRecipes = JsonUtil.arrayToLongArray(json.getJSONArray("public_recipes"));
            this.privateRecipes = JsonUtil.arrayToLongArray(json.getJSONArray("private_recipes"));
            this.menus = JsonUtil.arrayToLongArray(json.getJSONArray("menus"));
            this.isPrivate = json.getBoolean("is_private");
            this.numFollowers = json.getLong("num_followers");
            this.numFollowing = json.getLong("num_following");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getter methods


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long[] getPublicRecipes() {
        return publicRecipes;
    }

    public long[] getPrivateRecipes() {
        return privateRecipes;
    }

    public long[] getMenus() {
        return menus;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public long getNumFollowers() {
        return numFollowers;
    }

    public long getNumFollowing() {
        return numFollowing;
    }
}
