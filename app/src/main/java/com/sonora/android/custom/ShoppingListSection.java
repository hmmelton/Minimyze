package com.sonora.android.custom;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sonora.android.R;
import com.sonora.android.models.ListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by harrisonmelton on 2/5/17.
 * This class is a custom section class for the 3rd party SectionedRecyclerView.
 */

public class ShoppingListSection extends StatelessSection {

    private List<ListItem> mItems; // TODO: set this in constructor
    private String mTitle;

    public ShoppingListSection(String title) {
        super(R.layout.cell_shopping_list_header, R.layout.cell_shopping_list);

        this.mTitle = title;
    }

    @Override
    public int getContentItemsTotal() {
        // TODO: remove hard code
        return 4;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO: fill this in
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new ShoppingListHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        ShoppingListHeaderViewHolder viewHolder = (ShoppingListHeaderViewHolder) holder;
        viewHolder.headerTitle.setText(mTitle);
    }

    /**
     * Custom view holder for layout's RecyclerView
     */
    class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cell_title)
        TextView itemTitle;
        @BindView(R.id.cell_et)
        EditText itemCount;
        @BindView(R.id.cell_count_type)
        TextView itemType;
        @BindView(R.id.cell_check)
        CheckBox itemCheck;
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

    class ShoppingListHeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shopping_list_header)
        TextView headerTitle;

        ShoppingListHeaderViewHolder(View itemView) {
            super(itemView);
            // Set up Butter Knife for header view
            ButterKnife.bind(this, itemView);
        }
    }
}
