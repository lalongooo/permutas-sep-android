package com.permutassep.presentation.utils;

import android.content.Context;

import com.lalongooo.permutassep.R;
import com.permutassep.model.State;
import com.permutassep.presentation.config.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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


}