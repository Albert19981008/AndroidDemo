package com.example.readhub.list.helper;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * 进行网络请求的类
 */
public class NetworkHelper {

    /**
     * 从网络加载数据
     *
     * @param httpRequestCategory 需要注入的HTTP请求标签（新闻类型）
     * @param theTimeStamp        得到在此时间戳之前的新闻
     * @param callback            回调接口
     */
    public static void loadNewsWithOkHttp(@NonNull String httpRequestCategory, long theTimeStamp,
                                          @NonNull okhttp3.Callback callback) {
        try {
            OkHttpClient client = new OkHttpClient();

            //指定访问的服务器地址是readHub，并注入当前时间戳
            String URL = "https://api.readhub.cn/" + httpRequestCategory + "?lastCursor=" +
                    theTimeStamp / 10 + "0000&pageSize=20";

            Request request = new Request.Builder()
                    .url(URL)
                    .header("access-control-allow-origin", "https://readhub.cn")
                    .addHeader("content-encoding", "gzip")
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
