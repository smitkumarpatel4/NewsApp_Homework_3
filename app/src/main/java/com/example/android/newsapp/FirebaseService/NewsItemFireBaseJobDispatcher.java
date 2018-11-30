package com.example.android.newsapp.FirebaseService;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class NewsItemFireBaseJobDispatcher  {

    private static final String  ACTION_REMINDER_TAG = "LATEST_NEWS";
    private static final int REMINDER_INTERVALS = 10;
    private static final int REMINDER_SYNC_FLEXTIME = 10;


    public static boolean REMINDER_FLAG = false;

    public static void ScheduleTask (Context context){
        if(REMINDER_FLAG)
            return;

        GooglePlayDriver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Log.d("SyncDataBase", "Going to start job");

        Job constrainReminderJob= dispatcher.newJobBuilder()
                .setService(NewsItemFireBaseJobService.class)
                .setTag(ACTION_REMINDER_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(REMINDER_INTERVALS, REMINDER_INTERVALS + REMINDER_SYNC_FLEXTIME))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constrainReminderJob);
        REMINDER_FLAG = true;
    }

    public static NewsItemFireBaseJobDispatcher getNewsItemFireBaseJobService() {
        return new NewsItemFireBaseJobDispatcher();
    }
}
