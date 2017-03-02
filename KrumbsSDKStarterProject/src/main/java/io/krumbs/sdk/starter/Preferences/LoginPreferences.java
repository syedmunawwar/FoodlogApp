package io.krumbs.sdk.starter.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import io.krumbs.sdk.starter.Models.User;

/**
 * Created by SYED on 15-02-2017.
 */
public class LoginPreferences {
    SharedPreferences preferences;
    Context context;
    public static final String USERID = "uid";
    public static final String USERTABLE = "usertable";
    public static final String USERNAME = "name";
    public static final String EMAIL = "email";
    public static final String GENDER = "gender";
    public static final String AGE = "AGE";
    public static final String IS_LOGGED_IN = "isloggedin";

    public LoginPreferences(Context context){
        preferences = context.getSharedPreferences(USERTABLE,context.MODE_PRIVATE);
        this.context = context;
    }

    public void loginUser(User user){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERID,user.getUid());
        editor.putString(USERNAME,user.getUsername());
        editor.putString(EMAIL,user.getEmail());
        editor.putInt(AGE, user.getAge());
        editor.putString(GENDER, user.getGender());
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.apply();
    }

    public void logoutUser(){
        if(preferences.getBoolean(IS_LOGGED_IN,false)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }
    }
    public User getUser() {

        String userId = preferences.getString(USERID,"");
        String Name = preferences.getString(USERNAME,"");
        String email = preferences.getString(EMAIL,"");
        int age = preferences.getInt(AGE,-1);
        String gender = preferences.getString(GENDER, "");
        return new User(userId,Name,email,gender,age);
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN,false);
    }

}

