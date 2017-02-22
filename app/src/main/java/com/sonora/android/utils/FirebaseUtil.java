package com.sonora.android.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sonora.android.models.Menu;
import com.sonora.android.models.Recipe;
import com.sonora.android.models.User;

/**
 * Created by harrisonmelton on 2/20/17.
 * This is a utility file to help with database interactions.
 */

public class FirebaseUtil {

    // Create database instance and sub-database references
    private static final DatabaseReference DATABASE = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference RECIPE_REFERENCE = DATABASE.child("recipes");
    private static final DatabaseReference MENU_REFERENCE = DATABASE.child("menus");
    private static final DatabaseReference USER_REFERENCE = DATABASE.child("users");

    /**
     * This method uploads a recipe to the database.
     * @param recipe Recipe to be uploaded
     */
    public static void uploadRecipe(Recipe recipe) {
        RECIPE_REFERENCE.child(String.format("%s", recipe.getId())).setValue(recipe);
    }

    /**
     * This method uploads a menu to the database.
     * @param menu Menu to be uploaded
     */
    public static void uploadMenu(Menu menu) {
        MENU_REFERENCE.child(String.format("%s", menu.getId())).setValue(menu);
    }

    /**
     * This method uploads a user to the database.
     * @param user User to be uploaded
     */
    public static void uploadUser(User user) {
        USER_REFERENCE.child(user.getId()).setValue(user);
    }

    /**
     * This method retrieves
     * @param uid ID of user whose recipes are being queried
     * @param callback callback for calling class to use data
     */
    public static void getRecipesByUser(String uid, ValueEventListener callback) {
        RECIPE_REFERENCE.orderByChild("uid").equalTo(uid).addValueEventListener(callback);
    }

    /**
     * This method retrieves the single recipe associated with the passed ID.
     * @param id ID of recipe to be retrieved
     * @param callback callback for calling class to use data
     */
    public static void getRecipeById(String id, ValueEventListener callback) {
        RECIPE_REFERENCE.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(callback);
    }

    /**
     * This method retrieves the user associated with the passed ID.
     * @param id ID of user to be retrieved
     * @param callback callback for calling class to use data
     */
    public static void getUserById(String id, ValueEventListener callback) {
        USER_REFERENCE.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(callback);
    }

}
