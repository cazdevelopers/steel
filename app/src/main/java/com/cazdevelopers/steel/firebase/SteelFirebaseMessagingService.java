package com.cazdevelopers.steel.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (!remoteMessage.getData().isEmpty()) {
            Map<String, String> data = remoteMessage.getData();

        }

        super.onMessageReceived(remoteMessage);
    }
}
