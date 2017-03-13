package com.sonora.android.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sonora.android.R;

/**
 * Created by harrisonmelton on 3/12/17.
 * This is a custom circular view.
 */

public class CircleView extends View {

    private boolean byHeight;
    private Paint paint;

    public CircleView(Context context) {
        super(context);
        this.byHeight = true;
        paint = new Paint();
    }

    public CircleView(Context context, boolean byHeight) {
        this(context);
        this.byHeight = byHeight;
        paint = new Paint();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        getAttributes(context, attrs, 0, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        getAttributes(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
        getAttributes(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius;
        if (byHeight)
            radius = (this.getBottom() - this.getTop()) / 2;
        else
            radius = (this.getRight() - this.getLeft()) / 2;

        int cx = radius;
        int cy = radius;
        Log.d(getClass().getSimpleName(), String.format("%d %d %d, Left: %d, Top: %d", radius, cx, cy, this.getLeft(), this.getTop()));
        canvas.drawCircle(cx, cy, radius, paint);
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
                .obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr,
                        defStyleRes);
        try {
            // If the byHeight attribute was set, pass it to the class property
            byHeight = array.getBoolean(R.styleable.CircleView_cv_byHeight, true);
            // If the color attribute was set, pass it to the class property
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // If build version is greater than Marshmallow
                paint.setColor(array.getColor(R.styleable.CircleView_cv_color,
                        getContext().getColor(R.color.colorAccent)));
            } else {
                // If build version is less than marshmallow
                paint.setColor(array.getColor(R.styleable.CircleView_cv_color,
                        getContext().getResources().getColor(R.color.colorAccent)));
            }
        } finally {
            array.recycle();
        }
    }
}
