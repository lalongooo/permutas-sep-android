package com.permutassep.config;

public class Config {
	

    /**
     * The URL of the web service for the users registration 
     */
	public static final String USER_REGISTRARION_URL = "http://tlatoa.herokuapp.com/kerberos/api/user";
	
	/**
	 * Tlatoa web service URL for translation
	 */
	public static final String PHRASE_TRANSLATION_URL = "http://tlatoa.herokuapp.com/manager/api/sentence?phrase=";
	
	/**
	 * Enable caching.
	 */
	public static final boolean PHRASE_CACHE = false;
	
	/**
	 * Default animation time of each image in the translation result activity (milliseconds). 
	 */	
	public static final int ANIMATION_DURATION = 500;
	
	/**
	 * Default cache expiration time 
	 */		
	public static final long CACHE_EXPIRATION_VALID_TIME = 21600000;
	
	/**
	 * The URL provided by Facebook for downloading the users profile pictures.
	 */	
	public static final String FB_PROFILE_PICTURE_URL_BASE = "https://graph.facebook.com/fbUserId/picture?type=";

	/**
	 * Google Analytics (placeholder) property ID.
	 */	
	public static final String GA_PROPERTY_ID = "UA-45551921-1";
	
	/**
	 * Facebook Application ID
	 */
	public static final String FACEBOOK_APP_ID = "143269149215368";
	
	/**
	 * Facebook application namespace (required for the registration on Facebook)
	 */
	public static final String FACEBOOK_APP_NAMESPACE = "com.xihuanicode.tlatoa";
	
	/**
	 * The splash screen activity duration (in milliseconds).
	 */
	public static final int SPLASH_SCREEN_DELAY = 3200;

}