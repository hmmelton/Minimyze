package com.sonora.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sonora.android.animations.ChangeWeightAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {

    // Views
    @BindView(R.id.fullscreen_content) protected View mContentView;
    @BindView(R.id.image_icon_content) protected RelativeLayout mLogoContentView;
    @BindView(R.id.login_content) protected RelativeLayout mLoginContentView;
    @BindView(R.id.splash_title) protected TextView mTitle;

    private final int LOGIN_ANIMATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            showLogin();
        }

        /*// TODO: Remove this
        new Handler().postDelayed(() -> {
            // Go to main activity
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            finish();
        }, 1000);*/
    }

    /**
     * This method animates the logo section and the login section heights.
     */
    private void showLogin() {
        DecelerateInterpolator interpolator = new DecelerateInterpolator();

        // Create animation for logo section
        ChangeWeightAnimation logoAnim = new ChangeWeightAnimation(mLogoContentView, 10, 6);
        logoAnim.setDuration(LOGIN_ANIMATION_TIME);
        logoAnim.setInterpolator(interpolator);

        // Create animation for login section
        ChangeWeightAnimation loginAnim = new ChangeWeightAnimation(mLoginContentView, 0, 4);
        loginAnim.setDuration(LOGIN_ANIMATION_TIME);
        loginAnim.setInterpolator(interpolator);

        // Start animations
        mLogoContentView.startAnimation(logoAnim);
        mLoginContentView.startAnimation(loginAnim);
    }
}
