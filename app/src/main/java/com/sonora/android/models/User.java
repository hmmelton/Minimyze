package com.sonora.android.models;

import com.google.gson.annotations.SerializedName;

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

    // Empty constructor required by Firebase
    public User() {}

    // Constructor used when pulling from database
    public User(String id, String email, String firstName, String lastName,
                String profileImageUrl) {
        this.id = id;

    }

    // Getter and setter methods

    public String getId() {
        return id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
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
