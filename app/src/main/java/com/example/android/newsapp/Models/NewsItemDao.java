package com.example.android.newsapp.Models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewsItemDao {

        @Query("SELECT * FROM news_item")
        LiveData<List<NewsItem>> getAllNews();

        @Insert
        void insert(NewsItem newsItem);

//        @Update(onConflict =OnConflictStrategy.REPLACE)
//        void update(NewsItem newsItem);
//
//        @Delete
//        void delete();

        @Query("DELETE FROM news_item")
        void clearAll();


}
