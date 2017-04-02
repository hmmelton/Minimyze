package com.sonora.android.utils;

/**
 * Created by harrisonmelton on 3/14/17.
 * This singleton is used to store global constants.
 */

public class Constants {
    public static int ADD_RECIPE_INTENT;

    // Keys for sending intent data from recipe parts back to AddRecipeActivity
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String INSTRUCTIONS_KEY = "instructions";
    public static final String TAGS_KEY = "tags";
}
