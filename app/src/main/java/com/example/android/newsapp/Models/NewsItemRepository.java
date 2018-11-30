package com.example.android.newsapp.Models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.newsapp.MainActivity;
import com.example.android.newsapp.Utils.JsonUtils;
import com.example.android.newsapp.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsItemRepository {

    private static NewsItemDao mNewsItemDao;
    private static LiveData<List<NewsItem>> mAllNewsItem;

    public  NewsItemRepository(Application application){
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNewsItem =mNewsItemDao.getAllNews();
    }

    public LiveData<List<NewsItem>> getmAllNewsItem() {
        return mAllNewsItem;
    }


    public static void syncURL(){//URL url){
        new insertAsyncTask(mNewsItemDao).execute(NetworkUtils.buildUrl());
    }

    private static class insertAsyncTask extends AsyncTask<URL, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        private insertAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... newsurls) {

            mAsyncTaskDao.clearAll();
            String newItemResult = null;

            List<NewsItem> articles = null;
            try{
                newItemResult = NetworkUtils.getResponseFromHttpUrl(newsurls[0]);
                articles = JsonUtils.parseNews(newItemResult);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            for (NewsItem article : articles) {
                mAsyncTaskDao.insert(article);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mAsyncTaskDao.clearAll();
        }
    }
}
