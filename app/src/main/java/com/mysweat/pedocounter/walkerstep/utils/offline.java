package com.mysweat.pedocounter.walkerstep.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

public class offline {
     int MathCount;
     int ScratchCount;
     int SpinCount;
     int TotalCoins;
     int stepwithcoin;
    Context context;
    int luckyCount;
    SharedPreferences sharedPreferences;
    public static  MutableLiveData<String> getAllCoin= new MutableLiveData<>();

    public offline(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOCAL_DATABASE", 0);
        TotalCoins = Integer.parseInt(sharedPreferences.getString("TOTALCOINS", "0"));
        stepwithcoin = Integer.parseInt(sharedPreferences.getString("stepwithcoin", "0"));
        SpinCount = Integer.parseInt(this.sharedPreferences.getString("SPINCOUNT", "40"));
        ScratchCount = Integer.parseInt(this.sharedPreferences.getString("SCRATCHCOUNT", "40"));
        luckyCount = Integer.parseInt(this.sharedPreferences.getString("LUCKYCOUNT", "2"));
        MathCount = Integer.parseInt(this.sharedPreferences.getString("MATHCOUNT", "40"));
    }

    public void SaveTotalCoins(int i) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putString("TOTALCOINS", String.valueOf(i + this.TotalCoins));
        edit.putLong("millis", Calendar.getInstance().getTimeInMillis());
        edit.apply();
    }

    public void SaveStepToCoins(int i) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putString("stepwithcoin", String.valueOf(stepwithcoin));
//        edit.putLong("millis", Calendar.getInstance().getTimeInMillis());
        edit.apply();
    }
    public void SaveTotalWithdrawlCoins(int i) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putString("TOTALCOINS", String.valueOf(i));
        edit.putLong("millis", Calendar.getInstance().getTimeInMillis());
        edit.apply();
    }
    public int getTotalCoins() {
        int parseInt = Integer.parseInt(this.sharedPreferences.getString("TOTALCOINS", "0"));
        this.TotalCoins = parseInt;
        return parseInt;
    }
    public void SaveCount(int i, int i2) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        if (i2 == 1) {
            edit.putString("SPINCOUNT", String.valueOf(i));
        } else if (i2 == 2) {
            edit.putString("SCRATCHCOUNT", String.valueOf(i));
        } else if (i2 == 3) {
            edit.putString("LUCKYCOUNT", String.valueOf(i));
        } else if (i2 == 4) {
            edit.putString("MATHCOUNT", String.valueOf(i));
        }
        edit.apply();
    }
}
