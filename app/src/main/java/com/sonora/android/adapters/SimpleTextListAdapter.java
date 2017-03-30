package com.sonora.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonora.android.R;
import com.sonora.android.interfaces.SwipeableRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrison on 3/27/17.
 * This is an adapter for a RecyclerView that displays one item of text per cell.
 */

public class SimpleTextListAdapter extends RecyclerView.Adapter<SimpleTextListAdapter.ViewHolder> implements SwipeableRecyclerViewAdapter {

    private List<String> mElements;

    public SimpleTextListAdapter(List<String> elements) {
        mElements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate cell's view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_simple_text, parent, false);

        // Return view holder with inflated view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Set text of position
        holder.textView.setText(mElements.get(position));
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    @Override
    public void remove(int position) {
        mElements.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * View holder class
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_simple_cell)
        TextView textView;

        // Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
