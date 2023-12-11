package com.mysweat.pedocounter.walkerstep.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mysweat.pedocounter.walkerstep.Database.AchiveDatabase;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
import com.mysweat.pedocounter.walkerstep.R;


public class BadgesFragment extends Fragment {
    AchiveDatabase db_achieve;
    ImageView dis_eighty_off;
    ImageView dis_eighty_on;
    ImageView dis_fifteen_off;
    ImageView dis_fifteen_on;
    ImageView dis_fourty_off;
    ImageView dis_fourty_on;
    Context mContext;
    TextView dis_pg_txt;
    ProgressBar progressBar, progressBar_dis;
    ImageView dis_six_off;
    ImageView dis_six_on;
    ImageView dis_sixty_off;
    ImageView dis_sixty_on;
    ImageView dis_ten_off;
    ImageView dis_ten_on;
    ImageView dis_thirty_off;
    ImageView dis_thirty_on;
    ImageView dis_three_off;
    ImageView dis_three_on;
    ImageView dis_twenty_off;
    ImageView dis_twenty_on;
    TextView dis_txt_more, txt_more, cal_txt_more;
    LinearLayout ll_more_lay, ll_dis_more_lay, ll_cal_more_lay;
    int count = 0;
    int dis_count = 0;
    int cal_count = 0;

    ImageView eighty_off;
    ImageView eighty_on;
    ImageView fifteen_off;
    ImageView fifteen_on;
    ImageView fourty_off;
    ImageView fourty_on;
    TextView pg_txt;
    ImageView six_off;
    ImageView six_on;
    ImageView sixty_off;
    ImageView sixty_on;
    ImageView ten_off;
    ImageView ten_on;
    ImageView thirty_off;
    ImageView thirty_on;
    ImageView three_off;
    ImageView three_on;
    ImageView twenty_off;
    ImageView twenty_on;

    ImageView cal_eighty_off;
    ImageView cal_eighty_on;
    ImageView cal_fifteen_off;
    ImageView cal_fifteen_on;
    ImageView cal_fourty_off;
    ImageView cal_fourty_on;
    TextView cal_pg_txt;
    ProgressBar cal_progressBar;
    ImageView cal_six_off;
    ImageView cal_six_on;
    ImageView cal_sixty_off;
    ImageView cal_sixty_on;
    ImageView cal_ten_off;
    ImageView cal_ten_on;
    ImageView cal_thirty_off;
    ImageView cal_thirty_on;
    ImageView cal_three_off;
    ImageView cal_three_on;
    ImageView cal_twenty_off;
    ImageView cal_twenty_on;
    ScrollView scroll;

    LinearLayout ll_daily, ll_dis, ll_cal;
    Animation dly, dis, cal;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        char c;
        View inflate = layoutInflater.inflate(R.layout.fragment_dis, viewGroup, false);
        mContext = inflate.getContext();
        db_achieve = new AchiveDatabase(this.mContext);
        new AdsNativeHelper().shownativeads_small(getActivity(), inflate.findViewById(R.id.Admob_Native_Frame_small), inflate.findViewById(R.id.native_ad_container));

        ll_daily = inflate.findViewById(R.id.ll_daily);
        ll_dis = inflate.findViewById(R.id.ll_dis);
        ll_cal = inflate.findViewById(R.id.ll_cal);

        progressBar = (ProgressBar) inflate.findViewById(R.id.progressBar);
        dis_pg_txt = (TextView) inflate.findViewById(R.id.pg_txt);
        dis_three_on = (ImageView) inflate.findViewById(R.id.three_on);
        dis_three_off = (ImageView) inflate.findViewById(R.id.three_off);
        dis_six_on = (ImageView) inflate.findViewById(R.id.six_on);
        dis_six_off = (ImageView) inflate.findViewById(R.id.six_off);
        dis_ten_on = (ImageView) inflate.findViewById(R.id.ten_on);
        dis_ten_off = (ImageView) inflate.findViewById(R.id.ten_off);
        dis_fifteen_on = (ImageView) inflate.findViewById(R.id.fifteen_on);
        dis_fifteen_off = (ImageView) inflate.findViewById(R.id.fifteen_off);
        dis_twenty_on = (ImageView) inflate.findViewById(R.id.twenty_on);
        dis_twenty_off = (ImageView) inflate.findViewById(R.id.twenty_off);
        dis_thirty_on = (ImageView) inflate.findViewById(R.id.thirty_on);
        dis_thirty_off = (ImageView) inflate.findViewById(R.id.thirty_off);
        dis_fourty_on = (ImageView) inflate.findViewById(R.id.fourty_on);
        dis_fourty_off = (ImageView) inflate.findViewById(R.id.fourty_off);
        dis_sixty_on = (ImageView) inflate.findViewById(R.id.sixty_on);
        dis_sixty_off = (ImageView) inflate.findViewById(R.id.sixty_off);
        dis_eighty_on = (ImageView) inflate.findViewById(R.id.eighty_on);
        dis_eighty_off = (ImageView) inflate.findViewById(R.id.eighty_off);
        dis_txt_more = inflate.findViewById(R.id.dis_txt_more);
        txt_more = inflate.findViewById(R.id.txt_more);
        cal_txt_more = inflate.findViewById(R.id.cal_txt_more);
        ll_more_lay = inflate.findViewById(R.id.ll_more_lay);
        ll_dis_more_lay = inflate.findViewById(R.id.ll_dis_more_lay);
        ll_cal_more_lay = inflate.findViewById(R.id.ll_cal_more_lay);
        scroll = inflate.findViewById(R.id.scroll);


        progressBar_dis = (ProgressBar) inflate.findViewById(R.id.dis_progressBar);
        pg_txt = (TextView) inflate.findViewById(R.id.pg_txt);
        three_on = (ImageView) inflate.findViewById(R.id.three_on);
        three_off = (ImageView) inflate.findViewById(R.id.three_off);
        six_on = (ImageView) inflate.findViewById(R.id.six_on);
        six_off = (ImageView) inflate.findViewById(R.id.six_off);
        ten_on = (ImageView) inflate.findViewById(R.id.ten_on);
        ten_off = (ImageView) inflate.findViewById(R.id.ten_off);
        fifteen_on = (ImageView) inflate.findViewById(R.id.fifteen_on);
        fifteen_off = (ImageView) inflate.findViewById(R.id.fifteen_off);
        twenty_on = (ImageView) inflate.findViewById(R.id.twenty_on);
        twenty_off = (ImageView) inflate.findViewById(R.id.twenty_off);
        thirty_on = (ImageView) inflate.findViewById(R.id.thirty_on);
        thirty_off = (ImageView) inflate.findViewById(R.id.thirty_off);
        fourty_on = (ImageView) inflate.findViewById(R.id.fourty_on);
        fourty_off = (ImageView) inflate.findViewById(R.id.fourty_off);
        sixty_on = (ImageView) inflate.findViewById(R.id.sixty_on);
        sixty_off = (ImageView) inflate.findViewById(R.id.sixty_off);
        eighty_on = (ImageView) inflate.findViewById(R.id.eighty_on);
        eighty_off = (ImageView) inflate.findViewById(R.id.eighty_off);

        dly = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.slide_in_right);
        dis = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.badge_slide_in_left);
        cal = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.slide_in_right);

        ll_daily.startAnimation(dly);
        ll_dis.startAnimation(dis);
        ll_cal.startAnimation(cal);

        txt_more.setOnClickListener(v -> {
            count++;
            if (count % 2 == 0) {
                ll_more_lay.setVisibility(View.GONE);
            } else {
                ll_more_lay.setVisibility(View.VISIBLE);
            }
        });

        dis_txt_more.setOnClickListener(v -> {
            dis_count++;
            if (dis_count % 2 == 0) {
                ll_dis_more_lay.setVisibility(View.GONE);
            } else {
                ll_dis_more_lay.setVisibility(View.VISIBLE);
            }
        });

        cal_txt_more.setOnClickListener(v -> {
            cal_count++;
            if (cal_count % 2 == 0) {
                ll_cal_more_lay.setVisibility(View.GONE);
            } else {
                ll_cal_more_lay.setVisibility(View.VISIBLE);
            }
        });
        String userAchievementDistance = this.db_achieve.getUserAchievementDistance(mContext.getSharedPreferences("CurrentProfile", 0).getString("name", "0"));
        switch (userAchievementDistance.hashCode()) {
            case 49:
                if (userAchievementDistance.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (userAchievementDistance.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (userAchievementDistance.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (userAchievementDistance.equals("4")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 53:
                if (userAchievementDistance.equals("5")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 54:
                if (userAchievementDistance.equals("6")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 55:
                if (userAchievementDistance.equals("7")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 56:
                if (userAchievementDistance.equals("8")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 57:
                if (userAchievementDistance.equals("9")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.dis_three_off.setVisibility(View.INVISIBLE);
                this.dis_three_on.setVisibility(View.VISIBLE);
                TextView textView = this.dis_pg_txt;
                textView.setText("1 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(1);
                break;
            case 1:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                TextView textView2 = this.dis_pg_txt;
                textView2.setText("2 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(2);
                break;
            case 2:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                TextView textView3 = this.dis_pg_txt;
                textView3.setText("3 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(3);
                break;
            case 3:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                dis_fifteen_off.setVisibility(View.INVISIBLE);
                dis_fifteen_on.setVisibility(View.VISIBLE);
                TextView textView4 = this.dis_pg_txt;
                textView4.setText("4 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(4);
                break;
            case 4:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                dis_fifteen_off.setVisibility(View.INVISIBLE);
                dis_fifteen_on.setVisibility(View.VISIBLE);
                dis_twenty_off.setVisibility(View.INVISIBLE);
                dis_twenty_on.setVisibility(View.VISIBLE);
                TextView textView5 = this.dis_pg_txt;
                textView5.setText("5 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(5);
                break;
            case 5:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                dis_fifteen_off.setVisibility(View.INVISIBLE);
                dis_fifteen_on.setVisibility(View.VISIBLE);
                dis_twenty_off.setVisibility(View.INVISIBLE);
                dis_twenty_on.setVisibility(View.VISIBLE);
                dis_thirty_off.setVisibility(View.INVISIBLE);
                dis_thirty_on.setVisibility(View.VISIBLE);
                TextView textView6 = this.dis_pg_txt;
                textView6.setText("6 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(6);
                break;
            case 6:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                dis_fifteen_off.setVisibility(View.INVISIBLE);
                dis_fifteen_on.setVisibility(View.VISIBLE);
                dis_twenty_off.setVisibility(View.INVISIBLE);
                dis_twenty_on.setVisibility(View.VISIBLE);
                dis_thirty_off.setVisibility(View.INVISIBLE);
                dis_thirty_on.setVisibility(View.VISIBLE);
                dis_fourty_off.setVisibility(View.INVISIBLE);
                dis_fourty_on.setVisibility(View.VISIBLE);
                TextView textView7 = this.dis_pg_txt;
                textView7.setText("7 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(7);
                break;
            case 7:
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                dis_fifteen_off.setVisibility(View.INVISIBLE);
                dis_fifteen_on.setVisibility(View.VISIBLE);
                dis_twenty_off.setVisibility(View.INVISIBLE);
                dis_twenty_on.setVisibility(View.VISIBLE);
                dis_thirty_off.setVisibility(View.INVISIBLE);
                dis_thirty_on.setVisibility(View.VISIBLE);
                dis_fourty_off.setVisibility(View.INVISIBLE);
                dis_fourty_on.setVisibility(View.VISIBLE);
                dis_sixty_off.setVisibility(View.INVISIBLE);
                dis_sixty_on.setVisibility(View.VISIBLE);
                TextView textView8 = this.dis_pg_txt;
                textView8.setText("8 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(8);
                break;
            case '\b':
                dis_three_off.setVisibility(View.INVISIBLE);
                dis_three_on.setVisibility(View.VISIBLE);
                dis_six_off.setVisibility(View.INVISIBLE);
                dis_six_on.setVisibility(View.VISIBLE);
                dis_ten_off.setVisibility(View.INVISIBLE);
                dis_ten_on.setVisibility(View.VISIBLE);
                dis_fifteen_off.setVisibility(View.INVISIBLE);
                dis_fifteen_on.setVisibility(View.VISIBLE);
                dis_twenty_off.setVisibility(View.INVISIBLE);
                dis_twenty_on.setVisibility(View.VISIBLE);
                dis_thirty_off.setVisibility(View.INVISIBLE);
                dis_thirty_on.setVisibility(View.VISIBLE);
                dis_fourty_off.setVisibility(View.INVISIBLE);
                dis_fourty_on.setVisibility(View.VISIBLE);
                dis_sixty_off.setVisibility(View.INVISIBLE);
                dis_sixty_on.setVisibility(View.VISIBLE);
                dis_eighty_off.setVisibility(View.INVISIBLE);
                dis_eighty_on.setVisibility(View.VISIBLE);
                dis_pg_txt.setText(getString(R.string.all_achieve_completed));
                progressBar.setProgress(9);
                break;
        }


        String userAchievementSteps = this.db_achieve.getUserAchievementSteps(this.mContext.getSharedPreferences("CurrentProfile", 0).getString("name", "0"));
        switch (userAchievementSteps.hashCode()) {
            case 49:
                if (userAchievementSteps.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (userAchievementSteps.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (userAchievementSteps.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (userAchievementSteps.equals("4")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 53:
                if (userAchievementSteps.equals("5")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 54:
                if (userAchievementSteps.equals("6")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 55:
                if (userAchievementSteps.equals("7")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 56:
                if (userAchievementSteps.equals("8")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 57:
                if (userAchievementSteps.equals("9")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.three_off.setVisibility(View.INVISIBLE);
                this.three_on.setVisibility(View.VISIBLE);
                TextView textView = this.pg_txt;
                textView.setText("1 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(1);
                break;
            case 1:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                TextView textView2 = this.pg_txt;
                textView2.setText("2 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(2);
                break;
            case 2:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                TextView textView3 = this.pg_txt;
                textView3.setText("3 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(3);
                break;
            case 3:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                fifteen_off.setVisibility(View.INVISIBLE);
                fifteen_on.setVisibility(View.VISIBLE);
                TextView textView4 = this.pg_txt;
                textView4.setText("4 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(4);
                break;
            case 4:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                fifteen_off.setVisibility(View.INVISIBLE);
                fifteen_on.setVisibility(View.VISIBLE);
                twenty_off.setVisibility(View.INVISIBLE);
                twenty_on.setVisibility(View.VISIBLE);
                TextView textView5 = this.pg_txt;
                textView5.setText("5 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(5);
                break;
            case 5:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                fifteen_off.setVisibility(View.INVISIBLE);
                fifteen_on.setVisibility(View.VISIBLE);
                twenty_off.setVisibility(View.INVISIBLE);
                twenty_on.setVisibility(View.VISIBLE);
                thirty_off.setVisibility(View.INVISIBLE);
                thirty_on.setVisibility(View.VISIBLE);
                TextView textView6 = this.pg_txt;
                textView6.setText("6 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(6);
                break;
            case 6:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                fifteen_off.setVisibility(View.INVISIBLE);
                fifteen_on.setVisibility(View.VISIBLE);
                twenty_off.setVisibility(View.INVISIBLE);
                twenty_on.setVisibility(View.VISIBLE);
                thirty_off.setVisibility(View.INVISIBLE);
                thirty_on.setVisibility(View.VISIBLE);
                fourty_off.setVisibility(View.INVISIBLE);
                fourty_on.setVisibility(View.VISIBLE);
                TextView textView7 = this.pg_txt;
                textView7.setText("7 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(7);
                break;
            case 7:
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                fifteen_off.setVisibility(View.INVISIBLE);
                fifteen_on.setVisibility(View.VISIBLE);
                twenty_off.setVisibility(View.INVISIBLE);
                twenty_on.setVisibility(View.VISIBLE);
                thirty_off.setVisibility(View.INVISIBLE);
                thirty_on.setVisibility(View.VISIBLE);
                fourty_off.setVisibility(View.INVISIBLE);
                fourty_on.setVisibility(View.VISIBLE);
                sixty_off.setVisibility(View.INVISIBLE);
                sixty_on.setVisibility(View.VISIBLE);
                TextView textView8 = this.pg_txt;
                textView8.setText("8 " + getString(R.string.achieve_completed));
                this.progressBar.setProgress(8);
                break;
            case '\b':
                three_off.setVisibility(View.INVISIBLE);
                three_on.setVisibility(View.VISIBLE);
                six_off.setVisibility(View.INVISIBLE);
                six_on.setVisibility(View.VISIBLE);
                ten_off.setVisibility(View.INVISIBLE);
                ten_on.setVisibility(View.VISIBLE);
                fifteen_off.setVisibility(View.INVISIBLE);
                fifteen_on.setVisibility(View.VISIBLE);
                twenty_off.setVisibility(View.INVISIBLE);
                twenty_on.setVisibility(View.VISIBLE);
                thirty_off.setVisibility(View.INVISIBLE);
                thirty_on.setVisibility(View.VISIBLE);
                fourty_off.setVisibility(View.INVISIBLE);
                fourty_on.setVisibility(View.VISIBLE);
                sixty_off.setVisibility(View.INVISIBLE);
                sixty_on.setVisibility(View.VISIBLE);
                eighty_off.setVisibility(View.INVISIBLE);
                eighty_on.setVisibility(View.VISIBLE);
                pg_txt.setText(getString(R.string.all_achieve_completed));
                progressBar.setProgress(9);
                break;
        }


        cal_progressBar = (ProgressBar) inflate.findViewById(R.id.cal_progressBar);
        cal_pg_txt = (TextView) inflate.findViewById(R.id.cal_pg_txt);
        cal_three_on = (ImageView) inflate.findViewById(R.id.cal_three_on);
        cal_three_off = (ImageView) inflate.findViewById(R.id.cal_three_off);
        cal_six_on = (ImageView) inflate.findViewById(R.id.cal_six_on);
        cal_six_off = (ImageView) inflate.findViewById(R.id.cal_six_off);
        cal_ten_on = (ImageView) inflate.findViewById(R.id.cal_ten_on);
        cal_ten_off = (ImageView) inflate.findViewById(R.id.cal_ten_off);
        cal_fifteen_on = (ImageView) inflate.findViewById(R.id.cal_fifteen_on);
        cal_fifteen_off = (ImageView) inflate.findViewById(R.id.cal_fifteen_off);
        cal_twenty_on = (ImageView) inflate.findViewById(R.id.cal_twenty_on);
        cal_twenty_off = (ImageView) inflate.findViewById(R.id.cal_twenty_off);
        cal_thirty_on = (ImageView) inflate.findViewById(R.id.cal_thirty_on);
        cal_thirty_off = (ImageView) inflate.findViewById(R.id.cal_thirty_off);
        cal_fourty_on = (ImageView) inflate.findViewById(R.id.cal_fourty_on);
        cal_fourty_off = (ImageView) inflate.findViewById(R.id.cal_fourty_off);
        cal_sixty_on = (ImageView) inflate.findViewById(R.id.cal_sixty_on);
        cal_sixty_off = (ImageView) inflate.findViewById(R.id.cal_sixty_off);
        cal_eighty_on = (ImageView) inflate.findViewById(R.id.cal_eighty_on);
        cal_eighty_off = (ImageView) inflate.findViewById(R.id.cal_eighty_off);
        String userAchievementCalories = this.db_achieve.getUserAchievementCalories(this.mContext.getSharedPreferences("CurrentProfile", 0).getString("name", "0"));
        switch (userAchievementCalories.hashCode()) {
            case 49:
                if (userAchievementCalories.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (userAchievementCalories.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (userAchievementCalories.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (userAchievementCalories.equals("4")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 53:
                if (userAchievementCalories.equals("5")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 54:
                if (userAchievementCalories.equals("6")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 55:
                if (userAchievementCalories.equals("7")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 56:
                if (userAchievementCalories.equals("8")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 57:
                if (userAchievementCalories.equals("9")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                TextView textView = this.cal_pg_txt;
                textView.setText("1 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(1);
                break;
            case 1:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                TextView textView2 = this.cal_pg_txt;
                textView2.setText("2 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(2);
                break;
            case 2:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                TextView textView3 = this.cal_pg_txt;
                textView3.setText("3 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(3);
                break;
            case 3:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                cal_fifteen_off.setVisibility(View.INVISIBLE);
                cal_fifteen_on.setVisibility(View.VISIBLE);
                TextView textView4 = this.cal_pg_txt;
                textView4.setText("4 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(4);
                break;
            case 4:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                cal_fifteen_off.setVisibility(View.INVISIBLE);
                cal_fifteen_on.setVisibility(View.VISIBLE);
                cal_twenty_off.setVisibility(View.INVISIBLE);
                cal_twenty_on.setVisibility(View.VISIBLE);
                TextView textView5 = this.cal_pg_txt;
                textView5.setText("5 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(5);
                break;
            case 5:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                cal_fifteen_off.setVisibility(View.INVISIBLE);
                cal_fifteen_on.setVisibility(View.VISIBLE);
                cal_twenty_off.setVisibility(View.INVISIBLE);
                cal_twenty_on.setVisibility(View.VISIBLE);
                cal_thirty_off.setVisibility(View.INVISIBLE);
                cal_thirty_on.setVisibility(View.VISIBLE);
                TextView textView6 = this.cal_pg_txt;
                textView6.setText("6 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(6);
                break;
            case 6:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                cal_fifteen_off.setVisibility(View.INVISIBLE);
                cal_fifteen_on.setVisibility(View.VISIBLE);
                cal_twenty_off.setVisibility(View.INVISIBLE);
                cal_twenty_on.setVisibility(View.VISIBLE);
                cal_thirty_off.setVisibility(View.INVISIBLE);
                cal_thirty_on.setVisibility(View.VISIBLE);
                cal_fourty_off.setVisibility(View.INVISIBLE);
                cal_fourty_on.setVisibility(View.VISIBLE);
                TextView textView7 = this.cal_pg_txt;
                textView7.setText("7 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(7);
                break;
            case 7:
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                cal_fifteen_off.setVisibility(View.INVISIBLE);
                cal_fifteen_on.setVisibility(View.VISIBLE);
                cal_twenty_off.setVisibility(View.INVISIBLE);
                cal_twenty_on.setVisibility(View.VISIBLE);
                cal_thirty_off.setVisibility(View.INVISIBLE);
                cal_thirty_on.setVisibility(View.VISIBLE);
                cal_fourty_off.setVisibility(View.INVISIBLE);
                cal_fourty_on.setVisibility(View.VISIBLE);
                cal_sixty_off.setVisibility(View.INVISIBLE);
                cal_sixty_on.setVisibility(View.VISIBLE);
                TextView textView8 = this.cal_pg_txt;
                textView8.setText("8 " + getString(R.string.achieve_completed));
                this.cal_progressBar.setProgress(8);
                break;
            case '\b':
                cal_three_off.setVisibility(View.INVISIBLE);
                cal_three_on.setVisibility(View.VISIBLE);
                cal_six_off.setVisibility(View.INVISIBLE);
                cal_six_on.setVisibility(View.VISIBLE);
                cal_ten_off.setVisibility(View.INVISIBLE);
                cal_ten_on.setVisibility(View.VISIBLE);
                cal_fifteen_off.setVisibility(View.INVISIBLE);
                cal_fifteen_on.setVisibility(View.VISIBLE);
                cal_twenty_off.setVisibility(View.INVISIBLE);
                cal_twenty_on.setVisibility(View.VISIBLE);
                cal_thirty_off.setVisibility(View.INVISIBLE);
                cal_thirty_on.setVisibility(View.VISIBLE);
                cal_fourty_off.setVisibility(View.INVISIBLE);
                cal_fourty_on.setVisibility(View.VISIBLE);
                cal_sixty_off.setVisibility(View.INVISIBLE);
                cal_sixty_on.setVisibility(View.VISIBLE);
                cal_eighty_off.setVisibility(View.INVISIBLE);
                cal_eighty_on.setVisibility(View.VISIBLE);
                cal_pg_txt.setText(getString(R.string.all_achieve_completed));
                cal_progressBar.setProgress(9);
                break;
        }
//        this.backImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentAchievement fragmentAchievement = new FragmentAchievement();
//                FragmentTransaction beginTransaction = FragmentDistance.this.getActivity().getSupportFragmentManager().beginTransaction();
//                beginTransaction.replace(R.id.content, fragmentAchievement);
//                beginTransaction.commit();
//            }
//        });
        return inflate;
    }
}
