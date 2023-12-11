package com.mysweat.pedocounter.walkerstep.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PreferenceManager {
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    public PreferenceManager(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BASE_APP", 0);
        this.sp = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public void putString(String str, String str2) {
        this.editor.putString(str, str2);
        this.editor.apply();
    }
    public String getString(String str, String defult) {
        return sp.getString(str,defult);
    }

    public void putStringArray(String str, String[] strArr) {
        this.editor.putString(str, new Gson().toJson(strArr));
        this.editor.commit();
    }

    public String[] getStringArray(String str, String[] strArr) {
        return (String[]) new Gson().fromJson(this.sp.getString(str, new Gson().toJson(strArr)), String[].class);
    }

    public void putWatchvideoArray(String str, String[] strArr) {
        this.editor.putString(str, new Gson().toJson(strArr));
        this.editor.apply();
    }

    public String[] getWatchvideoArray(String str, String[] strArr) {
        return (String[]) new Gson().fromJson(this.sp.getString(str, new Gson().toJson(strArr)), String[].class);
    }

    public void setRandomArray(String str, ArrayList<String> strArr) {
        this.editor.putString(str, new Gson().toJson(strArr));
        this.editor.apply();
    }

    public ArrayList<String> getRandomArray(String str) {
        String serializedObject = sp.getString(str, "");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return  gson.fromJson(serializedObject, type);
    }

    public void saveCheckInDate() {
        putString("LAST_CHECK_IN_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public String getLastedCheckin() {
        return this.sp.getString("LAST_CHECK_IN_DATE", "");
    }

    public void saveEarMoneyDate(String saveEarMoneyDate) {
        putString(saveEarMoneyDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public String getEarMoneyin(String saveEarMoneyDate) {
        return this.sp.getString(saveEarMoneyDate, "");
    }

    public int calculateDaysSinceLastCheckIn(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = simpleDateFormat.parse(str);
            Date parse2 = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(parse2);
            calendar2.set(11, 0);
            calendar2.set(12, 0);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            return (int) ((calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / 86400000);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
