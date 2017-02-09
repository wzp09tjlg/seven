package com.jia.seven;

import android.app.Application;
import android.content.Context;

import com.jia.seven.network.parse.User;

/**
 * Created by wu on 2017/2/8.
 */
public class HSApplication extends Application {
    public static Context gContext;
    public static User gUser;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = getApplicationContext();
    }

    public static Context getContext(){
        return gContext;
    }

    public static User getUser(){
        return gUser;
    }

    public static void setUser(User user){
        gUser = user;
    }
}
