package com.cazdevelopers.steel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	private FirebaseAuth firebaseAuth;
	private GoogleApiClient googleApiClient;

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
			new AlertDialog.Builder(this).setMessage("You must log in to continue")
										 .setPositiveButton("Ok", (dialog, which) ->
										 {
											 googleApiClient = new GoogleApiClient.Builder(this, this, this)
													 .addApi(Auth.GOOGLE_SIGN_IN_API,
															 new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
																	 .requestEmail()
																	 .build()).build();
											 googleApiClient.connect();
										 })
										 .setNegativeButton("Cancel", (dialog, which) -> finish())
										 .show();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		startActivity(Auth.GoogleSignInApi.getSignInIntent(googleApiClient));
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Toast.makeText(this, "Failed to connect to Google services", Toast.LENGTH_SHORT).show();
	}
}
