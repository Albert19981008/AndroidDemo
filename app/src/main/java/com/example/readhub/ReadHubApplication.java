package com.example.readhub;

import android.app.Application;
import android.content.Context;


/**
 * 继承Application类，方便全局获取Context
 */
public class ReadHubApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
