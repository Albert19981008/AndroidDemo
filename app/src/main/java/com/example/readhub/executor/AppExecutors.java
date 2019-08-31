package com.example.readhub.executor;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 整个APP的线程池 包括三个线程池
 * 分别为 磁盘IO线程池 网络IO线程池 主线程池
 */
public class AppExecutors {

    //网络请求线程池最多三个线程
    private static final int THREAD_COUNT = 3;

    //磁盘IO线程池
    private final Executor diskIO;

    //网络IO线程池
    private final Executor networkIO;

    //主线程池
    private final Executor mainThread;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public AppExecutors() {
        this(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor());
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
}
