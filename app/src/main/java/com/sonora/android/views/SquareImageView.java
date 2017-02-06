package com.sonora.android.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sonora.android.R;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a custom ImageView class that forces the view into a square shape.
 */

public class SquareImageView extends ImageView {

    private boolean byHeight;

    public SquareImageView(Context context) {
        super(context);
        this.byHeight = true;
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs, 0, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
                .obtainStyledAttributes(attrs, R.styleable.SquareImageView, defStyleAttr,
                        defStyleRes);
        try {
            // If the byHeight attribute was set, pass it to the class property
            byHeight = array.getBoolean(R.styleable.SquareImageView_byHeight, true);
        } finally {
            array.recycle();
        }
    }
}
