package com.example.android.newsapp.Models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private static NewsItemRepository mRepository;
    private LiveData<List<NewsItem>> mAllNewsItem;

    public NewsItemViewModel(Application application) {
        super(application);
        mRepository = new NewsItemRepository(application);
        mAllNewsItem = mRepository.getmAllNewsItem();
    }

    public LiveData<List<NewsItem>> getmAllNewsItem(){
        return mAllNewsItem;
    }

    public static void syncURL(){
        mRepository.syncURL();
    }

}

