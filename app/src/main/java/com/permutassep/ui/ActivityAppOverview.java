package com.permutassep.ui;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.utils.Utils;

public class ActivityAppOverview extends BaseActivity {
    private ViewPager viewPager;
    private ImageView topImage1;
    private ImageView topImage2;
    private int lastPage = 0;
    private boolean startPressed = false;
    private int[] icons;
    private int[] messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.ca_activity_app_overview);

        icons = new int[]{
                R.drawable.app_overview_1_buy,
                R.drawable.app_overview_2_smartphone,
                R.drawable.app_overview_3_save_money,
                R.drawable.app_overview_4_coupons
        };
        messages = new int[]{
                R.string.app_overview_1st_item_text,
                R.string.app_overview_2nd_item_text,
                R.string.app_overview_3rd_item_text,
                R.string.app_overview_4th_item_text
        };
        viewPager = (ViewPager) findViewById(R.id.intro_view_pager);
        TextView startMessagingButton = (TextView) findViewById(R.id.start_messaging_button);
        startMessagingButton.setText(getString(R.string.app_overview_start_button));
        if (Build.VERSION.SDK_INT >= 21) {
            StateListAnimator animator = new StateListAnimator();
            animator.addState(new int[]{android.R.attr.state_pressed}, ObjectAnimator.ofFloat(startMessagingButton, "translationZ", Utils.dp(2, this), Utils.dp(4, this)).setDuration(200));
            animator.addState(new int[]{}, ObjectAnimator.ofFloat(startMessagingButton, "translationZ", Utils.dp(4, this), Utils.dp(2, this)).setDuration(200));
            startMessagingButton.setStateListAnimator(animator);
        }
        topImage1 = (ImageView) findViewById(R.id.icon_image1);
        topImage2 = (ImageView) findViewById(R.id.icon_image2);

        topImage2.setVisibility(View.GONE);
        viewPager.setAdapter(new IntroAdapter());
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(1);

        startMessagingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityAppOverview.this, ActivityMain.class));
                finish();
            }
        });
    }

    private class IntroAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), R.layout.activity_app_overview_vp, null);
            TextView messageTextView = (TextView) view.findViewById(R.id.message_text);
            container.addView(view, 0);
            messageTextView.setText(Utils.replaceTags(getString(messages[position]), getApplicationContext()));
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}