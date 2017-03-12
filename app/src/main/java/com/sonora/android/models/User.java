package com.sonora.android.models;

import com.google.gson.annotations.SerializedName;
import com.sonora.android.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a model class for users.
 */

public class User implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("facebook_id")
    String facebookId;
    @SerializedName("google_id")
    String googleId;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("bio")
    String bio;
    @SerializedName("prof_image_url")
    String profileImageUrl;
    @SerializedName("public_recipes")
    List<String> publicRecipes;
    @SerializedName("private_recipes")
    List<String> privateRecipes;
    @SerializedName("menus")
    List<Long> menus;
    @SerializedName("is_private")
    boolean isPrivate;
    @SerializedName("followers")
    List<String> followers;
    @SerializedName("following")
    List<String> following;
    @SerializedName("date_created")
    String dateCreated;
    @SerializedName("date_modified")
    String dateModified;

    // Constructor used when pulling from database
    public User(JSONObject json) {
        try {
            this.id = json.getString("id");
            this.facebookId = json.getString("facebook_id");
            this.googleId = json.getString("google_id");
            this.firstName = json.getString("first_name");
            this.lastName = json.getString("last_name");
            this.email = json.getString("email");
            this.bio = json.getString("bio");
            this.profileImageUrl = json.getString("prof_image_url");
            this.publicRecipes = JsonUtil.arrayToStringList(json.getJSONArray("public_recipes"));
            this.privateRecipes = JsonUtil.arrayToStringList(json.getJSONArray("private_recipes"));
            this.menus = JsonUtil.arrayToLongList(json.getJSONArray("menus"));
            this.isPrivate = json.getBoolean("is_private");
            this.followers = JsonUtil.arrayToStringList(json.getJSONArray("num_followers"));
            this.following = JsonUtil.arrayToStringList(json.getJSONArray("num_following"));
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

    public List<String> getPublicRecipes() {
        return publicRecipes;
    }

    public List<String> getPrivateRecipes() {
        return privateRecipes;
    }

    public List<Long> getMenus() {
        return menus;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getFollowing() {
        return following;
    }
}
