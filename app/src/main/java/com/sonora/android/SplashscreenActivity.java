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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
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
import com.google.firebase.auth.FirebaseAuth;
import com.sonora.android.animations.ChangeWeightAnimation;
import com.sonora.android.models.User;
import com.sonora.android.utils.SharedPrefsUtil;
import com.sonora.android.utils.SonoraApi;

import org.json.JSONException;

import java.util.Arrays;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // Views
    @BindView(R.id.fullscreen_content) View mContentView;
    @BindView(R.id.image_icon_content) RelativeLayout mLogoContentView;
    @BindView(R.id.login_content) RelativeLayout mLoginContentView;
    @BindView(R.id.splash_title) TextView mTitle;
    @BindView(R.id.login_progress) ProgressBar mProgress;

    // String Resources
    @BindString(R.string.default_web_client_id) String WEB_CLIENT_ID;
    @BindString(R.string.google_sign_in_error) String GOOGLE_SIGN_IN_ERROR;

    // OnClickListeners
    @OnClick(R.id.facebook_login) void onFacebookLoginClick() {
        mProgress.setVisibility(View.VISIBLE);
        // Login with Facebook
        LoginManager.getInstance().logInWithReadPermissions(SplashscreenActivity.this,
                Arrays.asList("public_profile", "email"));
    }

    @OnClick(R.id.google_login) void onGoogleLoginClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private final int LOGIN_ANIMATION_TIME = 600;
    private final int SPLASHSCREEN_WAIT_TIME = 1000;
    private final int RC_SIGN_IN = 0;

    private final String TAG = getClass().getSimpleName();
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        // Initialize Facebook login
        initFacebookLogin();
        // Initialize Google login
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

        new Handler().postDelayed(() -> {
            // After displaying splash screen,
            if (SharedPrefsUtil.getUser() == null) {
                // User has not yet logged in
                showLogin();
            } else {
                // User is already logged in, so go to main screen
                login();
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
                // Google Sign In was successful
                GoogleSignInAccount account = result.getSignInAccount();
                // TODO: Login with Google
                Log.e(TAG, account.getGivenName() + " " + account.getFamilyName());
            } else {
                // Sign in failed
                Toast.makeText(this, GOOGLE_SIGN_IN_ERROR, Toast.LENGTH_LONG).show();
            }
        }
        // Required by Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Connection failed -- alert user
        Toast.makeText(this, GOOGLE_SIGN_IN_ERROR, Toast.LENGTH_LONG).show();
        Log.e(TAG, "connection failed: " + connectionResult.getErrorMessage());
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
     * This method initializes the Facebook login process.
     */
    private void initFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        // Register callback for later use
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        makeFBGraphRequest(loginResult.getAccessToken());
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
     * This method calls the Facebook Graph API
     * @param token
     */
    private void makeFBGraphRequest(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                (object, response) -> {
                    try {
                        // Pull values from JSONObject
                        String email = object.getString("email");
                        String firstName = object.getString("first_name");
                        String lastName = object.getString("last_name");
                        String profileUrl = object.getJSONObject("picture")
                                .getJSONObject("data")
                                .getString("url");

                       // Login with API
                        Call<User> req = SonoraApplication.getApi().signIn(token.getUserId(),
                                "facebook", email, firstName, lastName, profileUrl);
                        // Make call
                        req.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.code() == 200) {
                                    // All went well -- save user and log in
                                    SharedPrefsUtil.saveUser(response.body());
                                    login();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e(TAG, "login error", t);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * This method initializes the Google login process.
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
    private void login() {
        startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}