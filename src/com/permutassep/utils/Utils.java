package com.permutassep.utils;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {

	public static Typeface getTypeFace(Context c){
		return Typeface.createFromAsset(c.getAssets(), "advent_bold_extra.ttf");
	}
	
}
