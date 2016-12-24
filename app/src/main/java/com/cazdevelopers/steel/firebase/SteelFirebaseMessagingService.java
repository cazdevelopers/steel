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
	private final SmsManager smsManager = SmsManager.getDefault();

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		if (!remoteMessage.getData().isEmpty()) {
			Map<String, String> data = remoteMessage.getData();
			for (String number : data.get(NUMBER).split(",")) {
				Log.d(TAG, "onMessageReceived: Sending text to " + number);
				sendMessage(smsManager.divideMessage(data.get(MESSAGE)), number);
			}
		}
	}

	private void sendMessage(ArrayList<String> message, String number) {
		if (message.size() == 1) {
			smsManager.sendTextMessage(number, null, message.get(0), null, null);
		} else {
			smsManager.sendMultipartTextMessage(number, null, message, null, null);
		}
	}
}
