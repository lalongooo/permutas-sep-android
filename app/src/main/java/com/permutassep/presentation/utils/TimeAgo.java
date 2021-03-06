package com.permutassep.presentation.utils;

import android.content.Context;
import android.content.res.Resources;

import com.lalongooo.permutassep.R;

import java.util.Date;

public class TimeAgo {

    private Resources mResources;

    public TimeAgo(Context context) {
        mResources = context.getResources();
    }

    public String timeAgo(Date date) {
        return timeAgo(date.getTime());
    }

    public String timeAgo(long millis) {
        long diff = new Date().getTime() - millis;

        String prefix = mResources.getString(R.string.time_ago_prefix);
        String suffix = mResources.getString(R.string.time_ago_suffix);

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words;

        if (seconds < 45) {
            words = mResources.getString(R.string.time_ago_seconds, Math.round(seconds));
        } else if (seconds < 90) {
            words = mResources.getString(R.string.time_ago_minute, 1);
        } else if (minutes < 45) {
            words = mResources.getString(R.string.time_ago_minutes, Math.round(minutes));
        } else if (minutes < 90) {
            words = mResources.getString(R.string.time_ago_hour, 1);
        } else if (hours < 24) {
            words = mResources.getString(R.string.time_ago_hours, Math.round(hours));
        } else if (hours < 42) {
            words = mResources.getString(R.string.time_ago_day, 1);
        } else if (days < 30) {
            words = mResources.getString(R.string.time_ago_days, Math.round(days));
        } else if (days < 45) {
            words = mResources.getString(R.string.time_ago_month, 1);
        } else if (days < 365) {
            words = mResources.getString(R.string.time_ago_months, Math.round(days / 30));
        } else if (years < 1.5) {
            words = mResources.getString(R.string.time_ago_year, 1);
        } else {
            words = mResources.getString(R.string.time_ago_years, Math.round(years));
        }

        StringBuilder sb = new StringBuilder();

        if (prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }

        sb.append(words);

        if (suffix.length() > 0) {
            sb.append(" ").append(suffix);
        }

        return sb.toString().trim();
    }
}