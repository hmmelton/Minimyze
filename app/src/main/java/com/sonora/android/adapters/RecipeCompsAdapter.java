package com.sonora.android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonora.android.R;
import com.sonora.android.interfaces.ListItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrison on 3/29/17.
 * This is an adapter class for new recipe components in the new recipe creation flow.
 */

public class RecipeCompsAdapter extends RecyclerView.Adapter<RecipeCompsAdapter.ViewHolder> {

    // Cell titles
    private String[] mComponents;
    // Cell icons
    private final int[] mIcons = new int[]{
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home
    };
    final private ListItemClickListener mOnClickListener;

    // Constructor
    public RecipeCompsAdapter(Context context, ListItemClickListener listener) {
        mComponents = context.getResources().getStringArray(R.array.recipe_components);
        mOnClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate cell layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_recipe_component, parent, false);
        // Return it in a view holder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Create bitmap from resource drawable
        Bitmap bmp = BitmapFactory.decodeResource(holder.imageView.getResources(), mIcons[position]);
        // Set info in cell views
        holder.imageView.setImageBitmap(bmp);
        holder.title.setText(mComponents[position]);
    }

    @Override
    public int getItemCount() {
        return mComponents.length;
    }

    /**
     * This class acts as a view holder for cells in a RecyclerView attached to an instance of the
     * enclosing RecyclerView.Adapter implementation.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_comp_image)
        ImageView imageView;
        @BindView(R.id.recipe_comp_title)
        TextView title;

        // Constructor
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get position of item that was clicked
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
