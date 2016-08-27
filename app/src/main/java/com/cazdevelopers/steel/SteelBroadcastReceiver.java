package com.cazdevelopers.steel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cazdevelopers.steel.notification.SteelNotificationListener;

/**
 * Created by coreywoodfield on 8/26/16.
 */
public class SteelBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, SteelNotificationListener.class));
        }
    }
}
