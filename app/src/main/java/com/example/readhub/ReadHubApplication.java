package com.example.readhub;

import android.app.Application;
import android.content.Context;


/**
 * 继承Application类，方便全局获取Context
 */
public class ReadHubApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static Context getApplication() {
        return sApplication;
    }
}
