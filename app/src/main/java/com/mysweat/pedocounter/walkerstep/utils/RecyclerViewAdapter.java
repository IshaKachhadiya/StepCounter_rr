package com.mysweat.pedocounter.walkerstep.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.R;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Activity activity;
    public ArrayList<String> recordList;

    public boolean isHeader(int i) {
        return i == 0;
    }

    public RecyclerViewAdapter(Activity activity, ArrayList<String> arrayList) {
        this.activity = activity;
        this.recordList = arrayList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCalories;
        TextView mDate;
        TextView mDistance;
        TextView mSteps, coins;
        RelativeLayout rl_my_coin;

        public ViewHolder(View view) {
            super(view);
            mDate = (TextView) view.findViewById(R.id.date);
            mDistance = (TextView) view.findViewById(R.id.distance);
            mCalories = (TextView) view.findViewById(R.id.calories);
            mSteps = (TextView) view.findViewById(R.id.steps);
            coins = (TextView) view.findViewById(R.id.coins);
            rl_my_coin = view.findViewById(R.id.rl_my_coin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.activity).inflate(R.layout.recyclerview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String[] split = this.recordList.get(i).split(",");
        String str = split[0];
        String str2 = split[1];
        String str3 = split[2];
        String str4 = split[3];
        String str5 = split[4];
        viewHolder.mDistance.setText(str);
        viewHolder.mCalories.setText(str2);
        viewHolder.mSteps.setText(str5);
        viewHolder.mDate.setText(str4);
        if (isHeader(i)) {
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        String savedValue = sharedPreferences.getString("coin", "");

        viewHolder.coins.setText(savedValue);

        int my_coin = ADSharedPref.getInteger(activity, ADSharedPref.MCOIN, 0);

        if (my_coin == 0) {
            viewHolder.rl_my_coin.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rl_my_coin.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return this.recordList.size();
    }
}
