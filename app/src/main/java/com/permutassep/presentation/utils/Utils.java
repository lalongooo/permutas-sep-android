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

    public static String formatDate(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(Config.API_DATE_FORMAT, Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(Config.APP_DATE_FORMAT, Locale.getDefault());

        return timeFormat.format(myDate);
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