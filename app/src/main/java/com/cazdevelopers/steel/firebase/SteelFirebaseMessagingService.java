package com.cazdevelopers.steel.firebase;

import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelFirebaseMessagingService extends FirebaseMessagingService {
	private static final String TAG = SteelFirebaseMessagingService.class.getSimpleName();
	public static final String MESSAGE = "message";
	public static final String NUMBER = "number";

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		if (!remoteMessage.getData().isEmpty()) {
			Map<String, String> data = remoteMessage.getData();
			SmsManager smsManager = SmsManager.getDefault();
			String message = data.get(MESSAGE);
			ArrayList<String> strings = smsManager.divideMessage(message);
			if (strings.size() == 1) {
				smsManager.sendTextMessage(data.get(NUMBER), null, message, null, null);
			} else {
				smsManager.sendMultipartTextMessage(data.get(NUMBER), null, strings, null, null);
			}
			Log.d(TAG, "onMessageReceived: sms sent");
			return;
		}

		super.onMessageReceived(remoteMessage);
	}
}
