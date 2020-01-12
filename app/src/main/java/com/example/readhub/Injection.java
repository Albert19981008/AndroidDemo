package com.example.readhub;


import com.example.readhub.data.DatabaseHelper;
import com.example.readhub.data.NewsDao;
import com.example.readhub.data.NewsRepository;
import com.example.readhub.executor.AppExecutors;


/**
 * 用于注入NewsRepository
 */
public class Injection {

    /**
     * 用于得到 NewsRepository
     *
     * @return NewsRepository
     */
    public static NewsRepository provideNewsRepository() {

        return NewsRepository.getInstance(new AppExecutors(), new NewsDao());
    }

    public static DatabaseHelper provideDatabaseHelper() {
        return DatabaseHelper.getInstance(new AppExecutors(), ReadHubApplication.getsNewsDatabase());
    }
}