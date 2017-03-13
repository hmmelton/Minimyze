package com.sonora.android.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by harrisonmelton on 3/12/17.
 * This is a util class for view animations.
 */

public class AnimationUtil {

    /**
     * This method translates a view from its current position to a new position on the screen
     * @param view view to be translated
     * @param deltaX change in x-axis coordinate
     * @param deltaY change in y-axis coordinate
     * @param listener animation listener
     * @param duration duration of animation
     */
    public static void translateAnimation(View view, float deltaX, float deltaY,
                                          TranslateAnimation.AnimationListener listener,
                                          int duration) {
        // Create translation animation
        TranslateAnimation anim = new TranslateAnimation(0, deltaX, 0, deltaY);
        anim.setDuration(duration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setFillAfter(true);
        anim.setAnimationListener(listener);
        // Start the animation
        view.startAnimation(anim);
    }
}
