package com.sonora.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sonora.android.R;
import com.sonora.android.models.Recipe;
import com.sonora.android.views.SquareImageView;

import java.util.List;

/**
 * Created by harrisonmelton on 3/11/17.
 * This is an adapter for the recipes list on the profile page.
 */

public class RecipesGridAdapter extends RecyclerView.Adapter<RecipesGridAdapter.ViewHolder> {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    private List<Recipe> mRecipes;

    public RecipesGridAdapter(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_grid_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext())
                .load(mRecipes.get(position).getImageUrl())
                .placeholder(R.drawable.menu_placeholder)
                .into(holder.imageView);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    /**
     * This method sets the Recipe list.
     * @param recipes List of recipes to be displayed by GridView attached to instanc of adapter.
     */
    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SquareImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (SquareImageView) itemView.findViewById(R.id.item_image_view);
        }
    }
}
