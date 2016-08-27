package com.cazdevelopers.steel.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.drawable.Icon;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.cazdevelopers.steel.BuildConfig;
import com.cazdevelopers.steel.R;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelNotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (!sbn.getPackageName().equals(BuildConfig.APPLICATION_ID) && !sbn.isOngoing()) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.steel_alloy);
            builder.setContentTitle("You got a notification!");
            String packageName = sbn.getPackageName();
            builder.setContentText(String.format("A notification was received by %s", packageName.substring(packageName.lastIndexOf('.') + 1)));
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }
}
