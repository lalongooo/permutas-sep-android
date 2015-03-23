package com.permutassep.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;

public class Utils {

	public static Typeface getTypeFace(Context c){
//		return Typeface.createFromAsset(c.getAssets(), "advent_bold_extra.ttf");
		return Typeface.DEFAULT;
	}

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
	
}
