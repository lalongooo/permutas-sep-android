package com.permutassep.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.permutassep.R;

public class ActivityNewsFeed extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
	}

	public void testTOSDialog(View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.tos_dialog_text))
				.setPositiveButton(getString(R.string.tos_dialog_accept), dialogClickListener)
				.setNegativeButton(getString(R.string.tos_dialog_cancel), dialogClickListener).show();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				Toast.makeText(getApplicationContext(), "Yes clicked!", Toast.LENGTH_SHORT).show();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				Toast.makeText(getApplicationContext(), "No clicked!",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

}
