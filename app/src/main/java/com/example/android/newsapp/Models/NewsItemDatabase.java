package com.example.android.newsapp.Models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1 , exportSchema = false )
public abstract class NewsItemDatabase extends RoomDatabase {

    private static volatile NewsItemDatabase INSTANCE;


    public static NewsItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsItemDatabase.class, "newsitem_databse")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract NewsItemDao newsItemDao();
}

