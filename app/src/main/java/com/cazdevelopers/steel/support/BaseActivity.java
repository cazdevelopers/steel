package com.cazdevelopers.steel.support;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by coreywoodfield on 9/28/16.
 */

public class BaseActivity extends AppCompatActivity {
	private ProgressDialog progressDialog;

	protected void showProgressDialog(String message) {
		progressDialog = ProgressDialog.show(this, null, message, true);
	}

	protected void hideProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}
