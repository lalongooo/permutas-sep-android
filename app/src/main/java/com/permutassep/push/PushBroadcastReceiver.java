package com.permutassep.push;

import android.content.Context;
import android.content.Intent;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;
import com.permutassep.ui.ActivityMain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PushBroadcastReceiver extends ParsePushBroadcastReceiver {

    public static final String PUSH_POST_ID_EXTRA = "PSPostId";

    @Override
    protected void onPushOpen(Context context, Intent intent) {

        // Send a Parse Analytics "push opened" event
        ParseAnalytics.trackAppOpenedInBackground(intent);

        String psPostId = null;
        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra(KEY_PUSH_DATA));
            psPostId = pushData.optString(PUSH_POST_ID_EXTRA, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent activityIntent = new Intent(context, ActivityMain.class);
        activityIntent.putExtra(PUSH_POST_ID_EXTRA, psPostId);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(activityIntent);
    }
}
