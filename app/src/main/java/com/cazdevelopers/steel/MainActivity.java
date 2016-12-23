package com.cazdevelopers.steel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cazdevelopers.steel.support.BaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends BaseActivity {
	private final static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int status = apiAvailability.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			apiAvailability.getErrorDialog(this, status, 0).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.token:
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				String token = sharedPreferences.getString("token", null);
				if (token == null) {
					Log.d(TAG, "onOptionsItemSelected: preferences has null token");
					token = FirebaseInstanceId.getInstance().getToken();
				}
				TextView textView = new TextView(this);
				textView.setText(token == null ? "No token found" : token);
				textView.setTextIsSelectable(true);
				textView.setGravity(Gravity.CENTER);
				new AlertDialog.Builder(this).setView(textView).setNeutralButton("Ok", null).show();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

}
