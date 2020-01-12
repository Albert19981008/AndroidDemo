package com.example.readhub.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.readhub.data.entity.News;

@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
}
