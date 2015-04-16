package com.permutassep.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.permutassep.R;
import com.permutassep.model.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

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

    public static List<State> getStateList(Context c) {
        String[] state_descr = c.getResources().getStringArray(R.array.states);
        String[] state_codes = c.getResources().getStringArray(R.array.state_codes);

        List<State> states = new ArrayList<>();

        for (short i = 0; i < state_descr.length; i++) {
            State s = new State(i, state_descr[i], state_codes[i]);
            states.add(s);
        }
        return states;
    }

    public static void showSimpleDialog(final int dlgMessage, final int btnText, FragmentActivity activity, final DialogInterface.OnClickListener onClickListener){
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
        dg.show(activity.getSupportFragmentManager(), "simple_dialog");
    }
}
