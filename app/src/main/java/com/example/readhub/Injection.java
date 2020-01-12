package com.example.readhub;


import com.example.readhub.data.NewsRepository;
import com.example.readhub.executor.AppExecutors;


/**
 * 用于注入NewsRepository
 */
public class Injection {

    public static NewsRepository provideNewsRepository() {
        return NewsRepository.getInstance(new AppExecutors(), ReadHubApplication.getsNewsDatabase());
    }
}