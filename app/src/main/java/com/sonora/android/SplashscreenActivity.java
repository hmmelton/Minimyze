package com.sonora.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sonora.android.animations.ChangeWeightAnimation;
import com.sonora.android.interfaces.OnCompleteListener;
import com.sonora.android.utils.AuthUtil;
import com.sonora.android.utils.SharedPrefsUtil;

import java.util.Arrays;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // Views
    @BindView(R.id.fullscreen_content) protected View mContentView;
    @BindView(R.id.image_icon_content) protected RelativeLayout mLogoContentView;
    @BindView(R.id.login_content) protected RelativeLayout mLoginContentView;
    @BindView(R.id.splash_title) protected TextView mTitle;

    // String Resources
    @BindString(R.string.default_web_client_id) protected String WEB_CLIENT_ID;
    @BindString(R.string.google_sign_in_error) protected String GOOGLE_SIGN_IN_ERROR;

    // OnClickListeners
    @OnClick(R.id.facebook_login) void onFacebookLoginClick() {
        // Login with Facebook
        LoginManager.getInstance().logInWithReadPermissions(SplashscreenActivity.this,
                Arrays.asList("public_profile", "email"));
    }

    @OnClick(R.id.google_login) void onGoogleLoginClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Code used for Google sign in intent
    private final int RC_SIGN_IN = 0;

    private final String TAG = getClass().getSimpleName();

    // Third-party goToLoginPage helpers
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        // Initialize Facebook goToLoginPage
        initFacebookLogin();
        // Initialize Google goToLoginPage
        initGoogleLogin();

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        final int SPLASHSCREEN_WAIT_TIME = 1000;

        new Handler().postDelayed(() -> {
            // After displaying splash screen,
            if (SharedPrefsUtil.getUser() == null) {
                // User has not yet logged in
                showLogin();
            } else {
                // User is already logged in, so go to main screen
                goToLoginPage();
            }
        }, SPLASHSCREEN_WAIT_TIME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                // TODO: Login with Google
            } else {
                // Sign in failed
                Toast.makeText(this, GOOGLE_SIGN_IN_ERROR, Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Connection failed -- alert user
        Toast.makeText(this, GOOGLE_SIGN_IN_ERROR, Toast.LENGTH_LONG).show();
        Log.e(TAG, "connection failed: " + connectionResult.getErrorMessage());
    }

    /**
     * This method animates the logo section and the goToLoginPage section heights.
     */
    private void showLogin() {
        final int LOGIN_ANIMATION_TIME = 600;
        DecelerateInterpolator interpolator = new DecelerateInterpolator();

        // Create animation for logo section
        ChangeWeightAnimation logoAnim = new ChangeWeightAnimation(mLogoContentView, 10, 6);
        logoAnim.setDuration(LOGIN_ANIMATION_TIME);
        logoAnim.setInterpolator(interpolator);

        // Create animation for goToLoginPage section
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
     * This method initializes the Facebook goToLoginPage process.
     */
    private void initFacebookLogin() {
        Log.e(TAG, "initFacebook");
        mCallbackManager = CallbackManager.Factory.create();
        // Register callback for later use
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, "fb success");
                        AuthCredential credential = FacebookAuthProvider
                                .getCredential(loginResult.getAccessToken().getToken());
                        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Handle login or user creation
                                AuthUtil.handleFacebookLogin(loginResult.getAccessToken(),
                                        new OnCompleteListener() {
                                    @Override
                                    public void complete(boolean result) {
                                        // Login successful -- go to main page
                                        Log.e(TAG, "auth util complete");
                                        goToLoginPage();
                                    }

                                    @Override
                                    public void error(String message) {
                                        Log.e(TAG, "auth util error");
                                        // Login error
                                        Toast.makeText(SplashscreenActivity.this, message,
                                                Toast.LENGTH_LONG).show();
                                        mAuth.signOut();
                                    }
                                });
                            } else {
                                // Authentication failed
                                Log.e(TAG, "fb error");
                                Toast.makeText(SplashscreenActivity.this, R.string.auth_failure,
                                        Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                            }
                        });
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
     * This method initializes the Google goToLoginPage process.
     */
    private void initGoogleLogin() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * This method logs the user into the application.
     */
    private void goToLoginPage() {
        startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}