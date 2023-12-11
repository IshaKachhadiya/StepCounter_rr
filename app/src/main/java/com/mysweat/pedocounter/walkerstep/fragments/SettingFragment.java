package com.mysweat.pedocounter.walkerstep.fragments;

import static com.mysweat.pedocounter.walkerstep.ui.SplashActivity.isNetworkAvailable;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mysweat.pedocounter.walkerstep.Database.WithdrawalModel;
import com.mysweat.pedocounter.walkerstep.service.MyCustom_Service;
import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.Database.UserTable;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
import com.mysweat.pedocounter.walkerstep.PhpAd.api.ApiClient;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.utils.offline;
import com.mysweat.pedocounter.walkerstep.ui.SplashActivity;

import java.text.DecimalFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingFragment extends Fragment {
    TextView UnitTxt;
    TextView txt_total_, txt_withdrw_coin, dailyGoalTxt, txt_shareapp, txt_rateus, txt_pp, txt_help, txt_info, txt_total_main_coin;
    Db_fun db;
    int count = 0;
    Context mContext;
    String name;
    RelativeLayout setGoalLay;
    RelativeLayout unitLay, rl_ad;
    LinearLayout ll_meter, ll_main_total, ll_one, ll_other, ll_home;
    String weight;
    public int setted_goal = 0;
    String goal = "";
    String unit = "";
    CheckBox checkBox, checkBox2;
    DecimalFormat decimalFormat;
    String formattedValue;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_setting, viewGroup, false);
        Context context = inflate.getContext();
        this.mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("CurrentProfile", 0);
        this.name = sharedPreferences.getString("name", "0");
        this.weight = sharedPreferences.getString(UserTable.Weight, "0");

        new AdsNativeHelper().shownativeads_small(getActivity(), inflate.findViewById(R.id.Admob_Native_Frame_small), inflate.findViewById(R.id.native_ad_container));

        db = new Db_fun(this.mContext);

        setGoalLay = (RelativeLayout) inflate.findViewById(R.id.setGoalLay);
        dailyGoalTxt = (TextView) inflate.findViewById(R.id.dailyGoalTxt);
        unitLay = (RelativeLayout) inflate.findViewById(R.id.unitLay);
        UnitTxt = (TextView) inflate.findViewById(R.id.UnitTxt);
        ll_meter = inflate.findViewById(R.id.ll_meter);
        checkBox = inflate.findViewById(R.id.kmCheck);
        checkBox2 = inflate.findViewById(R.id.mileCheck);
        txt_shareapp = inflate.findViewById(R.id.txt_shareapp);
        txt_rateus = inflate.findViewById(R.id.txt_rateus);
        txt_help = inflate.findViewById(R.id.txt_help);
        txt_pp = inflate.findViewById(R.id.txt_pp);
        txt_info = inflate.findViewById(R.id.txt_info);
        txt_total_main_coin = inflate.findViewById(R.id.txt_total_main_coin);
        ll_main_total = inflate.findViewById(R.id.ll_main_total);
        ll_one = inflate.findViewById(R.id.ll_one);
        rl_ad = inflate.findViewById(R.id.rl_ad);
        ll_other = inflate.findViewById(R.id.ll_other);
        ll_home = inflate.findViewById(R.id.ll_home);
        txt_withdrw_coin = inflate.findViewById(R.id.txt_withdrw_coin);
        txt_total_ = inflate.findViewById(R.id.txt_total_);

        txt_total_main_coin.setText(String.valueOf(new offline(requireActivity()).getTotalCoins()));
        float maincoinn = Float.parseFloat(txt_total_main_coin.getText().toString()) / 10000;
        decimalFormat = new DecimalFormat("#.##");

        formattedValue = decimalFormat.format(maincoinn);
        txt_total_.setText(formattedValue + "₹");
        int my_coin = ADSharedPref.getInteger(requireContext(), ADSharedPref.MCOIN, 0);

        if (my_coin == 0) {
            ll_main_total.setVisibility(View.VISIBLE);
            ll_one.setVisibility(View.GONE);
            ll_other.setVisibility(View.VISIBLE);
        } else {
            ll_one.setVisibility(View.VISIBLE);
            ll_other.setVisibility(View.VISIBLE);
            ll_main_total.setVisibility(View.GONE);

        }


        txt_withdrw_coin.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(requireActivity());
            dialog.setContentView(R.layout.activity_withdrawl_form);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
            TextView coin = dialog.findViewById(R.id.coin);
            EditText et_name = dialog.findViewById(R.id.name);
            EditText paytm = dialog.findViewById(R.id.paytm);
            TextView txt_dg_total_main_coin = dialog.findViewById(R.id.txt_dg_total_main_coin);
            TextView txt_total = dialog.findViewById(R.id.txt_total_coin);
            RelativeLayout gotoWithdrawal = dialog.findViewById(R.id.gotoWithdrawal);
            txt_dg_total_main_coin.setText(String.valueOf(new offline(requireActivity()).getTotalCoins()));

            float coinn = Float.parseFloat(txt_dg_total_main_coin.getText().toString()) / 10000;
            decimalFormat = new DecimalFormat("#.##");

            formattedValue = decimalFormat.format(coinn);
            txt_total.setText(formattedValue + "₹");

            ((ImageView) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    dialog.dismiss();
                }
            });

            gotoWithdrawal.setOnClickListener(view -> {
                if (et_name.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter Name", Toast.LENGTH_LONG).show();
                } else if (paytm.getText().length() != 10) {
                    Toast.makeText(getContext(), "Please Enter Valid Number", Toast.LENGTH_LONG).show();
                } else if (coin.length() == 0) {
                    Toast.makeText(getContext(), "This field is required", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(coin.getText().toString()) >= 50000) {
                    String totalInputcoin = coin.getText().toString();
                    if (Integer.parseInt(totalInputcoin) <= new offline(requireContext()).getTotalCoins()) {

                        ApiClient.getApi().getWithdrawal(et_name.getText().toString(), paytm.getText().toString(), Integer.parseInt(coin.getText().toString())).enqueue(new Callback<WithdrawalModel>() {
                            @Override
                            public void onResponse(Call<WithdrawalModel> call, Response<WithdrawalModel> response) {
                                showMessageDialog(response.body().getResponse());
                                int TotalAvailablecoin = new offline(requireContext()).getTotalCoins() - Integer.parseInt(coin.getText().toString());
                                offline offlineVar = new offline(requireContext());
                                offlineVar.SaveTotalWithdrawlCoins(TotalAvailablecoin);
                                txt_total_main_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                            }

                            @Override
                            public void onFailure(Call<WithdrawalModel> call, Throwable t) {
                                showMessageDialog(t.getMessage());
                            }
                        });
                    } else {
                        showMessageDialog("UnSufficient Coin....");
                    }
                } else {
                    showMessageDialog("Minimum withdrawal 30,00000 coin");
                }

            });
            dialog.show();
        });

        ll_home.setOnClickListener(v -> {
            ll_meter.setVisibility(View.GONE);
        });

        txt_shareapp.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + requireActivity().getApplicationContext());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        txt_rateus.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())));
        });

        txt_pp.setOnClickListener(v -> {
            if (isNetworkAvailable(requireActivity())) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashActivity.Privacy_police));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SplashActivity.Privacy_police)));
                }
            } else {
                Toast.makeText(getActivity(), "Connect Internet Please...", Toast.LENGTH_SHORT).show();
            }
        });


        txt_info.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(SettingFragment.this.mContext, R.style.Theme_Dialog);
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.dialog_info);
            requireActivity().getWindow().setLayout(-1, -1);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(17170445);

            ((ImageView) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });

        this.setGoalLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingFragment.this.mContext, R.style.Theme_Dialog);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_setgoal);
                requireActivity().getWindow().setLayout(-1, -1);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(17170445);
                final EditText editText = (EditText) dialog.findViewById(R.id.editGoal);
                goal = db.getGoal(SettingFragment.this.name);
                editText.setText(SettingFragment.this.goal);
                ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        dialog.dismiss();
                    }
                });
                ((TextView) dialog.findViewById(R.id.oktxt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        dialog.dismiss();
                        String obj = editText.getText().toString();
                        if (!obj.equals("0") && !obj.equals("") && !obj.contains(".") && !obj.contains("-")) {
                            SettingFragment.this.db.updateGoal(SettingFragment.this.name, obj);
                            SettingFragment.this.dailyGoalTxt.setText(obj);
                            MyCustom_Service.updateGoal = true;
                            SharedPreferences.Editor edit = SettingFragment.this.mContext.getSharedPreferences("Goals", 0).edit();
                            edit.putString(UserTable.Goal, obj);
                            edit.apply();
                            return;
                        }
                        Toast.makeText(SettingFragment.this.mContext, SettingFragment.this.getString(R.string.error_steps), 0).show();
                    }
                });
                dialog.show();
            }
        });
        this.unitLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count % 2 == 0) {
                    ll_meter.setVisibility(View.GONE);
                } else {
                    ll_meter.setVisibility(View.VISIBLE);
                    if (SettingFragment.this.mContext.getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0").equals("Kilometers")) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox2.setChecked(true);
                    }
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            checkBox2.setChecked(false);
                            ll_meter.setVisibility(View.GONE);
                            if (!checkBox.isChecked() && !checkBox2.isChecked()) {
                                Toast.makeText(SettingFragment.this.mContext, SettingFragment.this.getString(R.string.error_unit), 0).show();
                                return;
                            }
                            SharedPreferences.Editor edit = SettingFragment.this.mContext.getSharedPreferences("Goals", 0).edit();
                            if (checkBox.isChecked()) {
                                edit.putString(UserTable.Unit, "Kilometers");
                                edit.apply();
                                UnitTxt.setText(SettingFragment.this.getString(R.string.kilometers));
                                db.updateUnit(SettingFragment.this.name, "Kilometers");
                            }
                            if (checkBox2.isChecked()) {
                                edit.putString(UserTable.Unit, "Miles");
                                edit.apply();
                                UnitTxt.setText(SettingFragment.this.getString(R.string.miles));
                                db.updateUnit(SettingFragment.this.name, "Miles");
                            }
                        }
                    });
                    checkBox2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            checkBox.setChecked(false);
                            ll_meter.setVisibility(View.GONE);
                            if (!checkBox.isChecked() && !checkBox2.isChecked()) {
                                Toast.makeText(SettingFragment.this.mContext, SettingFragment.this.getString(R.string.error_unit), 0).show();
                                return;
                            }
                            SharedPreferences.Editor edit = SettingFragment.this.mContext.getSharedPreferences("Goals", 0).edit();
                            if (checkBox.isChecked()) {
                                edit.putString(UserTable.Unit, "Kilometers");
                                edit.apply();
                                UnitTxt.setText(SettingFragment.this.getString(R.string.kilometers));
                                db.updateUnit(SettingFragment.this.name, "Kilometers");
                            }
                            if (checkBox2.isChecked()) {
                                edit.putString(UserTable.Unit, "Miles");
                                edit.apply();
                                UnitTxt.setText(SettingFragment.this.getString(R.string.miles));
                                db.updateUnit(SettingFragment.this.name, "Miles");
                            }
                        }
                    });
                }
            }
        });

        if (Build.VERSION.SDK_INT > 28) {
            this.txt_help.setVisibility(0);
        }
        txt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingFragment.this.mContext, R.style.Theme_Dialog);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_perm);
                dialog.getWindow().setLayout(-1, -2);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                ((TextView) dialog.findViewById(R.id.txt_opensett)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        dialog.dismiss();
                        SettingFragment.this.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:com.burkapps.pedometer.stepcounter.calorieburner")));
                    }
                });
                ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        goal = this.db.getGoal(name);
        unit = this.db.getUnit(name);
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences("Goals", 0).edit();
        edit.putString(UserTable.Unit, this.unit);
        edit.apply();
        this.setted_goal = Integer.parseInt(this.goal);
        this.dailyGoalTxt.setText(this.goal);
        if (this.unit.equals("Kilometers")) {
            this.UnitTxt.setText(getString(R.string.kilometers));
        } else {
            this.UnitTxt.setText(getString(R.string.miles));
        }
        return inflate;
    }

    private void showMessageDialog(String message) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.activity_ad_screen);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        ((TextView) dialog.findViewById(R.id.cooins)).setText(message);
        ((TextView) dialog.findViewById(R.id.desc)).setVisibility(View.GONE);
        ((ImageView) dialog.findViewById(R.id.cancel)).setOnClickListener(v -> {
            dialog.dismiss();
        });
        ((TextView) dialog.findViewById(R.id.oktxt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}


//package com.mysweat.pedocounter.walkerstep.fragments;
//
//import static com.mysweat.pedocounter.walkerstep.ui.SplashActivity.isNetworkAvailable;
//
//import android.app.Dialog;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//
//import com.google.android.material.textfield.TextInputEditText;
//import com.mysweat.pedocounter.walkerstep.Database.WithdrawalModel;
//import com.mysweat.pedocounter.walkerstep.service.MyCustom_Service;
//import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
//import com.mysweat.pedocounter.walkerstep.Database.UserTable;
//import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
//import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
//import com.mysweat.pedocounter.walkerstep.PhpAd.api.ApiClient;
//import com.mysweat.pedocounter.walkerstep.R;
//import com.mysweat.pedocounter.walkerstep.databinding.FragmentSettingBinding;
//import com.mysweat.pedocounter.walkerstep.utils.offline;
//import com.mysweat.pedocounter.walkerstep.ui.SplashActivity;
//
//import java.util.Objects;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class SettingFragment extends Fragment {
//    Db_fun db;
//    int count = 0;
//    Context mContext;
//    String name;
//    String weight;
//    public int setted_goal = 0;
//    String goal = "";
//    String unit = "";
//    CheckBox checkBox, checkBox2;
//    FragmentSettingBinding binding;
//
//    @Override
//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        binding = FragmentSettingBinding.inflate(layoutInflater, viewGroup, false);
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CurrentProfile", 0);
//        this.name = sharedPreferences.getString("name", "0");
//        this.weight = sharedPreferences.getString(UserTable.Weight, "0");
//
//        new AdsNativeHelper().shownativeads_small(getActivity(), binding.adsLy.AdmobNativeFrameSmall, binding.adsLy.nativeAdContainer);
//
//        db = new Db_fun(this.mContext);
//
//        binding.txtShareapp.setOnClickListener(v -> {
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + requireActivity().getApplicationContext());
//            sendIntent.setType("text/plain");
//            startActivity(sendIntent);
//        });
//
//        binding.txtRateus.setOnClickListener(v -> {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())));
//        });
//
//        binding.txtPp.setOnClickListener(v -> {
//            if (isNetworkAvailable(requireActivity())) {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashActivity.Privacy_police));
//                    startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SplashActivity.Privacy_police)));
//                }
//            } else {
//                Toast.makeText(getActivity(), "Connect Internet Please...", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        binding.txtInfo.setOnClickListener(v -> {
//            final Dialog dialog = new Dialog(SettingFragment.this.mContext, R.style.Theme_Dialog);
//            dialog.requestWindowFeature(1);
//            dialog.setContentView(R.layout.dialog_info);
//            requireActivity().getWindow().setLayout(-1, -1);
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(17170445);
//            ((ImageView) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view2) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        });
//
//        binding.setGoalLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog(SettingFragment.this.mContext, R.style.Theme_Dialog);
//                dialog.requestWindowFeature(1);
//                dialog.setContentView(R.layout.dialog_setgoal);
//                requireActivity().getWindow().setLayout(-1, -1);
//                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(17170445);
//                final EditText editText = (EditText) dialog.findViewById(R.id.editGoal);
//                goal = db.getGoal(SettingFragment.this.name);
//                editText.setText(SettingFragment.this.goal);
//                ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view2) {
//                        dialog.dismiss();
//                    }
//                });
//                ((TextView) dialog.findViewById(R.id.oktxt)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view2) {
//                        dialog.dismiss();
//                        String obj = editText.getText().toString();
//                        if (!obj.equals("0") && !obj.equals("") && !obj.contains(".") && !obj.contains("-")) {
//                            SettingFragment.this.db.updateGoal(SettingFragment.this.name, obj);
//                            binding.dailyGoalTxt.setText(obj);
//                            MyCustom_Service.updateGoal = true;
//                            SharedPreferences.Editor edit = SettingFragment.this.mContext.getSharedPreferences("Goals", 0).edit();
//                            edit.putString(UserTable.Goal, obj);
//                            edit.apply();
//                            return;
//                        }
//                        Toast.makeText(SettingFragment.this.mContext, SettingFragment.this.getString(R.string.error_steps), 0).show();
//                    }
//                });
//                dialog.show();
//            }
//        });
//        binding.unitLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                count++;
//                if (count % 2 == 0) {
//                    binding.llMeter.setVisibility(View.GONE);
//                } else {
//                    binding.llMeter.setVisibility(View.VISIBLE);
//                    if (SettingFragment.this.mContext.getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0").equals("Kilometers")) {
//                        checkBox.setChecked(true);
//                    } else {
//                        checkBox2.setChecked(true);
//                    }
//                    checkBox.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view2) {
//                            checkBox2.setChecked(false);
//                            binding.llMeter.setVisibility(View.GONE);
//                            if (!checkBox.isChecked() && !checkBox2.isChecked()) {
//                                Toast.makeText(SettingFragment.this.mContext, SettingFragment.this.getString(R.string.error_unit), 0).show();
//                                return;
//                            }
//                            SharedPreferences.Editor edit = SettingFragment.this.mContext.getSharedPreferences("Goals", 0).edit();
//                            if (checkBox.isChecked()) {
//                                edit.putString(UserTable.Unit, "Kilometers");
//                                edit.apply();
//                                binding.UnitTxt.setText(SettingFragment.this.getString(R.string.kilometers));
//                                db.updateUnit(SettingFragment.this.name, "Kilometers");
//                            }
//                            if (checkBox2.isChecked()) {
//                                edit.putString(UserTable.Unit, "Miles");
//                                edit.apply();
//                                binding.UnitTxt.setText(SettingFragment.this.getString(R.string.miles));
//                                db.updateUnit(SettingFragment.this.name, "Miles");
//                            }
//                        }
//                    });
//                    checkBox2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view2) {
//                            checkBox.setChecked(false);
//                            binding.llMeter.setVisibility(View.GONE);
//                            if (!checkBox.isChecked() && !checkBox2.isChecked()) {
//                                Toast.makeText(SettingFragment.this.mContext, SettingFragment.this.getString(R.string.error_unit), 0).show();
//                                return;
//                            }
//                            SharedPreferences.Editor edit = SettingFragment.this.mContext.getSharedPreferences("Goals", 0).edit();
//                            if (checkBox.isChecked()) {
//                                edit.putString(UserTable.Unit, "Kilometers");
//                                edit.apply();
//                                binding.UnitTxt.setText(SettingFragment.this.getString(R.string.kilometers));
//                                db.updateUnit(SettingFragment.this.name, "Kilometers");
//                            }
//                            if (checkBox2.isChecked()) {
//                                edit.putString(UserTable.Unit, "Miles");
//                                edit.apply();
//                                binding.UnitTxt.setText(SettingFragment.this.getString(R.string.miles));
//                                db.updateUnit(SettingFragment.this.name, "Miles");
//                            }
//                        }
//                    });
//                }
//            }
//        });
//
//        if (Build.VERSION.SDK_INT > 28) {
//            binding.txtHelp.setVisibility(0);
//        }
//        binding.txtHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog(SettingFragment.this.mContext, R.style.Theme_Dialog);
//                dialog.requestWindowFeature(1);
//                dialog.setContentView(R.layout.dialog_perm);
//                dialog.getWindow().setLayout(-1, -2);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                ((TextView) dialog.findViewById(R.id.txt_opensett)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view2) {
//                        dialog.dismiss();
//                        SettingFragment.this.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:com.burkapps.pedometer.stepcounter.calorieburner")));
//                    }
//                });
//                ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view2) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
//        goal = this.db.getGoal(name);
//        unit = this.db.getUnit(name);
//        SharedPreferences.Editor edit = this.mContext.getSharedPreferences("Goals", 0).edit();
//        edit.putString(UserTable.Unit, this.unit);
//        edit.apply();
//        this.setted_goal = Integer.parseInt(this.goal);
//        binding.dailyGoalTxt.setText(this.goal);
//        if (this.unit.equals("Kilometers")) {
//            binding.UnitTxt.setText(getString(R.string.kilometers));
//        } else {
//            binding.UnitTxt.setText(getString(R.string.miles));
//        }
//        binding.txtTotalMainCoin.setText(String.valueOf(new offline(requireActivity()).getTotalCoins()));
//        int my_coin = ADSharedPref.getInteger(requireContext(), ADSharedPref.MCOIN, 0);
//
//        if (my_coin == 0) {
//            binding.llMainTotal.setVisibility(View.VISIBLE);
//            binding.rlAd.setVisibility(View.GONE);
//            binding.llOne.setVisibility(View.GONE);
//            binding.llOther.setVisibility(View.VISIBLE);
//        } else {
//            binding.llOne.setVisibility(View.VISIBLE);
//            binding.llOther.setVisibility(View.VISIBLE);
//            binding.llMainTotal.setVisibility(View.GONE);
//            binding.rlAd.setVisibility(View.VISIBLE);
//        }
//
//        binding.llHome.setOnClickListener(v -> {
//            binding.llMeter.setVisibility(View.GONE);
//        });
//
//        binding.txtWithdrwCoin.setOnClickListener(v -> {
//            final Dialog dialog = new Dialog(requireActivity());
//            dialog.setContentView(R.layout.activity_act_withdrawl_form);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//            dialog.setCancelable(false);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            dialog.getWindow().setGravity(Gravity.CENTER);
//            TextView coin = dialog.findViewById(R.id.coin);
//            TextInputEditText et_name = dialog.findViewById(R.id.name);
//            TextInputEditText paytm = dialog.findViewById(R.id.paytm);
//            if (name.length() == 0) {
//                Toast.makeText(getContext(),"Please Enter Name",Toast.LENGTH_LONG).show();
//            } else if (paytm.getText().length() != 10) {
//                Toast.makeText(getContext(),"Please Enter Valid Number",Toast.LENGTH_LONG).show();
//            } else {
//                if (Integer.parseInt(coin.getText().toString()) >= 500) {
//                    String totalInputcoin = coin.getText().toString();
//                    if (Integer.parseInt(totalInputcoin) <= new offline(requireContext()).getTotalCoins()) {
//
//                        ApiClient.getApi().getWithdrawal(et_name.getText().toString(), paytm.getText().toString(), Integer.parseInt(coin.getText().toString())).enqueue(new Callback<WithdrawalModel>() {
//                            @Override
//                            public void onResponse(Call<WithdrawalModel> call, Response<WithdrawalModel> response) {
//                                showMessageDialog(response.body().getResponse());
//                                int TotalAvailablecoin = new offline(requireContext()).getTotalCoins() - Integer.parseInt(coin.getText().toString());
//                                offline offlineVar = new offline(requireContext());
//                                offlineVar.SaveTotalWithdrawlCoins(TotalAvailablecoin);
//                                binding.txtTotalMainCoin.setText(String.valueOf(offlineVar.getTotalCoins()));
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<WithdrawalModel> call, Throwable t) {
//                                showMessageDialog(t.getMessage());
//                            }
//                        });
//                    } else {
//                        showMessageDialog("UnSufficient Coin....");
//                    }
//                } else {
//                    showMessageDialog("Minimum withdrawal 50,000 coin");
//                }
//            }
//            dialog.show();
//        });
//
//
//        return binding.getRoot();
//
//    }
//
//
//
//    private void showMessageDialog(String message) {
//        final Dialog dialog = new Dialog(requireContext());
//        dialog.setContentView(R.layout.activity_act_ad_screen);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.setCancelable(false);
//        ((TextView) dialog.findViewById(R.id.cooins)).setText(message);
//        ((TextView) dialog.findViewById(R.id.desc)).setVisibility(View.GONE);
//        ((TextView) dialog.findViewById(R.id.oktxt)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public final void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//}
