package com.example.readhub.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.readhub.Injection;
import com.example.readhub.data.NewsEntity;
import com.example.readhub.data.NewsRepository;
import com.example.readhub.list.helper.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.readhub.Constants.CATEGORIES;


/**
 * 更新三类新闻数据的Service，每次程序运行时候启动，
 */
public class UpdateService extends Service {

    //新闻种类编码 0 1 2
    private static final int[] TYPE_NUM = {0, 1, 2};

    //三种新闻对应的需要注入的HTTP请求
    private static final String[] HTTP_REQUESTS = CATEGORIES;

    //当前时间戳
    private static final long TIME_STAMP_NOW = System.currentTimeMillis() / 1000;

    //已加载每类新闻的时间戳最小值
    private static long[] sTimeStamps = new long[]{TIME_STAMP_NOW, TIME_STAMP_NOW, TIME_STAMP_NOW};

    //NewsRepository
    private static final NewsRepository NEWS_REPOSITORY = Injection.provideNewsRepository();

    //五天的总秒数
    private static final long FIVE_DAYS = 5 * 24 * 60 * 60;

    //接收再次加载信息的Handler
    private static Handler mHandler = new LoadingHandler();


    /**
     * 接收再次加载信息的Handler
     */
    private static class LoadingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (isValidNewsType(msg.what)) {
                if ((System.currentTimeMillis() / 1000 - sTimeStamps[msg.what]) < FIVE_DAYS) {
                    loadNewsAndInsertIntoDB(msg.what);
                }
            }
        }
    }


    /**
     * 从网上获取新闻并插入数据库
     *
     * @param newsType 新闻类型
     */
    private static void loadNewsAndInsertIntoDB(int newsType) {
        if (!isValidNewsType(newsType)) {
            return;
        }

        NEWS_REPOSITORY.loadNewsWithOkHttp(HTTP_REQUESTS[newsType], sTimeStamps[newsType],
                new okhttp3.Callback() {

                    //请求失败的回调
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("network", "没有联网 暂时无法更新！");
                    }

                    //请求成功的回调
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() != 200) {
                            //网络请求异常
                            Log.e("httpErrorCode: ", String.valueOf(response.code()));
                        }
                        String res = response.body().string();  //得到JSON格式的String

                        List<NewsEntity> list = new ArrayList<>();
                        sTimeStamps[newsType] = JSONParser.parseJSONAndReturnMinTime(res, HTTP_REQUESTS[newsType], list);
                        NEWS_REPOSITORY.insertNewsIfNotExist(list);

                        Message msg = Message.obtain();
                        msg.what = newsType;
                        mHandler.sendMessage(msg);  //发送信息以重复这个步骤 加载更多新闻
                    }

                }); //用当前时间戳发送网络请求获得数据
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        for (int value : TYPE_NUM) {
            UpdateService.loadNewsAndInsertIntoDB(value);
        }
        //删除五天前的数据
        NEWS_REPOSITORY.deleteOldNews(FIVE_DAYS);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //退出时丢掉所有消息 防内存泄漏
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private static boolean isValidNewsType(int newsType) {
        return newsType >= 0 && newsType < CATEGORIES.length;
    }
}
