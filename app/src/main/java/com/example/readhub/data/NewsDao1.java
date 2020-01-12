package com.example.readhub.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.readhub.data.entity.News;

import java.util.List;

@Dao
public interface NewsDao1 {

    @Query("select * from news")
    List<News> getAllNews();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCities(News... news);
}