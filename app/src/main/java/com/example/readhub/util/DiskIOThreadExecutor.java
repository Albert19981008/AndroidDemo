package com.example.readhub.util;


import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 负责磁盘IO（数据库读写）的线程池
 */
public class DiskIOThreadExecutor implements Executor {

    private final Executor mDiskIO;

    //数据库读写最多一个线程
    public DiskIOThreadExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mDiskIO.execute(command);
    }
}
