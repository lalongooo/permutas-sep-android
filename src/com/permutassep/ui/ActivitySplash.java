package com.permutassep.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.utils.Utils;


public class ActivitySplash extends ActionBarActivity {

	 private TextView tvAppName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);		
		tvAppName = (TextView) findViewById(R.id.tvAppName);		
		tvAppName.setTypeface(Utils.getTypeFace(this));
		
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				Intent mainIntent = new Intent().setClass(ActivitySplash.this, ActivityAppOverview.class);
				startActivity(mainIntent);
				finish();
			}

		};

		Timer timer = new Timer();
		timer.schedule(task, Config.SPLASH_SCREEN_DELAY);
	}

}
