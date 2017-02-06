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
import com.sonora.android.adapters.ShoppingListAdapter;
import com.sonora.android.models.ShoppingList;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrisonmelton on 2/4/17.
 * This class controls the content on the shopping lists page.
 */

public class ShoppingListFragment extends Fragment {

    @BindView(R.id.shopping_list_recycler) protected RecyclerView mRecyclerView;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ShoppingListAdapter(new ShoppingList(new JSONObject())));

        return root;
    }
}
