package com.sonora.android.models;

import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model class for users.
 */

public class User {

    private String id;
    private String firstName, lastName, email, bio;
    private String profileImageUrl;
    private List<Long> publicRecipes, privateRecipes, menus;
    private boolean isPrivate;
    private List<Long> followers, following;

    // Constructor
    public User(JSONObject json) {
        try {
            this.id = json.getString("id");
            this.firstName = json.getString("first_name");
            this.lastName = json.getString("last_name");
            this.email = json.getString("email");
            this.bio = json.getString("bio");
            this.profileImageUrl = json.getString("prof_image_url");
            this.publicRecipes = JsonUtil.arrayToLongList(json.getJSONArray("public_recipes"));
            this.privateRecipes = JsonUtil.arrayToLongList(json.getJSONArray("private_recipes"));
            this.menus = JsonUtil.arrayToLongList(json.getJSONArray("menus"));
            this.isPrivate = json.getBoolean("is_private");
            this.followers = JsonUtil.arrayToLongList(json.getJSONArray("num_followers"));
            this.following = JsonUtil.arrayToLongList(json.getJSONArray("num_following"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Empty constructor required for reading from Firebase
    public User() {}

    // Constructor used when creating account
    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = null;
        this.profileImageUrl = null;
        this.publicRecipes = new ArrayList<>();
        this.privateRecipes = new ArrayList<>();
        this.menus = new ArrayList<>();
        this.isPrivate = false;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    // Getter methods

    public String getId() {
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

    public List<Long> getPublicRecipes() {
        return publicRecipes;
    }

    public List<Long> getPrivateRecipes() {
        return privateRecipes;
    }

    public List<Long> getMenus() {
        return menus;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<Long> getFollowers() {
        return followers;
    }

    public List<Long> getFollowing() {
        return following;
    }
}
