package com.permutassep.ui;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.permutassep.R;
import com.permutassep.adapter.AppOverviewAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class ActivityAppOverview extends Activity {

	private PageIndicator indicator;
	private ViewPager viewPager;
	int backgroundColors [] = {0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_app_overview);
		setUI();
	}

	private void setUI() {
		
        ColorDrawable[] colors = {new ColorDrawable(backgroundColors[3]), new ColorDrawable(backgroundColors[0])};
        TransitionDrawable trans = new TransitionDrawable(colors);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setBackgroundDrawable(trans);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);

		AppOverviewAdapter adapter = new AppOverviewAdapter(this);
		viewPager.setAdapter(adapter);
		indicator.setViewPager(viewPager);
		
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
		    @Override
		    public void onPageSelected(int position) {
		    	int previousPosition = 1;
		        ColorDrawable[] colors = {new ColorDrawable(backgroundColors[previousPosition]), new ColorDrawable(backgroundColors[position])};
		        TransitionDrawable trans = new TransitionDrawable(colors);
		        viewPager.setBackgroundDrawable(trans);
		        trans.startTransition(2000);
		        previousPosition = position;
		    }

		    @Override
		    public void onPageScrolled(int arg0, float arg1, int arg2) {}

		    @Override
		    public void onPageScrollStateChanged(int arg0) {}
		});
	}

}
