package com.cazdevelopers.steel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cazdevelopers.steel.support.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	private FirebaseAuth firebaseAuth;
	private GoogleApiClient googleApiClient;
	private MenuItem logout;
	private boolean loggedIn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int status = apiAvailability.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			apiAvailability.getErrorDialog(this, status, 0).show();
		}

		firebaseAuth = FirebaseAuth.getInstance();
		if (firebaseAuth.getCurrentUser() == null) {
			loggedIn = false;
			new AlertDialog.Builder(this).setMessage("You must log in to continue")
										 .setPositiveButton("Ok", (dialog, which) ->
										 {
											 googleApiClient = new GoogleApiClient.Builder(this, this, this)
													 .addApi(Auth.GOOGLE_SIGN_IN_API,
															 new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
																	 .requestEmail()
																	 .build()).build();
											 googleApiClient.connect();
											 showProgressDialog("Connecting to Google");
										 })
										 .setNegativeButton("Cancel", (dialog, which) -> finish())
										 .show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		logout = menu.findItem(R.id.logout);
		logout.setVisible(loggedIn);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.logout:
				signOut();
				break;
			case R.id.token:
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				String token = sharedPreferences.getString("token", null);
				TextView textView = new TextView(this);
				textView.setText(token == null ? "No token found" : token);
				textView.setTextIsSelectable(true);
				new AlertDialog.Builder(this).setView(textView).setNeutralButton("Ok", null).show();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void signOut() {
		// Firebase sign out
		firebaseAuth.signOut();

		// Google sign out
		// This won't work quite yet - I have to get googleApiClient to persist and always be accessible
		Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(status -> logout.setVisible(false));
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		hideProgressDialog();
		startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				firebaseAuthWithGoogle(result.getSignInAccount());
			}
		}
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
		showProgressDialog("Authenticating");

		AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
		firebaseAuth.signInWithCredential(credential)
					.addOnCompleteListener(this, task -> {
						if (!task.isSuccessful()) {
							Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
						} else {
							loggedIn = true;
						}
						hideProgressDialog();
					});
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Toast.makeText(this, "Failed to connect to Google services", Toast.LENGTH_SHORT).show();
	}


}
