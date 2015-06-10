package com.permutassep.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;

public class ActivityNewPasswordEmailSent extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_email_sent);

        // Toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setTitle(R.string.new_password_toolbar_title);


        TextView tvMsg1 = (TextView) findViewById(R.id.tvMsg1);

        String msg = String.format(getString(R.string.new_password_email_sent_msg_1), getIntent().getExtras().getString("email"));
        SpannableStringBuilder str = new SpannableStringBuilder(msg);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), (msg.length() - (getIntent().getExtras().getString("email").length()) - 1), msg.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // tvMsg1.setText(String.format(getString(R.string.new_password_email_sent_msg_1), getIntent().getExtras().getString("email")));
        tvMsg1.setText(Html.fromHtml(String.format(getString(R.string.new_password_email_sent_msg_1), getIntent().getExtras().getString("email"))));
        tvMsg1.setText(str);
    }

}
