package com.sonora.android;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;
import com.sonora.android.utils.SonoraApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harrisonmelton on 1/27/17.
 * This is Sonora's Application class
 */

public class SonoraApplication extends Application {

    private static SonoraApplication instance;
    private static SonoraApi api;

    // Base URL for API
    private String base_url;

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);

        instance = this;
        base_url = getString(R.string.heroku_base_url);
        // Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(SonoraApi.class);
    }

    /**
     * This method returns the current instance of the Application class.
     * @return current instance of the application class
     */
    public static SonoraApplication getInstance() {
        return instance;
    }

    /**
     * This method returns a static instance of the application's API.
     * @return applicatin's API
     */
    public static SonoraApi getApi() {
        return api;
    }
}
