package com.cazdevelopers.steel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cazdevelopers.steel.auth.GoogleSignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
	private FirebaseAuth firebaseAuth;
	private FirebaseAuth.AuthStateListener authStateListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		firebaseAuth = FirebaseAuth.getInstance();
		if (firebaseAuth.getCurrentUser() == null) {
			startActivity(new Intent(this, GoogleSignInActivity.class));
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
}
