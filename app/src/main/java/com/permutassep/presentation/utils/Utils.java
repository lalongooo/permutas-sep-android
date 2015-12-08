package com.permutassep.presentation.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.lalongooo.permutassep.R;
import com.permutassep.model.State;
import com.permutassep.presentation.config.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final SimpleDateFormat apiDateFormat = new SimpleDateFormat(Config.API_DATE_FORMAT, Locale.getDefault());
    private static final SimpleDateFormat appTimeFormat = new SimpleDateFormat(Config.APP_DATE_FORMAT, Locale.getDefault());

    /**
     * Parses a string value into a Date object
     * @param string The string to be parsed
     * @return A date object based in the @{link String} parameter
     */
    public static Date toDate(String string){
        Date myDate = null;
        try {
            myDate = apiDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }

    public static String formatDate(String dateString) {

        return appTimeFormat.format(toDate(dateString));
    }

    public static HashMap<String, State> getStates(Context c) {
        String[] state_descr = c.getResources().getStringArray(R.array.states);
        String[] state_codes = c.getResources().getStringArray(R.array.state_codes);

        HashMap<String, State> states = new HashMap<>();

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

        for (int i = 0; i < state_descr.length; i++) {
            State s = new State(i, state_descr[i], state_codes[i]);
            states.add(s);
        }
        return states;
    }


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

}