package com.sonora.android.utils;

import com.sonora.android.R;
import com.sonora.android.SonoraApplication;
import com.sonora.android.models.PostResponse;
import com.sonora.android.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by harrisonmelton on 2/20/17.
 * This is a utility file to help with database interactions.
 */

public interface SonoraApi {

    // Base URL for API
    String BASE_URL =
            SonoraApplication.getInstance().getString(R.string.heroku_base_url);
    // Retrofit instance
    Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /* Recipe Methods */

    /**
     * This method queries for a recipe by its ID
     * @param recipeId ID of recipe to be fetched
     * @return Recipe with passed ID
     */
    @GET("recipe")
    Call<Recipe> getRecipeById(@Query("id") String recipeId);

    /**
     * This method adds a recipe to the database
     * @param recipe recipe to be added to database
     * @return server's response
     */
    @FormUrlEncoded
    @POST("recipe")
    Call<PostResponse> addRecipe(@FieldMap Recipe recipe);

    /**
     * This method deletes the recipe with the passed ID.
     * @param recipeId ID of recipe to be deleted
     * @return server's response
     */
    @DELETE("recipe")
    Call<PostResponse> deleteRecipe(@Query("id") String recipeId);

    /**
     * This method edits the recipe with the passed ID.
     * @param recipeId ID of recipe to be edited
     * @return server's response
     */
    @PUT("recipe")
    Call<PostResponse> editRecipe(@Query("id") String recipeId);

    /**
     * This method queries for recipes based on the passed user ID.
     * @param userId ID of user whose recipes are being fetched
     * @return List of user's Recipes
     */
    @GET("recipes/{user_id}")
    Call<List<Recipe>> getRecipesByUser(@Path("user_id") String userId);
}
