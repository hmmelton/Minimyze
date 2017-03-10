package com.sonora.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sonora.android.R;
import com.sonora.android.models.Menu;
import com.sonora.android.views.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is an adapter class for the "featured" section of the menus page
 */

public class FeaturedMenusAdapter extends RecyclerView.Adapter<FeaturedMenusAdapter.ViewHolder> {

    private List<Menu> mMenuList;

    // Constructor
    public FeaturedMenusAdapter(List<Menu> menus) {
        this.mMenuList = menus;
    }

    @Override
    public FeaturedMenusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cell_menus_featured, parent, false));
    }

    @Override
    public void onBindViewHolder(FeaturedMenusAdapter.ViewHolder holder, int position) {
        // TODO: set image and title in here
        Glide.with(holder.imageView.getContext())
                .load(R.drawable.menu_placeholder) // TODO: put real image here
                .placeholder(R.drawable.menu_placeholder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // TODO: uncomment next line and remove hard code
        // return mMenuList.size()
        return 5;
    }

    /**
     * This is a view holder class for the enclosing RecyclerView adapter.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.background_image)
        SquareImageView imageView;
        @BindView(R.id.cell_title)
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            // Initialize Butter Knife
            ButterKnife.bind(this, itemView);
        }
    }
}
