package com.example.android.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (WebView)findViewById(R.id.webview);


        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String urlStrig = intent.getStringExtra(Intent.EXTRA_TEXT);
            mWebView.loadUrl(urlStrig);
        }
    }

}
