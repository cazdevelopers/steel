package com.cazdevelopers.steel.notification;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.cazdevelopers.steel.BuildConfig;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelNotificationListener extends NotificationListenerService {

	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		// Don't do anything with notifications to this app - SteelFirebaseMessagingService takes care of that
		if (!sbn.getPackageName().equals(BuildConfig.APPLICATION_ID)) {

		}
//        if (!sbn.getPackageName().equals(BuildConfig.APPLICATION_ID) && !sbn.isOngoing()) {
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setContentTitle("You got a notification!");
//            String packageName = sbn.getPackageName();
//            builder.setContentText(String.format("A notification was received by %s", packageName.substring(packageName.lastIndexOf('.') + 1)));
//            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());
//        }
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {

	}
}
