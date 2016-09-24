package com.cazdevelopers.steel.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.cazdevelopers.steel.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by coreywoodfield on 9/23/16.
 */
public class GoogleSignInActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
	private final static String TAG = GoogleSignInActivity.class.getSimpleName();
	private GoogleApiClient googleApiClient;
	private FirebaseAuth firebaseAuth;
	private ProgressDialog progressDialog;


	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setContentView(R.layout.sign_in_activity);
		progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage("Loading...");
		progressDialog.show();

		GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail().build();

		googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).addConnectionCallbacks(this).build();

		firebaseAuth = FirebaseAuth.getInstance();
	}

	@Override
	protected void onStart() {
		googleApiClient.connect();
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		googleApiClient.disconnect();
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		progressDialog.dismiss();
		startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else {
				Toast.makeText(GoogleSignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
		progressDialog.show();

		firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(), null))
				.addOnCompleteListener(this, task -> {
					// If sign in fails, display a message to the user. If sign in succeeds
					// the auth state listener will be notified and logic to handle the
					// signed in user can be handled in the listener.
					if (!task.isSuccessful()) {
						Log.w(TAG, "signInWithCredential", task.getException());
						Toast.makeText(GoogleSignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
					}
					progressDialog.dismiss();
				});
	}

	@Override
	public void onConnectionSuspended(int i) {

	}
}
