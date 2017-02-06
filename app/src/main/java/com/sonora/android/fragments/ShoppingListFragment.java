package com.sonora.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.sonora.android.R;
import com.sonora.android.adapters.ShoppingListSection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by harrisonmelton on 2/4/17.
 * This class controls the content on the shopping lists page.
 */

public class ShoppingListFragment extends Fragment {

    @BindView(R.id.shopping_list_recycler) protected RecyclerView mRecyclerView;
    @OnTouch(R.id.shopping_list_recycler)
    boolean onLayoutTouched(View v, MotionEvent ev) {
        hideKeyboard(v);
        return false;
    }

    // Static method for constructing fragment
    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout's fragment
        View root = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        // Initialize Butter Knife
        ButterKnife.bind(this, root);

        // TODO: Pull data, create data structure to pass to adapter
        // Create an instance of SectionedRecyclerViewAdapter
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        // Add your Sections
        sectionAdapter.addSection(new ShoppingListSection("Beverages"));
        sectionAdapter.addSection(new ShoppingListSection("Bakery"));
        sectionAdapter.addSection(new ShoppingListSection("Canned and Jarred"));
        sectionAdapter.addSection(new ShoppingListSection("Dairy"));
        sectionAdapter.addSection(new ShoppingListSection("Dry and Baking"));
        sectionAdapter.addSection(new ShoppingListSection("Frozen"));
        sectionAdapter.addSection(new ShoppingListSection("Meats"));
        sectionAdapter.addSection(new ShoppingListSection("Produce"));
        sectionAdapter.addSection(new ShoppingListSection("Snacks"));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(sectionAdapter);

        return root;
    }

    /**
     * Hides virtual keyboard
     */
    protected void hideKeyboard(View view) {
        InputMethodManager in =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
