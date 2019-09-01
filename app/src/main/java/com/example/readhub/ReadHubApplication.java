package com.example.readhub;

import android.app.Application;
import android.content.Intent;

import com.example.readhub.service.UpdateService;


/**
 * 继承Application类，方便全局获取Context
 */
public class ReadHubApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        startUpdateService();
    }

    //开启更新新闻的服务
    private void startUpdateService() {
        Intent StartServiceIntent = new Intent(this, UpdateService.class);
        startService(StartServiceIntent);
    }

    public static Application getApplication() {
        return sApplication;
    }
}
