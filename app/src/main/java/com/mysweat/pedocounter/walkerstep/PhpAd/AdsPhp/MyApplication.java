package com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp;

import android.content.Context;


public class MyApplication extends MyApplicationAppOpen {
    public static Context context;
    public static Boolean isCrop, isSavePng;
    NewAppOpenAdManager appOpenManager;

    Class aClass;

    public void setClass(Class cls) {
        this.aClass = cls;
    }

    static {
        isCrop = true;
        isSavePng = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        appOpenManager = new NewAppOpenAdManager(this);
    }

    public static Context getContext() {
        return context;
    }
}
