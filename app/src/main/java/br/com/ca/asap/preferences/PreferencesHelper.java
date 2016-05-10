package br.com.ca.asap.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * PreferencesHelper
 *
 * Helper to read and write preferences
 *
 * @author Rodrigo Carvalho
 */
public class PreferencesHelper {

    //preference files
    public static final String SIGNIN_PREFERENCES = "SIGNIN_PREFERENCES";
    public static final String APP_PREFERENCES = "APP_PREFERENCES";

    //signin preferences keys
    public static final String IS_LOGGED = "IS_LOGGED";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_ID = "USER_ID";

    //app preferences keys
    public static final String LAST_SYNC = "LAST_SYNC"; //TODO: should be a date

    private Context context = null;
    private String preferencesFile = null;

    /**
     * PreferencesHelper
     *
     * @param context
     * @param preferencesFile
     */
    public PreferencesHelper(Context context, String preferencesFile) {
        this.context = context;
        this.preferencesFile = preferencesFile;
    }

    /**
     * openPreferencesFile
     *
     * @return
     */
    private SharedPreferences openPreferencesFile(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(this.preferencesFile, this.context.MODE_PRIVATE);
        return sharedpreferences;
    }

    /**
     * getStringPreferenceValue
     *
     * Return the value of key preference value, or null if it does not exist
     * @param preferenceKey
     * @return
     */
    public String getStringPrefrenceValue(String preferenceKey){
        String preferenceValue = null;

        SharedPreferences sharedpreferences = openPreferencesFile();

        preferenceValue = sharedpreferences.getString(preferenceKey,"");

        return preferenceValue;
    }

    /**
     * getIntPreferenceValue
     *
     * Return the value of key preference value, or null if it does not exist
     * @param preferenceKey
     * @return
     */
    public int getIntPrefrenceValue(String preferenceKey){
        int preferenceValue;

        SharedPreferences sharedpreferences = openPreferencesFile();

        preferenceValue = sharedpreferences.getInt(preferenceKey, 0);

        return preferenceValue;
    }

    /**
     * setStringPreferenceKey
     *
     * Set preference value for preference key
     *
     * @param preferenceKey
     * @return
     */
    public Boolean setStringPreferenceValue(String preferenceKey, String preferenceValue){
        SharedPreferences sharedpreferences = openPreferencesFile();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(preferenceKey, preferenceValue);
        editor.commit();

        return true;
    }

    /**
     * setIntPreferenceKey
     *
     * Set preference value for preference key
     *
     * @param preferenceKey
     * @return
     */
    public Boolean setIntPreferenceValue(String preferenceKey, int preferenceValue){
        SharedPreferences sharedpreferences = openPreferencesFile();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(preferenceKey, preferenceValue);
        editor.commit();

        return true;
    }
}
