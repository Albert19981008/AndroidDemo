package com.example.readhub.data;

import android.support.annotation.NonNull;

import com.example.readhub.data.entity.News;
import com.example.readhub.executor.AppExecutors;

import java.util.List;

public final class DatabaseHelper {

    private static volatile DatabaseHelper sDatabaseHelper;

    private AppExecutors appExecutors;

    private NewsDatabase newsDatabase;

    public DatabaseHelper(AppExecutors appExecutors, NewsDatabase newsDatabase) {
        this.appExecutors = appExecutors;
        this.newsDatabase = newsDatabase;
    }

    public static DatabaseHelper getInstance(@NonNull AppExecutors appExecutors,
                                             @NonNull NewsDatabase newsDatabase) {
        if (sDatabaseHelper == null) {
            synchronized (NewsRepository.class) {
                if (sDatabaseHelper == null) {
                    sDatabaseHelper = new DatabaseHelper(appExecutors, newsDatabase);
                }
            }
        }
        return sDatabaseHelper;
    }

    public void getAllNews(NewsCallBackApi callback) {
        appExecutors.diskIO().execute(() -> {
            List<News> newsList = newsDatabase.newsDao().getAllNews();
            if (newsList.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onNewsLoaded(newsList);
            }
        });
    }

    /**
     * 得到最近一段时间的新闻
     *
     * @param numOfPiece 一次想得到的新闻条数
     * @param type       想得到新闻的种类
     * @param endTime    想得到新闻的截止时间（得到的全是在此之前的新闻）
     * @param callback   需要实现的回调接口
     */
    public void getLatestNews(int numOfPiece,
                              @NonNull String type,
                              long endTime,
                              @NonNull final NewsCallBackApi callback) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<News> newsList = newsDatabase.newsDao().getLatestNews(numOfPiece, type, endTime);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (newsList.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onNewsLoaded(newsList);
                        }
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }
}
