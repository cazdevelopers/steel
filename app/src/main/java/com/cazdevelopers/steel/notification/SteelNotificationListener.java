package com.cazdevelopers.steel.notification;

import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelNotificationListener extends NotificationListenerService {

	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		Set<String> filter = PreferenceManager.getDefaultSharedPreferences(this).getStringSet("filter", new HashSet<>(0));
		if (!filter.contains(sbn.getPackageName())) {
			// TODO: 12/23/16 send notification to computer
		}
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {

	}
}
