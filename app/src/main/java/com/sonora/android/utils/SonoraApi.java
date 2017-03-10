package com.sonora.android.utils;

import com.sonora.android.models.PostResponse;
import com.sonora.android.models.Recipe;
import com.sonora.android.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by harrisonmelton on 2/20/17.
 * This is a utility file to help with database interactions.
 */

public interface SonoraApi {

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

    /* User Methods */

    /**
     * This method queries the database for a user.
     * @param Id ID of user to be queried
     * @return User
     */
    @GET("user")
    Call<User> getUser(@Query("id") String Id);

    /**
     * The method signs in a user, or registers him/her if no user currently exists
     * @param authId ID used to authenticate user
     * @param authProvider provider type (currently FB or GGL)
     * @param email new user's email address
     * @param firstName new user's first name
     * @param lastName new user's last name
     * @return newly created User
     */
    @POST("sign_in")
    Call<User> signIn(@Query("auth_id") String authId, @Query("auth_provider") String authProvider,
                      @Query("email") String email, @Query("first_name") String firstName,
                      @Query("last_name") String lastName);

    /* Photo Methods */

    @Multipart
    @POST("photo")
    Call<PostResponse> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);
}
