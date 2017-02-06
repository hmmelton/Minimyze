package com.sonora.android.adapters;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sonora.android.R;
import com.sonora.android.models.ShoppingList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by harrisonmelton on 2/4/17.
 * This is a custom adapter for the shopping list tab.
 */

public class ShoppingListAdapter
        extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private ShoppingList mList;

    // Constructor
    public ShoppingListAdapter(ShoppingList list) {
        this.mList = list;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingListViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cell_shopping_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //return mList.getCount();
        return 20;
    }

    /**
     * Custom view holder for layout's RecyclerView
     */
    class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cell_title) TextView itemTitle;
        @BindView(R.id.cell_et) EditText itemCount;
        @BindView(R.id.cell_count_type) TextView itemType;
        @BindView(R.id.cell_check) CheckBox itemCheck;
        // OnCheckedChangeListener for row's checkbox
        @OnCheckedChanged(R.id.cell_check)
        void checkChanged(CompoundButton button, boolean b) {
            if (b) {
                // Box was checked, put line through item text
                itemTitle.setPaintFlags(itemTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                itemTitle.setAlpha(0.5f);
            } else {
                // Box was unchecked, remove line from item text
                itemTitle.setPaintFlags(itemTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                itemTitle.setAlpha(1.0f);
            }
        }

        ShoppingListViewHolder(View itemView) {
            super(itemView);
            // Set up Butter Knife for individual view
            ButterKnife.bind(this, itemView);
        }
    }
}
