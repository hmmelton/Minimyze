package com.sonora.android;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by harrisonmelton on 1/27/17.
 * This is Sonora's Application class
 */

public class SonoraApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
