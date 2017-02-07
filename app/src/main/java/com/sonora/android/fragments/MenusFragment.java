package com.sonora.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonora.android.R;
import com.sonora.android.adapters.FeaturedMenusAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrisonmelton on 2/5/17.
 * This class controls the content on the menus page.
 */

public class MenusFragment extends Fragment {

    @BindView(R.id.menus_featured_recycler) RecyclerView mFeaturedRecycler;
    @BindView(R.id.menus_saved_recycler) RecyclerView mSavedRecycler;

    // Static method for constructing fragment
    public static MenusFragment newInstance() {
        return new MenusFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout's fragment
        View root = inflater.inflate(R.layout.fragment_menus, container, false);
        // Initialize Butter Knife
        ButterKnife.bind(this, root);

        mFeaturedRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mFeaturedRecycler.setAdapter(new FeaturedMenusAdapter(null));

        return root;
    }
}
