package com.example.readhub;


import com.example.readhub.data.DatabaseHelper;
import com.example.readhub.executor.AppExecutors;


/**
 * 用于注入NewsRepository
 */
public class Injection {

    public static DatabaseHelper provideDatabaseHelper() {
        return DatabaseHelper.getInstance(new AppExecutors(), ReadHubApplication.getsNewsDatabase());
    }
}