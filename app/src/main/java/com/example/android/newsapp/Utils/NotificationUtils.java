package com.example.android.newsapp.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.newsapp.MainActivity;
import com.example.android.newsapp.Notification.NotificationIntent;
import com.example.android.newsapp.R;

public class NotificationUtils {
    private static final int REMINDER_NOTIFICATION_ID = 1337;
    private static final int REMINDER_PENDING_INTENT_ID = 9001;
    private static final String REMINDER_NOTIFICATION_CHANNEL_ID = "notification_channel";
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 69;

    public static final String ACTION_IGNORE_NOTIFICATION = "ignore_notification";

    public static void ReamindUser(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel mChannel = new NotificationChannel(
                    REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_notification_bitmap_24px)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());

    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, NotificationIntent.class);
        ignoreReminderIntent.setAction(ACTION_IGNORE_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_clearmap, "EXIT",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }
    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_notification_bitmap_24px);
        return largeIcon;
    }


}
