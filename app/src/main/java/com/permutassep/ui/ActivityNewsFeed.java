package com.permutassep.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.permutassep.R;
import com.permutassep.config.Config;

public class ActivityNewsFeed extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		
	}
	
	private void showTOSDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.tos_dialog_text))
				.setPositiveButton(getString(R.string.tos_dialog_accept),dialogClickListener)
				.setNegativeButton(getString(R.string.tos_dialog_cancel),dialogClickListener).show();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				// Save the state
			    getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE)
			        .edit()
			        .putBoolean("tos_accepted", false)
			        .commit();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				finish();
				break;
			}
		}
	};

}
