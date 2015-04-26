package com.permutassep.config;

public class Config {

    /**
     * The URL of the web service for the users registration 
     */
	public static final String APP_PREFERENCES_NAME = "PERMUTAS_SEP_PREFERENCES";	

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
	public static final int SPLASH_SCREEN_DELAY = 1000;

    /**
     * Endpoint of the InegiFacil REST service
     */
    public static final String INEGI_FACIL_REST_BASE_URL = "http://inegifacil.com/";

    /**
     * Endpoint of the PermutasSEP REST service
     */
    public static final String PERMUTAS_SEP_REST_BASE_URL = "https://permuta-sep.herokuapp.com/api/";

    /**
     * Date format used in the entire application
     */
    public static final String APP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String TEM_PWD = "*12345ABCDEF*";

}