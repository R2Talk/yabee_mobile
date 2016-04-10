package br.com.ca.asap.user;

import br.com.ca.asap.vo.UserVo;

/**
 * SignManager
 *
 * Manages SignIn, SignOut and SignUp operations.
 *
 * @author Rodrigo Carvalho
 */
public class SignManager {

    //
    // Constructor
    //
    public SignManager() {
    }

    //
    // signIn
    //
    // set singleton current user
    //
    public void signIn(UserVo userVo){
        CurrentUser currentUser= CurrentUser.getInstance();
        currentUser.setUser(userVo);
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
}
