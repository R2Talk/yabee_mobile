package br.com.ca.asap.users;

import br.com.ca.asap.vo.UserVo;

/**
 * CurrentUser
 *
 * Singleton Class that represents current logged user
 *
 * @author Rodrigo Carvalho
 */

public class CurrentUser {

    private static CurrentUser instance = null;
    private static UserVo userVo = null;

    //constructor
    protected CurrentUser() {
        // Exists only to defeat instantiation.
    }

    //get singleton instance
    public static CurrentUser getInstance() {
        if(instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    //return singleton UserVo instance
    protected UserVo getUser(){
        return this.userVo;
    }

    //set singleton UserVo instance
    protected void setUser(UserVo userVo){
        //set reference to static UserVo
        this.userVo = userVo;
    }

    //invalidate singleton UserVo instance
    protected void invalidateUser(){
        //invalidate static UserVo attributes
        this.userVo = null;
    }
}