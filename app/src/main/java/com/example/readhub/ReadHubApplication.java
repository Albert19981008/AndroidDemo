package com.example.readhub;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;

import com.example.readhub.data.NewsDatabase;
import com.example.readhub.service.UpdateService;


/**
 * 继承Application类，方便全局获取Context
 */
public class ReadHubApplication extends Application {

    private static Application sApplication;

    private static NewsDatabase sNewsDatabase;

    private void initDb() {
        sNewsDatabase = Room
                .databaseBuilder(this, NewsDatabase.class, "news.db")
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
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

    public static NewsDatabase getsNewsDatabase() {
        return sNewsDatabase;
    }
}
