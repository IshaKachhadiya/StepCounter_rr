package com.mysweat.pedocounter.walkerstep.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.utils.RecyclerViewAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;


public class HistoryFragment extends Fragment {
    Db_fun db;
    TextView emptyTxt;
    Context mContext;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    RelativeLayout main_cardview1;
    TextView totalCalorieTxt;
    TextView totalDistanceTxt;
    TextView totalStepsTxt, txt_main_ttl_coin;
    LinearLayout ll_coin_point;
    ArrayList<String> list = new ArrayList<>();
    public ArrayList<String> totalTaken = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_two, viewGroup, false);
        mContext = inflate.getContext();
        recyclerView = (RecyclerView) inflate.findViewById(R.id.list);
        db = new Db_fun(mContext);
        emptyTxt = (TextView) inflate.findViewById(R.id.emptyTxt);
        totalStepsTxt = (TextView) inflate.findViewById(R.id.totalStepsTxt);
        totalCalorieTxt = (TextView) inflate.findViewById(R.id.totalCalorieTxt);
        totalDistanceTxt = (TextView) inflate.findViewById(R.id.totalDistanceTxt);
        txt_main_ttl_coin = (TextView) inflate.findViewById(R.id.txt_main_ttl_coin);
        ll_coin_point = inflate.findViewById(R.id.ll_coin_point);
        main_cardview1 = inflate.findViewById(R.id.main_cardview1);

        int my_coin = ADSharedPref.getInteger(getContext(), ADSharedPref.MCOIN, 0);
        if (my_coin == 0) {
            ll_coin_point.setVisibility(View.VISIBLE);
        } else {
            ll_coin_point.setVisibility(View.GONE);
        }
        card = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.slide_in_right);
        main_cardview1.startAnimation(card);
        getActivity();
        return inflate;
    }

    Animation rv, card;

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> allRecords = db.getAllRecords();
        list = allRecords;
        if (allRecords.size() == 0) {
            emptyTxt.setVisibility(View.VISIBLE);
        } else {
            emptyTxt.setVisibility(View.GONE);
        }
        FragmentActivity activity = getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecyclerViewAdapter(activity, list);
        recyclerView.setAdapter(adapter);
        rv = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.layout_animation_fall_down);
        recyclerView.startAnimation(rv);

        if (Db_fun.totalArr != null) {
            ArrayList<String> arrayList = Db_fun.totalArr;
            totalTaken = arrayList;
            if (arrayList.size() != 0) {
                String[] split = totalTaken.get(0).split(",");
                String str = split[0];
                String str2 = split[1];
                String str3 = split[2];
                totalStepsTxt.setText(str);
                totalCalorieTxt.setText(str2);
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
                totalDistanceTxt.setText(new DecimalFormat("##.##", decimalFormatSymbols).format(Double.parseDouble(str3)));
            }
        }
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        String savedValue = sharedPreferences.getString("coin", "");
        txt_main_ttl_coin.setText(savedValue);
    }
}
