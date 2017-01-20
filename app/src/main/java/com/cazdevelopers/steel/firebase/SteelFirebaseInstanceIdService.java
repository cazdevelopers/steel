package com.cazdevelopers.steel.firebase;

import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class SteelFirebaseInstanceIdService extends FirebaseInstanceIdService {
	private static final String TAG = SteelFirebaseInstanceIdService.class.getSimpleName();

	@Override
	public void onTokenRefresh() {
		Log.d(TAG, "onTokenRefresh: Saving token to sharedpreferences");
		PreferenceManager.getDefaultSharedPreferences(this)
						 .edit()
						 .putString("token", FirebaseInstanceId.getInstance().getToken())
						 .apply();
	}
}
