package com.example.android.newsapp.FirebaseService;

import com.example.android.newsapp.Models.NewsItemRepository;
import com.example.android.newsapp.Utils.NotificationUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;
import android.util.Log;

public class NewsItemFireBaseJobService extends JobService {

    private NewsItemRepository newsItemRepository;

    private AsyncTask<Void, Void, Void> mAsyncTask;

    public NewsItemFireBaseJobService() {

    }

    @Override
    public boolean onStartJob(final JobParameters job) {

         mAsyncTask = new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                newsItemRepository.syncURL();
                NotificationUtils.ReamindUser(NewsItemFireBaseJobService.this);
                return null;
            }
             @Override
             protected void onPostExecute(Void o) {
                 jobFinished(job, false);
                 Log.d("Hi", "Completed");
             }
        };
         mAsyncTask.execute();
         return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
        }
        return true;
    }
}
