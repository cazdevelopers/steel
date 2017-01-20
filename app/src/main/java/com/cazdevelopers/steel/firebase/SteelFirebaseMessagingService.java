package com.cazdevelopers.steel.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.cazdevelopers.steel.SteelBroadcastReceiver;
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
	public static final String MESSAGE_SENT = "message_sent";
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
		int size = message.size();
		Intent intent = new Intent(this, SteelBroadcastReceiver.class).setAction(MESSAGE_SENT);
		if (size == 1) {
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			smsManager.sendTextMessage(number, null, message.get(0), pendingIntent, null);
		} else {
			ArrayList<PendingIntent> intents = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				intents.add(PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_CANCEL_CURRENT));
			}
			smsManager.sendMultipartTextMessage(number, null, message, intents, null);
		}
	}
}
