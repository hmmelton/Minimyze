package com.sonora.android;

import android.app.Application;

/**
 * Created by harrisonmelton on 1/27/17.
 * This is Sonora's Application class
 */

public class SonoraApplication extends Application {

    private static SonoraApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    /**
     * This method returns the current instance of the Application class.
     * @return current instance of the application class
     */
    public static SonoraApplication getInstance() {
        return instance;
    }
}
