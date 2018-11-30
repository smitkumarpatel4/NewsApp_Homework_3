package com.example.android.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.newsapp.Adapter.NewsRecyclerViewAdapter;
import com.example.android.newsapp.FirebaseService.NewsItemFireBaseJobDispatcher;
import com.example.android.newsapp.Models.NewsItem;
import com.example.android.newsapp.Models.NewsItemDatabase;
import com.example.android.newsapp.Models.NewsItemViewModel;
import com.example.android.newsapp.Utils.JsonUtils;
import com.example.android.newsapp.Utils.NetworkUtils;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

   // private ProgressBar mProgressbar;

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private NewsItemViewModel mNewsItemViewModel;

    private List<NewsItem> mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  mProgressbar = (ProgressBar) findViewById(R.id.progress);

        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

       // mAdapter = new NewsRecyclerViewAdapter(MainActivity.this, mNewsItemViewModel);

        mAdapter = new NewsRecyclerViewAdapter(MainActivity.this, mNewsList);
        mRecyclerView.setAdapter(mAdapter);

        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        mNewsItemViewModel.getmAllNewsItem().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mAdapter.setNewsList(newsItems);
            }
        });

        NewsItemFireBaseJobDispatcher.getNewsItemFireBaseJobService().ScheduleTask(this);

    }

//    private void  makeNewsURL(){
//        URL newsURL = NetworkUtils.buildUrl();
//        new NewsQueryTask().execute(newsURL);
//    }
//    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
//        @Override
//        protected String doInBackground(URL... urls) {
//            URL clickUrl = urls[0];
//            String newsAppClickResult = null;
//            try{
//                newsAppClickResult = NetworkUtils.getResponseFromHttpUrl(clickUrl);
//            }
//            catch (IOException e){
//                e.printStackTrace();
//            }
//            return newsAppClickResult;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Log.d("string_of_array", s);
//            newsItem = JsonUtils.parseNews(s);
//            mAdapter.mNewsList.addAll(newsItem);
//            mAdapter.notifyDataSetChanged();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatClickedId = item.getItemId();
        if (itemThatClickedId == R.id.action_refresh){
//            Context context = MainActivity.this;
//            URL newsURL = NetworkUtils.buildUrl();
            mNewsItemViewModel.syncURL();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
