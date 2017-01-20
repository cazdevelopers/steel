package com.cazdevelopers.steel;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;

import com.cazdevelopers.steel.firebase.SteelFirebaseMessagingService;
import com.cazdevelopers.steel.notification.SteelNotificationListener;

/**
 * Created by coreywoodfield on 8/26/16.
 */
public class SteelBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = SteelBroadcastReceiver.class.getSimpleName();

	private static final long[] VIBRATE_PATTERN = new long[]{300, 100, 300, 100, 300};

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			context.startService(new Intent(context, SteelNotificationListener.class));
		} else if (intent.getAction().equals(SteelFirebaseMessagingService.MESSAGE_SENT)) {
			Log.d(TAG, "onReceive: received broadcast");
			int resultCode = getResultCode();
			Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			if (resultCode == Activity.RESULT_OK) {
				vibrator.vibrate(500);
			} else {
				switch (resultCode) {
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Log.e(TAG, "onReceive: RESULT_ERROR_GENERIC_FAILURE");
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Log.e(TAG, "onReceive: RESULT_ERROR_NO_SERVICE");
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Log.e(TAG, "onReceive: RESULT_ERROR_NULL_PDU");
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Log.e(TAG, "onReceive: RESULT_ERROR_RADIO_OFF");
						break;
				}
				vibrator.vibrate(VIBRATE_PATTERN, -1);
			}
		}
	}
}
