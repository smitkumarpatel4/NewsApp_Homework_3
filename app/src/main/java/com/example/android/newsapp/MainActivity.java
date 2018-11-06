package com.example.android.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static final String URL_EXTRA = "newsArticleUrl";
    public static final String URL_Result = "newsUrlResult";

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> newsItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NewsRecyclerViewAdapter(MainActivity.this, newsItem);
        mRecyclerView.setAdapter(mAdapter);

        //makeNewsURL();
    }
    private void  makeNewsURL(){
        URL newsURL = NetworkUtils.buildUrl();
        new NewsQueryTask().execute(newsURL);
    }
    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL clickUrl = urls[0];
            String newsAppClickResult = null;
            try{
                newsAppClickResult = NetworkUtils.getResponseFromHttpUrl(clickUrl);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return newsAppClickResult;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("string_of_array", s);
            newsItem = JsonUtils.parseNews(s);
            populateRecyclerView(newsItem);
//            mAdapter.mNewsList.addAll(newsItem);
//            mAdapter.notifyDataSetChanged();
        }
    }


    public void populateRecyclerView(ArrayList<NewsItem> newsItem ){
         mAdapter.mNewsList.addAll(newsItem);
         mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatClickedId = item.getItemId();
            if (itemThatClickedId == R.id.action_search){
                Context context = MainActivity.this;
                makeNewsURL();
                return true;
            }
            return super.onOptionsItemSelected(item);
    }
}