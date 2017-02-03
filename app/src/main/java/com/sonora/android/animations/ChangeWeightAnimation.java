package com.sonora.android.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by harrisonmelton on 2/2/17.
 * This is an animation class that changes the layout_weight property of a view.
 */

public class ChangeWeightAnimation extends Animation {

    private float mStartWeight;
    private float mDeltaWeight;
    private View mView;

    public ChangeWeightAnimation(View content, float startWeight, float endWeight) {
        mView = content;
        mStartWeight = startWeight;
        mDeltaWeight = endWeight - startWeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mView.getLayoutParams();

        // Set new weight
        params.weight = (mStartWeight + (mDeltaWeight * interpolatedTime));
        // Set params with new weight
        mView.setLayoutParams(params);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
