package com.example.android.newsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText mSearchText;
    private ProgressBar mProgressBar;

    private static final String TAG = "MainActivity";
    private static final int LOADER_ID=1;
    private static final String SEARCH_QUERY_URL_EXTRA = "searchQuery";
    private static final String SEARCH_QUERY_RESULT = "searchResults";
    private String newsSearchResult;

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> newsItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchText = (EditText) findViewById(R.id.search_box);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NewsRecyclerViewAdapter(MainActivity.this, newsItem);
        mRecyclerView.setAdapter(mAdapter);
        //makeNewsURL();

        if(savedInstanceState != null && savedInstanceState.containsKey(SEARCH_QUERY_RESULT)){
            String searchResults = savedInstanceState.getString(SEARCH_QUERY_RESULT);
            populateRecyclerView(searchResults);
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_QUERY_RESULT, newsSearchResult);

    }

    private URL  makeNewsURL(){
        URL newsURL = NetworkUtils.buildUrl();
        return newsURL;
        //new NewsQueryTask().execute(newsURL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = makeNewsURL();
            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_QUERY_URL_EXTRA, url.toString());
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> gitHubSearchLoader = loaderManager.getLoader(LOADER_ID);
            if(gitHubSearchLoader == null){
                loaderManager.initLoader(LOADER_ID, bundle, this).forceLoad();
            }else{
                loaderManager.restartLoader(LOADER_ID, bundle, this).forceLoad();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<String>(this)
        {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.d(TAG, "onStartLoading called");
                super.onStartLoading();
                if(bundle == null){
                    Log.d(TAG, "bundle null");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Nullable
            @Override
            public String loadInBackground() {
                Log.d(TAG, "loadInBackground called");

                String newsSearchQuery = bundle.getString(SEARCH_QUERY_URL_EXTRA);
                if(newsSearchQuery == null || newsSearchQuery.isEmpty()){
                    return null;
                }
                try {
                    Log.d(TAG, "begin network call");
                    newsSearchResult = NetworkUtils.getResponseFromHttpUrl(new URL(newsSearchQuery));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, newsSearchResult);
                return newsSearchResult;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String o) {
       // Log.d("mycode", o);
        mProgressBar.setVisibility(View.GONE);
        populateRecyclerView(o);
    }



    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void populateRecyclerView(String searchResults ){
        //Log.d("mycode", searchResults);
        newsItem = JsonUtils.parseNews(searchResults);
        mAdapter.mNewsList.addAll(newsItem);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
}