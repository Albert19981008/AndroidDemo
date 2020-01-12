package com.example.readhub.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.readhub.data.entity.News;
import com.example.readhub.executor.AppExecutors;
import com.example.readhub.list.helper.NetworkHelper;

import java.util.List;


/**
 * 封装好的用来提供新闻数据和管理线程池的类
 */
public class NewsRepository {

    //单例类
    private static volatile NewsRepository instance;

    //数据层
    private NewsDao mNewsDao;

    //线程池
    private AppExecutors mAppExecutors;

    private NewsRepository(@NonNull AppExecutors appExecutors,
                           @NonNull NewsDao newsDao) {
        mAppExecutors = appExecutors;
        mNewsDao = newsDao;
    }

    /**
     * 单例模式
     *
     * @param appExecutors 线程池
     * @param newsDao      数据层实现
     * @return 单例返回NewsRepository
     */
    public static NewsRepository getInstance(@NonNull AppExecutors appExecutors,
                                             @NonNull NewsDao newsDao) {
        if (instance == null) {
            synchronized (NewsRepository.class) {
                if (instance == null) {
                    instance = new NewsRepository(appExecutors, newsDao);
                }
            }
        }
        return instance;
    }


    /**
     * presenter中需要实现的回调接口
     */
    public interface LoadNewsCallback {

        void onNewsLoaded(List<News> newsList);

        void onDataNotAvailable();
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
                              @NonNull final LoadNewsCallback callback) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<News> newsList = mNewsDao.getLatestNews(numOfPiece, type, endTime);
                mAppExecutors.mainThread().execute(new Runnable() {
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
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * 删掉太旧的新闻
     *
     * @param timeLimits 这个时间段以前的新闻将不再保留
     */
    public void deleteOldNews(long timeLimits) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.deleteOldNews(timeLimits);
                Log.i("db", "delete");
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    /**
     * 把每条DB中没有的新闻插入
     *
     * @param newsList 要插入的新闻列表
     */
    public void insertNewsIfNotExist(@NonNull List<News> newsList) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNewsDao.insertNewsIfNotExist(newsList);
                Log.i("db", "insert");
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * 从网络加载数据
     *
     * @param httpRequest  需要注入的HTTP请求（新闻类型）
     * @param theTimeStamp 得到在此时间戳之前的新闻
     * @param callback     回调接口
     */
    public void loadNewsWithOkHttp(@NonNull String httpRequest,
                                   long theTimeStamp,
                                   @NonNull okhttp3.Callback callback) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                NetworkHelper.loadNewsWithOkHttp(httpRequest, theTimeStamp, callback);
            }
        };
        mAppExecutors.networkIO().execute(runnable);
    }
}
