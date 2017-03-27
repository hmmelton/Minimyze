package com.sonora.android.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sonora.android.R;

/**
 * Created by harrison on 3/27/17.
 * This class provides a line separator for RecyclerView rows.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mLineDrawable;

    // Constructor
    public DividerItemDecoration(Context context) {
        mLineDrawable = ContextCompat.getDrawable(context, R.drawable.line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // Get parent side paddings
        int left = parent.getPaddingLeft();
        int right = parent.getPaddingRight();

        int childCount = parent.getChildCount(); // Number of children in RecyclerView parent
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // Find bottom of RecyclerView cell (to set as top), and add to this value the
            // height of the line (to set as bottom)
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mLineDrawable.getIntrinsicHeight();

            mLineDrawable.setBounds(left, top, right, bottom);
            mLineDrawable.draw(c);
        }
    }
}
