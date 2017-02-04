package com.sonora.android;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sonora.android.animations.ChangeWeightAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    // OnClickListeners
    @OnClick(R.id.facebook_login) void onFacebookLoginClick() {
        Toast.makeText(SplashscreenActivity.this, "Coming soon", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.google_login) void onGoogleLoginClick() {
        Toast.makeText(SplashscreenActivity.this, "Coming soon", Toast.LENGTH_LONG).show();
    }

    private final int LOGIN_ANIMATION_TIME = 600;
    private final int SPLASHSCREEN_WAIT_TIME = 1000;

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

        new Handler().postDelayed(() -> {
            // After displaying splash screen,
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                showLogin();
            }
        }, SPLASHSCREEN_WAIT_TIME);
    }

    /**
     * This method animates the logo section and the login section heights.
     */
    private void showLogin() {
        DecelerateInterpolator interpolator = new DecelerateInterpolator();

        // Create animation for logo section
        ChangeWeightAnimation logoAnim = new ChangeWeightAnimation(mLogoContentView, 10, 6);
        logoAnim.setDuration(LOGIN_ANIMATION_TIME);

        // Create animation for login section
        ChangeWeightAnimation loginAnim = new ChangeWeightAnimation(mLoginContentView, 0, 4);
        loginAnim.setDuration(LOGIN_ANIMATION_TIME);

        // Create fade in animation for title
        AlphaAnimation titleAnim = new AlphaAnimation(0.0f, 1.0f);
        titleAnim.setDuration(LOGIN_ANIMATION_TIME / 2);
        titleAnim.setStartOffset(LOGIN_ANIMATION_TIME);

        // Start animations
        mLogoContentView.startAnimation(logoAnim);
        mLoginContentView.startAnimation(loginAnim);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.startAnimation(titleAnim);
    }
}
