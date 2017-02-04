package com.sonora.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sonora.android.animations.ChangeWeightAnimation;

import java.util.Arrays;

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
        // Login with Facebook
        LoginManager.getInstance().logInWithReadPermissions(SplashscreenActivity.this,
                Arrays.asList("public_profile", "email"));
    }

    @OnClick(R.id.google_login) void onGoogleLoginClick() {
        Toast.makeText(SplashscreenActivity.this, "Coming soon", Toast.LENGTH_LONG).show();
    }

    private final int LOGIN_ANIMATION_TIME = 600;
    private final int SPLASHSCREEN_WAIT_TIME = 1000;

    private final String TAG = getClass().getSimpleName();
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        // Initialize Firebase Facebook login
        this.mAuth = FirebaseAuth.getInstance();
        // Initialize Facebook login
        initFacebookLogin();
        // Initialize Firebase mAuth listener
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Required by Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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

    /**
     * This method initializes the Facebook login listener
     */
    private void initFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        // Register callback for later use
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "FB Login Cancelled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    /**
     * This method uses a Facebook access token to sign the user into Firebase.
     * @param token Facebook AccessToken used to log in user
     */
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the mAuth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(SplashscreenActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                        finish();
                    }
                });
    }
}
