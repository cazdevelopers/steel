package com.cazdevelopers.steel.firebase;

import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .edit()
                .putString("token", FirebaseInstanceId.getInstance().getToken())
                .apply();
    }
}
