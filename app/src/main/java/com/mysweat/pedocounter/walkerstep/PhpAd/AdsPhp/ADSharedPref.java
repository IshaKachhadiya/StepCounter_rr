package com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp;

import android.content.Context;
import android.content.SharedPreferences;

public class ADSharedPref {
    public static final String PREF_NAME = "PREF_PROFILE";
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String CLICK = "CLICK";
    public static final String Privacy_police = "Privacy_police";
    public static final String unity_intersitial = "AD_INTER";
    public static final String FBAD_INTER = "FBAD_INTER";

    public static final String admob_interstital = "admob_interstital";
    public static final String REWARd = "rewarded";
    public static final String MCOIN = "mcoin";
    public static final String NATIVEAPP = "nativepp";
    public static final String FBAD_NATIVE = "FBAD_NATIVE";
    public static final String admob_native = "admob_native";

    public static final String unity_adid = "unity_adid";

    public static String openads = "openads";

    public static final String BACK = "AD_BACK";

    public static final String APP_STATUS = "APP_STATUS";
    public static final String APP_LINK = "APP_LINK";
    public static final String AppOpen = "APP_OPEN";
    public static final String SPLASH_AD = "SPLASH_AD";
    public static final String VC = "VC";
    public static String appopen_s = "appopn_s";

    public static void setInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int getInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String getString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }


}

