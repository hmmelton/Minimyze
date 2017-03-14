package com.sonora.android.fragments;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sonora.android.R;
import com.sonora.android.SonoraApplication;
import com.sonora.android.adapters.RecipesGridAdapter;
import com.sonora.android.custom.SpacesItemDecoration;
import com.sonora.android.models.Recipe;
import com.sonora.android.models.User;
import com.sonora.android.utils.AnimationUtil;
import com.sonora.android.utils.SharedPrefsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.codetail.animation.ViewAnimationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sonora.android.SonoraApplication.getApi;

/**
 * Created by harrisonmelton on 3/11/17.
 * This class controls the content of the profile page.
 */

public class ProfileFragment extends Fragment {

    /* Views */
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_posts)
    TextView mUserPosts;
    @BindView(R.id.user_followers)
    TextView mUserFollowers;
    @BindView(R.id.user_following)
    TextView mUserFollowing;
    @BindView(R.id.user_recipes_grid)
    RecyclerView mRecipesGrid;
    @BindView(R.id.profile_action_button)
    FloatingActionButton mFab;
    @BindView(R.id.add_recipe_layout)
    RelativeLayout mAddLayout;
    SwipeRefreshLayout mContent;

    /* Actions */

    // FAB OnClick handler
    @OnClick(R.id.profile_action_button)
    void onFabClick(View v) {
        // Get screen dimensions
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Android native animator
        Animator revealAnimation = ViewAnimationUtils.createCircularReveal(mAddLayout, size.x,
                size.y, 0, (float) Math.hypot(size.x, size.y));
        revealAnimation.setInterpolator(new DecelerateInterpolator());
        revealAnimation.setDuration(500);

        // FAB translation animation
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        AnimationUtil.translateAnimation(v, v.getWidth() + params.leftMargin + params.rightMargin,
                v.getHeight() + params.topMargin + params.bottomMargin,
                new TranslateAnimation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mAddLayout.setVisibility(View.VISIBLE);
                        revealAnimation.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                }, 250);
    }

    /* Dimens */
    @BindDimen(R.dimen.item_spacing)
    int itemSpacing;

    /* Colors */
    @BindColor(R.color.colorPrimary)
    int PRIMARY_COLOR;
    @BindColor(R.color.colorPrimaryDark)
    int PRIMARY_COLOR_DARK;


    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContent =
                (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, mContent);

        // Set FAB colors
        mFab.setBackgroundTintList(ColorStateList.valueOf(PRIMARY_COLOR));
        mFab.setRippleColor(PRIMARY_COLOR_DARK);

        mContent.setOnRefreshListener(this::getCurrentUserInfo);
        setUpRecyclerView();
        populateViews(SharedPrefsUtil.getUser());

        return mContent;
    }

    /**
     * This method fetches the currently-logged-in user from the database.
     */
    private void getCurrentUserInfo() {
        Call<User> request = SonoraApplication.getApi().getUser(SharedPrefsUtil.getUser().getId());
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mContent.setRefreshing(false);
                // Save potentially-updated user to local storage
                SharedPrefsUtil.saveUser(response.body());
                // Repopulate views with new info
                populateViews(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // There was an error -- alert user
                mContent.setRefreshing(false);
                Toast.makeText(getContext(), R.string.update_user_error, Toast.LENGTH_LONG).show();
                Log.e(TAG, "error updating user", t);
            }
        });
    }

    /**
     * This method populates the views associated with user data.
     * @param user User whose data is to be displayed
     */
    private void populateViews(User user) {
        // Set profile image
        Glide.with(getContext())
                .load(user.getProfileImageUrl())
                .into(mProfileImage);
        // Set user name
        mUserName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        // Set post, follower, following count
        mUserPosts.setText(String.format("%s",
                user.getPrivateRecipes().size() + user.getPublicRecipes().size()));
        mUserFollowers.setText(String.format("%s", user.getFollowers().size()));
        mUserFollowing.setText(String.format("%s", user.getFollowing().size()));

        // Make call to API to fetch current user's recipes
        Call<List<Recipe>> request = getApi().getRecipesByUser(user.getId());
        request.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                // Recipes pulled successfully -- update adapter
                ((RecipesGridAdapter) mRecipesGrid.getAdapter()).setRecipes(response.body());
                mContent.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // There was an error -- notify user
                mContent.setRefreshing(false);
                Toast.makeText(getContext(), R.string.update_user_error, Toast.LENGTH_LONG).show();
                Log.e(TAG, user.getId());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    /**
     * This method sets up the fragment's RecyclerView.
     */
    private void setUpRecyclerView() {
        mRecipesGrid
                .setLayoutManager(new GridLayoutManager(getContext(), 3 /* number of columns */));
        mRecipesGrid.addItemDecoration(new SpacesItemDecoration(itemSpacing));
        mRecipesGrid.setAdapter(new RecipesGridAdapter(new ArrayList<>()));
    }
}
