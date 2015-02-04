package com.permutassep.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.permutassep.R;
import com.permutassep.config.Config;


public class ActivitySplash extends ActionBarActivity {

	TextView tvAppName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
				
		Typeface tf = Typeface.createFromAsset(getAssets(), "advent_bold_extra.ttf");
		tvAppName = (TextView) findViewById(R.id.tvAppName);
		tvAppName.setTypeface(tf);
		
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
