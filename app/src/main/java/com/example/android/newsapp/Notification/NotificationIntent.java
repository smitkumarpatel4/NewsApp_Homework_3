package com.example.android.newsapp.Notification;

import android.app.IntentService;
import android.content.Intent;

public class NotificationIntent extends IntentService {

    public NotificationIntent() {
        super("NotificationIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
    }
}
