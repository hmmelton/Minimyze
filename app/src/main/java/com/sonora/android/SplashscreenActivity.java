package com.sonora.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);

        // TODO: Remove this
        new Handler().postDelayed(() -> {
            // Go to main activity
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            finish();
        }, 1000);
    }
}
