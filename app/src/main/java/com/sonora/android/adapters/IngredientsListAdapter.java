package com.sonora.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonora.android.R;
import com.sonora.android.models.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrison on 3/27/17.
 * This is an adapter class for a RecyclerView of Ingredient objects.
 */

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.ViewHolder> {

    private List<Ingredient> mIngredients;

    public IngredientsListAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_ingredients_list, parent, false);

        // Return new view holder passed newly inflated cell
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        // Set data
        holder.ingredientName.setText(ingredient.getItemName());
        holder.ingredientCount.setText(String.valueOf(ingredient.getCount()));
        holder.ingredientCountType.setText(ingredient.getCountType());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    /**
     * This method adds an ingredient to the list.
     * @param ingredient Ingredient object to add to list
     */
    public void addIngredient(Ingredient ingredient) {
        // Add ingredient to top, so user does not have to scroll down to see it
        mIngredients.add(0, ingredient);
        notifyItemInserted(0);
    }

    /**
     * this method returns the current list of ingredients.
     * @return current list of ingredients
     */
    public List<Ingredient> getIngredients() {
        List<Ingredient> result = new ArrayList<>();
        // Copy ingredients into new list
        Collections.copy(result, mIngredients);
        return result;
    }

    /**
     * View holder class for adapter.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;
        @BindView(R.id.tv_ingredient_count)
        TextView ingredientCount;
        @BindView(R.id.tv_ingredient_count_type)
        TextView ingredientCountType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
