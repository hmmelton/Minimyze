package com.sonora.android.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.sonora.android.R;
import com.sonora.android.custom.DividerItemDecoration;
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
    public void addSwipeToRemove(Context context) {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            Drawable background; // Color bg
            Drawable xMark; // Drawable image
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(context, R.drawable.ic_cancel_circle);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) context.getResources().getDimension(R.dimen.standard_margin);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                SwipeableRecyclerViewAdapter adapter =
                        (SwipeableRecyclerViewAdapter) mRecyclerView.getAdapter();
                adapter.remove(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                if (viewHolder.getAdapterPosition() < 0) {
                    return;
                }

                if (!initiated) {
                    init();
                }

                // Draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // Get height and width of remove image
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                // Draw remove image
                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                        isCurrentlyActive);
            }

        };
        // Attach to RecyclerView
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * This method adds horizontal lines between RecyclerView items.
     */
    public void addHorizontalSeparatorLines() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext()));
    }
}
