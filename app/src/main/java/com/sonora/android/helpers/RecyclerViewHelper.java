package com.sonora.android.helpers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sonora.android.interfaces.SwipeableRecyclerViewAdapter;

/**
 * Created by harrison on 3/27/17.
 * This is a helper class for RecyclerViews.
 */

public class RecyclerViewHelper {

    private RecyclerView mRecyclerView;

    // Constructor
    public RecyclerViewHelper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    /**
     * This method adds swipe-to-remove functionality to {@link RecyclerViewHelper#mRecyclerView}
     */
    public void setSwipeToRemoveItems() {
        // Create callback for swipes
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                SwipeableRecyclerViewAdapter adapter =
                        (SwipeableRecyclerViewAdapter) mRecyclerView.getAdapter();
                adapter.remove(position);
            }
        };

        // Create ItemTouchHelper and attach it to RecyclerView
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(mRecyclerView);
    }
}
