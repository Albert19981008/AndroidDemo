package com.example.readhub.data;

import com.example.readhub.data.entity.News;

import java.util.List;

public interface NewsCallBackApi {

    void onNewsLoaded(List<News> newsList);

    void onDataNotAvailable();
}
