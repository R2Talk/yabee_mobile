package br.com.ca.asap.users;

import android.content.Context;

import br.com.ca.asap.preferences.PreferencesHelper;
import br.com.ca.asap.vo.UserVo;

/**
 * SignManager
 *
 * Manages SignIn, SignOut and SignUp operations.
 *
 * @author Rodrigo Carvalho
 */
public class SignManager {

    Context context = null;
    //
    // Constructor
    //
    public SignManager(Context context) {
        this.context = context;
    }

    //
    // signIn
    //
    // set singleton current user
    //
    public void signIn(UserVo userVo){
        CurrentUser currentUser= CurrentUser.getInstance();
        currentUser.setUser(userVo);

        //save session sign in data
        PreferencesHelper preferencesHelper = new PreferencesHelper(context, PreferencesHelper.SIGNIN_PREFERENCES);

        preferencesHelper.setStringPreferenceValue(PreferencesHelper.IS_LOGGED,"yes");
        preferencesHelper.setIntPreferenceValue(PreferencesHelper.USER_ID, userVo.getUserId().intValue());
        preferencesHelper.setStringPreferenceValue(PreferencesHelper.USER_EMAIL, userVo.getEmail());
        preferencesHelper.setStringPreferenceValue(PreferencesHelper.USER_NAME, userVo.getName());

    }

    //
    //signOut
    //
    public void signOut(){
        //clear singleton
        CurrentUser currentUser= CurrentUser.getInstance();
        currentUser.setUser(null);

        //remove from preferences
        PreferencesHelper preferencesHelper = new PreferencesHelper(context, PreferencesHelper.SIGNIN_PREFERENCES);

        preferencesHelper.setStringPreferenceValue(PreferencesHelper.IS_LOGGED,"");
        preferencesHelper.setIntPreferenceValue(PreferencesHelper.USER_ID, 0);
        preferencesHelper.setStringPreferenceValue(PreferencesHelper.USER_NAME, "");

    }

    //
    // isSignedIn
    //
    // return true if current user is not null
    //
    public Boolean isSignedIn(){
        // check if there is an user logged in
        CurrentUser currentUser= CurrentUser.getInstance();
        UserVo userVo = currentUser.getUser();
        if (userVo != null){
            return true;
        } else {
            return false;
        }
    }

    //
    // signUp
    //
    public void signUp(){

    }

    //
    // getCurrentUser
    //
    public UserVo getCurrentUser(){
        UserVo userVo = null;

        CurrentUser currentUser= CurrentUser.getInstance();
        userVo = currentUser.getUser();

        return userVo;
    }

    /**
     * knownUser
     *
     * Check if the user is logged in based in the last openned session
     *
     * @return
     */
    public Boolean knownUser(){
        Boolean result = false;

        //
        //check shared preferences to check if the user is already know.
        //
        PreferencesHelper preferencesHelper = new PreferencesHelper(context, PreferencesHelper.SIGNIN_PREFERENCES);
        String isLogged = preferencesHelper.getStringPrefrenceValue(PreferencesHelper.IS_LOGGED);

        //check if already logged in
        if (isLogged.equals("yes")) { // if the user is already logged in
            result = true;
        }

        return result;
    }

    /**
     * initializeSessionFromPreferences
     *
     * Automatic SignIn for alread openned session
     */
    public Boolean initializeSessionFromPreferences(){
        //
        //check shared preferences to check if the user is already know.
        //
        PreferencesHelper preferencesHelper = new PreferencesHelper(context, PreferencesHelper.SIGNIN_PREFERENCES);
        String isLogged = preferencesHelper.getStringPrefrenceValue(PreferencesHelper.IS_LOGGED);

        //check if already logged in
        if (isLogged.equals("yes")){ // if the user is already logged in

            //create user object and proceed for the initial activity
            int userId = preferencesHelper.getIntPrefrenceValue(PreferencesHelper.USER_ID);
            String userName = preferencesHelper.getStringPrefrenceValue(PreferencesHelper.USER_NAME);
            UserVo userVo = new UserVo(userId,userName,"", "", true);

            //sign in
            signIn(userVo);

            return true;

        } else {
            return false; //TODO: create exception
        }
    }

}
