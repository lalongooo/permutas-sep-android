package com.permutassep.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.lalongooo.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.model.State;
import com.permutassep.model.User;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.kots.mob.complex.preferences.ComplexPreferences;

public class Utils {

    public static float density = 1;


    public static int dp(float value, Context c) {
        density = c.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    public static Spannable replaceTags(String str, Context context) {
        try {
            int start = -1;
            int startColor = -1;
            int end = -1;
            StringBuilder stringBuilder = new StringBuilder(str);
            while ((start = stringBuilder.indexOf("<br>")) != -1) {
                stringBuilder.replace(start, start + 4, "\n");
            }
            while ((start = stringBuilder.indexOf("<br/>")) != -1) {
                stringBuilder.replace(start, start + 5, "\n");
            }
            ArrayList<Integer> bolds = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            while ((start = stringBuilder.indexOf("<b>")) != -1 || (startColor = stringBuilder.indexOf("<c")) != -1) {
                if (start != -1) {
                    stringBuilder.replace(start, start + 3, "");
                    end = stringBuilder.indexOf("</b>");
                    stringBuilder.replace(end, end + 4, "");
                    bolds.add(start);
                    bolds.add(end);
                } else if (startColor != -1) {
                    stringBuilder.replace(startColor, startColor + 2, "");
                    end = stringBuilder.indexOf(">", startColor);
                    int color = Color.parseColor(stringBuilder.substring(startColor, end));
                    stringBuilder.replace(startColor, end + 1, "");
                    end = stringBuilder.indexOf("</c>");
                    stringBuilder.replace(end, end + 4, "");
                    colors.add(startColor);
                    colors.add(end);
                    colors.add(color);
                }
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
            for (int a = 0; a < colors.size() / 3; a++) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(colors.get(a * 3 + 2)), colors.get(a * 3), colors.get(a * 3 + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableStringBuilder;
        } catch (Exception e) {

        }
        return new SpannableStringBuilder(str);
    }

    public static Typeface getTypeFace(Context c) {
//		return Typeface.createFromAsset(c.getAssets(), "advent_bold_extra.ttf");
        return Typeface.DEFAULT;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static HashMap<String, State> getStates(Context c) {
        String[] state_descr = c.getResources().getStringArray(R.array.states);
        String[] state_codes = c.getResources().getStringArray(R.array.state_codes);

        HashMap<String, State> states = new HashMap<String, State>();

        for (short i = 0; i < state_descr.length; i++) {
            State s = new State(i, state_descr[i], state_codes[i]);
            states.put(String.valueOf(i), s);
        }
        return states;
    }

    /**
     * Retrieves a list of the Mexico states.
     *
     * @param context The current context
     */
    public static List<State> getStateList(Context context) {
        String[] state_descr = context.getResources().getStringArray(R.array.states);
        String[] state_codes = context.getResources().getStringArray(R.array.state_codes);

        List<State> states = new ArrayList<>();

        for (short i = 0; i < state_descr.length; i++) {
            State s = new State(i, state_descr[i], state_codes[i]);
            states.add(s);
        }
        return states;
    }

    public static void showSimpleDialog(final int dlgMessage, final int btnText, FragmentActivity activity, final DialogInterface.OnClickListener onClickListener) {
        DialogFragment dg = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(getActivity())
                        .setMessage(dlgMessage)
                        .setPositiveButton(btnText, onClickListener)
                        .setCancelable(false)
                        .create();
            }
        };
        dg.setCancelable(false);
        dg.show(activity.getSupportFragmentManager(), "simple_dialog");
    }

    /**
     * Check if the device is connected to the internet
     *
     * @param context The current context
     */
    public static Boolean isNetworkAvailable(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Retrieves the current logged user
     *
     * @param context The current context
     */
    public static User getUser(Context context){
        return ComplexPreferences.getComplexPreferences(context, Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE).getObject(PrefUtils.PREF_USER_KEY, User.class);
    }

    /**
     * Retrieves a List<NameValuePair>
     * By providing a URL like this: http://stackoverflow.com?param1=value1&param2=&param3=value3&param3
     * Taken from http://stackoverflow.com/questions/13592236/parse-the-uri-string-into-name-value-collection-in-java
     *
     * @param url The url to extract the query string parameters
     */
    public static List<NameValuePair> splitQuery(String url){

        List<NameValuePair> params = null;
        try {
            params = URLEncodedUtils.parse(new URI(url), "UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return params;
    }
}