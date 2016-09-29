package com.cazdevelopers.steel.firebase;

import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelFirebaseMessagingService extends FirebaseMessagingService {
	private static final String TAG = SteelFirebaseMessagingService.class.getSimpleName();

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		Log.d(TAG, "onMessageReceived: received message");
		if (!remoteMessage.getData().isEmpty()) {
			Log.d(TAG, "onMessageReceived: message has data");
			Map<String, String> data = remoteMessage.getData();
			if (data.containsKey("message")) {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(data.get("number"), null, data.get("message"), null, null);
				Log.d(TAG, "onMessageReceived: sms sent");
				return;
			}
		}

		super.onMessageReceived(remoteMessage);
	}
}
