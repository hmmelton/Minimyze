package com.sonora.android.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sonora.android.AddRecipeActivity;
import com.sonora.android.R;
import com.sonora.android.adapters.RecipesGridAdapter;
import com.sonora.android.custom.SpacesItemDecoration;
import com.sonora.android.models.User;
import com.sonora.android.utils.Constants;
import com.sonora.android.utils.DatabaseUtil;
import com.sonora.android.utils.SharedPrefsUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    SwipeRefreshLayout mContent;

    /* Actions */

    // FAB OnClick handler
    @OnClick(R.id.profile_action_button)
    void onFabClick() {
        // Navigate to add recipe page
        Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
        startActivityForResult(intent, Constants.ADD_RECIPE_INTENT);
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
        DatabaseUtil.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mContent.setRefreshing(false);
                // Save potentially-updated user to local storage
                SharedPrefsUtil.saveUser(user);
                // Repopulate views with new info
                populateViews(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // There was an error -- alert user
                mContent.setRefreshing(false);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
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
        // TODO: get recipes by user id
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
