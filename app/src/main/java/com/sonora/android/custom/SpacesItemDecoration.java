package com.sonora.android.custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by harrisonmelton on 3/11/17.
 * This class gives uniform spacing between grid items in an "n x 3" matrix.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        // Get position of view in current layout
        int position = parent.getChildLayoutPosition(view);

        // Add bottom and left spacing to all
        outRect.left = space;
        outRect.bottom = space;
        if (position == 0 || position == 1 || position == 2) {
            // Add top space only to first row
            outRect.top = space;
        }
        if (position % 3 == 2) {
            // Add right space only to last column
            outRect.right = space;
        }
    }
}
