package com.mysweat.pedocounter.walkerstep.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreferences {
    public static String PREFERENCE = "iScreenRecorder";
    public static SharedPreferences sharedPreferences;
    private static MySharePreferences instance;

    public MySharePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
    }

    public static MySharePreferences getInstance(Context ctx) {
        if (instance == null) {
            instance = new MySharePreferences(ctx);
        }
        return instance;
    }

    public static void putString(String key, String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    public static String getString(String key,String defultValue) {
        return sharedPreferences.getString(key, defultValue);
    }

    public static void putInt(String key, Integer val) {
        sharedPreferences.edit().putInt(key, val).apply();
    }

    public static void putBoolean(String key, Boolean val) {
        sharedPreferences.edit().putBoolean(key, val).apply();
    }

    public static boolean getBoolean(String key,boolean defultValue) {
        return sharedPreferences.getBoolean(key, defultValue);
    }

    public static int getInt(String key,Integer defultValue) {
        return sharedPreferences.getInt(key, defultValue);
    }
}