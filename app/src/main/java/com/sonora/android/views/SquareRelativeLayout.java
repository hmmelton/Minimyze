package com.sonora.android.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.sonora.android.R;

/**
 * Created by harrisonmelton on 3/15/17.
 * This is a square RelativeLayout
 */

public class SquareRelativeLayout extends RelativeLayout {

    private boolean byHeight;

    public SquareRelativeLayout(Context context) {
        super(context);
        this.byHeight = true;
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs, 0, 0);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributes(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("all")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (byHeight)
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        else
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (byHeight)
            super.onSizeChanged(h, h, oldw, oldh);
        else
            super.onSizeChanged(w, w, oldw, oldh);
    }

    /**
     * This method fetches attributes passed in XML when the view is created
     * @param context context
     * @param attrs attributes given to the view in XML
     * @param defStyleAttr default style attribute
     * @param defStyleRes default style resource
     */
    private void getAttributes(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        // Grab custom attributes
        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SquareView, defStyleAttr,
                        defStyleRes);
        try {
            // If the byHeight attribute was set, pass it to the class property
            byHeight = array.getBoolean(R.styleable.SquareView_byHeight, true);
        } finally {
            array.recycle();
        }
    }
}
